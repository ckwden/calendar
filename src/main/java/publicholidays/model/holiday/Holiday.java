package publicholidays.model.holiday;

/**
 * Models a public holiday from the Holiday API
 */
public interface Holiday {

    String getName();

    int getYear();

    int getMonth();

    int getDay();

}
