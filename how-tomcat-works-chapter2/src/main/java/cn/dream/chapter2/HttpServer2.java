package cn.dream.chapter2;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class HttpServer2 {

    //server shutdown command
    private static final String SHUTDOWN = "/shutdown";

    public static void main(String[] args) throws Exception {
        HttpServer2 server = new HttpServer2();
        server.await();
    }

    public void await() throws Exception {
        int port = 8090;
        boolean loop = true;
        ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));

        System.out.println(String.format("服务已启监听端口%d", port));
        while (loop) {
            Socket client = serverSocket.accept();
            OutputStream out = client.getOutputStream();
            InputStream in = client.getInputStream();
            Request request = new Request(in);
            Response response = new Response(out);
            response.setRequest(request);
            request.parse();
            //TODO 第二请求URI为空
            String uri = request.getRequestUri();
            System.out.print(new Date() + " uri:" + uri + "\n");

            if (uri != null && uri.startsWith("/servlet/")) {
                new ServletProcessor().process(request,response);
            }else{
                new StaticResourceProcessor().process(request,response);
            }

           // close the socket
            client.close();

            if (SHUTDOWN.equals(request.getRequestUri())) {
                loop = false;
            }
        }
    }

}
