package publicholidays.controller;

import publicholidays.model.holiday.Holiday;

public interface JSONManager {

    /**
     * Creates the Holiday object representing the model sent from the Holiday API upon request
     * @param response The response from the Holiday API from user query
     * @return The Holiday object of the model, null if the query finds that the date from the user query is not a
     * holiday
     */
    Holiday getHoliday(String response);
}
