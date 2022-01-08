package cn.dream.chapter5.container;

import cn.dream.chapter5.connect.parameter.Request;
import cn.dream.chapter5.connect.parameter.Response;

import javax.servlet.ServletException;

public class DateValve implements Valve {
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
}
