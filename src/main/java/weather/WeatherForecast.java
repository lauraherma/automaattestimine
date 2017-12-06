package weather;

import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WeatherForecast {
    private static String OPEN_WEATHER_MAP_TOKEN = "2fbd6d14616656d03ea35090ba7464f9";
    private static final String inputFilePath = "input.txt";

    private String cityName;
    private double latitude;
    private double longitude;
    private String country;

    private List<WeatherReport> weatherReports = new ArrayList<>();
    private String coordinates;

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
        String uri = "http://api.openweathermap.org/data/2.5/forecast?q=" + city + "&appid=" + OPEN_WEATHER_MAP_TOKEN;
        return new URI(uri).toURL().openStream();
    }


    public String getName() {
        return cityName;
    }

    public void setName(String name) {
        this.cityName = name;
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


    private static String getCityFromProgramArguments(String[] arguments) {
        return arguments[0];
    }

    private static boolean cityIsPresentInProgramArguments(String[] arguments) {
        return arguments.length > 0;
    }

    private static WeatherForecast getAndCreateWeatherForecastForCity(String city) {
        JSONObject weatherForecastJSON = WeatherForecast.getWeatherForecastForCity(city);
        WeatherForecast weatherForecast = new WeatherForecast(weatherForecastJSON);
        return weatherForecast;
    }

    private static String readCityFromInputTxt() throws IOException {
        File file = new File(inputFilePath);
        List<String> lines = Files.readLines(file, Charset.defaultCharset());
        return lines.get(0);
    }

    public String getCoordinates() {
        int latitudeDoubleToInteger = (int) getLatitude();
        int longitudeDoubleToInteger = (int) getLongitude();
        String latitude = Integer.toString(latitudeDoubleToInteger);
        String longitude = Integer.toString(longitudeDoubleToInteger);
        String formattedLatitude = StringUtils.leftPad(latitude, 3, "0");
        String formattedLongitude = StringUtils.leftPad(longitude, 3, "0");
        return formattedLatitude + ":" + formattedLongitude;
    }

    @Override
    public String toString() {
        return "WeatherForecast{" +
                "name='" + cityName + '\'' +
                ", country='" + country + '\'' +
                ", coordinates='" + getCoordinates() + '\'' +
                '}';
    }

    private static void writeToFile(WeatherForecast weatherForecast) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(weatherForecast.cityName + ".txt", "UTF-8");
        writer.println("City: " + weatherForecast.getName());
        writer.println("Coordinates: " + weatherForecast.getCoordinates());
        writer.println("Current temperature: " + weatherForecast.getWeatherReports().get(0).getTemperature() + "°C");

        for (int day = 1; day <= 3; day++) {
            List<Integer> averageTemperatures = getAverageFromPeriod(weatherForecast, day);
            int temperatureMin = averageTemperatures.get(0);
            int temperatureMax = averageTemperatures.get(1);
            writer.println("Minimum temperature for day " + day + " is " + temperatureMin + "°C and maximum is " + temperatureMax + "°C");
        }

        writer.close();
    }

    protected static List<Integer> getAverageFromPeriod(WeatherForecast weatherForecast, int day) {
        final int hoursPerReport = 3;
        final int hoursPerDay = 24;
        final int reportsPerDay = hoursPerDay / hoursPerReport;

        int reportsFromIndex = day * reportsPerDay;
        int reportsToIndex = reportsFromIndex + reportsPerDay;

        List<Integer> minimumTemperatures = new ArrayList<>();
        List<Integer> maximumTemperatures = new ArrayList<>();

        for (int dayIndex = reportsFromIndex; dayIndex < reportsToIndex; dayIndex++) {
            final WeatherReport weatherReport = weatherForecast.getWeatherReports().get(dayIndex);
            minimumTemperatures.add(weatherReport.getTemperatureMin());
            maximumTemperatures.add(weatherReport.getTemperatureMax());
        }

        List<Integer> averageTemperatures = new ArrayList<>();
        averageTemperatures.add(minimumTemperatures.stream().mapToInt(temperature -> temperature).sum() / reportsPerDay);
        averageTemperatures.add(maximumTemperatures.stream().mapToInt(temperature -> temperature).sum() / reportsPerDay);

        return averageTemperatures;
    }

    public static Optional<String> getCityFromConsole() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter city: ");

        try {
            return Optional.ofNullable(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void main(String[] arguments) {
        try {
            Optional<String> optionalCity = getCityFromConsole();

            if (optionalCity.isPresent() && !optionalCity.get().equals("")) {
                String city = optionalCity.get();
                writeToFile(getAndCreateWeatherForecastForCity(city));
            } else if (cityIsPresentInProgramArguments(arguments)) {
                String city = getCityFromProgramArguments(arguments);
                writeToFile(getAndCreateWeatherForecastForCity(city));
            } else {
                String city = readCityFromInputTxt();
                writeToFile(getAndCreateWeatherForecastForCity(city));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
