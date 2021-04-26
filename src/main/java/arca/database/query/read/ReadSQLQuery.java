package arca.database.query.read;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadSQLQuery {


    public static final String read(final String name) {
        final StringBuffer buffer = new StringBuffer();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(String.format("querys/%s.sql", name)))) {
            String line;
            while ((line = br.readLine()) != null) {
                buffer.append(String.format("%s ", line.trim()));
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return buffer.toString();
    }
}
