public class MockPlayerThread extends PlayerThread {
    private String testType;
    public String result;

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
                    result = "won";
                    Logger.logWin(player);
                } else {
                    result = "lost";
                    Logger.logExit(player);
                }
                break;
        }

    }


}
