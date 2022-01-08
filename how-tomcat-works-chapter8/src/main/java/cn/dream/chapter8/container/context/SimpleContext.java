package cn.dream.chapter8.container.context;

import cn.dream.chapter8.connect.parameter.HttpRequest;
import cn.dream.chapter8.connect.parameter.Request;
import cn.dream.chapter8.connect.parameter.Response;
import cn.dream.chapter8.container.ContainerBase;
import cn.dream.chapter8.util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SimpleContext extends ContainerBase implements Context {

    private final Map<String, String> servletMapping = new HashMap<>();

    @Override
    public String findServletMapping(String relativeURI) {
        synchronized (servletMapping) {
            return servletMapping.get(relativeURI);
        }
    }

    @Override
    public void addServletMapping(String relativeURI, String name) {
        servletMapping.putIfAbsent(relativeURI, name);
    }

    @Override
    public void setPath(String path) {

        setName(RequestUtil.URLDecode(path));
    }

    @Override
    public String getPath() {
        return name;
    }

    @Override
    public void invoke(Request request, Response response) throws ServletException {
        if (request instanceof HttpServletRequest) {
            ((HttpRequest) request).setContextPath(getPath());
        }
        super.invoke(request, response);
    }

    @Override
    protected void startInternal() {
        pipeline.setBasic(new SimpleContextBasicValve());
        super.startInternal();
    }
}