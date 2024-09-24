// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class StandaloneType extends Type {

    private String type;

    public StandaloneType (String type) {
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
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
        buffer.append("StandaloneType(\n");

        buffer.append(" "+tab+type);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [StandaloneType]");
        return buffer.toString();
    }
}
