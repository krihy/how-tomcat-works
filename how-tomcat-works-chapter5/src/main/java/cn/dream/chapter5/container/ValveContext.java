package cn.dream.chapter5.container;

import cn.dream.chapter5.connect.parameter.Request;
import cn.dream.chapter5.connect.parameter.Response;

import javax.servlet.ServletException;

public interface ValveContext {

    void invokeNext(Request request, Response response) throws ServletException;
}
