// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class MultipleProgramVars extends ProgramVarList {

    private ProgramVarList ProgramVarList;
    private ProgramVar ProgramVar;

    public MultipleProgramVars (ProgramVarList ProgramVarList, ProgramVar ProgramVar) {
        this.ProgramVarList=ProgramVarList;
        if(ProgramVarList!=null) ProgramVarList.setParent(this);
        this.ProgramVar=ProgramVar;
        if(ProgramVar!=null) ProgramVar.setParent(this);
    }

    public ProgramVarList getProgramVarList() {
        return ProgramVarList;
    }

    public void setProgramVarList(ProgramVarList ProgramVarList) {
        this.ProgramVarList=ProgramVarList;
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
        if(ProgramVarList!=null) ProgramVarList.accept(visitor);
        if(ProgramVar!=null) ProgramVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramVarList!=null) ProgramVarList.traverseTopDown(visitor);
        if(ProgramVar!=null) ProgramVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramVarList!=null) ProgramVarList.traverseBottomUp(visitor);
        if(ProgramVar!=null) ProgramVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MultipleProgramVars(\n");

        if(ProgramVarList!=null)
            buffer.append(ProgramVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ProgramVar!=null)
            buffer.append(ProgramVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MultipleProgramVars]");
        return buffer.toString();
    }
}
