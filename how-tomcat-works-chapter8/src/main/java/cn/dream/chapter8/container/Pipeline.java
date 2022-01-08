package cn.dream.chapter8.container;

import cn.dream.chapter8.connect.parameter.Request;
import cn.dream.chapter8.connect.parameter.Response;

import javax.servlet.ServletException;

public interface Pipeline {

    void addValve(Valve valve);

    boolean removeValve(Valve valve);

    void setBasic(Valve valve);

    void invoke(Request request, Response response) throws ServletException;

}
