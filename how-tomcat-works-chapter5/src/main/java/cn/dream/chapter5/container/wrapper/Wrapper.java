package cn.dream.chapter5.container.wrapper;

import cn.dream.chapter5.container.Container;
import cn.dream.chapter5.container.Loader;

import javax.servlet.Servlet;

public interface Wrapper extends Container {

    Servlet allocate() throws javax.servlet.ServletException;

   // void load() throws javax.servlet.ServletException;

    void setServletClass(String name);

    String getServletClass();

    void setLoader(Loader loader);

    Loader getLoader();
}
