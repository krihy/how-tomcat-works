package cn.dream.common;

import java.io.File;
import java.time.format.DateTimeFormatter;

public class Constants {

    //save static resource in server file system
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webRoot";

    public static final String Package = "cn.dream.common";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;

    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//    public static void main(String[] args) {
//       String s = Constants.FORMATTER.format(LocalDateTime.now());
//        System.out.println(s);
//    }
}
