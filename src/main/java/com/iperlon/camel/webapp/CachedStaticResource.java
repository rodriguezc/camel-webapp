package com.iperlon.camel.webapp;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class CachedStaticResource extends StaticResource {
    private byte[] cache;
    public CachedStaticResource(StaticResource decorated) throws Exception {
        InputStream is = decorated.createIs();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOUtils.copy(is, bos);
        bos.flush();
        bos.close();
        this.cache = bos.toByteArray();
        setMimeType(decorated.getMimeType());
        setLength(decorated.getLength());
    }
    @Override

    public InputStream createIs() throws Exception{
        return new ByteArrayInputStream(cache);
    }
}
