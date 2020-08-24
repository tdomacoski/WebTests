package arca.logger;

public interface Logger {
    void add(final String log);
    void error(final Exception error);
}
