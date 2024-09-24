// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class InnerType extends Type {

    private String outerType;
    private String innerType;

    public InnerType (String outerType, String innerType) {
        this.outerType=outerType;
        this.innerType=innerType;
    }

    public String getOuterType() {
        return outerType;
    }

    public void setOuterType(String outerType) {
        this.outerType=outerType;
    }

    public String getInnerType() {
        return innerType;
    }

    public void setInnerType(String innerType) {
        this.innerType=innerType;
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
        buffer.append("InnerType(\n");

        buffer.append(" "+tab+outerType);
        buffer.append("\n");

        buffer.append(" "+tab+innerType);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InnerType]");
        return buffer.toString();
    }
}
