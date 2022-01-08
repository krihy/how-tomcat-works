package cn.dream.chapter8.container.context;

import cn.dream.chapter8.connect.parameter.Request;
import cn.dream.chapter8.connect.parameter.Response;
import cn.dream.chapter8.container.ValveBase;
import cn.dream.chapter8.container.ValveContext;
import cn.dream.chapter8.container.wrapper.Wrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class SimpleContextBasicValve extends ValveBase {

    @Override
    public String getInfo() {
        return "simple context basic valve";
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws ServletException {

        HttpServletRequest hreq = (HttpServletRequest) request.getRequest();

        // String contextPath = hreq.getContextPath();

        Wrapper wrapper = null;
        try {
            wrapper = (Wrapper) (getContainer()).map(request, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (wrapper == null) {
            System.out.println("wrapper not found for request" +
                    ((HttpServletRequest) request.getRequest()).getRequestURI());
            return;
        }
        try {
            wrapper.invoke(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    protected void startInternal() {

    }

    @Override
    protected void stopInternal() {

    }
}
