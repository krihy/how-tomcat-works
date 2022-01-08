package cn.dream.chapter3;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

public class HttpRequest implements HttpServletRequest {

    private SocketInputStream in;
    private String requestUri;
    private String method;
    private String queryString;
    private String protocol;
    private Map headers = new HashMap();
    private ParameterMap parameterMap;

    //数据是否已解析
    private boolean parsed;

    public HttpRequest(SocketInputStream input) {
        this.in = input;
    }

    /**
     * 采用门面模式限制servlet内部访问该方法
     *
     * @throws IOException
     */
    public void parse() throws IOException {

        int len = 0;
        StringBuilder content = new StringBuilder();
        byte[] b = new byte[1024];
        while (in.available() > 0) {
            len = in.read(b);
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

    public void setQueryString(String queryString) {

        this.queryString = queryString;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setRequestUri(String uri) {
        this.requestUri = uri;
    }

    public void addHeader(String name, String value) {
        this.headers.put(name, value);
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String s) {
        return 0;
    }

    @Override
    public String getHeader(String s) {
        return this.getHeader(s);
    }

    @Override
    public Enumeration getHeaders(String s) {
        return Collections.enumeration(this.headers.entrySet());
    }

    @Override
    public Enumeration getHeaderNames() {
        return Collections.enumeration(this.headers.keySet());
    }

    @Override
    public int getIntHeader(String s) {
        return 0;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getPathInfo() {
        return null;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getQueryString() {
        return this.queryString;
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String s) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return this.requestUri;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return null;
    }

    @Override
    public HttpSession getSession(boolean b) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
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
        return null;
    }

    @Override
    public String getParameter(String s) {
        return String.valueOf(this.parameterMap.get(s));
    }

    @Override
    public Enumeration getParameterNames() {
        parseParameters();
        return Collections.enumeration(this.parameterMap.entrySet());
    }

    @Override
    public String[] getParameterValues(String s) {
        parseParameters();
        return (String[]) this.parameterMap.get(s);
    }

    @Override
    public Map getParameterMap() {
        parseParameters();
        return this.parameterMap;
    }

    @Override
    public String getProtocol() {
        return protocol;
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

    //解析参数
    private void parseParameters() {
        synchronized (this) {
            if (!parsed) {

                // parseParameters
                parameterMap = new ParameterMap();
                parameterMap.setLocked(false);

                //do parse body and query string

                parameterMap.setLocked(true);
                parsed = true;

            }
        }
    }

}
