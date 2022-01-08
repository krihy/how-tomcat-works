package cn.dream.chapter7.connect.parameter;

import javax.servlet.ServletRequest;
import java.io.InputStream;

/**
 * http request body
 */
public interface Request {

    InputStream getStream();

    void setStream(InputStream input);


    /**
     * Return the Response with which this Request is associated.
     */
    public Response getResponse();


    /**
     * Set the Response with which this Request is associated.
     *
     * @param response The new associated response
     */
    public void setResponse(Response response);

    /**
     * Return the <code>ServletRequest</code> for which this object
     * is the facade.
     */
    public ServletRequest getRequest();

    void setProtocol(String protocol);

}
