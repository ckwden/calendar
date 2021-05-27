package publicholidays;

import org.junit.Before;
import org.junit.Test;
import publicholidays.controller.ConfigReader;
import publicholidays.controller.ConfigReaderImpl;

import static org.junit.Assert.assertEquals;

public class ConfigReaderTest {

    private ConfigReader reader;

    @Before
    public void setup() {
        reader = new ConfigReaderImpl("src/test/resources/config.json");
    }

    @Test
    public void testRightInfo() {
        assertEquals("randomkey", reader.getHolidayKey());
        assertEquals("randomsid", reader.getTwilioSID());
        assertEquals("randomtoken", reader.getTwilioToken());
        assertEquals("randomnumberto", reader.getTwilioNumberTo());
        assertEquals("randomnumberfrom", reader.getTwilioNumberFrom());
    }
}
