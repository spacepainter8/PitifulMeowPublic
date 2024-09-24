// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class ProgramCVCDecls extends ProgramCVCDeclList {

    private ProgramCVCDeclList ProgramCVCDeclList;
    private ProgramCVCDecl ProgramCVCDecl;

    public ProgramCVCDecls (ProgramCVCDeclList ProgramCVCDeclList, ProgramCVCDecl ProgramCVCDecl) {
        this.ProgramCVCDeclList=ProgramCVCDeclList;
        if(ProgramCVCDeclList!=null) ProgramCVCDeclList.setParent(this);
        this.ProgramCVCDecl=ProgramCVCDecl;
        if(ProgramCVCDecl!=null) ProgramCVCDecl.setParent(this);
    }

    public ProgramCVCDeclList getProgramCVCDeclList() {
        return ProgramCVCDeclList;
    }

    public void setProgramCVCDeclList(ProgramCVCDeclList ProgramCVCDeclList) {
        this.ProgramCVCDeclList=ProgramCVCDeclList;
    }

    public ProgramCVCDecl getProgramCVCDecl() {
        return ProgramCVCDecl;
    }

    public void setProgramCVCDecl(ProgramCVCDecl ProgramCVCDecl) {
        this.ProgramCVCDecl=ProgramCVCDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ProgramCVCDeclList!=null) ProgramCVCDeclList.accept(visitor);
        if(ProgramCVCDecl!=null) ProgramCVCDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ProgramCVCDeclList!=null) ProgramCVCDeclList.traverseTopDown(visitor);
        if(ProgramCVCDecl!=null) ProgramCVCDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ProgramCVCDeclList!=null) ProgramCVCDeclList.traverseBottomUp(visitor);
        if(ProgramCVCDecl!=null) ProgramCVCDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ProgramCVCDecls(\n");

        if(ProgramCVCDeclList!=null)
            buffer.append(ProgramCVCDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(ProgramCVCDecl!=null)
            buffer.append(ProgramCVCDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ProgramCVCDecls]");
        return buffer.toString();
    }
}
