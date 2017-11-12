package weather;

import org.json.JSONObject;

public class WeatherReport {
    private int timestamp;
    private String dateTime;
    private int temperature;
    private int temperatureMin;
    private int temperatureMax;

    public WeatherReport(JSONObject weatherReportJSON) {
        this.setTimestamp(weatherReportJSON.getInt("dt"));
        this.setDateTime(weatherReportJSON.getString("dt_txt"));

        final JSONObject main = weatherReportJSON.getJSONObject("main");
        this.setTemperature(convertKelvinToCelsius(main.getDouble("temp")));
        this.setTemperatureMin(convertKelvinToCelsius(main.getDouble("temp_min")));
        this.setTemperatureMax(convertKelvinToCelsius(main.getDouble("temp_max")));
    }

    public int convertKelvinToCelsius(double kelvin) {
        return (int) Math.round(kelvin - 273.15f);
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(int temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public int getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(int temperatureMax) {
        this.temperatureMax = temperatureMax;
    }
}
