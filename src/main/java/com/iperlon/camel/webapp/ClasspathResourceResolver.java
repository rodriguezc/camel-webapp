package com.iperlon.camel.webapp;

import org.apache.camel.spi.ClassResolver;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class ClasspathResourceResolver implements ResourceResolver {

    private ClassResolver classResolver;

    public ClasspathResourceResolver(ClassResolver classResolver) {
        this.classResolver=classResolver;
    }

    @Override
    public StaticResource get(String path) throws Exception {
        String uri = "/www/" + path;
        InputStream is = classResolver.loadResourceAsStream(uri);
        if(is != null) {
            is = new BufferedInputStream(is);
            StaticResource staticResource = new ClasspathStaticResource(classResolver, uri);
            staticResource.setMimeType(MimeTypeUtils.getMimeType(is, path));
            staticResource.setLength(readLength(is));
            return staticResource;
        }
        return null;
    }

    private long readLength(InputStream is) throws Exception {
        int count = 0;
        try(BufferedInputStream bis = new BufferedInputStream(is)) {
            while ((bis.read() != -1)) {
                ++count;
            }
        }
        return count;
    }
}
