public class MockPlayerThread extends PlayerThread {
    private String testType;

    public MockPlayerThread(Player player) {
        super(player);
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    @Override
    public void run() {
        switch (testType) {
            case "drawCard":
                drawCard();
                break;
            case "discardCard":
                discardCard();
                break;
            case "currentHand":
                readCurrentHand();
                break;
            case "checkHand":
                if(checkHandState()) {
                    Logger.logWin(player);
                } else {
                    Logger.logExit(player);
                }

                break;
        }

    }


}
