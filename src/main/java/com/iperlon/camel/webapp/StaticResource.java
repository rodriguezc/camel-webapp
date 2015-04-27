package com.iperlon.camel.webapp;

import java.io.InputStream;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public abstract class StaticResource {

    private long length;
    private String mimeType;

    public abstract InputStream createIs() throws Exception;

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
