package cn.dream.chapter4.connect;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

public class ResponseStream extends ServletOutputStream {
    private Response response;

    public ResponseStream(Response response) {
        this.response = response;
    }

    public void setCommit(boolean autoCommit) {
        //todo
    }

    @Override
    public void write(int b) throws IOException {

    }
}
