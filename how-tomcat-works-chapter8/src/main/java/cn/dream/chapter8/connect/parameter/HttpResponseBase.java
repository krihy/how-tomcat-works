package cn.dream.chapter8.connect.parameter;

import cn.dream.common.Constants;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Locale;

public abstract class HttpResponseBase extends ResponseBase implements HttpResponse, HttpServletResponse {

    protected HttpResponseFacade facade = new HttpResponseFacade(this);

    public HttpResponseBase() {
    }


    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String s) {
        return false;
    }

    @Override
    public String encodeURL(String s) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String s) {
        return null;
    }

    @Override
    public String encodeUrl(String s) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return null;
    }

    @Override
    public void sendError(int i, String s) throws IOException {

    }

    @Override
    public void sendError(int i) throws IOException {

    }

    @Override
    public void sendRedirect(String s) throws IOException {

    }

    @Override
    public void setDateHeader(String s, long l) {

    }

    @Override
    public void addDateHeader(String s, long l) {

    }

    @Override
    public void setHeader(String s, String s1) {

    }

    @Override
    public void addHeader(String s, String s1) {

    }

    @Override
    public void setIntHeader(String s, int i) {

    }

    @Override
    public void addIntHeader(String s, int i) {

    }

    @Override
    public void setStatus(int i) {

    }

    @Override
    public void setStatus(int i, String s) {

    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(getStream(), true);
    }

    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    /**
     * 采用门面模式限制servlet内部访问该方法
     *
     * @throws IOException
     */
    public void sendStaticResource() throws IOException {

        HttpServletRequest request = (HttpServletRequest) getRequest();
        String requestUri = request.getRequestURI();

        if (requestUri == null || "/".equals(requestUri)) {
            requestUri = "/index.html";
        }
        File staticResource = new File(Constants.WEB_ROOT + File.separator + requestUri);
        BufferedOutputStream writer = new BufferedOutputStream(getStream());
        if (staticResource.exists()) {
            //设置响应头
            byte[] content = ("HTTP/1.1 200 OK\r\nAccept-Ranges: bytes\r\nContent-Type: text/html" +
                    "\r\nContent-Length: 20\r\n" +
                    "\r\n" /*+ "<h2>hello welcome </h2>"*/).getBytes();
            writer.write(content);
            try (FileInputStream fis = new FileInputStream(staticResource);) {
                byte[] b = new byte[1024];
                int len;
                while ((len = fis.read(b)) != -1) {
                    writer.write(b, 0, len);
                }
            }
        } else {
            byte[] content = ("HTTP/1.1 404 BAD_REQUEST\r\nContent-Type: text/html\r\nContent-Length: 23\r\n" +
                    "\r\n" + "<h1>404 File not exits</h1>").getBytes();
            writer.write(content);
        }
        writer.flush();

    }

    @Override
    public ServletResponse getResponse() {
        return this.facade;
    }
}
