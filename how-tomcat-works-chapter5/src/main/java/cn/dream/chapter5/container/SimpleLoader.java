package cn.dream.chapter5.container;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

public class SimpleLoader implements Loader {

    //save static resource in server file system
    public static final String WEB_ROOT = System.getProperty("user.dir") +
            File.separator + "webRoot" + File.separator + "servlet";

    private ClassLoader classLoader;

    public SimpleLoader() {

        try {
            URL[] urls = new URL[1];
            File classPath = new File(WEB_ROOT);
            String repository = new URL("file", null,
                    classPath.getCanonicalPath() + File.separator).toString();
            urls[0] = new URL(null, repository, (URLStreamHandler) null);
            //创建 urlClassLoader
            classLoader = new URLClassLoader(urls);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Class<?> loader(String className) {

        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("loader " + className + "failed" + ex.getMessage(), ex);
        }

    }
}
