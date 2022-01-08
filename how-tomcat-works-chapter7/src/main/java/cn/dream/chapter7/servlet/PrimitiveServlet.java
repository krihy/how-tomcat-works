package cn.dream.chapter7.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

public class PrimitiveServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        PrintWriter writer = servletResponse.getWriter();
        String head = ("HTTP/1.1 200 OK\r\nAccept-Ranges: bytes\r\nContent-Type: text/html" +
                "\r\nContent-Length: 30\r\n" +
                "\r\n");
        writer.write(head);
        writer.flush();
        writer.println(getServletInfo() + " : hello rose is red");
        writer.print("violets is red");
        writer.flush();
    }

    @Override
    public String getServletInfo() {
        return "primitive servlet";
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
}
