package cn.dream.chapter7.connect.parameter;

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
