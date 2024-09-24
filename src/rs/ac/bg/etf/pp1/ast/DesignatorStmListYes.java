// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class DesignatorStmListYes extends DesignatorStmListYesNo {

    private DesignatorStmList DesignatorStmList;

    public DesignatorStmListYes (DesignatorStmList DesignatorStmList) {
        this.DesignatorStmList=DesignatorStmList;
        if(DesignatorStmList!=null) DesignatorStmList.setParent(this);
    }

    public DesignatorStmList getDesignatorStmList() {
        return DesignatorStmList;
    }

    public void setDesignatorStmList(DesignatorStmList DesignatorStmList) {
        this.DesignatorStmList=DesignatorStmList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorStmList!=null) DesignatorStmList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorStmList!=null) DesignatorStmList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorStmList!=null) DesignatorStmList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignatorStmListYes(\n");

        if(DesignatorStmList!=null)
            buffer.append(DesignatorStmList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignatorStmListYes]");
        return buffer.toString();
    }
}
