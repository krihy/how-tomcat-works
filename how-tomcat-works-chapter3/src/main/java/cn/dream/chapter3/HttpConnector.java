package cn.dream.chapter3;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConnector implements Runnable {

    boolean stopped;
    private String schema = "HTTP";

    @Override
    public void run() {
        int port = 8090;
        boolean stopped = false;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
            System.out.println("服务器已监听端口" + port);
        } catch (IOException e) {
            e.printStackTrace();
            //退出
            System.exit(1);
        }

        while (!stopped) {
            Socket client = null;
            try {
                client = serverSocket.accept();
            } catch (IOException e) {
                continue;
            }

            HttpProcessor processor = new HttpProcessor(this);
            processor.process(client);

        }
    }

    public String getSchema() {
        return schema;
    }

    public void start() {

        Thread thread = new Thread(this);
        thread.start();
    }
}
