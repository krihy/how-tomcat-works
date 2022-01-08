package cn.dream.chapter4;

import cn.dream.chapter4.connect.*;

import javax.naming.directory.DirContext;
import javax.servlet.ServletException;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class SimpleContainer implements Container {
    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public Container getParent() {
        return null;
    }

    @Override
    public void setParent(Container container) {

    }

    @Override
    public ClassLoader getParentClassLoader() {
        return null;
    }

    @Override
    public void setParentClassLoader(ClassLoader parent) {

    }

    @Override
    public DirContext getResources() {
        return null;
    }

    @Override
    public void setResources(DirContext resources) {

    }

    @Override
    public void addChild(Container child) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    @Override
    public Container findChild(String name) {
        return null;
    }

    @Override
    public Container[] findChildren() {
        return new Container[0];
    }

    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        String uri = ((HttpRequestBase) request).getRequestURI();
        try {
            if (uri != null && uri.startsWith("/servlet/")) {
                new ServletProcessor().process((HttpRequestBase) request, (HttpResponseBase) response);
            } else {
                new StaticResourceProcessor().process((HttpRequestBase) request, (HttpResponseBase) response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public Container map(Request request, boolean update) {
        return null;
    }

    @Override
    public void removeChild(Container child) {

    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {

    }
}
