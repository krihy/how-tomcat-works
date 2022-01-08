package cn.dream.chapter5.connect;

import cn.dream.chapter5.connect.parameter.HttpRequestBase;
import cn.dream.chapter5.connect.parameter.HttpRequestImpl;
import cn.dream.chapter5.connect.parameter.HttpResponseBase;
import cn.dream.chapter5.connect.parameter.HttpResponseImpl;
import cn.dream.common.Constants;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Random;

public class HttpProcessor implements Runnable {
    private HttpRequestBase request;
    private HttpResponseBase response;
    private boolean stopped;
    private HttpConnector connector;
    private Random random = new Random();
    private boolean http11;
    boolean keepAlive;
    boolean sendAck;
    private static final byte[] ack = "HTTP/1.1 100 Continue\r\n\r\n".getBytes();

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    private void process(Socket client) {
        boolean ok = true;
        boolean finishResponse = true;

        boolean keepAlive = true;
        SocketInputStream in = null;
        OutputStream out = null;
        try {
            out = client.getOutputStream();
            in = new SocketInputStream(client.getInputStream(), connector.getBufferSize());
        } catch (Exception ex) {
            ex.printStackTrace();
            ok = false;
        }
        if (!stopped && ok && keepAlive) {
            finishResponse = true;
            try {
                request = new HttpRequestImpl();
                request.setStream(in);
                response = new HttpResponseImpl();
                request.setResponse(response);
                response.setRequest(request);
                response.setStream(out);
            } catch (Exception e) {
                ok = false;
                e.printStackTrace();
            }
        }

        if (ok) {

            try {
                parseConnect(socket);
                parseRequest(in, out);
                if (!request.getRequest().getProtocol().startsWith("HTTP/0")) {
                    parseHeader(in);
                }

                if (http11) {
                    ackRequest(out);

                }
            } catch (Exception e) {
                ok = false;
                finishResponse = false;
                e.printStackTrace();
            }
        }

        if (ok) {
            try {
                String uri = request.getRequestURI();
                System.out.println(Constants.FORMATTER.format(LocalDateTime.now()) + " uri:" + uri);

                connector.getContainer().invoke(request, response);

                // close the socket
                client.close();
            } catch (Exception e) {
                ok = false;
                finishResponse = false;
                e.printStackTrace();
            }
        }

        if ("close".equals(response.getHeader("connection"))) {
            keepAlive = false;

        }

    }

    private void ackRequest(OutputStream output) throws IOException {
        if (sendAck) {
            //send ack to request client to persist connection
            output.write(ack);
        }
    }

    /**
     * 解析请求行
     *
     * @param input
     * @param output
     * @throws IOException
     */
    private void parseRequest(SocketInputStream input, OutputStream output) throws IOException {
        HttpRequestLine requestLine = new HttpRequestLine();
        input.readRequestLine(requestLine);

        String method = new String(requestLine.method, 0, requestLine.methodEnd);
        String uri = null;
        String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);

        //validate the request line
        if (method.length() < 1) {
            throw new RuntimeException("Missing HTTP request Method ");
        }
        if (requestLine.uriEnd < 1) {
            throw new RuntimeException("Missing HTTP request Uri ");
        }
        int question = requestLine.indexOf("?");
        if (question > -1) {
            request.setQueryString(new String(requestLine.uri,
                    question + 1, requestLine.uriEnd - question - 1));
            uri = new String(requestLine.uri, 0, question);
        } else {
            request.setQueryString(null);
            uri = new String(requestLine.uri, 0, requestLine.uriEnd);
        }

        //parse absolute uri
        if (!uri.startsWith("/")) {
            int pos = uri.indexOf("://");
            if (pos != -1) {
                pos = uri.indexOf("/", pos + 3);
                if (pos == -1) {
                    uri = "";
                } else {
                    uri = uri.substring(pos);
                }
            }
        }

        //parse jsessionid

        request.setMethod(method);
        request.setRequestUri(uri);
        request.setProtocol(protocol);
        if (request.getProtocol().equals("HTTP/1.1")) {
            http11 = true;
            sendAck = false;
        } else {
            http11 = false;
            sendAck = false;
        }
    }


    public void parseConnect(Socket socket) {

    }

    /**
     * 解析请求头
     *
     * @param input
     */
    public void parseHeader(SocketInputStream input) {

        try {
            while (true) {
                HttpHeader header = new HttpHeader();
                input.readHeader(header);
                if (header.nameEnd == 0) {
                    if (header.valueEnd == 0) {
                        return;
                    } else {
                        throw new RuntimeException("parse header error");
                    }
                }
                String name = new String(header.name, 0, header.nameEnd);
                String value = new String(header.value, 0, header.valueEnd);
                request.addHeader(name, value);

                if ("content-length".equals(name)) {
                    // request.set
                } else if (header.equals(DefaultHeaders.CONNECTION_CLOSE)) {
                    if (header.valueEquals(DefaultHeaders.CONNECTION_CLOSE_VALUE)) {
                        keepAlive = false;
                        response.setHeader("Connection", "close");
                    }

                } else if (header.equals(DefaultHeaders.EXPECT_NAME)) {
                    if (header.valueEquals(DefaultHeaders.EXPECT_100_VALUE))
                        sendAck = true;
                    else
                        throw new RuntimeException("httpProcessor.parseHeaders.unknownExpectation");
                }
                header.recycle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (!stopped) {
            String threadName = "{" + Thread.currentThread().getName() + "}";
            System.out.println(Constants.FORMATTER.format(LocalDateTime.now()) + threadName + " start running");
            Socket socket = await();
            if (socket == null) {
                continue;
            }
            System.out.println(Constants.FORMATTER.format(LocalDateTime.now()) + threadName + " do process ");
            process(socket);
            try {
                Thread.sleep(random.nextInt(5) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //回收当前对象，在 HttpConnect 有新的socket 后可以重新assign，线程还是在运行中 daemon
            connector.recycle(this);
            System.out.println(Constants.FORMATTER.format(LocalDateTime.now()) + threadName + " do recycle ");
        }
    }

    private boolean available = false;
    private Socket socket;

    //socket 消费者

    private synchronized Socket await() {
        while (!available) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        //消费socket
        Socket socket = this.socket;
        //设置为不可用
        available = false;
        notifyAll();
        return socket;
    }

    //socket 生产者
    public synchronized void assign(Socket socket) {
        while (available) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        //生产socket
        this.socket = socket;
        //设置为可用
        available = true;
        notifyAll();
    }
}
