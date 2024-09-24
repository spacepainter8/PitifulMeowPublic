// generated with ast extension for cup
// version 0.8
// 17/0/2024 1:19:12


package rs.ac.bg.etf.pp1.ast;

public class ForStatement extends Statement {

    private ForStart ForStart;
    private DesignatorStmListYesNo DesignatorStmListYesNo;
    private CondStart CondStart;
    private CondFactYesNo CondFactYesNo;
    private StartOfCounter StartOfCounter;
    private DesignatorStmListYesNo DesignatorStmListYesNo1;
    private EndOfCounter EndOfCounter;
    private StartOfBody StartOfBody;
    private Statement Statement;

    public ForStatement (ForStart ForStart, DesignatorStmListYesNo DesignatorStmListYesNo, CondStart CondStart, CondFactYesNo CondFactYesNo, StartOfCounter StartOfCounter, DesignatorStmListYesNo DesignatorStmListYesNo1, EndOfCounter EndOfCounter, StartOfBody StartOfBody, Statement Statement) {
        this.ForStart=ForStart;
        if(ForStart!=null) ForStart.setParent(this);
        this.DesignatorStmListYesNo=DesignatorStmListYesNo;
        if(DesignatorStmListYesNo!=null) DesignatorStmListYesNo.setParent(this);
        this.CondStart=CondStart;
        if(CondStart!=null) CondStart.setParent(this);
        this.CondFactYesNo=CondFactYesNo;
        if(CondFactYesNo!=null) CondFactYesNo.setParent(this);
        this.StartOfCounter=StartOfCounter;
        if(StartOfCounter!=null) StartOfCounter.setParent(this);
        this.DesignatorStmListYesNo1=DesignatorStmListYesNo1;
        if(DesignatorStmListYesNo1!=null) DesignatorStmListYesNo1.setParent(this);
        this.EndOfCounter=EndOfCounter;
        if(EndOfCounter!=null) EndOfCounter.setParent(this);
        this.StartOfBody=StartOfBody;
        if(StartOfBody!=null) StartOfBody.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public ForStart getForStart() {
        return ForStart;
    }

    public void setForStart(ForStart ForStart) {
        this.ForStart=ForStart;
    }

    public DesignatorStmListYesNo getDesignatorStmListYesNo() {
        return DesignatorStmListYesNo;
    }

    public void setDesignatorStmListYesNo(DesignatorStmListYesNo DesignatorStmListYesNo) {
        this.DesignatorStmListYesNo=DesignatorStmListYesNo;
    }

    public CondStart getCondStart() {
        return CondStart;
    }

    public void setCondStart(CondStart CondStart) {
        this.CondStart=CondStart;
    }

    public CondFactYesNo getCondFactYesNo() {
        return CondFactYesNo;
    }

    public void setCondFactYesNo(CondFactYesNo CondFactYesNo) {
        this.CondFactYesNo=CondFactYesNo;
    }

    public StartOfCounter getStartOfCounter() {
        return StartOfCounter;
    }

    public void setStartOfCounter(StartOfCounter StartOfCounter) {
        this.StartOfCounter=StartOfCounter;
    }

    public DesignatorStmListYesNo getDesignatorStmListYesNo1() {
        return DesignatorStmListYesNo1;
    }

    public void setDesignatorStmListYesNo1(DesignatorStmListYesNo DesignatorStmListYesNo1) {
        this.DesignatorStmListYesNo1=DesignatorStmListYesNo1;
    }

    public EndOfCounter getEndOfCounter() {
        return EndOfCounter;
    }

    public void setEndOfCounter(EndOfCounter EndOfCounter) {
        this.EndOfCounter=EndOfCounter;
    }

    public StartOfBody getStartOfBody() {
        return StartOfBody;
    }

    public void setStartOfBody(StartOfBody StartOfBody) {
        this.StartOfBody=StartOfBody;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(ForStart!=null) ForStart.accept(visitor);
        if(DesignatorStmListYesNo!=null) DesignatorStmListYesNo.accept(visitor);
        if(CondStart!=null) CondStart.accept(visitor);
        if(CondFactYesNo!=null) CondFactYesNo.accept(visitor);
        if(StartOfCounter!=null) StartOfCounter.accept(visitor);
        if(DesignatorStmListYesNo1!=null) DesignatorStmListYesNo1.accept(visitor);
        if(EndOfCounter!=null) EndOfCounter.accept(visitor);
        if(StartOfBody!=null) StartOfBody.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(ForStart!=null) ForStart.traverseTopDown(visitor);
        if(DesignatorStmListYesNo!=null) DesignatorStmListYesNo.traverseTopDown(visitor);
        if(CondStart!=null) CondStart.traverseTopDown(visitor);
        if(CondFactYesNo!=null) CondFactYesNo.traverseTopDown(visitor);
        if(StartOfCounter!=null) StartOfCounter.traverseTopDown(visitor);
        if(DesignatorStmListYesNo1!=null) DesignatorStmListYesNo1.traverseTopDown(visitor);
        if(EndOfCounter!=null) EndOfCounter.traverseTopDown(visitor);
        if(StartOfBody!=null) StartOfBody.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(ForStart!=null) ForStart.traverseBottomUp(visitor);
        if(DesignatorStmListYesNo!=null) DesignatorStmListYesNo.traverseBottomUp(visitor);
        if(CondStart!=null) CondStart.traverseBottomUp(visitor);
        if(CondFactYesNo!=null) CondFactYesNo.traverseBottomUp(visitor);
        if(StartOfCounter!=null) StartOfCounter.traverseBottomUp(visitor);
        if(DesignatorStmListYesNo1!=null) DesignatorStmListYesNo1.traverseBottomUp(visitor);
        if(EndOfCounter!=null) EndOfCounter.traverseBottomUp(visitor);
        if(StartOfBody!=null) StartOfBody.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ForStatement(\n");

        if(ForStart!=null)
            buffer.append(ForStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStmListYesNo!=null)
            buffer.append(DesignatorStmListYesNo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondStart!=null)
            buffer.append(CondStart.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(CondFactYesNo!=null)
            buffer.append(CondFactYesNo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StartOfCounter!=null)
            buffer.append(StartOfCounter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorStmListYesNo1!=null)
            buffer.append(DesignatorStmListYesNo1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(EndOfCounter!=null)
            buffer.append(EndOfCounter.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StartOfBody!=null)
            buffer.append(StartOfBody.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ForStatement]");
        return buffer.toString();
    }
}
