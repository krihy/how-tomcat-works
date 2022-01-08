package cn.dream.chapter5;

import cn.dream.chapter5.connect.HttpConnector;
import cn.dream.chapter5.container.*;
import cn.dream.chapter5.container.context.Context;
import cn.dream.chapter5.container.context.SimpleContext;
import cn.dream.chapter5.container.context.SimpleContextMapper;
import cn.dream.chapter5.container.wrapper.ClientIpValve;
import cn.dream.chapter5.container.wrapper.SimpleWrapper;
import cn.dream.chapter5.container.wrapper.Wrapper;

import java.io.IOException;

/**
 * 启动类
 */
public class Bootstrap {

    public static void main(String[] args) {
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("primitive");
        wrapper1.setServletClass("cn.dream.chapter5.servlet.PrimitiveServlet");

        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("modern");
        wrapper2.setServletClass("cn.dream.chapter5.servlet.ModernServlet");

        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("HTTP/1.1");

        Context context = new SimpleContext();
        Loader loader = new SimpleLoader();
        context.setLoader(loader);
        context.setPath("/servlet");
        context.addChild(wrapper1);
        context.addChild(wrapper2);
        context.addMapper(mapper);
        context.addServletMapping("/primitive", "primitive" );
        context.addServletMapping("/modern", "modern" );

        Valve valve1 = new DateValve();
        Valve valve2 = new ClientIpValve();
        ((Pipeline)context).addValve(valve1);
        ((Pipeline)context).addValve(valve2);

        HttpConnector connector = new HttpConnector();
        connector.setContainer(context);
        connector.start();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
