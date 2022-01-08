package cn.dream.chapter4.connect;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

public abstract class ResponseBase implements Response, ServletResponse {

    private OutputStream output;
    private Request request;
    private PrintWriter writer;
    protected ServletOutputStream stream = null;

    protected ResponseFacade facade = new ResponseFacade(this);

    public ResponseBase() {
    }


    @Override
    public String getCharacterEncoding() {
        return null;
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null)
            throw new IllegalStateException("getOutputStream error");

        if (stream == null)
            stream = createOutputStream();
        ((ResponseStream) stream).setCommit(true);
        return (stream);
    }

    public ServletOutputStream createOutputStream() {
        return new ResponseStream(this);
    }
    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(output, true);
    }


    @Override
    public void setContentLength(int i) {

    }

    @Override
    public void setContentType(String s) {

    }

    @Override
    public void setBufferSize(int i) {

    }

    @Override
    public int getBufferSize() {
        return 0;
    }

    @Override
    public void flushBuffer() throws IOException {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale locale) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Request getRequest() {
        return this.request;
    }

    @Override
    public void setRequest(Request request) {

        this.request = request;
    }

    @Override
    public OutputStream getStream() {
        return this.output;
    }

    @Override
    public void setStream(OutputStream stream) {

        this.output = stream;
    }

    @Override
    public ServletResponse getResponse() {
        return facade;
    }
}

