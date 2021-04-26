package arca.logger;

import arca.properties.LoadProperties;
import arca.util.DateUtils;

import java.io.File;
import java.io.FileWriter;

public class LoggerFile  implements Logger {
    private String path;
    private final String name;

    public LoggerFile(){
        this.name = "";
        checkFolder();
    }

    public LoggerFile(final String name){
        this.name = name.toLowerCase().trim().replaceAll(" ", "_");
        checkFolder();
    }
    private final void checkFolder(){
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
//            writer.write(String.format("%s\n", log));
            writer.write(String.format("[%s]: %s\n", DateUtils.currentHours(), log));
            writer.close();
        }catch (final Exception e){
            e.printStackTrace();
        }
    }

    private final File getFile(){
        return new File(String.format("%s%s_%s.txt", path, name,DateUtils.ddMMyy.format(System.currentTimeMillis())));
    }

    public static final void debug(final String s){
        System.out.println(s);
    }


    public String getName() {
        return name;
    }
}
