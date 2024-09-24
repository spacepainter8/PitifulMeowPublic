// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class Designators extends DesignatorCommaList {

    private DesignatorCommaList DesignatorCommaList;
    private DesignatorComma DesignatorComma;

    public Designators (DesignatorCommaList DesignatorCommaList, DesignatorComma DesignatorComma) {
        this.DesignatorCommaList=DesignatorCommaList;
        if(DesignatorCommaList!=null) DesignatorCommaList.setParent(this);
        this.DesignatorComma=DesignatorComma;
        if(DesignatorComma!=null) DesignatorComma.setParent(this);
    }

    public DesignatorCommaList getDesignatorCommaList() {
        return DesignatorCommaList;
    }

    public void setDesignatorCommaList(DesignatorCommaList DesignatorCommaList) {
        this.DesignatorCommaList=DesignatorCommaList;
    }

    public DesignatorComma getDesignatorComma() {
        return DesignatorComma;
    }

    public void setDesignatorComma(DesignatorComma DesignatorComma) {
        this.DesignatorComma=DesignatorComma;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorCommaList!=null) DesignatorCommaList.accept(visitor);
        if(DesignatorComma!=null) DesignatorComma.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorCommaList!=null) DesignatorCommaList.traverseTopDown(visitor);
        if(DesignatorComma!=null) DesignatorComma.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorCommaList!=null) DesignatorCommaList.traverseBottomUp(visitor);
        if(DesignatorComma!=null) DesignatorComma.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Designators(\n");

        if(DesignatorCommaList!=null)
            buffer.append(DesignatorCommaList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorComma!=null)
            buffer.append(DesignatorComma.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Designators]");
        return buffer.toString();
    }
}
