package cn.dream.chapter2;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.*;
import java.util.Locale;

public class Response implements ServletResponse
{

    private final OutputStream out;
    private Request request;

    public Response(OutputStream out) {
        this.out = out;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    /**
     * 采用门面模式限制servlet内部访问该方法
     * @throws IOException
     */
    public void sendStaticResource() throws IOException {

        String requestUri = request.getRequestUri();

        if (requestUri == null || "/".equals(requestUri)) {
            requestUri = "/index.html";
        }
        File staticResource = new File(Constant.WEB_ROOT + File.separator + requestUri);
        BufferedOutputStream writer = new BufferedOutputStream(out);
        if (staticResource.exists()) {
            //设置响应头
            byte[] content = ("HTTP/1.1 200 OK\r\nAccept-Ranges: bytes\r\nContent-Type: text/html" +
                    "\r\nContent-Length: 20\r\n" +
                    "\r\n" /*+ "<h2>hello welcome </h2>"*/).getBytes();
            writer.write(content);
           try( FileInputStream fis = new FileInputStream(staticResource);) {
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
    public String getCharacterEncoding() {
        return null;
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return null;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(out,true);
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
}

