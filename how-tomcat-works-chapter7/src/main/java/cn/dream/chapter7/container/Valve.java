package cn.dream.chapter7.container;

import cn.dream.chapter7.connect.parameter.Request;
import cn.dream.chapter7.connect.parameter.Response;

import javax.servlet.ServletException;

public interface Valve {

    String getInfo();

    void invoke(Request request, Response response, ValveContext context) throws ServletException;

}
