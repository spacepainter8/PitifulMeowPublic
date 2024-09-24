// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class CVCDecls extends CVCDeclList {

    private CVCDeclList CVCDeclList;
    private CVCDecl CVCDecl;

    public CVCDecls (CVCDeclList CVCDeclList, CVCDecl CVCDecl) {
        this.CVCDeclList=CVCDeclList;
        if(CVCDeclList!=null) CVCDeclList.setParent(this);
        this.CVCDecl=CVCDecl;
        if(CVCDecl!=null) CVCDecl.setParent(this);
    }

    public CVCDeclList getCVCDeclList() {
        return CVCDeclList;
    }

    public void setCVCDeclList(CVCDeclList CVCDeclList) {
        this.CVCDeclList=CVCDeclList;
    }

    public CVCDecl getCVCDecl() {
        return CVCDecl;
    }

    public void setCVCDecl(CVCDecl CVCDecl) {
        this.CVCDecl=CVCDecl;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(CVCDeclList!=null) CVCDeclList.accept(visitor);
        if(CVCDecl!=null) CVCDecl.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(CVCDeclList!=null) CVCDeclList.traverseTopDown(visitor);
        if(CVCDecl!=null) CVCDecl.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(CVCDeclList!=null) CVCDeclList.traverseBottomUp(visitor);
        if(CVCDecl!=null) CVCDecl.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("CVCDecls(\n");

        if(CVCDeclList!=null)
            buffer.append(CVCDeclList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CVCDecl!=null)
            buffer.append(CVCDecl.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [CVCDecls]");
        return buffer.toString();
    }
}
