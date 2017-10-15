package weather;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    private static String OPEN_WEATHER_MAP_TOKEN = "7b8aedb7fbb758c13688845658637a2b";
    private static String OPEN_WEATHER_MAP_UNITS = "metric";

    private String city;
    private String name;
    private double latitude;
    private double longitude;
    private String country;

    List<WeatherReport> weatherReports = new ArrayList<WeatherReport>();

    public static JSONObject getWeatherForecastForCity(String city) {
        try {
            String url = "http://samples.openweathermap.org/data/2.5/forecast?q=" + city + ",us&appid=" + OPEN_WEATHER_MAP_TOKEN;
            URI uri = new URI(url);
            JSONTokener tokener = new JSONTokener(uri.toURL().openStream());
            return new JSONObject(tokener);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public WeatherForecast(JSONObject weatherForecast) {
        JSONObject city = weatherForecast.getJSONObject("city");

        this.name = city.getString("name");
        this.latitude = city.getJSONObject("coord").getDouble("lat");
        this.longitude = city.getJSONObject("coord").getDouble("lon");
        this.country = city.getString("country");

        JSONArray weatherReports = weatherForecast.getJSONArray("list");

        int fourDays = 8 * 4;
        for (int i = 0; i < fourDays; i++) {
            JSONObject weatherReportJSON = (JSONObject) weatherReports.get(i);
            final WeatherReport weatherReport = new WeatherReport(weatherReportJSON);
            this.weatherReports.add(weatherReport);
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public int getCurrentDayMaximumTemperature () {
        return this.weatherReports.get(0).getTemperatureMax();
    }


    public int getCurrentDayMinimumTemperature () {
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
