package cn.dream.chapter5.container.wrapper;

import cn.dream.chapter5.connect.parameter.Request;
import cn.dream.chapter5.connect.parameter.Response;
import cn.dream.chapter5.container.ValveBase;
import cn.dream.chapter5.container.ValveContext;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleWrapperBasicValve extends ValveBase {

    @Override
    public String getInfo() {
        return "SimpleWrapperValue";
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) {

        SimpleWrapper wrapper = (SimpleWrapper)getContainer();
        ServletRequest sreq = request.getRequest();
        ServletResponse sresp = response.getResponse();

        Servlet servlet;
        HttpServletRequest hreq = null;
        HttpServletResponse hresp = null;
        if (sreq instanceof HttpServletRequest) {
            hreq = (HttpServletRequest)sreq;
        }

        if (sresp instanceof HttpServletResponse) {
            hresp = (HttpServletResponse)sresp;
        }
        try {

            servlet = wrapper.allocate();
            if (hreq != null && hresp != null) {
                System.out.println("call http servlet service");
                servlet.service(hreq, hresp);
            }else {
                System.out.println("call servlet service");
                servlet.service(sreq, sresp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
