package cn.dream.chapter5.container.wrapper;

import cn.dream.chapter5.container.Container;
import cn.dream.chapter5.container.ContainerBase;
import cn.dream.chapter5.container.Loader;
import cn.dream.chapter5.container.context.Context;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class SimpleWrapper extends ContainerBase implements Wrapper {
    private Loader loader;

    private String servletName;

    public SimpleWrapper() {
        pipeline.setBasic(new SimpleWrapperBasicValve());
    }

    @Override
    public Servlet allocate() throws ServletException {
        Class<?> servletClass = getLoader().loader(getServletClass());
        try {
            return (Servlet) servletClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setServletClass(String name) {
        this.servletName = name;
    }

    @Override
    public String getServletClass() {
        return this.servletName;
    }

    @Override
    public void setLoader(Loader loader) {
        this.loader = loader;
    }

    @Override
    public Loader getLoader() {
        if (loader != null) {
            return loader;
        }
        if (parent != null) {
            return ((Context)parent).getLoader();
        }
        return null;
    }

}
