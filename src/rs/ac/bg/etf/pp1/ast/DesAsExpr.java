// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class DesAsExpr extends DesignatorStatement {

    private DesignatorAssignopExpr DesignatorAssignopExpr;

    public DesAsExpr (DesignatorAssignopExpr DesignatorAssignopExpr) {
        this.DesignatorAssignopExpr=DesignatorAssignopExpr;
        if(DesignatorAssignopExpr!=null) DesignatorAssignopExpr.setParent(this);
    }

    public DesignatorAssignopExpr getDesignatorAssignopExpr() {
        return DesignatorAssignopExpr;
    }

    public void setDesignatorAssignopExpr(DesignatorAssignopExpr DesignatorAssignopExpr) {
        this.DesignatorAssignopExpr=DesignatorAssignopExpr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorAssignopExpr!=null) DesignatorAssignopExpr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorAssignopExpr!=null) DesignatorAssignopExpr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorAssignopExpr!=null) DesignatorAssignopExpr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesAsExpr(\n");

        if(DesignatorAssignopExpr!=null)
            buffer.append(DesignatorAssignopExpr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesAsExpr]");
        return buffer.toString();
    }
}
