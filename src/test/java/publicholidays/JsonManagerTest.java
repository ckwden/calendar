package publicholidays;

import org.junit.Test;
import publicholidays.model.JsonManager;
import publicholidays.model.holiday.Holiday;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonManagerTest {

    @Test
    public void testCorrectHoliday() {
        String response = "[{\"name\": \"New Year's Day\",  \n" +
                "  \"name_local\": \"\",  \n" +
                "  \"language\": \"\",  \n" +
                "  \"description\": \"\",  \n" +
                "  \"country\": \"SG\",  \n" +
                "  \"location\": \"\",\n" +
                "  \"type\": \"public_holiday\",  \n" +
                "  \"date\": \"1/1/2020\",  \n" +
                "  \"date_year\": \"2020\",  \n" +
                "  \"date_month\":\"1\",  \n" +
                "  \"date_day\": \"1\",  \n" +
                "  \"week_day\": \"Wednesday\"}]";
        List<Holiday> holidays = JsonManager.getHoliday(response);
        Holiday holiday = holidays.get(0);
        assertEquals("New Year's Day", holiday.getName());
        assertEquals(2020, holiday.getYear());
        assertEquals(1, holiday.getMonth());
        assertEquals(1, holiday.getDay());
    }
}
