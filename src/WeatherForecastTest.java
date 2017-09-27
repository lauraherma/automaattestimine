import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastTest {
    WeatherForecast weatherForecast;

    @BeforeEach
    void setUp() {
        this.weatherForecast = new WeatherForecast();
    }

    @AfterEach
    void tearDown() {
        this.weatherForecast = null;
    }

    @Test
    public void testIsTemperatureInt() {
        int actual = weatherForecast.getTemperature();
        int expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    public void testIsCorrectCurrentTemperature() {
        int actual = weatherForecast.getCurrentTemperature();
        int expected = 1;

        assertEquals(expected, actual);
    }

    @Test
    public void testHasFourDayWeatherForecast() {
        List<WeatherReport> weatherReports = weatherForecast.getNextFourDaysWeatherReports();
        int actual = weatherReports.size();
        int expected = 4;

        assertEquals(expected, actual);
    }

    @Test
    public void testIsCoordinatesInRightFormat() {
        String coordinates = weatherForecast.getCoordinates();
        Pattern pattern = Pattern.compile("[a-zA-Z]{3}:[a-zA-Z]{3}");
        Matcher matcher = pattern.matcher(coordinates);
        boolean expected = matcher.find();
        boolean actual = true;

        assertEquals(expected, actual);
    }

    @Test
    public void testIsCoordinatesRightLength() {
        String coordinates = weatherForecast.getCoordinates();
        int expected = 7;
        int actual = coordinates.length();

        assertEquals(expected, actual);
    }

    @Test
    public void testCurrentDayMaximumTemperature() {
        int expected = 20;
        int actual = weatherForecast.getCurrentDayMaximumTemperature();

        assertEquals(expected, actual);
    }

    @Test
    public void testCurrentDayMinimumTemperature() {
        int expected = 5;
        int actual = weatherForecast.getCurrentDayMinimumTemperature();

        assertEquals(expected, actual);
    }
}