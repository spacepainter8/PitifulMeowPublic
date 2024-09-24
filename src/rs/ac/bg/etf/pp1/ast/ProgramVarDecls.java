// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class ProgramVarDecls extends ProgramVarDecl {

    private Type Type;
    private ProgramVarList ProgramVarList;
    private ProgramVarDeclEnd ProgramVarDeclEnd;

    public ProgramVarDecls (Type Type, ProgramVarList ProgramVarList, ProgramVarDeclEnd ProgramVarDeclEnd) {
        this.Type=Type;
        if(Type!=null) Type.setParent(this);
        this.ProgramVarList=ProgramVarList;
        if(ProgramVarList!=null) ProgramVarList.setParent(this);
        this.ProgramVarDeclEnd=ProgramVarDeclEnd;
        if(ProgramVarDeclEnd!=null) ProgramVarDeclEnd.setParent(this);
    }

    public Type getType() {
        return Type;
    }

    public void setType(Type Type) {
        this.Type=Type;
    }

    public ProgramVarList getProgramVarList() {
        return ProgramVarList;
    }

    public void setProgramVarList(ProgramVarList ProgramVarList) {
        this.ProgramVarList=ProgramVarList;
    }

    public ProgramVarDeclEnd getProgramVarDeclEnd() {
        return ProgramVarDeclEnd;
    }

    public void setProgramVarDeclEnd(ProgramVarDeclEnd ProgramVarDeclEnd) {
        this.ProgramVarDeclEnd=ProgramVarDeclEnd;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Type!=null) Type.accept(visitor);
        if(ProgramVarList!=null) ProgramVarList.accept(visitor);
        if(ProgramVarDeclEnd!=null) ProgramVarDeclEnd.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Type!=null) Type.traverseTopDown(visitor);
        if(ProgramVarList!=null) ProgramVarList.traverseTopDown(visitor);
        if(ProgramVarDeclEnd!=null) ProgramVarDeclEnd.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Type!=null) Type.traverseBottomUp(visitor);
        if(ProgramVarList!=null) ProgramVarList.traverseBottomUp(visitor);
        if(ProgramVarDeclEnd!=null) ProgramVarDeclEnd.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramVarDecls(\n");

        if(Type!=null)
            buffer.append(Type.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ProgramVarList!=null)
            buffer.append(ProgramVarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ProgramVarDeclEnd!=null)
            buffer.append(ProgramVarDeclEnd.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramVarDecls]");
        return buffer.toString();
    }
}
