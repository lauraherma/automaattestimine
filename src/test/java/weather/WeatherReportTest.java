package weather;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherReportTest {
    WeatherReport weatherReport;

    @BeforeEach
    void setUp() {
        String jsonData = "{\n" +
                "      \"dt\": 1487624400,\n" +
                "      \"main\": {\n" +
                "        \"temp\": 272.424,\n" +
                "        \"temp_min\": 272.424,\n" +
                "        \"temp_max\": 272.424,\n" +
                "        \"pressure\": 968.38,\n" +
                "        \"sea_level\": 1043.17,\n" +
                "        \"grnd_level\": 968.38,\n" +
                "        \"humidity\": 85,\n" +
                "        \"temp_kf\": 0\n" +
                "      },\n" +
                "      \"weather\": [\n" +
                "        {\n" +
                "          \"id\": 801,\n" +
                "          \"main\": \"Clouds\",\n" +
                "          \"description\": \"few clouds\",\n" +
                "          \"icon\": \"02n\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"clouds\": {\n" +
                "        \"all\": 20\n" +
                "      },\n" +
                "      \"wind\": {\n" +
                "        \"speed\": 3.57,\n" +
                "        \"deg\": 255.503\n" +
                "      },\n" +
                "      \"rain\": {},\n" +
                "      \"snow\": {},\n" +
                "      \"sys\": {\n" +
                "        \"pod\": \"n\"\n" +
                "      },\n" +
                "      \"dt_txt\": \"2017-02-20 21:00:00\"\n" +
                "    }";
        JSONObject mockWeatherReport = new JSONObject(jsonData);
        this.weatherReport = new WeatherReport(mockWeatherReport);
    }
    @Test
    public void testWeatherReportTimestamp(){
        int expected=1487624400;
        int actual=weatherReport.getTimestamp();
        assertEquals(expected, actual);
    }
    @Test
    public void testWeatherReportTemperature(){
        int expected=-1;
        int actual=weatherReport.getTemperature();
        assertEquals(expected, actual);
    }
    @Test
    public void testWeatherReportTemperatureMax(){
        int expected=-1;
        int actual=weatherReport.getTemperatureMax();
        assertEquals(expected, actual);
    }
    @Test
    public void testWeatherReportTemperatureMin(){
        int expected=-1;
        int actual=weatherReport.getTemperatureMin();
        assertEquals(expected, actual);
    }
    @Test
    public void testWeatherReportDateTime(){
        String expected="2017-02-20 21:00:00";
        String actual=weatherReport.getDateTime();
        assertEquals(expected, actual);
    }
    @Test
    public void testKelvinToCelsius(){
        int expected=27;
        int actual=weatherReport.convertKelvinToCelsius(300);
        assertEquals(expected, actual);
    }
}
