package cn.dream.chapter7.connect.parameter;

public interface HttpRequest extends Request {

    String getDecodedRequestURI();

    void setContextPath(String path);

    String getContextPath();

}

