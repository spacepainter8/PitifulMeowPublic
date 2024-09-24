// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class SingleProgramVar extends ProgramVarList {

    private ProgramVar ProgramVar;

    public SingleProgramVar (ProgramVar ProgramVar) {
        this.ProgramVar=ProgramVar;
        if(ProgramVar!=null) ProgramVar.setParent(this);
    }

    public ProgramVar getProgramVar() {
        return ProgramVar;
    }

    public void setProgramVar(ProgramVar ProgramVar) {
        this.ProgramVar=ProgramVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgramVar!=null) ProgramVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramVar!=null) ProgramVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramVar!=null) ProgramVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleProgramVar(\n");

        if(ProgramVar!=null)
            buffer.append(ProgramVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleProgramVar]");
        return buffer.toString();
    }
}
