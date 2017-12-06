package weather;

import com.google.common.io.Files;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class FileWeatherForecast {
    private File inputFile;

    FileWeatherForecast() {
    }

    void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    List<String> getCitiesFromInputFile() throws IOException {
        return Files.readLines(inputFile, Charset.defaultCharset());
    }
}
