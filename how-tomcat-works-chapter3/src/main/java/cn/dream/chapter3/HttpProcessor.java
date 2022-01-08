package cn.dream.chapter3;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

public class HttpProcessor {

    private HttpConnector connector;
    private HttpRequest request;
    private HttpResponse response;

    public HttpProcessor(HttpConnector connector) {
        this.connector = connector;
    }

    public void process(Socket client) {

        try {
            OutputStream out = client.getOutputStream();
            SocketInputStream in = new SocketInputStream(client.getInputStream(), 2048);
            request = new HttpRequest(in);
            response = new HttpResponse(out);
            response.setRequest(request);
            parseRequest(in, out);
            parseHeader(in);

            String uri = request.getRequestURI();
            System.out.print(new Date() + " uri:" + uri + "\n");

            if (uri != null && uri.startsWith("/servlet/")) {
                new ServletProcessor().process(request, response);
            } else {
                new StaticResourceProcessor().process(request, response);
            }

            // close the socket
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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
                    question+1, requestLine.uriEnd-question-1));
            uri = new String(requestLine.uri, 0, question);
        }else {
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
                }else{
                    uri = uri.substring(pos);
                }
            }
        }

        //parse jsessionid

        request.setMethod(method);
        request.setRequestUri(uri);
        request.setProtocol(protocol);
    }

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
                }
                header.recycle();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
