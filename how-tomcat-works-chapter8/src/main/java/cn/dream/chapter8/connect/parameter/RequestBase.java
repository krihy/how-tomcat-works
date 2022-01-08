package cn.dream.chapter8.connect.parameter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * http request body
 */
public abstract class RequestBase implements Request, ServletRequest {

    private InputStream input;
    private String requestUri;
    protected ServletInputStream stream = null;
    protected String protocol;

    protected RequestFacade facade = new RequestFacade(this);


    /**
     * 采用门面模式限制servlet内部访问该方法
     *
     * @throws IOException
     */
    public void parse() throws IOException {

        int len = 0;
        StringBuilder content = new StringBuilder();
        byte[] b = new byte[1024];
        while (input.available() > 0) {
            len = input.read(b);
            content.append(new String(b, 0, len));
        }
        parseUri(content.toString());
    }

    private void parseUri(String requestString) {
        //HTTP Protocol
        //GET /uri http/1.1
        int start = requestString.indexOf(" ");
        if (start != -1) {
            int end = requestString.indexOf(" ", start + 1);
            if (end > start)
                this.requestUri = requestString.substring(start + 1, end);
        }
    }

    public String getRequestUri() {
        return requestUri;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

    }

    @Override
    public int getContentLength() {
        return 0;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.stream == null) {
            stream = createInputStream();
        }
        return this.stream;
    }

    public ServletInputStream createInputStream() throws IOException {

        return (new RequestStream(this));

    }

    @Override
    public String getParameter(String s) {
        return null;
    }

    @Override
    public Enumeration getParameterNames() {
        return null;
    }

    @Override
    public String[] getParameterValues(String s) {
        return new String[0];
    }

    @Override
    public Map getParameterMap() {
        return null;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getScheme() {
        return null;
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public void setStream(InputStream input) {
        this.input = input;
    }

    @Override
    public InputStream getStream() {
        return this.input;
    }

    @Override
    public ServletRequest getRequest() {
        return this.facade;
    }

    /**
     * Set the protocol name and version associated with this Request.
     *
     * @param protocol Protocol name and version
     */
    @Override
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
