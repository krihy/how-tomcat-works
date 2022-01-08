package cn.dream.chapter5.container.context;

import cn.dream.chapter5.connect.parameter.Request;
import cn.dream.chapter5.connect.parameter.Response;
import cn.dream.chapter5.container.ValveBase;
import cn.dream.chapter5.container.ValveContext;
import cn.dream.chapter5.container.wrapper.Wrapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class SimpleContextBasicValve extends ValveBase {

    @Override
    public String getInfo() {
        return "simple context basic valve";
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws ServletException {

        HttpServletRequest hreq = (HttpServletRequest)request.getRequest();

       // String contextPath = hreq.getContextPath();

        Wrapper wrapper = null;
        try {
            wrapper = (Wrapper) (getContainer()).map(request, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (wrapper == null) {
            System.out.println("not found");
            return;
        }
        try {
            wrapper.invoke(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


}
