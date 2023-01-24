package item3.field;

public class Concert {

    private boolean stageOn;

    private IElvis elvis;

    public Concert(IElvis elvis) {
        this.elvis = elvis;
    }

    public void perform() {
        stageOn = true;
        elvis.sing();
    }

    public boolean isStageOn() {
        return stageOn;
    }
}
