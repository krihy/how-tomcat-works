package cn.dream.chapter1;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    //save static resource in server file system
    static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webRoot";

    //server shutdown command
    private static final String SHUTDOWN = "/shutdown";

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
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
            service(request, response);
            //close the socket
            client.close();

            if (SHUTDOWN.equals(request.getRequestUri())) {
                loop = false;
            }
        }
    }

    public void service(Request request, Response response) throws IOException {
        try {
            request.parse();
            response.send();
        } catch (IOException ex) {
            System.out.println(ex.getCause().getMessage());
            throw ex;
        }
    }
}
