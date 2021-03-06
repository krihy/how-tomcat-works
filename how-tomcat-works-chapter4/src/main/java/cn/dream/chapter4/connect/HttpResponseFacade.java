package cn.dream.chapter4.connect;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * 门面模式
 */
public class HttpResponseFacade extends ResponseFacade implements HttpServletResponse {
    private HttpResponseBase response;

    public HttpResponseFacade(HttpResponseBase response) {
        super(response);
        this.response = response;
    }

    @Override
    public String getCharacterEncoding() {
        return response.getCharacterEncoding();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return response.getOutputStream();
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return response.getWriter();
    }

    @Override
    public void setContentLength(int i) {

        response.setContentLength(i);
    }

    @Override
    public void setContentType(String s) {

        response.setContentType(s);
    }

    @Override
    public void setBufferSize(int i) {

        response.setBufferSize(i);
    }

    @Override
    public int getBufferSize() {
        return response.getBufferSize();
    }

    @Override
    public void flushBuffer() throws IOException {

        response.flushBuffer();
    }

    @Override
    public void resetBuffer() {

        response.resetBuffer();
    }

    @Override
    public boolean isCommitted() {
        return response.isCommitted();
    }

    @Override
    public void reset() {

        response.reset();
    }

    @Override
    public void setLocale(Locale locale) {

        response.setLocale(locale);
    }

    @Override
    public Locale getLocale() {
        return response.getLocale();
    }

    @Override
    public void addCookie(Cookie cookie) {
        response.addCookie(cookie);
    }

    @Override
    public boolean containsHeader(String s) {
        return response.containsHeader(s);
    }

    @Override
    public String encodeURL(String s) {
        return response.encodeURL(s);
    }

    @Override
    public String encodeRedirectURL(String s) {
        return response.encodeRedirectURL(s);
    }

    @Override
    public String encodeUrl(String s) {
        return response.encodeURL(s);
    }

    @Override
    public String encodeRedirectUrl(String s) {
        return response.encodeRedirectURL(s);
    }

    @Override
    public void sendError(int i, String s) throws IOException {

        response.sendError(i, s);
    }

    @Override
    public void sendError(int i) throws IOException {

        response.sendError(i);
    }

    @Override
    public void sendRedirect(String s) throws IOException {

        response.sendRedirect(s);
    }

    @Override
    public void setDateHeader(String s, long l) {

        response.setDateHeader(s, l);
    }

    @Override
    public void addDateHeader(String s, long l) {
        response.addDateHeader(s, l);

    }

    @Override
    public void setHeader(String s, String s1) {

        response.setHeader(s, s1);
    }

    @Override
    public void addHeader(String s, String s1) {

        response.addHeader(s, s1);
    }

    @Override
    public void setIntHeader(String s, int i) {

        response.setIntHeader(s, i);
    }

    @Override
    public void addIntHeader(String s, int i) {

        response.addIntHeader(s, i);
    }

    @Override
    public void setStatus(int i) {

        response.setStatus(i);
    }

    @Override
    public void setStatus(int i, String s) {

        response.setStatus(i, s);
    }
}
