package cn.dream.chapter8.container;

import cn.dream.chapter8.connect.parameter.Request;
import cn.dream.chapter8.connect.parameter.Response;

import javax.servlet.ServletException;

public interface ValveContext {

    void invokeNext(Request request, Response response) throws ServletException;
}
