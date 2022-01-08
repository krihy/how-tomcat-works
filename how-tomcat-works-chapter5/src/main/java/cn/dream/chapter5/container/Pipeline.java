package cn.dream.chapter5.container;

import cn.dream.chapter5.connect.parameter.Request;
import cn.dream.chapter5.connect.parameter.Response;

import javax.servlet.ServletException;

public interface Pipeline {

    void addValve(Valve valve);

    boolean removeValve(Valve valve);

    void setBasic(Valve valve);

    void invoke(Request request, Response response) throws ServletException;

}
