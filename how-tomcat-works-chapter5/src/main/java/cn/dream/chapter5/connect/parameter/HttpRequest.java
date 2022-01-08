package cn.dream.chapter5.connect.parameter;

public interface HttpRequest extends Request {

    String getDecodedRequestURI();

    void setContextPath(String path);

    String getContextPath();

}

