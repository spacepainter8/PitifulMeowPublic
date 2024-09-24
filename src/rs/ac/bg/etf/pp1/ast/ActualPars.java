// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class ActualPars extends DesignatorExpression {

    private ActParsStart ActParsStart;
    private ActPars ActPars;

    public ActualPars (ActParsStart ActParsStart, ActPars ActPars) {
        this.ActParsStart=ActParsStart;
        if(ActParsStart!=null) ActParsStart.setParent(this);
        this.ActPars=ActPars;
        if(ActPars!=null) ActPars.setParent(this);
    }

    public ActParsStart getActParsStart() {
        return ActParsStart;
    }

    public void setActParsStart(ActParsStart ActParsStart) {
        this.ActParsStart=ActParsStart;
    }

    public ActPars getActPars() {
        return ActPars;
    }

    public void setActPars(ActPars ActPars) {
        this.ActPars=ActPars;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsStart!=null) ActParsStart.accept(visitor);
        if(ActPars!=null) ActPars.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsStart!=null) ActParsStart.traverseTopDown(visitor);
        if(ActPars!=null) ActPars.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsStart!=null) ActParsStart.traverseBottomUp(visitor);
        if(ActPars!=null) ActPars.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ActualPars(\n");

        if(ActParsStart!=null)
            buffer.append(ActParsStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ActPars!=null)
            buffer.append(ActPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ActualPars]");
        return buffer.toString();
    }
}
