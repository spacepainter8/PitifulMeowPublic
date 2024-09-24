// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class InnerDesignatorArray extends Designator {

    private InnerArrayName InnerArrayName;
    private Expr Expr;

    public InnerDesignatorArray (InnerArrayName InnerArrayName, Expr Expr) {
        this.InnerArrayName=InnerArrayName;
        if(InnerArrayName!=null) InnerArrayName.setParent(this);
        this.Expr=Expr;
        if(Expr!=null) Expr.setParent(this);
    }

    public InnerArrayName getInnerArrayName() {
        return InnerArrayName;
    }

    public void setInnerArrayName(InnerArrayName InnerArrayName) {
        this.InnerArrayName=InnerArrayName;
    }

    public Expr getExpr() {
        return Expr;
    }

    public void setExpr(Expr Expr) {
        this.Expr=Expr;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(InnerArrayName!=null) InnerArrayName.accept(visitor);
        if(Expr!=null) Expr.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(InnerArrayName!=null) InnerArrayName.traverseTopDown(visitor);
        if(Expr!=null) Expr.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(InnerArrayName!=null) InnerArrayName.traverseBottomUp(visitor);
        if(Expr!=null) Expr.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("InnerDesignatorArray(\n");

        if(InnerArrayName!=null)
            buffer.append(InnerArrayName.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Expr!=null)
            buffer.append(Expr.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [InnerDesignatorArray]");
        return buffer.toString();
    }
}
