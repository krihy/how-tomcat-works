package cn.dream.chapter1;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * http request body
 */
public class Request {

    private final InputStream in;
    private String requestUri;

    public Request(InputStream inputStream) {
        this.in = inputStream;
    }

    public void parse() throws IOException {

        int len = 0;
        StringBuilder content = new StringBuilder();
        byte[] b = new byte[1024];
        while (in.available() > 0) {
            len = in.read(b);
            content.append(new String(b, 0, len));
        }
        parseUri(content.toString());
    }

    private void parseUri(String requestString) {
        //HTTP Protocol
        //GET /uri http/1.1
        int start = requestString.indexOf(" ");
        if (start != -1) {
            int end = requestString.indexOf(" ", start + 1);
            if (end > start)
                this.requestUri = requestString.substring(start + 1, end);
            System.out.print(new Date().getTime() + " url:" + this.requestUri + "\n");
        }
    }

    public String getRequestUri() {
        return requestUri;
    }
}
