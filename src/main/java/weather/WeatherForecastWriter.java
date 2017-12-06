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
                List<Integer> averageTemperatures = weatherForecast.getAverageForPeriod(day);
                int temperatureMin = averageTemperatures.get(0);
                int temperatureMax = averageTemperatures.get(1);
                writer.println("Minimum temperature for day " + day + " is " + temperatureMin + "°C and maximum is " + temperatureMax + "°C");
            }
        }

        writer.close();
    }


}
