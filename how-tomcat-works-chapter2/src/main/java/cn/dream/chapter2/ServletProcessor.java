package cn.dream.chapter2;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ServletProcessor {

    public void process(Request request, Response response) throws Exception {

        //获取servlet name
        String requestUri = request.getRequestUri();
        String servletName = "cn.dream.chapter2." +
                requestUri.substring(requestUri.lastIndexOf("/") + 1);
        URL[] urls = new URL[1];
        File classPath = new File(Constant.WEB_ROOT
                + "/servlet/");
        String repository = new URL("file", null,
                classPath.getCanonicalPath() + File.separator).toString();
        urls[0] = new URL(null, repository);
        //创建url urlClassLoader
        ClassLoader classLoader = new URLClassLoader(urls);
        Class<?> servletClass = classLoader.loadClass(servletName);
        //加载类，通过反射创建对象
        Servlet servlet = (Servlet) servletClass.newInstance();
        //调用servlet service 方法
        // servlet.service((ServletRequest) request, (ServletResponse) response);
        //采用门面模式,对servlet 编程人员屏蔽 parse 和 sendStaticResource 方法
        servlet.service(new RequestFacade(request), new ResponseFacade(response));

    }
}
