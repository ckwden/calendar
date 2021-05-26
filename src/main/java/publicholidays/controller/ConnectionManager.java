package publicholidays.controller;

/**
 * Retrieves the data sent from the Holidays API
 */
public interface ConnectionManager {

    /**
     * Retrieves the data received after querying the Holidays API
     * @param link The Holidays API url
     * @param key The user's key for the API
     * @param countryCode The ISO 3166 Alpha 2 code of the country required for the API query
     * @param dayOfMonth The day of the month that the user wishes to query
     * @param month The month in terms of its number that the user wishes to query
     * @param year The year that the user wishes to query
     * @return A string in JSON format representing the retreieved data from the Holidays API
     */
    String getData(String link, String key, String countryCode, int dayOfMonth, int month, int year);
}
