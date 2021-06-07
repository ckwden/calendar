package publicholidays.model.twilio;

/**
 * Implements the Messenger interface as a dummy
 */
public class MessengerDummy implements Messenger {

    private final String numberTo;
    private final String numberFrom;

    public MessengerDummy(String numberTo, String numberFrom) {
        this.numberFrom = numberFrom;
        this.numberTo = numberTo;
    }

    @Override
    public void sendReport(String report) {
        System.out.println(numberFrom + " sends report to " + numberTo);
        System.out.println(report);
    }

    @Override
    public String getResponse() {
        return null;
    }
}
