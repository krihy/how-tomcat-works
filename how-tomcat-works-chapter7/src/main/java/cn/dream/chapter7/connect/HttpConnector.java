package cn.dream.chapter7.connect;

import cn.dream.chapter7.container.Container;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;

public class HttpConnector implements Runnable {

    boolean stopped;
    private String schema = "HTTP";
    protected int minProcessors = 5;
    private int maxProcessors = 20;
    private Stack<HttpProcessor> processors = new Stack<>();
    private int curProcessors = processors.size();
    private Container container;

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

            HttpProcessor processor = newProcessor();
            // processor reach max size, just close the socket
            if (processor == null) {
                System.out.println("processor reach max, this request has been given up ");
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                continue;
            }
            processor.assign(client);

        }
    }

    public String getSchema() {
        return schema;
    }

    public void start() {
        while (curProcessors < minProcessors) {

            if (maxProcessors > 0 && curProcessors >= maxProcessors) {
                break;
            }
            HttpProcessor p = newProcessor();
            recycle(p);
        }

        // setContainer(new SimpleContainer());
        //启动 HttpConnector
        Thread thread = new Thread(this);
        thread.start();
    }

    public void recycle(HttpProcessor processor) {
        if (processor != null) {
            processors.push(processor);
        }
    }

    private HttpProcessor newProcessor() {

        //当前值小于最小或者 当前栈中为空且小于最大值时，创建新对象 或不限制个数
        if (curProcessors < minProcessors
                || (maxProcessors > 0 && processors.isEmpty() && curProcessors < maxProcessors)
                || maxProcessors < 0) {
            curProcessors++;
            return createProcessor();
        }

        //当前栈不为空时，取栈中对象
        if (!processors.isEmpty()) {
            return processors.pop();
        }
        //其余情况返回 null
        return null;
    }

    private HttpProcessor createProcessor() {
        HttpProcessor processor = new HttpProcessor(this);
        //创建后,启动HttpProcessor 线程, 等待 a socket assigned
        Thread processorThread = new Thread(processor, "processor-thread-" + curProcessors);
        processorThread.start();
        return processor;
    }

    public int getBufferSize() {
        return 1024;
    }

    public void setMinProcessors(int minProcessors) {
        this.minProcessors = minProcessors;
    }

    public void setMaxProcessors(int maxProcessors) {
        this.maxProcessors = maxProcessors;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
