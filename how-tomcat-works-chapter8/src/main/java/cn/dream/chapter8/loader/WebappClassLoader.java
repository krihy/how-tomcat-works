package cn.dream.chapter8.loader;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class WebappClassLoader extends URLClassLoader implements Reloader {
    private String jarPath ;
    private boolean delegate;

    public WebappClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public WebappClassLoader(URL[] urls) {
        super(urls);
    }

    public WebappClassLoader(URL[] urls, ClassLoader parent, URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    public void addRepository(String repository) {
        if (repository.startsWith("/WEB-INF/lib") ||
                repository.startsWith("WEB-INF/classes")) {
            return;
        }

        try {

            URL url = new URL(repository);
            addURL(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String[] findRepositories() {
        return new String[0];
    }

    @Override
    public boolean modified() {
        return false;
    }

    public void setJarPath(String jarPath) {
        this.jarPath  = jarPath;
    }

    public void setDelegate(boolean delegate) {
        this.delegate = delegate;
    }

    public boolean getDelegate() {
        return this.delegate;
    }
}
