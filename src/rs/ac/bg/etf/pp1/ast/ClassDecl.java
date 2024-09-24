// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class ClassDecl implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private String I1;
    private ExtType ExtType;
    private StVarDeclList StVarDeclList;
    private StaticInitializerList StaticInitializerList;
    private ClassVarDeclList ClassVarDeclList;
    private MethodDeclListYesNo MethodDeclListYesNo;

    public ClassDecl (String I1, ExtType ExtType, StVarDeclList StVarDeclList, StaticInitializerList StaticInitializerList, ClassVarDeclList ClassVarDeclList, MethodDeclListYesNo MethodDeclListYesNo) {
        this.I1=I1;
        this.ExtType=ExtType;
        if(ExtType!=null) ExtType.setParent(this);
        this.StVarDeclList=StVarDeclList;
        if(StVarDeclList!=null) StVarDeclList.setParent(this);
        this.StaticInitializerList=StaticInitializerList;
        if(StaticInitializerList!=null) StaticInitializerList.setParent(this);
        this.ClassVarDeclList=ClassVarDeclList;
        if(ClassVarDeclList!=null) ClassVarDeclList.setParent(this);
        this.MethodDeclListYesNo=MethodDeclListYesNo;
        if(MethodDeclListYesNo!=null) MethodDeclListYesNo.setParent(this);
    }

    public String getI1() {
        return I1;
    }

    public void setI1(String I1) {
        this.I1=I1;
    }

    public ExtType getExtType() {
        return ExtType;
    }

    public void setExtType(ExtType ExtType) {
        this.ExtType=ExtType;
    }

    public StVarDeclList getStVarDeclList() {
        return StVarDeclList;
    }

    public void setStVarDeclList(StVarDeclList StVarDeclList) {
        this.StVarDeclList=StVarDeclList;
    }

    public StaticInitializerList getStaticInitializerList() {
        return StaticInitializerList;
    }

    public void setStaticInitializerList(StaticInitializerList StaticInitializerList) {
        this.StaticInitializerList=StaticInitializerList;
    }

    public ClassVarDeclList getClassVarDeclList() {
        return ClassVarDeclList;
    }

    public void setClassVarDeclList(ClassVarDeclList ClassVarDeclList) {
        this.ClassVarDeclList=ClassVarDeclList;
    }

    public MethodDeclListYesNo getMethodDeclListYesNo() {
        return MethodDeclListYesNo;
    }

    public void setMethodDeclListYesNo(MethodDeclListYesNo MethodDeclListYesNo) {
        this.MethodDeclListYesNo=MethodDeclListYesNo;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ExtType!=null) ExtType.accept(visitor);
        if(StVarDeclList!=null) StVarDeclList.accept(visitor);
        if(StaticInitializerList!=null) StaticInitializerList.accept(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.accept(visitor);
        if(MethodDeclListYesNo!=null) MethodDeclListYesNo.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ExtType!=null) ExtType.traverseTopDown(visitor);
        if(StVarDeclList!=null) StVarDeclList.traverseTopDown(visitor);
        if(StaticInitializerList!=null) StaticInitializerList.traverseTopDown(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseTopDown(visitor);
        if(MethodDeclListYesNo!=null) MethodDeclListYesNo.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ExtType!=null) ExtType.traverseBottomUp(visitor);
        if(StVarDeclList!=null) StVarDeclList.traverseBottomUp(visitor);
        if(StaticInitializerList!=null) StaticInitializerList.traverseBottomUp(visitor);
        if(ClassVarDeclList!=null) ClassVarDeclList.traverseBottomUp(visitor);
        if(MethodDeclListYesNo!=null) MethodDeclListYesNo.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ClassDecl(\n");

        buffer.append(" "+tab+I1);
        buffer.append("\n");

        if(ExtType!=null)
            buffer.append(ExtType.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StVarDeclList!=null)
            buffer.append(StVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StaticInitializerList!=null)
            buffer.append(StaticInitializerList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVarDeclList!=null)
            buffer.append(ClassVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclListYesNo!=null)
            buffer.append(MethodDeclListYesNo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ClassDecl]");
        return buffer.toString();
    }
}
