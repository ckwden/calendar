package publicholidays.model.twilio;

public class MessengerDummy implements Messenger {

    private String sid;
    private String token;
    private String numberTo;
    private String numberFrom;

    public MessengerDummy(String sid, String token, String numberTo, String numberFrom) {
        this.sid = sid;
        this.token = token;
        this.numberFrom = numberFrom;
        this.numberTo = numberTo;
    }

    @Override
    public void sendReport(String report) {
        System.out.println(numberFrom + " sends report to " + numberTo);
        System.out.println(report);
    }
}
