// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStms extends DesignatorStmList {

    private DesignatorStmList DesignatorStmList;
    private DesignatorStatement DesignatorStatement;

    public DesignatorStms (DesignatorStmList DesignatorStmList, DesignatorStatement DesignatorStatement) {
        this.DesignatorStmList=DesignatorStmList;
        if(DesignatorStmList!=null) DesignatorStmList.setParent(this);
        this.DesignatorStatement=DesignatorStatement;
        if(DesignatorStatement!=null) DesignatorStatement.setParent(this);
    }

    public DesignatorStmList getDesignatorStmList() {
        return DesignatorStmList;
    }

    public void setDesignatorStmList(DesignatorStmList DesignatorStmList) {
        this.DesignatorStmList=DesignatorStmList;
    }

    public DesignatorStatement getDesignatorStatement() {
        return DesignatorStatement;
    }

    public void setDesignatorStatement(DesignatorStatement DesignatorStatement) {
        this.DesignatorStatement=DesignatorStatement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStmList!=null) DesignatorStmList.accept(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStmList!=null) DesignatorStmList.traverseTopDown(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStmList!=null) DesignatorStmList.traverseBottomUp(visitor);
        if(DesignatorStatement!=null) DesignatorStatement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStms(\n");

        if(DesignatorStmList!=null)
            buffer.append(DesignatorStmList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStatement!=null)
            buffer.append(DesignatorStatement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStms]");
        return buffer.toString();
    }
}
