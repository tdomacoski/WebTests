package arca.logger;

public class LoggerEmpty  implements  Logger{
    @Override
    public void add(String log) {
    }
    @Override
    public void error(Exception error) {
    }
}
