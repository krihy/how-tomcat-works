package cn.dream.chapter7.container;

import cn.dream.chapter7.connect.parameter.Request;
import cn.dream.chapter7.connect.parameter.Response;

import javax.servlet.ServletException;

public interface ValveContext {

    void invokeNext(Request request, Response response) throws ServletException;
}
