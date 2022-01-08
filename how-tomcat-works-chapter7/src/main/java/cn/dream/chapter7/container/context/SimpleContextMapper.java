package cn.dream.chapter7.container.context;

import cn.dream.chapter7.connect.parameter.HttpRequest;
import cn.dream.chapter7.connect.parameter.Request;
import cn.dream.chapter7.container.Container;
import cn.dream.chapter7.container.Mapper;
import cn.dream.chapter7.container.wrapper.Wrapper;

import javax.servlet.http.HttpServletRequest;

public class SimpleContextMapper implements Mapper {
    private String protocol;
    private SimpleContext context;

    @Override
    public Container map(Request request, boolean update) {
        String contextPath = ((HttpServletRequest) (request.getRequest())).getContextPath();
        String requestURI = ((HttpRequest) request).getDecodedRequestURI();

        String relativeUri = requestURI.substring(contextPath.length());
        Wrapper wrapper = null;
        String name = context.findServletMapping(relativeUri);
        if (name != null) {
            wrapper = (Wrapper) context.findChild(name);
        }
        return wrapper;

    }

    @Override
    public Container getContainer() {
        return context;
    }

    @Override
    public void setContainer(Container container) {
        if (!(container instanceof SimpleContext)) {

            throw new IllegalArgumentException("illegal type of container ");
        }
        this.context = (SimpleContext) container;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public void setProtocol(String protocol) {

        this.protocol = protocol;
    }
}
