package weather;

import com.google.common.io.Files;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;

public class FileCityNames {
    private File inputFile;


    void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    List<String> readCitiesFromInputFile() throws IOException {
        return Files.readLines(inputFile, Charset.defaultCharset());
    }
}
