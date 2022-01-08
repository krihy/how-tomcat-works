package cn.dream.chapter8.loader;

import cn.dream.chapter8.container.Container;
import cn.dream.chapter8.life.LifeCycleBase;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;

public class WebappLoader extends LifeCycleBase implements Loader, Runnable {

    //save static resource in server file system
    public static final String WEB_ROOT = System.getProperty("user.dir") +
            File.separator + "webRoot" + File.separator + "servlet";

    private WebappClassLoader classLoader;
    private String loaderClass;
    private ClassLoader parentClassLoader;
    private boolean delegate;
    private Container container;
    private List<String> repositories;


    public WebappLoader() {
    }

    @Override
    public Class<?> loader(String className) {

        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("loader " + className + "failed" + ex.getMessage(), ex);
        }
    }

    @Override
    public void addRepository(String repository) {

    }

    @Override
    public String[] findRepositories() {
        return new String[0];
    }

    @Override
    public WebappClassLoader getClassLoader() {

        return this.classLoader;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public boolean getDelegate() {
        return this.delegate;
    }

    @Override
    public void setDelegate(boolean delegate) {

        this.delegate = delegate;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public boolean getReloadable() {
        return false;
    }

    @Override
    public void setReloadable(boolean reloadable) {

    }

    @Override
    public boolean modified() {
        return false;
    }

    @Override
    protected void startInternal() {
        //create classloader
        try {
            classLoader = createClassLoader();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //set repository
        classLoader.setDelegate(getDelegate());
        for (String repository : repositories) {
            classLoader.addRepository(repository);
        }
        setRepositories();
        //set the class path
        setClassPath();

        //set permissions
        setPermission();

    }

    @Override
    protected void stopInternal() {
    }

    @Override
    public void run() {

    }

    public void setLoaderClass(String loaderClass) {
        this.loaderClass = loaderClass;
    }

    public String getLoaderClass() {
        return loaderClass;
    }

    /**
     * create a class loader
     *
     * @return classloader
     * @throws Exception
     */
    private WebappClassLoader createClassLoader() throws Exception {
        Class<?> clazz = Class.forName(loaderClass);
        WebappClassLoader classLoader;
        if (parentClassLoader == null) {
            classLoader = (WebappClassLoader) clazz.newInstance();
        } else {
            Class<?>[] argTypes = {ClassLoader.class};
            Object[] args = {parentClassLoader};
            Constructor<?> constructor = clazz.getConstructor(argTypes);
            classLoader = (WebappClassLoader) constructor.newInstance(args);
        }
        return classLoader;
    }

    private void setRepositories() {



    }

    private void setClassPath() {

    }

    private void setPermission() {

    }
}
