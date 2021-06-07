package publicholidays;

import org.junit.Before;
import org.junit.Test;
import publicholidays.model.ConfigReader;
import publicholidays.model.ConfigReaderImpl;

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
        assertEquals("+123242344", reader.getTwilioNumberTo());
        assertEquals("+123242345", reader.getTwilioNumberFrom());
    }
}
