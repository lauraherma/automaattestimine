package weather;

import com.google.common.io.Files;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    private static String OPEN_WEATHER_MAP_TOKEN = "2fbd6d14616656d03ea35090ba7464f9";
    private static String OPEN_WEATHER_MAP_UNITS = "metric";
    private static final String inputFilePath = "input.txt";

    private String name;
    private double latitude;
    private double longitude;
    private String country;

    List<WeatherReport> weatherReports = new ArrayList<>();
    private String coordinates;

    public static JSONObject getWeatherForecastForCity(String city) {
        try {
            InputStream inputStream = getWeatherForecastApiUriStream(city);
            return getJsonObject(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static JSONObject getJsonObject(InputStream inputStream) {
        JSONTokener tokener = new JSONTokener(inputStream);
        return new JSONObject(tokener);
    }

    public static InputStream getWeatherForecastApiUriStream(String city) throws URISyntaxException, IOException {
        String uri = "http://samples.openweathermap.org/data/2.5/forecast?q=" + city + ",us&appid=" + OPEN_WEATHER_MAP_TOKEN;
        return new URI(uri).toURL().openStream();
    }

    WeatherForecast(JSONObject weatherForecast) {
        JSONObject city = weatherForecast.getJSONObject("city");

        this.setName(city.getString("name"));
        this.setLatitude(city.getJSONObject("coord").getDouble("lat"));
        this.setLongitude(city.getJSONObject("coord").getDouble("lon"));
        this.setCountry(city.getString("country"));

        JSONArray weatherReportsJson = weatherForecast.getJSONArray("list");
        List<WeatherReport> weatherReports = new ArrayList<>();

        int fourDays = 8 * 4;
        for (int i = 0; i < fourDays; i++) {
            JSONObject weatherReportJSON = (JSONObject) weatherReportsJson.get(i);
            final WeatherReport weatherReport = new WeatherReport(weatherReportJSON);
            weatherReports.add(weatherReport);
        }

        this.setWeatherReports(weatherReports);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<WeatherReport> getWeatherReports() {
        return weatherReports;
    }

    public void setWeatherReports(List<WeatherReport> weatherReports) {
        this.weatherReports = weatherReports;
    }

    public int getCurrentDayMaximumTemperature() {
        return this.weatherReports.get(0).getTemperatureMax();
    }


    public int getCurrentDayMinimumTemperature() {
        return this.weatherReports.get(0).getTemperatureMin();
    }

    public static void main(String[] arguments) {
        try {
            if (cityIsPresentInProgramArguments(arguments)) {
                String city = getCityFromProgramArguments(arguments);
                getAndCreateWeatherForecastForCity(city);
            }
            else {
                String city = readCityFromInputTxt();
                getAndCreateWeatherForecastForCity(city);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getCityFromProgramArguments(String[] arguments) {
        return arguments[0];
    }

    private static boolean cityIsPresentInProgramArguments(String[] arguments) {
        return arguments.length > 0;
    }

    private static void getAndCreateWeatherForecastForCity (String city) {
        JSONObject weatherForecastJSON = WeatherForecast.getWeatherForecastForCity(city);
        WeatherForecast weatherForecast = new WeatherForecast(weatherForecastJSON);
    }

    private static String readCityFromInputTxt() throws IOException {
        File file = new File(inputFilePath);
        List<String> lines = Files.readLines(file, Charset.defaultCharset());
        return lines.get(0);
    }

    public String getCoordinates() {
        return null;
    }
}
