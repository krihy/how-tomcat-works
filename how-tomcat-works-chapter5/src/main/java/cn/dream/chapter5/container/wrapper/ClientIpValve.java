package cn.dream.chapter5.container.wrapper;

import cn.dream.chapter5.connect.parameter.Request;
import cn.dream.chapter5.connect.parameter.Response;
import cn.dream.chapter5.container.Valve;
import cn.dream.chapter5.container.ValveContext;

import javax.servlet.ServletException;

public class ClientIpValve implements Valve {
    @Override
    public String getInfo() {
        return "header ip value";
    }

    @Override
    public void invoke(Request request, Response response, ValveContext context) throws ServletException {

        System.out.println(getInfo() + " invoke first");

        context.invokeNext(request, response);

        System.out.println(getInfo() + " invoke again");
    }
}
