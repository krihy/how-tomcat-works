package cn.dream.chapter8.container.wrapper;

import cn.dream.chapter8.container.ContainerBase;
import cn.dream.chapter8.loader.Loader;
import cn.dream.chapter8.container.context.Context;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

public class SimpleWrapper extends ContainerBase implements Wrapper {
    private Loader loader;

    private String servletName;

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
            return ((Context) parent).getLoader();
        }
        return null;
    }

    @Override
    protected void startInternal() {
        pipeline.setBasic(new SimpleWrapperBasicValve());
        super.startInternal();
    }
}
