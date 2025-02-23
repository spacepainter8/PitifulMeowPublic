// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class FormParArray extends FormPar {

    private Type Type;
    private String fparName;

    public FormParArray (Type Type, String fparName) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.fparName=fparName;
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public String getFparName() {
        return fparName;
    }

    public void setFparName(String fparName) {
        this.fparName=fparName;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParArray(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(" "+tab+fparName);
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParArray]");
        return buffer.toString();
    }
}
