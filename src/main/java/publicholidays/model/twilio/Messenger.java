package publicholidays.model.twilio;

/**
 * Models the Twilio API
 */
public interface Messenger {

    /**
     * Makes the API call to send the report of known public holidays
     * @param report The report to be sent through Twilio SMS
     */
    void sendReport(String report);
}
