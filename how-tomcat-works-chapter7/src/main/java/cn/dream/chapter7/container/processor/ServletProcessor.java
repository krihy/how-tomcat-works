package cn.dream.chapter7.container.processor;

import cn.dream.chapter7.connect.parameter.HttpRequestBase;
import cn.dream.chapter7.connect.parameter.HttpRequestFacade;
import cn.dream.chapter7.connect.parameter.HttpResponseBase;
import cn.dream.chapter7.connect.parameter.HttpResponseFacade;
import cn.dream.common.Constants;

import javax.servlet.Servlet;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class ServletProcessor {

    public void process(HttpRequestBase request, HttpResponseBase response) throws Exception {

        //获取servlet name
        String requestUri = request.getRequestURI();
        String servletName = "cn.dream.chapter5." +
                requestUri.substring(requestUri.lastIndexOf("/") + 1);
        URL[] urls = new URL[1];
        File classPath = new File(Constants.WEB_ROOT
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
        //采用门面模式,对servlet 编程人员屏蔽 parse 和 sendStaticResource 方法
        servlet.service(new HttpRequestFacade(request), new HttpResponseFacade(response));

    }
}
