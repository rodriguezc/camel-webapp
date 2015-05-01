package com.iperlon.camel.webapp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class FSStaticResource extends StaticResource {

    private File file;

    public FSStaticResource(File file) {
        this.file = file;

    }
    @Override
    public InputStream createIs() throws Exception{
        return new BufferedInputStream(new FileInputStream(file));
    }
}
