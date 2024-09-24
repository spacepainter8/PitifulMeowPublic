// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class ProgramVars extends ProgramCVCDecl {

    private ProgramVarDecl ProgramVarDecl;

    public ProgramVars (ProgramVarDecl ProgramVarDecl) {
        this.ProgramVarDecl=ProgramVarDecl;
        if(ProgramVarDecl!=null) ProgramVarDecl.setParent(this);
    }

    public ProgramVarDecl getProgramVarDecl() {
        return ProgramVarDecl;
    }

    public void setProgramVarDecl(ProgramVarDecl ProgramVarDecl) {
        this.ProgramVarDecl=ProgramVarDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgramVarDecl!=null) ProgramVarDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramVarDecl!=null) ProgramVarDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramVarDecl!=null) ProgramVarDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramVars(\n");

        if(ProgramVarDecl!=null)
            buffer.append(ProgramVarDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramVars]");
        return buffer.toString();
    }
}
