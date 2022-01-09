package cn.dream.chapter8.naming;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Encapsultes the contents of a resource.
 */
public class Resource {

    protected byte[] binaryContent = null;


    protected InputStream inputStream;


    public byte[] getContent() {
        return this.binaryContent;
    }

    public InputStream streamContent() {
        if (binaryContent != null) {
            return new ByteArrayInputStream(binaryContent);
        }
        return inputStream;
    }

    public void setContent(byte[] binaryContent) {
        this.binaryContent = binaryContent;
    }


    public void setContent(InputStream inputStream) {
        this.inputStream = inputStream;
    }


}
