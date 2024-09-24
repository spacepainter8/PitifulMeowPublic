package rs.ac.bg.etf.pp1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.*;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

	// this is to save stuff for that weird designator statement
	Stack<List<Obj>> desList = new Stack<>();

	private int mainPc;

	public int getMainPc() {
		return mainPc;
	}

	boolean exprReturnFound = false;

	// this is for if-else structures
	Stack<ArrayList<Integer>> patchFrom_OR_ELSE_AFTERIFNOELSE = new Stack<>();
	Stack<ArrayList<Integer>> patchFrom_THEN = new Stack<>();
	Stack<ArrayList<Integer>> patchFrom_AFTERIFELSE = new Stack<>();

	// this is for FOR structures
	Stack<ArrayList<Integer>> patchFrom_BODY = new Stack<>();
	Stack<ArrayList<Integer>> patchFrom_END = new Stack<>();
	Stack<Integer> patchFrom_COUNTER = new Stack<>();
	Stack<Integer> patchFrom_CONDITION = new Stack<>();
	
	
	
	HashMap<String, Integer> backwardsJump = new HashMap<>();
	HashMap<Integer, String> forwardsJump = new HashMap<>();
	
	
	public void visit(ProgName prog) {
		// this is the start of code so we need to plug in chr, ord and len
		
		Obj chr = Tab.find("chr");
		chr.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);			// enter 1 1
		Code.put(1);
		Code.put(Code.load_n); // pass load_0 to the stack
		Code.put(Code.exit);
		Code.put(Code.return_); // exit properly
		
		// ord is the same
		Obj ord = Tab.find("ord");
		ord.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);			// enter 1 1
		Code.put(1);
		Code.put(Code.load_n); // pass load_0 to the stack
		Code.put(Code.exit);
		Code.put(Code.return_); // exit properly
		
		// len
		Obj len = Tab.find("len");
		len.setAdr(Code.pc);
		Code.put(Code.enter);
		Code.put(1);			// enter 1 1
		Code.put(1);
		Code.put(Code.load_n); // pass load_0 to the stack
		Code.put(Code.arraylength); // arrayLength to get length 
		Code.put(Code.exit);
		Code.put(Code.return_); // exit properly
		
	}
	

	public void visit(MethodTypeName meth) {
		Obj fun = meth.obj;

		// this was set previously in SemanticPass so I could more easily identify the
		// correct main method
		if (fun.getFpPos() == 8)
			mainPc = Code.pc;
		// starting point for the program
		meth.obj.setAdr(Code.pc);
		// enter argsNum localsNum (argsNum+localVariablesNum)
		Code.put(Code.enter);
		Code.put(fun.getLevel());
		Code.put(fun.getLocalSymbols().size());
	}

	// MethodDeclFormPars
	// MethodDeclNoFormPars

	// finish up method processing here, just put exit and return
	public void visit(MethodDeclFormPars meth) {
		if (!meth.getMethodTypeName().obj.getType().equals(Tab.noType) && !exprReturnFound) {
			Code.put(Code.trap);
			Code.put(1);
		}

		exprReturnFound = false;
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(MethodDeclNoFormPars meth) {
		if (!meth.getMethodTypeName().obj.getType().equals(Tab.noType) && !exprReturnFound) {
			Code.put(Code.trap);
			Code.put(1);
		}
		exprReturnFound = false;
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	// assignment -> only need to save expr from stack into desig. -> so just a
	// store
	public void visit(AssignDesignator assign) {
		Code.store(assign.getDesignator().obj);
	}

	// the 3 simple factors only need to be sent to the stack for further usage
	// so load

	public void visit(FactorNumber num) {
		// create object to be sent via load, only need to worry about address;
		Obj node = new Obj(Obj.Con, "const", Tab.intType);
		node.setAdr(num.getN1());
		node.setLevel(0);
		Code.load(node);
	}

	public void visit(FactorChar chr) {
		// create object to be sent via load, only need to worry about address;
		Obj node = new Obj(Obj.Con, "const", Tab.charType);
		node.setAdr(chr.getC1());
		node.setLevel(0);
		Code.load(node);
	}

	public void visit(FactorBool b) {
		// create object to be sent via load, only need to worry about address;
		Obj node = new Obj(Obj.Con, "const", TabExt.boolType);
		// node.setAdr(num);
		if (b.getB1() == true)
			node.setAdr(1);
		else
			node.setAdr(0);
		node.setLevel(0);
		Code.load(node);
	}

	// gonna do term and expr now so i can test on simple cases

	// get the type of mulop from fpPos
	// 0 -> mul, 1 -> div, 2 -> rem
	public void visit(Mul mul) {
		mul.obj = new Obj(Obj.Con, "", Tab.noType);
		mul.obj.setFpPos(0);
	}

	public void visit(Div div) {
		div.obj = new Obj(Obj.Con, "", Tab.noType);
		div.obj.setFpPos(1);
	}

	public void visit(Percent rem) {
		rem.obj = new Obj(Obj.Con, "", Tab.noType);
		rem.obj.setFpPos(2);
	}

	// term now -> just load the appropriate op to the stack
	public void visit(MultipleFactors muls) {
		int kind = muls.getMulop().obj.getFpPos();
		switch (kind) {
		case 0:
			Code.put(Code.mul);
			break;
		case 1:
			Code.put(Code.div);
			break;
		case 2:
			Code.put(Code.rem);
			break;
		}
	}

	// similarly now for Expr
	// get the type of addop from fpPos
	// 0 -> add, 1-> sub

	public void visit(Plus plus) {
		plus.obj = new Obj(Obj.Con, "", Tab.noType);
		plus.obj.setFpPos(0);
	}

	public void visit(Minus minus) {
		minus.obj = new Obj(Obj.Con, "", Tab.noType);
		minus.obj.setFpPos(1);
	}

	// now just load the appropriate op to the stack
	public void visit(MultipleTerms adds) {
		int kind = adds.getAddop().obj.getFpPos();
		switch (kind) {
		case 0:
			Code.put(Code.add);
			break;
		case 1:
			Code.put(Code.sub);
			break;
		}
	}

	// now just load neg to the stack -> to negate what's already there
	public void visit(SingleNegativeTerm neg) {
		Code.put(Code.neg);
	}

	// new array as factor
	// put newarray, then put 0/1 depending on type of elem
	// 0 for char, 1 for others
	public void visit(FactorNewArray newArr) {
		Struct type = newArr.getType().struct;
		Code.put(Code.newarray);
		if (type.equals(Tab.charType))
			Code.put(0);
		else
			Code.put(1);
	}

	// load designator to stack only if it's the designator that's from
	// FactorDesignator
	// be careful with designator [expr] stuff cause that actually loads
	// elem of the array to the stack
	// but still do it only if it's from FactorDesignator

	// for array -> elem stuff, maybe before going to the expr
	// put the name on the stack so we have array(name), elemIndex(expr)
	// and then just do load to load the value

	public void visit(InnerDesignator des) {
		if (des.getParent().getClass().equals(FactorDesignator.class)) {
			Code.load(des.obj);
		}
	}

	public void visit(RegularDesignator des) {
		if (des.getParent().getClass().equals(FactorDesignator.class)) {
			Code.load(des.obj);
		}
	}

	// array stuff, need to get the designator object to the stack before expr

	public void visit(InnerArrayName arr) {
		Code.load(arr.obj);
	}

	public void visit(ArrayName arr) {
		Code.load(arr.obj);
	}

	// Stack : arrayAddr index
	// now if it's gonna be used as a factor get it's value by loading aload, baload
	// if we send the .obj via Code.load(obj) it will recognize elemType
	// and act accordingly

	// if not then just leave it on the stack it will be used for storing

	public void visit(InnerDesignatorArray des) {
		if (des.getParent().getClass().equals(FactorDesignator.class)) {
			Code.load(des.obj);
		}
	}

	public void visit(RegularDesignatorArray des) {
		if (des.getParent().getClass().equals(FactorDesignator.class)) {
			Code.load(des.obj);
		}
	}

	// do print now so it's easier to see that code is working correctly

//	public void visit(PrintStmt printStmt){
//		if(printStmt.getExpr().struct == Tab.intType){
//			Code.loadConst(5);
//			Code.put(Code.print);
//		}else{
//			Code.loadConst(1);
//			Code.put(Code.bprint);
//		}
//	}

	// Stack: expr
	// so the val is already on the stack
	// just need to add width and call print

	// unspecified width
	public void visit(PrintStatement print) {
		Struct expr = print.getExpr().struct;
		if (expr.equals(Tab.intType)) {
			Code.loadConst(5);
			Code.put(Code.print);
		} else if (expr.equals(Tab.charType)) {
			Code.loadConst(1);
			Code.put(Code.bprint);
		} else if (expr.equals(TabExt.boolType)) {
			Code.loadConst(1);
			Code.put(Code.print);
		}
	}

	// now this with specified width
	public void visit(PrintNumberStatement print) {
		Code.loadConst(print.getN1());
		Struct expr = print.getExpr().struct;
		if (expr.equals(Tab.charType))
			Code.put(Code.bprint);
		else
			Code.put(Code.print);
	}

	// function calls as factors now
	// actPars are already on stack cause they are expr
	// now just need to call the function call s
	// s is going to be adr - pc
	// calling the function as a factor so the value is definitely being used
	// no need to pop if from the stack at a later stage

	public void visit(FactorFunction f) {
		Obj fun = f.getDesignator().obj;
		int offset = fun.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	public void visit(FactorFunctionWithParams f) {
		Obj fun = f.getDesignator().obj;
		int offset = fun.getAdr() - Code.pc;
		Code.put(Code.call);
		Code.put2(offset);
	}

	

	// kind = 0 to indicate decrement
	// kind = 1 to indicate increment
	// kind = 2 to indicate no parameters
	// kind = 3 to indicate actual parameters

	public void visit(DesDesExpr des) {
		int kind = des.getDesignatorExpression().obj.getKind();

		// function calls are 2 and 3
		// function calls as statements are the same
		// only thing is if it's not void need to pop the value from the stack
		if (kind == 2 || kind == 3) {

			Obj fun = des.getDesignator().obj;
			int offset = fun.getAdr() - Code.pc;
			Code.put(Code.call);
			Code.put2(offset);

			// function is non-void so we should pop the stack

			if (!fun.getType().equals(Tab.noType))
				Code.put(Code.pop);
			return;
		}

		// increment is 1
		// (name) Stack :
		// (name) Stack: des 1 add store
		// (array) Stack : array index
		// (array) Stack : array index array index aload 1 add astore

		if (kind == 1) {
			Obj node = des.getDesignator().obj;
			if (node.getKind() != Obj.Elem) {
				Code.load(node);
				Code.loadConst(1);
				Code.put(Code.add);
				Code.store(node);
			} else {
				// Stack: arr ind
				Code.put(Code.dup2);
				// Stack: arr ind arr ind
				Code.load(node); // i think this will do aload
				Code.loadConst(1);
				Code.put(Code.add);
				Code.store(node);
			}
			return;
		}

		// decrement is 0
		// the same as increment only sub instead of add

		if (kind == 0) {
			Obj node = des.getDesignator().obj;
			if (node.getKind() != Obj.Elem) {
				Code.load(node);
				Code.loadConst(1);
				Code.put(Code.sub);
				Code.store(node);
			} else {
				// Stack: arr ind
				Code.put(Code.dup2);
				// Stack: arr ind arr ind
				Code.load(node); // i think this will do aload
				Code.loadConst(1);
				Code.put(Code.sub);
				Code.store(node);
			}
			return;
		}

	}

	public void visit(WeirdDesStart wds) {
		desList.push(new ArrayList<>());
	}
	
	
	// save designators for weird designator statement
	public void visit(WeirdDesignator des) {
		desList.peek().add(des.getDesignator().obj);
	}

	public void visit(WeirdDesignatorComma comma) {
		// this is empty but needs to be recognized
		Obj node = Tab.noObj;
		desList.peek().add(node);
	}

	
	public void visit(WeirdDesignatorStatement des) {
		// debug
	
		
		
		//
		
		
		
		
		
		
		
		// check runtime error here
		Code.loadConst(desList.peek().size());
		Code.load(des.getDesignator().obj);
		Code.put(Code.arraylength);
		Code.put(Code.add);
		Code.load(des.getDesignator1().obj);
		Code.put(Code.arraylength);
		Code.putFalseJump(Code.gt, 0);
		int adrTrap = Code.pc - 2;
		Code.put(Code.trap);
		Code.put(0);
		Code.fixup(adrTrap);

		// gonna consist of two parts
		// first part is gonna deal with the list
		// the second part is gonna deal with the dst array

		// first part
		// need to go from the back bcs of the stack
		int i = desList.peek().size() - 1;
		for (i = desList.peek().size() - 1; i >= 0; i--) {
			Obj d = desList.peek().get(i);

			if (d.getType().equals(Tab.noType)) {
				// skip an element
				continue;
			}

			if (d.getKind() == Obj.Var) {
				// this is a simple variable
				// Stack:

				Code.load(des.getDesignator1().obj);
				// Stack: srcArr
				Code.loadConst(i);
				// Stack: srcArr index
				if (des.getDesignator1().obj.getType().getElemType().equals(Tab.charType))
					Code.put(Code.baload);
				else
					Code.put(Code.aload);
				// Stack : srcArr[index]
				Code.store(d);
				// d = srcArr[index]
				continue;
			}

			if (d.getKind() == Obj.Elem) {
				// this is an array element
				// Stack: dstArr dstIndex
				Code.load(des.getDesignator1().obj);
				// Stack: dstArr dstIndex srcArr
				Code.loadConst(i);
				// Stack: dstArr dstIndex srcArr srcIndex
				if (des.getDesignator1().obj.getType().getElemType().equals(Tab.charType))
					Code.put(Code.baload);
				else
					Code.put(Code.aload);
				// Stack: dstArr dstIndex srcArr[srcIndex]
				Code.store(d);
				// dstArr[dstIndex] = srcArr[srcIndex]
				continue;
			}
		}

		// now for the second part
		// nemam pojma da li je ovo dobro, uzasno izgleda

		// this is what i'm trying to emulate
//		for (int x=0;x<len(arr0) && (x+i)<len(arr1);x++) {
//			arr0[x] = arr1[x+i];
//		}

		// this will print out the topmost value from the stack
		// debugging ! DELETE LATER
//			Code.put(Code.dup);
//			Code.put(Code.print);
//			Code.put(Code.dup_x2);
//			Code.put(Code.pop);
		//

		i = desList.peek().size();
		// this is to test out for loop conditions
		Code.loadConst(-1);
		// for loop conditioning starts here
		int adrCon = Code.pc;
		Code.loadConst(1);
		Code.put(Code.add);
		Code.put(Code.dup);
		Code.load(des.getDesignator().obj);
		Code.put(Code.arraylength);
		// x < len(arr0)
		Code.putFalseJump(Code.lt, 0);
		int adr0 = Code.pc - 2;
		Code.put(Code.dup);
		Code.loadConst(i);
		Code.put(Code.add);
		Code.load(des.getDesignator1().obj);
		Code.put(Code.arraylength);
		// (x+i)<len(arr1)
		Code.putFalseJump(Code.lt, 0);
		int adr1 = Code.pc - 2;
		// for loop starts here
		Code.put(Code.dup);
		Code.put(Code.dup);
		Code.loadConst(i);
		Code.put(Code.add);
		// arr1[x+i]
		Code.load(des.getDesignator1().obj);
		Code.put(Code.dup_x1);
		Code.put(Code.pop);
		if (des.getDesignator1().obj.getType().getElemType().equals(Tab.charType))
			Code.put(Code.baload);
		else
			Code.put(Code.aload);
		Code.load(des.getDesignator().obj);
		Code.put(Code.dup_x2);
		Code.put(Code.pop);
		// arr0[x] = arr1[x+i]
		if (des.getDesignator().obj.getType().getElemType().equals(Tab.charType))
			Code.put(Code.bastore);
		else
			Code.put(Code.astore);
		Code.putJump(adrCon);
		// end of for loop
		Code.fixup(adr1);
		Code.fixup(adr0);
		Code.put(Code.pop);
		
		desList.pop();

	}

	// dont forget to open and close the scope

	// if-else stuff

	// this is where the conditioning starts
	// both rules are part of an AND rule

	public void visit(SingleExpr expr) {
		// Stack : expr
		// the expr is a boolean
		// need to check whether it's true or false

		if (expr.getParent().getClass().equals(CondFactYes.class)) {
			// we've come here from the FOR loop
			
			Code.loadConst(1);
			// if the condition is true (equal to the 1 on the stack)
			// we need to jump to the BODY

			Code.putFalseJump(Code.inverse[Code.eq], 0);
			patchFrom_BODY.peek().add(Code.pc - 2);

			// if we didn't jump that means the condition was bad
			// so we need to unconditionally jump to the end of the FOR loop
			Code.putJump(0);
			patchFrom_END.peek().add(Code.pc - 2);

		} else {
			// we've come here from the IF-ELSE loop
			// load false to the stack
			Code.loadConst(0);
			// if it is false (equal to the 0 on the stack) then the whole AND condition is
			// false
			// so there is no need to check the other parts of the AND condition
			// which means we need to jump to the next OR condition
			// or if this is the only OR condition we need to jump to the ELSE part
			// or if there is no ELSE part we need to jump to the end of the IF part
			// nevertheless this is going to need to be patched

			Code.putFalseJump(Code.ne, 0);

			// need to save this address here
			// so it can be patched either from the next OR condition
			// or whatever comes after the IF statements
			// it will be patched from the next OR condition if this is not the last OR
			// condition
			// it will be patched from after IF statements if this is the last OR condition

			patchFrom_OR_ELSE_AFTERIFNOELSE.peek().add(Code.pc - 2);
		}

	}

	public void visit(Exprs expr) {
		// Stack: expr expr
		// exprs are booleans
		// we need to check whether the condition is true or false based on the relop

		// relop coding
		// 0 -> ==, 1 -> !=, 2 -> >, 3 -> >=, 4 -> <, 5 -> <=
		int kind = expr.getRelop().obj.getKind();

		if (expr.getParent().getClass().equals(CondFactYes.class)) {
			// we've come here from the FOR loop
			
			// if the condition is true (equal to the 1 on the stack)
			// we need to jump to the BODY

			switch (kind) {
			case 0:
				Code.putFalseJump(Code.inverse[Code.eq], 0);
				break;
			case 1:
				Code.putFalseJump(Code.inverse[Code.ne], 0);
				break;
			case 2:
				Code.putFalseJump(Code.inverse[Code.gt], 0);
				break;
			case 3:
				Code.putFalseJump(Code.inverse[Code.ge], 0);
				break;
			case 4:
				Code.putFalseJump(Code.inverse[Code.lt], 0);
				break;
			case 5:
				Code.putFalseJump(Code.inverse[Code.le], 0);
				break;
			}
			patchFrom_BODY.peek().add(Code.pc - 2);

			// if we didn't jump that means the condition was bad
			// so we need to unconditionally jump to the end of the FOR loop
			Code.putJump(0);
			patchFrom_END.peek().add(Code.pc - 2);

		} else {
			// we've come here from the IF-ELSE block
			// everything else is the same as above, just check for the right condition
			switch (kind) {
			case 0:
				Code.putFalseJump(Code.eq, 0);
				break;
			case 1:
				Code.putFalseJump(Code.ne, 0);
				break;
			case 2:
				Code.putFalseJump(Code.gt, 0);
				break;
			case 3:
				Code.putFalseJump(Code.ge, 0);
				break;
			case 4:
				Code.putFalseJump(Code.lt, 0);
				break;
			case 5:
				Code.putFalseJump(Code.le, 0);
				break;
			}

			patchFrom_OR_ELSE_AFTERIFNOELSE.peek().add(Code.pc - 2);
		}

	}

	public void visit(EmptyOr or) {

		// code-wise we will get to here only if the previous part of this OR condition
		// was true
		// if it weren't true we would've continuously jumped in code to the next part
		// of the OR condition
		// code looks a bit like this
		// cond -> single condition with (if not true jump to next or) at the end
		// C1 C2 C3
		// (cond and cond) or (cond) or (cond and cond)
		// if any of the conds from C1 wasnt true we wouldve jumped to C2
		//
		// if all the conds from C1 were true we wouldve gotten to the end of C1
		// eventually getting to this part in the code

		// so at this point in code we would have something like
		// jump over all of the other conditions; start next OR condition;

		// so if it were true we would just jump over the other conditions
		// and if it weren't true we would skip the jump
		// and get directly to the start of the next OR condition

		// we know this condition is true, no need to check other parts of the OR
		// condition
		// so we can unconditionally jump directly to the start of the THEN block
		Code.putJump(0);
		// this will need to be patched from the start of the THEN block
		patchFrom_THEN.peek().add(Code.pc - 2);

		// from here, we can patch the stuff from the AND block which needed to jump to
		// the next OR condition
		// the last condition won't ever visit this rule parser-wise, and it will be
		// patched from after the IF

		for (int adr : patchFrom_OR_ELSE_AFTERIFNOELSE.peek()) {
			Code.fixup(adr);
		}
		// everything that needed patching was patched so we can clear that part
		patchFrom_OR_ELSE_AFTERIFNOELSE.peek().clear();

	}

	public void visit(ThenStart then) {
		// this is the start of the THEN block
		// here we need to patch the case when we come across
		// a true OR condition and we don't need to check other OR conditions

		for (int adr : patchFrom_THEN.peek()) {
			Code.fixup(adr);
		}

		// everything that needed patching was patched so we can clear that part
		patchFrom_THEN.peek().clear();

	}

	public void visit(ThenEnd then) {
		// this is the end of the THEN block
		// in case we are in an IF-ELSE block it is also the start of the ELSE block

		// because we are in the THEN block, we need to unconditionally
		// jump over the ELSE block if we are in IF-ELSE block

		if (then.getParent().getClass().equals(IfElseStatement.class)) {
			// we are in an IF-ELSE block
			// unconditional jump to the end of the IF-ELSE block
			Code.putJump(0);
			patchFrom_AFTERIFELSE.peek().add(Code.pc - 2);
		}

		// whether if it's an IF or IF-ELSE block
		// we need to patch the addresses which jump to then end of the THEN block

		for (int adr : patchFrom_OR_ELSE_AFTERIFNOELSE.peek()) {
			Code.fixup(adr);
		}

		// clear
		patchFrom_OR_ELSE_AFTERIFNOELSE.peek().clear();
	}

	public void visit(IfElseStatement elseEnd) {
		// we are at the end of the ELSE block
		// need to patch the addresses from the end of the THEN block
		// because they need to jump the whole of the ELSE block

		for (int adr : patchFrom_AFTERIFELSE.peek()) {
			Code.fixup(adr);
		}

		// clear
		patchFrom_AFTERIFELSE.peek().clear();

		// also we are the end of the whole IF-ELSE thing
		// so just need to pop from the stacks so we can start refering
		// to the level above it

		patchFrom_AFTERIFELSE.pop();
		patchFrom_OR_ELSE_AFTERIFNOELSE.pop();
		patchFrom_THEN.pop();
	}

	public void visit(IfStatement ifStm) {
		// we are the end of the IF stuff
		// so just need to pop from the stacks so we can start refering
		// to the level above it

		patchFrom_AFTERIFELSE.pop();
		patchFrom_OR_ELSE_AFTERIFNOELSE.pop();
		patchFrom_THEN.pop();
	}

	public void visit(IfStart ifStart) {
		// this is the beginning of the whole thing
		// need to create all of the stacks

		patchFrom_AFTERIFELSE.push(new ArrayList<>());
		patchFrom_OR_ELSE_AFTERIFNOELSE.push(new ArrayList<>());
		patchFrom_THEN.push(new ArrayList<>());

	}

	// return

	public void visit(ReturnStatement ret) {
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ReturnExprStatement ret) {
		exprReturnFound = true;
		Code.put(Code.exit);
		Code.put(Code.return_);
	}

	public void visit(ReadStatement readStm) {
		// Code.put(Code.read);

		if (readStm.getDesignator().obj.getType().equals(Tab.charType)) {
			Code.put(Code.bread);
		} else
			Code.put(Code.read);

		Code.store(readStm.getDesignator().obj);
	}

	// for loop

	// if we have a condition it's going to be checked in the CondFact part
	// alongside the if-else

	public void visit(CondFactNo cond) {
		// we don't have a condition which means it is always true
		// just need to jump to the start of the BODY
		Code.putJump(0);
		patchFrom_BODY.peek().add(Code.pc - 2);
	}
	
	
	public void visit(ForStatement forStm) {
		// this is the end of the FOR statement
		// 	so we need to unconditionally jump to the start of the COUNTER
		
		Code.putJump(patchFrom_COUNTER.peek());

		// this is the end of the whole FOR loop
		//	so we need to patch some stuff from here
		
		
		for(int adr:patchFrom_END.peek()) {
			Code.fixup(adr);
		}
		
		// clear
		patchFrom_END.peek().clear();
		
		// this is also end of the for loop so we need to pop the stacks
		patchFrom_BODY.pop();
		patchFrom_CONDITION.pop();
		patchFrom_COUNTER.pop();
		patchFrom_END.pop();
	}
	
	public void visit(EndOfCounter des) {
		// this is the end of the COUNTER
		// we need to unconditionally jump to the condition
		Code.putJump(patchFrom_CONDITION.peek());
		//patchFrom_CONDITION.peek().add(Code.pc-2);
		
	}
	
	public void visit(StartOfBody forBody) {
		// this is the stuff from the FOR body 
		// 	need to patch some stuff here
		
		for(int adr:patchFrom_BODY.peek()) {
			Code.fixup(adr);
		}
		
		// clea
		patchFrom_BODY.peek().clear();
	}
	
	public void visit(StartOfCounter cnt) {
		// this is the start of the counter
		// 	so we need to patch some stuff from here
		
		
//		for(int adr:patchFrom_COUNTER.peek()) {
//			
//			Code.fixup(adr);
//		}
//		
		patchFrom_COUNTER.push(Code.pc);
		
		// clea
		//patchFrom_COUNTER.peek();
	}
	
	public void visit(CondStart condStart) {
		// this is the start of the condition
		// so we need to patch some stuff from here

//		for(int adr:patchFrom_CONDITION.peek()) {
//			Code.fixup(adr);
//		}
		
		patchFrom_CONDITION.push(Code.pc);
		
		// clea
		//patchFrom_CONDITION.peek().clear();
		
	}
	
	public void visit(ForStart forStart) {
		// start of FOR; need to set up the stacks
		
		patchFrom_BODY.push(new ArrayList<>());
		//patchFrom_CONDITION.push(new ArrayList<>());
		//patchFrom_COUNTER.push(new ArrayList<>());
		patchFrom_END.push(new ArrayList<>());
		
	}
	
	// break -> just jump to the end of the FOR stuff
	
	public void visit(BreakStatement breakStm) {
		Code.putJump(0);
		patchFrom_END.peek().add(Code.pc-2);
	}
	
	// continue -> just jump to the start of COUNTER
	
	public void visit(ContinueStatement cntStm) {
		Code.putJump(patchFrom_COUNTER.peek());
		
	}
	
	
	public void visit(LabelStatement lblStm) {
		String lbl = lblStm.getI();
		backwardsJump.put(lbl, Code.pc);
		
		for(Entry<Integer, String> e:forwardsJump.entrySet()) {
			if (e.getValue().equals(lbl)) {
		
				Code.fixup(e.getKey());
			}
		}
	}
	
	public void visit(GotoStatement gotoStm) {
		String lbl = gotoStm.getI();
		Integer adr = backwardsJump.get(lbl);
		if (adr != null) {
			Code.putJump(adr);
			return;
		}
		Code.putJump(0);
		forwardsJump.put(Code.pc-2, lbl);
		System.out.println(Code.pc-2);
	}
	
	
	

}
