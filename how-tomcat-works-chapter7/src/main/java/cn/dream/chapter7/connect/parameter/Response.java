package cn.dream.chapter7.connect.parameter;

import javax.servlet.ServletResponse;
import java.io.OutputStream;

public interface Response {

    public Request getRequest();


    /**
     * Set the Request with which this Response is associated.
     *
     * @param request The new associated request
     */
    public void setRequest(Request request);


    /**
     * Return the output stream associated with this Response.
     */
    public OutputStream getStream();


    /**
     * Set the output stream associated with this Response.
     *
     * @param stream The new output stream
     */
    public void setStream(OutputStream stream);

    /**
     * return response associate with this response , this is facade
     *
     * @return
     */
    ServletResponse getResponse();


}

