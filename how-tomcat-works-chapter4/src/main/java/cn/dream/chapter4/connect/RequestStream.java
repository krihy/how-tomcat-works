package cn.dream.chapter4.connect;

import javax.servlet.ServletInputStream;
import java.io.IOException;

public class RequestStream extends ServletInputStream {

    private Request request;

    public RequestStream(Request request) {
        this.request = request;
    }

    @Override
    public int read() throws IOException {
        return 0;
    }
}
