package cn.dream.chapter5.container;

import cn.dream.chapter5.connect.parameter.Request;

public interface Mapper {

    /**
     * Return the child Container that should be used to process this Request,
     * based upon its characteristics.  If no such child Container can be
     * identified, return <code>null</code> instead.
     *
     * @param request Request being processed
     * @param update Update the Request to reflect the mapping selection?
     */
    public Container map(Request request, boolean update);

    public Container getContainer();

    void setContainer(Container container);

    String getProtocol();

    void setProtocol(String protocol);

}
