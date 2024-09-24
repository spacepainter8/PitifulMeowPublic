// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class LabelStatement extends Statement {

    private String i;

    public LabelStatement (String i) {
        this.i=i;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i=i;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("LabelStatement(\n");

        buffer.append(" "+tab+i);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [LabelStatement]");
        return buffer.toString();
    }
}
