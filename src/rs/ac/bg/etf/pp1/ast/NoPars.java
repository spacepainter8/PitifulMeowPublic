// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class NoPars extends DesignatorExpression {

    private ActParsStart ActParsStart;

    public NoPars (ActParsStart ActParsStart) {
        this.ActParsStart=ActParsStart;
        if(ActParsStart!=null) ActParsStart.setParent(this);
    }

    public ActParsStart getActParsStart() {
        return ActParsStart;
    }

    public void setActParsStart(ActParsStart ActParsStart) {
        this.ActParsStart=ActParsStart;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ActParsStart!=null) ActParsStart.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ActParsStart!=null) ActParsStart.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ActParsStart!=null) ActParsStart.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NoPars(\n");

        if(ActParsStart!=null)
            buffer.append(ActParsStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [NoPars]");
        return buffer.toString();
    }
}
