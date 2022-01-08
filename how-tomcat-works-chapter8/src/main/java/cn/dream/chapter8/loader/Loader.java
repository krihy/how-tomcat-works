package cn.dream.chapter8.loader;

import cn.dream.chapter8.container.Container;

public interface Loader {

    Class<?> loader(String className);


    void addRepository(String repository);

    String[] findRepositories();

    WebappClassLoader getClassLoader();


    void setContainer(Container container);

    Container getContainer();

    boolean getDelegate();

    void setDelegate(boolean delegate);

    String getInfo();

    boolean getReloadable();

    void setReloadable(boolean reloadable);

    boolean modified();

//    void addPropertyChangeListener();


}
