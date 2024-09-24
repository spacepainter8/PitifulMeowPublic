// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class DesDesExpr extends DesignatorStatement {

    private Designator Designator;
    private DesignatorExpression DesignatorExpression;

    public DesDesExpr (Designator Designator, DesignatorExpression DesignatorExpression) {
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.DesignatorExpression=DesignatorExpression;
        if(DesignatorExpression!=null) DesignatorExpression.setParent(this);
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public DesignatorExpression getDesignatorExpression() {
        return DesignatorExpression;
    }

    public void setDesignatorExpression(DesignatorExpression DesignatorExpression) {
        this.DesignatorExpression=DesignatorExpression;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Designator!=null) Designator.accept(visitor);
        if(DesignatorExpression!=null) DesignatorExpression.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(DesignatorExpression!=null) DesignatorExpression.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(DesignatorExpression!=null) DesignatorExpression.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesDesExpr(\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorExpression!=null)
            buffer.append(DesignatorExpression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesDesExpr]");
        return buffer.toString();
    }
}
