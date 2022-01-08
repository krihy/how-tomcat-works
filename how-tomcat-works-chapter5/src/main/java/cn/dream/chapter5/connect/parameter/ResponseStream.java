package cn.dream.chapter5.connect.parameter;

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
