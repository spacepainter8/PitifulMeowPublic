package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.ac.bg.etf.pp1.ast.SyntaxNode;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticPass extends VisitorAdaptor {

	Obj currentMethod = null;
	String currentNamespace = null;

	Struct currentType = null;
	Struct constType = null;
	Struct methodType = null;

	int fparNum = 0;
	boolean returnFound = false;
	
	
//	boolean wrongMulFactorType = false;
//	boolean multipleFactors = false;
//	boolean wrongAddFactorType = false;
//	boolean multipleAdds = false;
//	int exprNum = 0;
//	//int termNum = 0;
//	int exprErrorNum = 0;
//	boolean errorReported = false;
//	int bracketExpr = 0;
//	Map<ExprStart, Integer> termNums = new LinkedHashMap<ExprStart, Integer>();
//	Map<ExprStart, ExprStart> childParent = new LinkedHashMap<ExprStart, ExprStart>();
//	ExprStart currentExpr = null;
//	
	
	int actParsCount = 0;
	Stack<List<Struct>> actParsList = new Stack<>();
	
	Stack<List<Obj>> desList = new Stack<>();
	
	int forCounter = 0;
	
	int nVars = 0;
	boolean errorDetected = false;
	
	

	Logger log = Logger.getLogger(getClass());
	int localVarCount = 0; 
	int globalVarCount = 0; 
	
	

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0 : info.getLine();
		if (line != 0)
			msg.append(" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	  public boolean passed(){
	    	return !errorDetected;
	    }

	
	
	public String visitObjNode(Obj objToVisit) {
		//output.append("[");
		
		StringBuilder output = new StringBuilder();
		
		switch (objToVisit.getKind()) {
		case Obj.Con:  output.append("Con "); break;
		case Obj.Var:  output.append("Var "); break;
		case Obj.Type: output.append("Type "); break;
		case Obj.Meth: output.append("Meth "); break;
		case Obj.Fld:  output.append("Fld "); break;
		case Obj.Prog: output.append("Prog "); break;
		}
		
		output.append(objToVisit.getName());
		output.append(": ");
		
//		if ((Obj.Var == objToVisit.getKind()) && "this".equalsIgnoreCase(objToVisit.getName()))
//			output.append("");
//		else
//			objToVisit.getType().accept(this);
//		
		
		switch (objToVisit.getType().getKind()) {
		case Struct.None:
			output.append("notype");
			break;
		case Struct.Int:
			output.append("int");
			break;
		case Struct.Char:
			output.append("char");
			break;
		case Struct.Array:
			output.append("Arr of ");
			
			switch (objToVisit.getType().getElemType().getKind()) {
			case Struct.None:
				output.append("notype");
				break;
			case Struct.Int:
				output.append("int");
				break;
			case Struct.Char:
				output.append("char");
				break;
			case Struct.Class:
				output.append("Class");
				break;
			}
			break;
		
		}
		
		
		output.append(", ");
		output.append(objToVisit.getAdr());
		output.append(", ");
		output.append(objToVisit.getLevel() + " ");
//				
//		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) {
//			output.append("\n");
//			nextIndentationLevel();
//		}
//		
//
//		for (Obj o : objToVisit.getLocalSymbols()) {
//			output.append(currentIndent.toString());
//			o.accept(this);
//			output.append("\n");
//		}
//		
//		if (objToVisit.getKind() == Obj.Prog || objToVisit.getKind() == Obj.Meth) 
//			previousIndentationLevel();
		
		//output.append("\n");

		//output.append("]");
		return output.toString();
		
	}

	
	public void visit(ProgName progName) {
		progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
		Tab.openScope();
	}

	public void visit(Program program) {
    	nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgName().obj);
		Obj node = Tab.find("main");
		// check if main method was declared
		
		if (node == Tab.noObj || node.getKind() != Obj.Meth || node.getType() != Tab.noType
				|| node.getLevel() != 0) {
			report_error("Nije pronadjena odgovarajuca main metoda",null);
		} else Tab.find("main").setFpPos(8);
	// setting this in else so I know when i come across the main method	
		
		Tab.closeScope();
		// print out stuff
		report_info("\n", null);
		report_info("Deklarisano je "+localVarCount+" lokalnih promenljivih",null);
		if (localVarCount > 256) report_info("Dozvoljeno je maksimalno 256 lokalnih promenljivih",null);
		report_info("Deklarisano je "+globalVarCount+" globalnih promenljivih",null);
		if (globalVarCount > 256) report_info("Dozvoljeno je maksimalno 65536 globalnih promenljivih",null);

	}
	

	public void visit(StandaloneType standaloneType) {
		Obj type = Tab.find(standaloneType.getType());
		// check if type is in symbol table
		if (type == Tab.noObj) {
			report_error("Nije pronadjen tip " + standaloneType.getType() + " u tabeli simbola! ", null);
			// type.struct = Tab.noType;
			standaloneType.struct = Tab.noType;
		} else {
			// check if the fetched node is of Type kind
			if (type.getKind() == Obj.Type) {
				standaloneType.struct = type.getType();
			} else {
				report_error("Greska: Ime " + standaloneType.getType() + " ne predstavlja tip!", standaloneType);
				standaloneType.struct = Tab.noType;
			}
		}

		currentType = standaloneType.struct;
	}

	public void visit(InnerType innerType) {
		String typeName = innerType.getOuterType() + "::" + innerType.getInnerType();
		Obj type = Tab.find(typeName);
		if (type == Tab.noObj) {
			report_error("Nije pronadjen tip " + typeName + " u tabeli simbola! ", null);
			// type.struct = Tab.noType;
			innerType.struct = Tab.noType;
		} else {
			if (type.getKind() == Obj.Type) {
				innerType.struct = type.getType();
			} else {
				report_error("Greska: Ime " + typeName + " ne predstavlja tip!", innerType);
				innerType.struct = Tab.noType;
			}
		}

		currentType = innerType.struct;
	}

	public void visit(NumberConst numberConst) {
		// save const type
		constType = Tab.intType;
		// save value
		numberConst.obj = new Obj(0, "test", constType);
		numberConst.obj.setAdr(numberConst.getVal());

	}

	public void visit(CharConst charConst) {
		// save const type into its struct
		constType = Tab.charType;
		// save value
		charConst.obj = new Obj(0, "test", constType);
		charConst.obj.setAdr(charConst.getVal());
	}

	public void visit(BoolConst boolConst) {
		// save const type into its struct
		constType = TabExt.boolType;
		boolConst.obj = new Obj(0, "test", constType);
		// save value
		if (boolConst.getVal() == false) {
			boolConst.obj.setAdr(0);
		} else {
			boolConst.obj.setAdr(1);
		}
	}

	public void visit(SingleConst singleConst) {
		// check if symbol already in symbol table
		Obj tabSymbol = Tab.find(singleConst.getConstName());
		if (tabSymbol != Tab.noObj) {
			// symbol already in table
			report_error(singleConst.getConstName() + " se vec nalazi u tabeli simbola", singleConst);
			return;
		} else {
			// symbol not in table
			// check if the type from ConstType is equivalent currentType
			if (!constType.equals(currentType)) {
				// types aren't equivalant

				report_error("Tip konstante " + singleConst.getConstName() + " nije ekvivalentan sa navedenim tipom",
						singleConst);
				return;
			} else {
				// if all is well add into symbol table
				if (currentNamespace != null)
					singleConst.setConstName(currentNamespace + "::" + singleConst.getConstName());
				
				Obj node = Tab.insert(Obj.Con, singleConst.getConstName(), constType);
				// set adr to value
				node.setAdr(singleConst.getConstType().obj.getAdr());

			}
		}

	}

	public void visit(MultipleConsts multipleConsts) {
		// check if symbol already in symbol table
		Obj tabSymbol = Tab.find(multipleConsts.getConstName());
		if (tabSymbol != Tab.noObj) {
			// symbol already in table
			report_error(multipleConsts.getConstName() + " se vec nalazi u tabeli simbola", multipleConsts);
			return;
		} else {
			// symbol not in table
			// check if the type from ConstType is equivalent currentType
			if (!constType.equals(currentType)) {
				// types aren't equivalant
				report_error("Tip konstante " + multipleConsts.getConstName() + " nije ekvivalentan sa navedenim tipom",
						multipleConsts);
				return;
			} else {
				// if all is well add into symbol table
				if (currentNamespace != null)
					multipleConsts.setConstName(currentNamespace + "::" + multipleConsts.getConstName());

				Obj node = Tab.insert(Obj.Con, multipleConsts.getConstName(), constType);
				// set adr to value
				node.setAdr(multipleConsts.getConstType().obj.getAdr());

			}
		}

	}

	public void visit(ConstDeclEnd constDeclEnd) {
		currentType = null;
		constType = null;
	}

	public void visit(SimpleProgramVar simpleProgramVar) {
		// check the tables for multiple declaration
		// insert into table
		Obj node = Tab.currentScope.findSymbol(simpleProgramVar.getVarName());
		if (currentNamespace != null)
			simpleProgramVar.setVarName(currentNamespace + "::" + simpleProgramVar.getVarName());

		if (node != null) {
			// object of same name in same scope -> error, bad!
			report_error("Promenljiva sa imenom " + simpleProgramVar.getVarName() + " vec postoji u ovom scope-u; greska",
					simpleProgramVar);
			return;
		} else {
			// need this to sort out references
			Struct varType = currentType;

			Tab.insert(Obj.Var, simpleProgramVar.getVarName(), varType);
			globalVarCount++;
		}
	}
	
	public void visit(SimpleVar simpleVar) {
		// check the tables for multiple declaration
		// insert into table
		Obj node = Tab.currentScope.findSymbol(simpleVar.getVarName());
		if (currentNamespace != null && currentMethod == null)
			simpleVar.setVarName(currentNamespace + "::" + simpleVar.getVarName());

		if (node != null) {
			// object of same name in same scope -> error, bad!
			report_error("Promenljiva sa imenom " + simpleVar.getVarName() + " vec postoji u ovom scope-u; greska",
					simpleVar);
			return;
		} else {
			// need this to sort out references
			Struct varType = currentType;

			Tab.insert(Obj.Var, simpleVar.getVarName(), varType);
			if (currentMethod == null) globalVarCount++;
			else localVarCount++;
		}
	}

	public void visit(ArrayProgramVar arrayProgramVar) {
		// check the tables for multiple declaration
		// insert into table
		Obj node = Tab.currentScope.findSymbol(arrayProgramVar.getVarName());
		if (currentNamespace != null)
			arrayProgramVar.setVarName(currentNamespace + "::" + arrayProgramVar.getVarName());

		if (node != null) {
			// object of same name in same scope -> error, bad!
			report_error("Objekat sa imenom " + arrayProgramVar.getVarName() + " vec postoji u ovom scope-u; greska",
					arrayProgramVar);
			return;
		} else {
			// need this to sort out references
			Struct varType = currentType;
			// need to create Struct node first
			Struct arrType = new Struct(Struct.Array, varType);
			Tab.insert(Obj.Var, arrayProgramVar.getVarName(), arrType);
			globalVarCount++;
		}

	}
	
	public void visit(ArrayVar arrayVar) {
		// check the tables for multiple declaration
		// insert into table
		Obj node = Tab.currentScope.findSymbol(arrayVar.getVarName());
		if (currentNamespace != null && currentMethod == null)
			arrayVar.setVarName(currentNamespace + "::" + arrayVar.getVarName());

		if (node != null) {
			// object of same name in same scope -> error, bad!
			report_error("Objekat sa imenom " + arrayVar.getVarName() + " vec postoji u ovom scope-u; greska",
					arrayVar);
			return;
		} else {
			// need this to sort out references
			Struct varType = currentType;
			// need to create Struct node first
			Struct arrType = new Struct(Struct.Array, varType);
			
			Tab.insert(Obj.Var, arrayVar.getVarName(), arrType);
			if (currentMethod == null) globalVarCount++;
			else localVarCount++;
		}

	}

	public void visit(ProgramVarDeclEnd programVarDeclEnd) {
		currentType = null;
	}
	
	public void visit(VarDecl varDecl) {
		currentType = null;
	}

	public void visit(MethodTypeName methodTypeName) {
		// keep in my mind that you need to set level to num of pars

		// check for multiple declaration
		Obj node = Tab.currentScope.findSymbol(methodTypeName.getName());
		if (node != null) {
			currentMethod = null;
			report_error("Metoda sa imenom " + methodTypeName.getName() + " vec definisana u ovom scope-u; greska ",
					methodTypeName);
			methodTypeName.obj = Tab.noObj;
			Tab.openScope();
			return;
		}
		// add method to symbol table
		Struct methodType = currentType;
		if (currentNamespace != null)
			methodTypeName.setName(currentNamespace + "::" + methodTypeName.getName());

		node = Tab.insert(Obj.Meth, methodTypeName.getName(), methodType);
		// set currentMethod
		currentMethod = node;
		methodTypeName.obj = node;
		// open scope
		Tab.openScope();

	}

	public void visit(MethodTypeType methodTypeType) {
		currentType = methodTypeType.getType().struct;
		methodType = methodTypeType.getType().struct;
	}

	public void visit(MethodTypeVoid methodTypeVoid) {
		currentType = Tab.noType;
		methodType = Tab.noType;
		
	}

	public void visit(MethodDeclNoFormPars methodDeclNFP) {
		// check if there was return -> report error if there wasnt return and its a non-void function
		// chain symbols
		// close scope
		// set currentMethod and currentType to null
		

		
		
//		if (methodType!=Tab.noType && returnFound==false) {
//			report_error("Nije pronadjen return iskaz za funkciju " + methodDeclNFP.getMethodTypeName().getName(), methodDeclNFP);
//		}
		methodDeclNFP.getMethodTypeName().obj.setLevel(fparNum);
		Tab.chainLocalSymbols(methodDeclNFP.getMethodTypeName().obj);
		Tab.closeScope();
		returnFound = false;
		currentMethod = null;
		currentType = null;
		methodType = null;
		fparNum = 0;
	}

	public void visit(MethodDeclFormPars methodDeclFP) {
		// check if there was return -> report error if there wasnt return and its a non-void function
		
		// chain symbols
		// close scope
		// set currentMethod and currentType to null
		
		
		
//		if (methodType!=Tab.noType && returnFound==false) {
//			report_error("Nije pronadjen return iskaz za funkciju " + methodDeclFP.getMethodTypeName().getName(), methodDeclFP);
//		}
		returnFound = false;
		methodDeclFP.getMethodTypeName().obj.setLevel(fparNum);
		Tab.chainLocalSymbols(methodDeclFP.getMethodTypeName().obj);
		Tab.closeScope();
		currentMethod = null;
		currentType = null;
		methodType = null;
		fparNum = 0;
	}
	
	public void visit(NamespaceName namespaceName) {
		currentNamespace = namespaceName.getName();
	}
	
	public void visit(Namespace namespace) {
		currentNamespace = null;
	}
	
	public void visit(FormParIdent fparId) {
		// check for the same name
		// add into table
		// increment fparNum
		Obj node = Tab.currentScope.findSymbol(fparId.getFparName());
		if (node != null) {
			report_error("Promenljiva sa imenom " + fparId.getFparName() + " vec definisana u ovom scope-u; greska ",
					fparId);
			currentType = null;
			return;
		}
		Struct type = currentType;
		Tab.insert(Obj.Var,fparId.getFparName(), type);
		fparNum++;
		localVarCount++;
	}
	
	public void visit(FormParArray fparArr) {
		Obj node = Tab.currentScope.findSymbol(fparArr.getFparName());
		if (node != null) {
			report_error("Promenljiva sa imenom " + fparArr.getFparName() + " vec definisana u ovom scope-u; greska ",
					fparArr);
			currentType = null;
			return;
		}

		
		Struct fparType = currentType;
		Struct arrType = new Struct(Struct.Array, fparType);
		Tab.insert(Obj.Var, fparArr.getFparName(), arrType);
		localVarCount++;
		
		fparNum++;
	}
	
	public void visit(ReturnStatement returnS) {
		returnFound = true;
		if (currentMethod == null) {
			report_error("Return iskaz se moze naci samo u metodi",returnS);
			return;
		}
		if(!methodType.equals(Tab.noType)) {
			report_error("Return bez izraza se moze naci samo u void funkciji; greska",returnS);
			return;
		}
	}
	
	public void visit(ReturnExprStatement returnES) {
		returnFound = true;
		if (currentMethod == null) {
			report_error("Return iskaz se moze naci samo u metodi",returnES);
			return;
		}
		if (methodType.equals(Tab.noType)) {
			report_error("Iskoriscen return sa iskazom u void funkciji; greska",returnES);
			return;
		}
		if(!returnES.getExpr().struct.equals(methodType)) {
			report_error("Neekvivalentni izraz iz return iskaza i povratna vrednost funkcije" ,returnES);
			return;
		}
	}
	
	public void visit(FactorNumber factNum) {
		factNum.struct = Tab.intType;
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
		
	}
	
	public void visit(FactorChar factChar) {
		factChar.struct = Tab.charType;
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
	}
	
	public void visit(FactorBool factBool) {
		factBool.struct = TabExt.boolType;
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
	}
	
//	public void visit(Multiplication multipl) {
//		
////		if(multipl.getFactor().struct != Tab.intType) {
////			multipl.struct = multipl.getFactor().struct;
////			//exprErrorNum++;
////		} else multipl.struct = Tab.intType;
//		
//		multipl.struct = multipl.getFactor().struct;
//		
//		
//	}
//	
//	public void visit(ExprMultiplication exprMul) {
//
////		if (exprMul.getFactor().struct != Tab.intType) {
////			exprMul.struct = exprMul.getFactor().struct;
////			exprErrorNum++;
////		} else exprMul.struct = Tab.intType;
//		
//		
//		
//	}
	
	public void visit(SingleFactor factor) {
		factor.struct = factor.getFactor().struct;
	}
	
	public void visit(MultipleFactors mulFacts) {
		if (!mulFacts.getFactor().struct.equals(Tab.intType) || !mulFacts.getTerm().struct.equals(Tab.intType)) {
			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama",mulFacts);
			mulFacts.struct = Tab.noType;
		} else mulFacts.struct = mulFacts.getFactor().struct;
	}
	
//	public void visit(Addition add) {
////		multipleAdds = true;
////		if(add.getTerm().struct != Tab.intType ) {
////			
////			//report_error("Iskoriscena vrednost neintovskog tipa pri operacijama ", add);
////			wrongAddFactorType = true;
////			add.struct = Tab.noType;
////		} else add.struct = Tab.intType;
//		
//		if (add.getTerm().struct != Tab.intType) {
//			add.struct = add.getTerm().struct;
//			exprErrorNum++;
//		} else add.struct = Tab.intType;
//		
//	}
	

	
//	public void visit(ExprAddition eAdd) {
//		exprNum--;
//		
////		if (exprNum==0) report_info(termNum+"", null);
////		
////		if ((eAdd.getTerm().struct != Tab.intType && multipleAdds || (wrongMulFactorType && multipleFactors) || wrongAddFactorType) && exprNum==0) {
////			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eAdd);
////			wrongAddFactorType = true;
////
////		}
////		if ((eAdd.getTerm().struct != Tab.intType  || (wrongMulFactorType ) || wrongAddFactorType) && exprNum!=0) {
////			//report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eAdd);
////			wrongAddFactorType = true;
////		}
////		
////		if (exprNum==0) {
////			multipleAdds = false;
////			multipleFactors = false;
////		}
////		
////		if (wrongAddFactorType) eAdd.struct = Tab.noType;
////		else eAdd.struct = Tab.intType;
////		
////		termNum=0;
////		wrongAddFactorType = false;
////		
////		wrongMulFactorType = false;
//		
//		//if (eAdd.getTerm().struct != Tab.intType) exprErrorNum++;
//		//report_info(termNum+"" + eAdd, null);
//		if (exprErrorNum!=0 && termNums.get(currentExpr)>1 &&  bracketExpr == 0) {
//			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eAdd);
//			eAdd.struct = Tab.noType;
//			errorReported = true;
//		} else if (exprErrorNum!=0 &&  bracketExpr != 0) {
//			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eAdd);
//			eAdd.struct = Tab.noType;
//			errorReported = true;
//		}
//			else eAdd.struct = eAdd.getTerm().struct;
//		
//		
//		if (exprNum==0) {
//			exprErrorNum = 0;
//			errorReported = false;
//		}
//		
//		currentExpr = childParent.get(currentExpr);
//	
//	}
//	
//	public void visit(ExprMinus exprMinus) {
//		termNums.replace(currentExpr, termNums.get(currentExpr)+1);
//		
//	}
//	
//	public void visit(ExprSubtraction eSub) {
//		exprNum--;
////		if ((eSub.getTerm().struct != Tab.intType || (wrongMulFactorType && multipleFactors) || wrongAddFactorType) && exprNum==0) {
////			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eSub);
////			wrongAddFactorType = true;
////		}
////		if ((eSub.getTerm().struct != Tab.intType || (wrongMulFactorType) || wrongAddFactorType) && exprNum!=0) {
////			//report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eSub);
////			wrongAddFactorType = true;
////		}
////		
////		
////		if (exprNum==0) {
////			multipleAdds = false;
////			multipleFactors = false;
////		}
////		
////		if (wrongAddFactorType ) eSub.struct = Tab.noType;
////		else eSub.struct = Tab.intType;
////		
////		multipleAdds = false;
////		wrongAddFactorType = false;
////		multipleFactors = false;
////		wrongMulFactorType = false;
//		
//		if (eSub.getTerm().struct != Tab.intType) exprErrorNum++;
//		if (exprErrorNum!=0 && termNums.get(currentExpr)>1 &&  bracketExpr == 0) {
//			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eSub);
//			eSub.struct = Tab.noType;
//			errorReported = true;
//		} else if (exprErrorNum!=0 &&  bracketExpr != 0) {
//			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama", eSub);
//			eSub.struct = Tab.noType;
//			errorReported = true;
//		}
//			else eSub.struct = eSub.getTerm().struct;
//		
//		if (exprNum==0) {
//			exprErrorNum = 0;
//			errorReported = false;
//			
//		}
//		
//		currentExpr = childParent.get(currentExpr);
//	}
	
	public void visit(SingleTerm term) {
		term.struct = term.getTerm().struct;
	}
	
	public void visit(SingleNegativeTerm negTerm) {
		if (!negTerm.getTerm().struct.equals(Tab.intType)) {
			negTerm.struct = Tab.noType;
			report_error("Iskoriscena vrednost neintovskog tipa pri operaciji",negTerm);
		} else negTerm.struct = Tab.intType;
	}
	
	public void visit(MultipleTerms multTerm) {
		if (!multTerm.getTerm().struct.equals(Tab.intType) || !multTerm.getExpr().struct.equals(Tab.intType)) {
			report_error("Iskoriscena vrednost neintovskog tipa pri operacijama",multTerm);
			multTerm.struct = Tab.noType;
		} else multTerm.struct = multTerm.getTerm().struct;
	}
	

	
	public void visit(FactorExpr factExpr) {
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
		factExpr.struct = factExpr.getExpr().struct;
	}
	
	public void visit(FactorNewArray fna) {
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
		if(!fna.getExpr().struct.equals(Tab.intType) || fna.getType().struct.equals(Tab.noType)) {
			fna.struct = Tab.noType;
			report_error("Iskoriscena vrednost neintovskog tipa pri indeksiranju ili nepoznat tip", fna);
			//exprErrorNum++;
		} else {
			//fna.struct = fna.getType().struct;
			fna.struct = new Struct(Struct.Array, fna.getType().struct);
		}
	}
	
//	public void visit(ExprLBrack lBrack) {
//		//bracketExpr++;
//	}
	
//	public void visit(ExprRBrack rBrack) {
//		//bracketExpr--;
//	}
	
	public void visit(InnerDesignator innerDesig) {
//		if (currentExpr != null) {
//			termNums.replace(currentExpr, termNums.get(currentExpr)+1);
//		}
		String name = innerDesig.getNsName() + "::" + innerDesig.getName();
		Obj node = Tab.find(name);
		if (node == Tab.noObj) {
			report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", innerDesig);
			innerDesig.obj = Tab.noObj;
		} else if (node.getKind()!=Obj.Var && node.getKind()!=Obj.Con && node.getKind()!=Obj.Meth) {
			report_error("Identifikator "+name+" nije odgovarajuceg tipa;greska", innerDesig);
			innerDesig.obj = Tab.noObj;
		} else {
			innerDesig.obj = node;
			report_info(visitObjNode(node),innerDesig);
		}
	}
	
	public void visit(RegularDesignator regDesig) {
//		if (currentExpr != null) {
//			termNums.replace(currentExpr, termNums.get(currentExpr)+1);
//		}
		
//		String name = regDesig.getName();
//		if (currentNamespace != null && currentMethod == null) name = currentNamespace + "::" + name;
//		Obj node = Tab.find(name);
//		if (node == Tab.noObj) {
//			report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", regDesig);
//			regDesig.obj = Tab.noObj;
//		} else if (node.getKind()!=Obj.Var && node.getKind()!=Obj.Con && node.getKind()!=Obj.Meth) {
//			report_error("Identifikator "+name+" nije odgovarajuceg tipa;greska", regDesig);
//			regDesig.obj = Tab.noObj;
//		} else regDesig.obj = node;
		
		String name = regDesig.getName();
		String qualifiedName = currentNamespace + "::"+ name;
		Obj node = null;
		// trying to find the obj with this name
		if (currentNamespace == null) {
			node = Tab.find(name);
			if (node == Tab.noObj) {
				report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", regDesig);
				regDesig.obj = Tab.noObj;
				return;
			}
		} else {
			if (Tab.currentScope.findSymbol(name) != null) {
				// found in local scope
				node = Tab.currentScope.findSymbol(name);
			} else if (Tab.find(qualifiedName) != Tab.noObj) {
				// found in current namespace
				node = Tab.find(qualifiedName);
			} else if (Tab.find(name) != Tab.noObj) {
				// found globally
				node = Tab.find(name);
			} else {
				// didn't find
				report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", regDesig);
				regDesig.obj = Tab.noObj;
				return;
			}
		}
		
		// do the rest of the checks here
		if (node.getKind()!=Obj.Var && node.getKind()!=Obj.Con && node.getKind()!=Obj.Meth) {
			report_error("Identifikator "+name+" nije odgovarajuceg tipa;greska", regDesig);
			regDesig.obj = Tab.noObj;
		} else {
			regDesig.obj = node;
			report_info(visitObjNode(node),regDesig);
		}
	}
	
	public void visit(InnerDesignatorArray innerDesigArr) {
//		if (currentExpr != null) {
//			termNums.replace(currentExpr, termNums.get(currentExpr)+1);
//		}
		Obj node = innerDesigArr.getInnerArrayName().obj;
		if (node.equals(Tab.noObj)) {
			innerDesigArr.obj = Tab.noObj;
			return;
		}
		if (!innerDesigArr.getExpr().struct.equals(Tab.intType) ) {
			report_error("Iskoriscen podatak neintovskog tipa za indeksiranje", innerDesigArr);
			innerDesigArr.obj = Tab.noObj;
		} else {
			Obj elem = new Obj(Obj.Elem,node.getName(), node.getType().getElemType());
			innerDesigArr.obj = elem;
			report_info(visitObjNode(node),innerDesigArr);
		}
	}
	
	public void visit(InnerArrayName arr) {
		String name = arr.getNsName() + "::" + arr.getName();
		Obj node = Tab.find(name);
		if (node == Tab.noObj) {
			report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", arr);
			arr.obj = Tab.noObj;
		} else if (node.getKind()!=Obj.Var && node.getKind()!=Obj.Con && node.getKind()!=Obj.Meth) {
			report_error("Identifikator "+name+" nije odgovarajuceg tipa;greska", arr);
			arr.obj = Tab.noObj;
		} else if (node.getType().getKind() != Struct.Array ){
			report_error("Identifikator "+name+" nije niz;greska", arr);
			arr.obj = Tab.noObj;
		} else arr.obj = node;
	}
	
	public void visit(RegularDesignatorArray regDesArr) {
//		if (currentExpr != null) {
//			termNums.replace(currentExpr, termNums.get(currentExpr)+1);
//		}
//		String name = regDesArr.getName();
//		if (currentNamespace != null) name = currentNamespace + "::" + name;
//		Obj node = Tab.find(name);
//		
//		if (node == Tab.noObj) {
//			report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", regDesArr);
//			regDesArr.obj = Tab.noObj;
//		} else if (node.getKind()!=Obj.Var && node.getKind()!=Obj.Con && node.getKind()!=Obj.Meth) {
//			report_error("Identifikator "+name+" nije odgovarajuceg tipa;greska", regDesArr);
//			regDesArr.obj = Tab.noObj;
//		} else if (node.getType().getKind() != Struct.Array ){
//			report_error("Identifikator "+name+" nije niz;greska", regDesArr);
//			regDesArr.obj = Tab.noObj;
//		} else if (regDesArr.getExpr().struct != Tab.intType ) {
//			report_error("Iskoriscen podatak neintovskog tipa za indeksiranje", regDesArr);
//			regDesArr.obj = Tab.noObj;
//		} else {
//			Obj elem = new Obj(Obj.Elem,name, node.getType().getElemType());
//			regDesArr.obj = elem;
//			
//		}
//		
		Obj node = regDesArr.getArrayName().obj;
		if (node.equals(Tab.noObj)) {
			regDesArr.obj = Tab.noObj;
			return;
		}
		
		// do the rest of the checks here
		if (!regDesArr.getExpr().struct.equals(Tab.intType) ) {
			report_error("Iskoriscen podatak neintovskog tipa za indeksiranje", regDesArr);
			regDesArr.obj = Tab.noObj;
		} else {
			Obj elem = new Obj(Obj.Elem,node.getName(), node.getType().getElemType());
			regDesArr.obj = elem;

			report_info(visitObjNode(node),regDesArr);
		}
	}
	
	public void visit(ArrayName arr) {
		String name = arr.getName();
		String qualifiedName = currentNamespace + "::"+ name;
		Obj node = null;
		// trying to find the obj with this name
		if (currentNamespace == null) {
			node = Tab.find(name);
			if (node == Tab.noObj) {
				report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", arr);
				arr.obj = Tab.noObj;
				return;
			}
		} else {
			if (Tab.currentScope.findSymbol(name) != null) {
				// found in local scope
				node = Tab.currentScope.findSymbol(name);
			} else if (Tab.find(qualifiedName) != Tab.noObj) {
				// found in current namespace
				node = Tab.find(qualifiedName);
			} else if (Tab.find(name) != Tab.noObj) {
				// found globally
				node = Tab.find(name);
			} else {
				// didn't find
				report_error("Identifikator "+name+" nije pronadjen u tabeli simbola; greska", arr);
				arr.obj = Tab.noObj;
				return;
			}
		}
		
		// do the rest of the checks here
		if (node.getKind()!=Obj.Var && node.getKind()!=Obj.Con && node.getKind()!=Obj.Meth) {
			report_error("Identifikator "+name+" nije odgovarajuceg tipa;greska", arr);
			arr.obj = Tab.noObj;
		} else if (node.getType().getKind() != Struct.Array ){
			report_error("Identifikator "+name+" nije niz;greska", arr);
			arr.obj = Tab.noObj;
		} else arr.obj = node;
		
	}
	
	
	public void visit(FactorDesignator fDes) {
		
		fDes.struct = fDes.getDesignator().obj.getType();
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
	}
	
	public void visit(FactorFunction factorFunction) {
		Obj funName = factorFunction.getDesignator().obj;
		// check if  funName is function if not return noType 
		
		
		if (funName.getKind() != Obj.Meth) {
			factorFunction.struct = Tab.noType;
			report_error("Identifikator sa imenom "+funName.getName()+" ne oznacava metodu; greska",factorFunction);
		}
		// save ActPars in a structure then check if the len of it is the same
			// as level of funName, if not return noType
		else if (actParsList.peek().size() != funName.getLevel()) {
			factorFunction.struct = Tab.noType;
			report_error("Metoda sa imenom "+funName.getName()+" zahteva " + funName.getLevel() + " parametara, a prosledjeno joj je " + actParsList.peek().size() + "; greska",factorFunction);
		} // go through the structure and funName.locals (if len > 0) to check if the types are assignable (kompatibilni pri dodeli??)
		// kompatibilni pri dodeli -> pripazi koji je src a koji dst
		// if at least one isnt return noType
		else if (actParsList.peek().size() != 0) {
			int  i = 0;
			for (Obj param:funName.getLocalSymbols()) {
				if (!actParsList.peek().get(i).assignableTo(param.getType())) {
					factorFunction.struct = Tab.noType;
					report_error("Nekompatibilni tipovi stvarnih i prosledjenih parametara za funkciju " + funName.getName(), factorFunction);
					break;
				}
				
				i++;
				if (i >= actParsList.peek().size()) break;
			}
		}
		
		// clear out the structure which was used to store actpars
		
		factorFunction.struct = funName.getType();
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
		actParsList.pop();
		actParsCount = 0;
	}
	
	
	public void visit(FactorFunctionWithParams factorFunction) {
		Obj funName = factorFunction.getDesignator().obj;
	
		
		
		// check if  funName is function if not return noType 
		if (funName.getKind() != Obj.Meth) {
			factorFunction.struct = Tab.noType;
			report_error("Identifikator sa imenom "+funName.getName()+" ne oznacava metodu; greska",factorFunction);
		}
		// save ActPars in a structure then check if the len of it is the same
			// as level of funName, if not return noType
		else if (actParsList.peek().size() != funName.getLevel()) {
			factorFunction.struct = Tab.noType;
			report_error("Metoda sa imenom "+funName.getName()+" zahteva " + funName.getLevel() + " parametara, a prosledjeno joj je " + actParsList.peek().size() + "; greska",factorFunction);
			} // go through the structure and funName.locals (if len > 0) to check if the types are assignable (kompatibilni pri dodeli??)
		// kompatibilni pri dodeli -> pripazi koji je src a koji dst
		// if at least one isnt return noType
		else if (actParsList.peek().size() != 0) {
			int  i = 0;
			for (Obj param:funName.getLocalSymbols()) {
				if (!actParsList.peek().get(i).assignableTo(param.getType())) {
					factorFunction.struct = Tab.noType;
					report_error("Nekompatibilni tipovi stvarnih i prosledjenih parametara za funkciju " + funName.getName(), factorFunction);
					break;
				}
				
				i++;
				if (i >= actParsList.peek().size()) break;
			}
			
		
		}
		
		// clear out the structure which was used to store actpars
		
		
		factorFunction.struct = funName.getType();
		//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
		actParsList.pop();
		actParsCount = 0;
	}
	
	// actPars stuff -> add into List and plus one for actParsCount
	
	public void visit(ActParsStart actPars) {
		actParsList.push(new ArrayList<>());
	}
	
	
	public void visit(ActParSingleExpr actPar) {
		actParsCount++;
		actParsList.peek().add(actPar.getExpr().struct);
	}
	
	public void visit(ActParExprs actPar) {
		actParsCount++;
		actParsList.peek().add(actPar.getExpr().struct);
	}
	
	// DesignatorStatement stuff
	
	
	// Designator Assign expression
	public void visit(AssignDesignator assignDes) {
		if (assignDes.getDesignator().obj.getKind() != Obj.Var && assignDes.getDesignator().obj.getKind() != Obj.Elem) {
			report_error("Identifikator sa imenom "+assignDes.getDesignator().obj.getName()+" nije ni promenljiva ni element niza; greska",assignDes);
			return;
		}

		// src.assign(dest)
		// designator is dest, expr is src
		if (!assignDes.getExpr().struct.assignableTo(assignDes.getDesignator().obj.getType())) {
			report_error("Dodela izmedju nekompatibilnih tipova ",assignDes);
			return;
		}
	}
	

	// return designator expression from here
	
	public void visit(Decrement dec) {
		// kind = 0 to indicate decrement
		dec.obj = new Obj(0,"",Tab.intType);
	}
	public void visit(Increment inc) {
		// kind = 1 to indicate increment
		inc.obj = new Obj(1,"",Tab.intType);
	}
	public void visit(NoPars noPars) {
		// kind = 2 to indicate no parameters
		noPars.obj = new Obj(2,"",Tab.intType);
	}
	public void visit(ActualPars actPars) {
		// kind = 3 to indicate actual parameters
		actPars.obj = new Obj(3,"",Tab.intType);
	}
	
	// Designator increment, decrement, noPars and actualPars
	public void visit(DesDesExpr ddExpr) {
		int kind = ddExpr.getDesignatorExpression().obj.getKind();
		if (kind==0 || kind==1) {
			// indicates decrement and increment, respectively
			Obj des = ddExpr.getDesignator().obj;
			if (des.getKind()!=Obj.Var && des.getKind()!=Obj.Elem) {
				report_error("Identifikator sa imenom "+des.getName()+" nije ni promenljiva ni element niza; greska",ddExpr);
				return;
			}
			
			if (!des.getType().equals(Tab.intType)) {
				report_error("Identifikator sa imenom "+des.getName()+" nije intovskog tipa; greska",ddExpr);
				return;
			}
		}
		if (kind==2 || kind==3) {
			Obj des = ddExpr.getDesignator().obj;
			
			
			
			// check if  funName is function if not return noType 
			if (des.getKind() != Obj.Meth) {
				report_error("Identifikator sa imenom "+des.getName()+" ne oznacava metodu; greska",ddExpr);
			}
			// save ActPars in a structure then check if the len of it is the same
				// as level of funName, if not return noType
			else if (actParsList.peek().size() != des.getLevel()) {
				report_error("Metoda sa imenom "+des.getName()+" zahteva " + des.getLevel() + " parametara, a prosledjeno joj je " + actParsList.peek().size() + "; greska",ddExpr);
				} // go through the structure and funName.locals (if len > 0) to check if the types are assignable (kompatibilni pri dodeli??)
			// kompatibilni pri dodeli -> pripazi koji je src a koji dst
			// if at least one isnt return noType
			else if (actParsList.peek().size() != 0) {
				int  i = 0;
				for (Obj param:des.getLocalSymbols()) {
					if (!actParsList.peek().get(i).assignableTo(param.getType())) {
						report_error("Nekompatibilni tipovi stvarnih i prosledjenih parametara za funkciju " + des.getName(), ddExpr);
						break;
					}
					
					i++;
					if (i >= actParsList.peek().size()) break;
				}
			}
			
			// clear out the structure which was used to store actpars
			
			
			//termNums.replace(currentExpr, termNums.get(currentExpr)+1);
			actParsList.pop();
			actParsCount = 0;
		}
		
		
		
		
	}

	
	
	
	// Designator worst expression ever
	public void visit(WeirdDesStart wds) {
		desList.push(new ArrayList<>());
	}
	
	
	public void visit(WeirdDesignatorStatement desStm) {
	
		
		
		
		// designator() is dstDes
		// designator1() is srcDes
		
		// check if srcDes is of Array type
		if (desStm.getDesignator1().obj.getType().getKind() != Struct.Array) {
			report_error("Identifikator sa imenom "+desStm.getDesignator1().obj.getName()+" nije nizovskog tipa; greska",desStm);
			desList.pop();
			return;
		}
		
		// every designator in desList must be of kind either Var or Elem
		for (Obj d:desList.peek()) {
			if (d.getKind() != Obj.Var && d.getKind() != Obj.Elem) {
				report_error("Identifikator sa imenom "+d.getName()+" nije ni promenljiva ni element niza; greska",desStm);
				desList.pop();
				return;
			}
		}
		

		// dstDes must be of Array type
		if (desStm.getDesignator().obj.getType().getKind() != Struct.Array) {
			report_error("Identifikator sa imenom "+desStm.getDesignator().obj.getName()+" nije nizovskog tipa; greska",desStm);
			desList.pop();
			return;
		}
		// elemType of srcDes must be assignable to every designator in desList
		// src.assign(dest)
		// src is srcDes, but find elemType
		// dest is foreach from desList -> its Type
		Struct src = desStm.getDesignator1().obj.getType().getElemType();
		for(Obj d:desList.peek()) {
			Struct dst = d.getType();
			if (!src.assignableTo(dst)) {
				report_error("Nekompatibilni tipovi pri dodeli (pojedinacni elementi) " ,desStm);
				desList.pop();
				return;
			}
		}
		
		// elemType of srcDes must be assignable to elemType of dstDes
		// src.assign(dest)
		// src is srcDes, but find elemType
		// dst is dstDes, but find elemType
		Struct dst = desStm.getDesignator().obj.getType().getElemType();
		if (!src.assignableTo(dst)) {
			report_error("Nekompatibilni tipovi pri dodeli (izmedju dva niza)",desStm);
			desList.pop();
			return;
		}
		
		// before every return clear out the structure that's used to store designators 
			// from desList
		desList.pop();
		
	}
	
	// store all designators in a list so they can be used in WeirdDesignatorStatement
	public void visit(WeirdDesignator wDes) {
		desList.peek().add(wDes.getDesignator().obj);
	}
	
	// dealing with condition stuff for the if statement
	// dont forget i have condition in two if productions
	
	// get kind of Relop
	public void visit(EqRel r) {
		r.obj = new Obj(0, "", Tab.intType);
	}
	public void visit(UnRel r) {
		r.obj = new Obj(1, "", Tab.intType);
	}
	public void visit(RRel r) {
		r.obj = new Obj(2, "", Tab.intType);
	}
	public void visit(ReqRel r) {
		r.obj = new Obj(3, "", Tab.intType);
	}
	public void visit(LRel r) {
		r.obj = new Obj(4, "", Tab.intType);
	}
	public void visit(LeqRel r) {
		r.obj = new Obj(5, "", Tab.intType);
	}

	
	
	public void visit(SingleExpr expr) {
		
		if (!expr.getExpr().struct.equals(TabExt.boolType)) {
			report_error("Iskoriscena non-boolean vrednost pri stvaranju logickog uslova",expr);
			expr.struct = Tab.noType;
			return;
		}
		else expr.struct = expr.getExpr().struct;
	}
	
	public void visit(Exprs mulExpr) {
		Struct e1 = mulExpr.getExpr().struct;
		Struct e2 = mulExpr.getExpr1().struct;
		
		if (!e1.compatibleWith(e2)) {
			report_error("Iskorisceni nekompatibilni tipovi pri relacionim operacijama",mulExpr);
			mulExpr.struct = Tab.noType;
			return;
		}
		if ((e1.getKind()==Struct.Array || e2.getKind()==Struct.Array) && (mulExpr.getRelop().obj.getKind()!=0 && mulExpr.getRelop().obj.getKind()!=1)) {
			report_error("Iskorisceni nizovski tipovi, a pritom primenjivana neka operacija koja nije != ili ==",mulExpr);
			mulExpr.struct = Tab.noType;
			return;
		}
		
		mulExpr.struct = TabExt.boolType;
	
	}
	
	public void visit(SingleCondFact fact) {
		fact.struct = fact.getCondFact().struct;
	}
	
	public void visit(CondFacts facts) {
		if (!facts.getCondTerm().struct.equals(TabExt.boolType) || !facts.getCondFact().struct.equals(TabExt.boolType)) {
			report_error("Iskoriscena non-boolean vrednost pri AND operaciji",facts);
			facts.struct = Tab.noType;
		} else facts.struct = TabExt.boolType;
	}
	
	public void visit(SingleCondTerm cond) {
		cond.struct = cond.getCondTerm().struct;
	}
	
	public void visit(CondTerms conds) {
		Struct s1 = conds.getCondTermList().struct;
		Struct s2 = conds.getCondTerm().struct;
		
		if((!s1.equals(TabExt.boolType)) || (!s2.equals(TabExt.boolType))) {
			report_error("Iskoriscena non-boolean vrednost pri OR operacijia", conds);
			conds.struct = Tab.noType;
		} else conds.struct = TabExt.boolType;
	}
	
	public void visit(Conditions cond) {
		cond.struct = cond.getCondTermList().struct;
	}
	
	public void visit(ErrorConditions err) {
		err.struct = Tab.noType;
	}
	
	public void visit(IfStatement ifStm) {
		if (!ifStm.getCondition().struct.equals(TabExt.boolType)) {
			report_error("Iskoriscena ne-bool vrednost u uslovu if naredbe", ifStm);
		}
	}
	
	public void visit(IfElseStatement ifStm) {
		if (!ifStm.getCondition().struct.equals(TabExt.boolType)) {
			report_error("Iskoriscena ne-bool vrednost u uslovu if-else naredbe", ifStm);
		}
	}
	
	// for loop stuff -> inc forCounter at start; dec it at end
	// if there is CondFact -> check if it's bool
	
	public void visit(ForStart forSt) {
		forCounter++;
	}
	
	public void visit(ForStatement forStm) {
		forCounter--;
	}
	
	public void visit(CondFactYes cond) {
		if(!cond.getCondFact().struct.equals(TabExt.boolType)) {
			report_error("Iskoriscena ne-bool vrednost u uslovu for petlje", cond);
		}
	}
	
	public void visit(BreakStatement breakStm) {
		if (forCounter == 0) {
			report_error("Iskoriscen break van for petlje",breakStm);
		}
	}
	
	public void visit(ContinueStatement continueStm) {
		if (forCounter == 0) {
			report_error("Iskoriscen continue van for petlje",continueStm);
		}
	}
	
	// read statement
	// designator must be Var or Elem
	// designator must be int, char or bool
	
	public void visit(ReadStatement readStm) {
		Obj des = readStm.getDesignator().obj;
		if(des.getKind() != Obj.Var && des.getKind() != Obj.Elem) {
			report_error("Identifikator sa imenom "+des.getName()+ " u read iskazu ne oznacava ni promenljivu ni element niza",readStm);
			return;
		}
		
		Struct desType = des.getType();
		if(!desType.equals(Tab.intType) && !desType.equals(Tab.charType) && !desType.equals(TabExt.boolType)) {
			report_error("Identifikator sa imenom "+des.getName()+ " mora biti tipa int, char ili bool",readStm);
			return;
		}
	}
	
	// print statement
	// expr in both must be either int, char or bool
	public void visit(PrintStatement printStm) {
		Struct desType = printStm.getExpr().struct;
		if(!desType.equals(Tab.intType) && !desType.equals(Tab.charType) && !desType.equals(TabExt.boolType)) {
			report_error("Izraz u print iskazu mora biti tipa int, char ili bool",printStm);
			return;
		}
	}
	public void visit(PrintNumberStatement printStm) {
		Struct desType = printStm.getExpr().struct;
		if(!desType.equals(Tab.intType) && !desType.equals(Tab.charType) && !desType.equals(TabExt.boolType)) {
			report_error("Izraz u print iskazu mora biti tipa int, char ili bool",printStm);
			return;
		}
	}
	
	
	
}
