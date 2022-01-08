package cn.dream.chapter8.connect.parameter;

public interface HttpRequest extends Request {

    String getDecodedRequestURI();

    void setContextPath(String path);

    String getContextPath();

}

