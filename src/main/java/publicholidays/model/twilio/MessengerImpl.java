package publicholidays.model.twilio;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 * Implements the Messenger interface
 */
public class MessengerImpl implements Messenger {

    private final String sid;
    private final String token;
    private final String numberTo;
    private final String numberFrom;

    public MessengerImpl(String sid, String token, String numberTo, String numberFrom) {
        this.sid = sid;
        this.token = token;
        this.numberTo = numberTo;
        this.numberFrom = numberFrom;
    }

    @Override
    public void sendReport(String report) {
        Twilio.init(sid, token);
        Message.creator(new PhoneNumber(numberTo), new PhoneNumber(numberFrom), report).create();
    }
}
