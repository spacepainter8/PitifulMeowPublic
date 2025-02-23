// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class SingleStVarDecl extends StVarDeclList {

    private StVarDeclList StVarDeclList;
    private ClassVarDecl ClassVarDecl;

    public SingleStVarDecl (StVarDeclList StVarDeclList, ClassVarDecl ClassVarDecl) {
        this.StVarDeclList=StVarDeclList;
        if(StVarDeclList!=null) StVarDeclList.setParent(this);
        this.ClassVarDecl=ClassVarDecl;
        if(ClassVarDecl!=null) ClassVarDecl.setParent(this);
    }

    public StVarDeclList getStVarDeclList() {
        return StVarDeclList;
    }

    public void setStVarDeclList(StVarDeclList StVarDeclList) {
        this.StVarDeclList=StVarDeclList;
    }

    public ClassVarDecl getClassVarDecl() {
        return ClassVarDecl;
    }

    public void setClassVarDecl(ClassVarDecl ClassVarDecl) {
        this.ClassVarDecl=ClassVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(StVarDeclList!=null) StVarDeclList.accept(visitor);
        if(ClassVarDecl!=null) ClassVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(StVarDeclList!=null) StVarDeclList.traverseTopDown(visitor);
        if(ClassVarDecl!=null) ClassVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(StVarDeclList!=null) StVarDeclList.traverseBottomUp(visitor);
        if(ClassVarDecl!=null) ClassVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleStVarDecl(\n");

        if(StVarDeclList!=null)
            buffer.append(StVarDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ClassVarDecl!=null)
            buffer.append(ClassVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleStVarDecl]");
        return buffer.toString();
    }
}
