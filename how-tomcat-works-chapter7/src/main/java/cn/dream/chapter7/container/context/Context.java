package cn.dream.chapter7.container.context;

import cn.dream.chapter7.container.Container;
import cn.dream.chapter7.container.Loader;

public interface Context extends Container {

    Loader getLoader();

    void setLoader(Loader loader);

    String findServletMapping(String relativeURI);

    void addServletMapping(String relativeURI, String name);

    void setPath(String path);

    String getPath();


}
