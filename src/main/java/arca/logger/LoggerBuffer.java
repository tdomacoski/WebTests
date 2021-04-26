package arca.logger;

public class LoggerBuffer implements Logger{

    private final StringBuilder builder = new StringBuilder();
    @Override
    public void add(String log) {
        builder.append(String.format("%s\n", log));
    }

    @Override
    public void error(Exception error) {
        builder.append(String.format("%s\n", error.toString()));
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
