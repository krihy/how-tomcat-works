package cn.dream.chapter8.container.context;

import cn.dream.chapter8.container.Container;
import cn.dream.chapter8.loader.Loader;

public interface Context extends Container {

    Loader getLoader();

    void setLoader(Loader loader);

    String findServletMapping(String relativeURI);

    void addServletMapping(String relativeURI, String name);

    void setPath(String path);

    String getPath();


}
