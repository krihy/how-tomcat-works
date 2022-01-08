package cn.dream.chapter7.container.context;

import cn.dream.chapter7.connect.parameter.Request;
import cn.dream.chapter7.connect.parameter.Response;
import cn.dream.chapter7.container.ValveBase;
import cn.dream.chapter7.container.ValveContext;

import javax.servlet.ServletException;

public class DateValve extends ValveBase {
    @Override
    public String getInfo() {
        return "date valve";
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws ServletException {

        System.out.println(getInfo() + " execute before");
        context.invokeNext(request, response);
        System.out.println(getInfo() + " execute before");

    }

    @Override
    protected void startInternal() {

    }

    @Override
    protected void stopInternal() {

    }
}
