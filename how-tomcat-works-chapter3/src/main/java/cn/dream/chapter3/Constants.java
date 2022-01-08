package cn.dream.chapter3;

import java.io.File;

public class Constants {

    //save static resource in server file system
    static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webRoot";

    public static final String Package = "cn.dream.chapter3";
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;

    public static final int PROCESSOR_IDLE = 0;
    public static final int PROCESSOR_ACTIVE = 1;
}
