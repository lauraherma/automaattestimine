package weather;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    private static String OPEN_WEATHER_MAP_TOKEN = "7b8aedb7fbb758c13688845658637a2b";
    private static String OPEN_WEATHER_MAP_UNITS = "metric";

    private String name;
    private double latitude;
    private double longitude;
    private String country;

    List<WeatherReport> weatherReports = new ArrayList<>();

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

    public static void main(String[] args) {
        try {
            JSONObject weatherForecastJSON = WeatherForecast.getWeatherForecastForCity("Tallinn");
            WeatherForecast weatherForecast = new WeatherForecast(weatherForecastJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
