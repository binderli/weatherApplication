
package de.fh.albsig.binderlisa;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import junit.framework.TestCase;

/**
 * @author Lisa Binder Matrikelnummer: 86099
 *
 */
@TestInstance(value = Lifecycle.PER_CLASS)
public class ParseAndFormatTest extends TestCase {

    private static ClassLoader LOADER = ParseAndFormatTest.class
            .getClassLoader();
    public Weather mockedWeather;
    private Weather weather;
    private InputStream testData;
    private XMLOutput xml;
    private static Logger log = Logger.getLogger(YahooRetriever.class);

    @BeforeAll
    void init() {

        weather = null;
        mockedWeather = null;
        xml = new XMLOutput();

    }

    @BeforeEach
    void initWeather() {
        weather = new Weather();
    }

    @BeforeEach
    void initMock() {
        mockedWeather = mock(Weather.class);
        when(mockedWeather.getCity()).thenReturn("Berlin");
        when(mockedWeather.getCity()).thenReturn("Berlin");
        when(mockedWeather.getRegion()).thenReturn(" BE");
        when(mockedWeather.getCountry()).thenReturn("Germany");
        when(mockedWeather.getTemp()).thenReturn("0");
        when(mockedWeather.getCondition()).thenReturn("Cloudy");
        when(mockedWeather.getChill()).thenReturn("3");
        when(mockedWeather.getHumidity()).thenReturn("50");

    }

    @Test
    public void testParser() throws DocumentException {
        testData = LOADER.getResourceAsStream("testWeather.xml");
        final Weather weather = new YahooParser().parse(testData);
        assertEquals("Seoul", weather.getCity());
        assertEquals(" Seoul", weather.getRegion());
        assertEquals("South Korea", weather.getCountry());
        assertEquals("30", weather.getTemp());
        assertEquals("Clear", weather.getCondition());
        assertEquals("25", weather.getChill());
        assertEquals("43", weather.getHumidity());
    }

    @Test
    public void weatherObjectTest() {

        weather.setCity("Berlin");
        weather.setRegion(" BE");
        weather.setCountry("Germany");
        weather.setTemp("0");
        weather.setCondition("Cloudy");
        weather.setChill("3");
        weather.setHumidity("50");
        assertEquals(weather.getCity(), mockedWeather.getCity());
        assertEquals(weather.getRegion(), mockedWeather.getRegion());
        assertEquals(weather.getCountry(), mockedWeather.getCountry());
        assertEquals(weather.getTemp(), mockedWeather.getTemp());
        assertEquals(weather.getCondition(), mockedWeather.getCondition());
        assertEquals(weather.getChill(), mockedWeather.getChill());
        assertEquals(weather.getHumidity(), mockedWeather.getHumidity());
    }

    @Test
    public void writeToXmlTest() throws IOException {
        String testContent;
        String outputContent;
        weather = new Weather();
        weather.setCity("Berlin");
        weather.setRegion(" BE");
        weather.setCountry("Germany");
        weather.setTemp("0");
        weather.setCondition("Cloudy");
        weather.setChill("3");
        weather.setHumidity("50");
        xml.writeToXML(weather, "src/test/resources/");
        testContent = FileIO.read("src/test/resources/testOutput.xml");
        outputContent = FileIO.read("src/test/resources/WeatherBerlin.xml");
        assertEquals(testContent, outputContent);

    }

    @AfterEach
    void resetWeather() throws IOException {
        weather = null;
        testData = null;

    }

    @AfterAll
    void resetAll() throws IOException {
        xml = null;
        Files.delete(Paths.get("src/test/resources/WeatherBerlin.xml"));
    }

}