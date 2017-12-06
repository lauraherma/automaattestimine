package weather;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WeatherForecastWriter {
    private WeatherForecast weatherForecast;

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    void writeToFile() throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(weatherForecast.getName() + ".txt", "UTF-8");
        writer.println("City: " + weatherForecast.getName());
        writer.println("Coordinates: " + weatherForecast.getCoordinates());

        if (weatherForecast.getWeatherReports().size() > 0) {
            writer.println("Current temperature: " + weatherForecast.getWeatherReports().get(0).getTemperature() + "°C");
        }

        for (int day = 1; day <= 3; day++) {
            if (weatherForecast.getWeatherReports().size() > 0) {
                List<Integer> averageTemperatures = getAverageFromPeriod(weatherForecast, day);
                int temperatureMin = averageTemperatures.get(0);
                int temperatureMax = averageTemperatures.get(1);
                writer.println("Minimum temperature for day " + day + " is " + temperatureMin + "°C and maximum is " + temperatureMax + "°C");
            }
        }

        writer.close();
    }

    List<Integer> getAverageFromPeriod(WeatherForecast weatherForecast, int day) {
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
}
