package weather;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastTest {
    WeatherForecast weatherForecast;
    WeatherReport weatherReport;

    @BeforeEach
    void setUp() {
        final String jsonData = getLinesFromFile("api-mock-data.json");
        JSONObject mockWeatherReport = new JSONObject(jsonData);
        this.weatherForecast = new WeatherForecast(mockWeatherReport);
        this.weatherReport = this.weatherForecast.weatherReports.get(0);
    }

    public static String getLinesFromFile(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            return StringUtils.join(stream.collect(Collectors.toList()), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{}";

    }

    @Test
    public void testIsTemperatureInt() {
        boolean parsable = true;

        try {
            weatherReport.getTemperature();
        } catch (NumberFormatException e) {
            parsable = false;
        }

        assertEquals(parsable, true);
    }

    @Test
    public void testIsCorrectCurrentTemperature() {
        int actual = weatherReport.getTemperature();
        int expected = 14;

        assertEquals(expected, actual);
    }

    @Test
    public void testHasFourDayWeatherForecast() {
        List<WeatherReport> weatherReports = weatherForecast.getWeatherReports();
        int actual = weatherReports.size();
        int expected = 4 * 8;

        assertEquals(expected, actual);
    }

//
//    @Test
//    public void testIsCoordinatesInRightFormat() {
//        String coordinates = weatherForecast.getCoordinates();
//        Pattern pattern = Pattern.compile("[a-zA-Z]{3}:[a-zA-Z]{3}");
//        Matcher matcher = pattern.matcher(coordinates);
//        boolean expected = matcher.find();
//        boolean actual = true;
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    public void testIsCoordinatesRightLength() {
//        String coordinates = weatherForecast.getCoordinates();
//        int expected = 7;
//        int actual = coordinates.length();
//
//        assertEquals(expected, actual);
//    }

    @Test
    public void testCurrentDayMaximumTemperature() {
        int expected = 14;
        int actual = weatherForecast.getCurrentDayMaximumTemperature();

        assertEquals(expected, actual);
    }

    @Test
    public void testCurrentDayMinimumTemperature() {
        int expected = 8;
        int actual = weatherForecast.getCurrentDayMinimumTemperature();

        assertEquals(expected, actual);
    }
    @Test
    public void testWeatherForecastName(){
        String expected="Altstadt";
        String actual=weatherForecast.getName();
        assertEquals(expected,actual);
    }
    @Test
    public void testWeatherForecastLatitude(){
        Double expected=48.137;
        Double actual=weatherForecast.getLatitude();
        assertEquals(expected,actual);
    }
    @Test
    public void testWeatherForecastLongitude(){
        Double expected=11.5752;
        Double actual=weatherForecast.getLongitude();
        assertEquals(expected,actual);
    }
    @Test
    public void testWeatherForecastCountry(){
        String expected="none";
        String actual=weatherForecast.getCountry();
        assertEquals(expected,actual);
    }
    @Test
    public void testWeatherForecastWeatherReportCount(){
        int expected=32;
        int actual=weatherForecast.getWeatherReports().size();
        assertEquals(expected,actual);
    }
}