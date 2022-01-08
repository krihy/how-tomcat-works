package cn.dream.chapter1;

import java.io.*;

public class Response {

    private final OutputStream out;
    private Request request;

    public Response(OutputStream out) {
        this.out = out;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void send() throws IOException {

        String requestUri = request.getRequestUri();

        if (requestUri == null || "/".equals(requestUri)) {
            requestUri = "/index.html";
        }
        File staticResource = new File(HttpServer.WEB_ROOT + File.separator + requestUri);
        BufferedOutputStream writer = new BufferedOutputStream(out);
        if (staticResource.exists()) {
            //设置响应头
            byte[] content = ("HTTP/1.1 200 OK\r\nAccept-Ranges: bytes\r\nContent-Type: text/html" +
                    "\r\nContent-Length: 1216\r\n" +
                    "\r\n" + "<h2>hello welcome </h2>").getBytes();
            writer.write(content);
           try( FileInputStream fis = new FileInputStream(staticResource);) {
               byte[] b = new byte[1024];
               int len;
               while ((len = fis.read(b)) != -1) {
                   //writer.write(b, 0, len);
               }
           }
        } else {
            byte[] content = ("HTTP/1.1 404 BAD_REQUEST\r\nContent-Type: text/html\r\nContent-Length: 23\r\n" +
                    "\r\n" + "<h1>404 File not exits</h1>").getBytes();
            writer.write(content);
        }
        writer.flush();

    }
}

