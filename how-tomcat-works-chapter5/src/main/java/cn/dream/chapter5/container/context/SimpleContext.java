package cn.dream.chapter5.container.context;

import cn.dream.chapter5.connect.parameter.HttpRequest;
import cn.dream.chapter5.connect.parameter.Request;
import cn.dream.chapter5.connect.parameter.Response;
import cn.dream.chapter5.container.ContainerBase;
import cn.dream.chapter5.container.Loader;
import cn.dream.chapter5.util.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class SimpleContext extends ContainerBase implements Context{

  //  private Map<String, Wrapper> wrappers = new HashMap<>();
    private Loader loader;
    private final Map<String, String> servletMapping = new HashMap<>();


    public SimpleContext() {
        pipeline.setBasic(new SimpleContextBasicValve());
        //set defaultMapper for context
       // addDefaultMapper("cn.dream.chapter5.container" +
        //        ".context.SimpleContextMapper");
    }

    @Override
    public Loader getLoader() {
        return loader;
    }

    @Override
    public void setLoader(Loader loader) {

        this.loader = loader;
    }

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
            ((HttpRequest)request).setContextPath(getPath());
        }
        super.invoke(request, response);
    }
}