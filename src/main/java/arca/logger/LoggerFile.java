package arca.logger;

import arca.properties.LoadProperties;
import arca.util.DateUtils;

import java.io.File;
import java.io.FileWriter;

public class LoggerFile  implements Logger {
    private static String path;

    static {
        path = LoadProperties.get(LoadProperties.loggerPath);
        final File pathFile = new File(path);
        if(!pathFile.exists()){
            pathFile.mkdirs();
        }
    }

    @Override
    public void add(String log) {
        System.out.println(log);
        write(log);

    }

    @Override
    public void error(Exception error) {
        error.printStackTrace();
        write(error.toString());
    }


    private  final void write(final String log){
        try {
            final FileWriter writer = new FileWriter(getFile(), true);
            writer.write(log);
            writer.close();
        }catch (final Exception e){
            e.printStackTrace();
        }
    }

    private final File getFile(){
        return new File(String.format("%s%s.txt", path, DateUtils.ddMMyy.format(System.currentTimeMillis())));
    }

    public static final void debug(final String s){
        System.out.println(s);
    }


}
