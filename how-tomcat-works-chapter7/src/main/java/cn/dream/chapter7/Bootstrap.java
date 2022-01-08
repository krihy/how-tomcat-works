package cn.dream.chapter7;

import cn.dream.chapter7.connect.HttpConnector;
import cn.dream.chapter7.container.*;
import cn.dream.chapter7.container.context.Context;
import cn.dream.chapter7.container.context.DateValve;
import cn.dream.chapter7.container.context.SimpleContext;
import cn.dream.chapter7.container.context.SimpleContextMapper;
import cn.dream.chapter7.container.context.ClientIpValve;
import cn.dream.chapter7.container.wrapper.SimpleWrapper;
import cn.dream.chapter7.container.wrapper.Wrapper;
import cn.dream.chapter7.life.LifeCycle;
import cn.dream.chapter7.life.SimpleContextListener;

import java.io.IOException;

/**
 * 启动类
 */
public class Bootstrap {

    public static void main(String[] args) {
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("primitive");
        wrapper1.setServletClass("cn.dream.cn.dream.chapter8.servlet.PrimitiveServlet");

        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("modern");
        wrapper2.setServletClass("cn.dream.cn.dream.chapter8.servlet.ModernServlet");

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("HTTP/1.1");

        Context context = new SimpleContext();
        context.setName("SimpleContext");
        Loader loader = new SimpleLoader();
        context.setLoader(loader);
        context.setPath("/servlet");
        context.addChild(wrapper1);
        context.addChild(wrapper2);
        context.addMapper(mapper);
        context.addServletMapping("/primitive", "primitive");
        context.addServletMapping("/modern", "modern");
        ((LifeCycle)context).addLifeCycleListener(new SimpleContextListener());

        Valve valve1 = new DateValve();
        Valve valve2 = new ClientIpValve();
        ((Pipeline) context).addValve(valve1);
        ((Pipeline) context).addValve(valve2);

        ((LifeCycle) context).start();

        HttpConnector connector = new HttpConnector();
        connector.setContainer(context);
        connector.start();
        try {
            System.in.read();

        } catch (IOException e) {
            e.printStackTrace();
        }
        ((LifeCycle) context).stop();
        System.exit(0);
    }
}
