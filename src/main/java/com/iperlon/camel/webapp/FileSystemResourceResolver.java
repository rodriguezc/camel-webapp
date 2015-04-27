package com.iperlon.camel.webapp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class FileSystemResourceResolver implements ResourceResolver {

    private String directory;

    public FileSystemResourceResolver(String directory) {
        File file = new File(directory);
        if(file.exists()) {
            this.directory = file.getAbsolutePath();
        } else {
            throw new RuntimeException("Directory doesn't exists: "+directory);
        }
    }

    @Override
    public StaticResource get(String path) throws Exception{
        File file = new File(directory+path);
        if(file.exists()) {
            StaticResource staticResource = new FSStaticResource(file);
            try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(file))) {
                String mimeType = MimeTypeUtils.getMimeType(is, file.getName());
                staticResource.setMimeType(mimeType);
            }
            staticResource.setLength(file.length());
            return staticResource;
        }
        return null;
    }
}
