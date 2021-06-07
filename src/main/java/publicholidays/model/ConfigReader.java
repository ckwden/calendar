package publicholidays.model;

/**
 * Retrieves the required data in a config file for the application
 */
public interface ConfigReader {

    /**
     * Gets the user key for the Holidays API in the configuration file
     * @return Holidays API user key
     */
    String getHolidayKey();

    /**
     * Gets the user SID for Twilio API in the configuration file
     * @return Twilio API user SID
     */
    String getTwilioSID();

    /**
     * Gets the user token for Twilio API in the configuration file
     * @return Twilio API user token
     */
    String getTwilioToken();

    /**
     * Gets the number that will be sent messages to
     * @return The number that the report will be sent to
     */
    String getTwilioNumberTo();

    /**
     * Gets the number that will the messages will be sent from
     * @return The number that the report is sent from
     */
    String getTwilioNumberFrom();
}
