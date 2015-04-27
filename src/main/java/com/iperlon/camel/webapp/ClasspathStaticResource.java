package com.iperlon.camel.webapp;

import org.apache.camel.spi.ClassResolver;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class ClasspathStaticResource extends StaticResource {

    private ClassResolver classResolver;
    private String path;

    public ClasspathStaticResource(ClassResolver classResolver, String path) {
        this.classResolver = classResolver;
        this.path = path;
    }
    @Override
    public InputStream createIs() throws Exception{
        return new BufferedInputStream(classResolver.loadResourceAsStream(path));
    }
}
