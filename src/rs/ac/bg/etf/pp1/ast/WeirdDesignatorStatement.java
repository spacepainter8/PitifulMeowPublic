// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class WeirdDesignatorStatement extends DesignatorStatement {

    private WeirdDesStart WeirdDesStart;
    private DesignatorCommaList DesignatorCommaList;
    private Designator Designator;
    private Designator Designator1;

    public WeirdDesignatorStatement (WeirdDesStart WeirdDesStart, DesignatorCommaList DesignatorCommaList, Designator Designator, Designator Designator1) {
        this.WeirdDesStart=WeirdDesStart;
        if(WeirdDesStart!=null) WeirdDesStart.setParent(this);
        this.DesignatorCommaList=DesignatorCommaList;
        if(DesignatorCommaList!=null) DesignatorCommaList.setParent(this);
        this.Designator=Designator;
        if(Designator!=null) Designator.setParent(this);
        this.Designator1=Designator1;
        if(Designator1!=null) Designator1.setParent(this);
    }

    public WeirdDesStart getWeirdDesStart() {
        return WeirdDesStart;
    }

    public void setWeirdDesStart(WeirdDesStart WeirdDesStart) {
        this.WeirdDesStart=WeirdDesStart;
    }

    public DesignatorCommaList getDesignatorCommaList() {
        return DesignatorCommaList;
    }

    public void setDesignatorCommaList(DesignatorCommaList DesignatorCommaList) {
        this.DesignatorCommaList=DesignatorCommaList;
    }

    public Designator getDesignator() {
        return Designator;
    }

    public void setDesignator(Designator Designator) {
        this.Designator=Designator;
    }

    public Designator getDesignator1() {
        return Designator1;
    }

    public void setDesignator1(Designator Designator1) {
        this.Designator1=Designator1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(WeirdDesStart!=null) WeirdDesStart.accept(visitor);
        if(DesignatorCommaList!=null) DesignatorCommaList.accept(visitor);
        if(Designator!=null) Designator.accept(visitor);
        if(Designator1!=null) Designator1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(WeirdDesStart!=null) WeirdDesStart.traverseTopDown(visitor);
        if(DesignatorCommaList!=null) DesignatorCommaList.traverseTopDown(visitor);
        if(Designator!=null) Designator.traverseTopDown(visitor);
        if(Designator1!=null) Designator1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(WeirdDesStart!=null) WeirdDesStart.traverseBottomUp(visitor);
        if(DesignatorCommaList!=null) DesignatorCommaList.traverseBottomUp(visitor);
        if(Designator!=null) Designator.traverseBottomUp(visitor);
        if(Designator1!=null) Designator1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("WeirdDesignatorStatement(\n");

        if(WeirdDesStart!=null)
            buffer.append(WeirdDesStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorCommaList!=null)
            buffer.append(DesignatorCommaList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator!=null)
            buffer.append(Designator.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Designator1!=null)
            buffer.append(Designator1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [WeirdDesignatorStatement]");
        return buffer.toString();
    }
}
