package com.iperlon.camel.webapp;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;

import java.io.InputStream;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class MimeTypeUtils {

    public static String getMimeType(InputStream is, String fileName) throws Exception {
        Detector detector = new DefaultDetector(MimeTypes.getDefaultMimeTypes());
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY,  fileName);
        MediaType mediaType = detector.detect(is, metadata);
        String mimeType = mediaType.getType()+"/"+mediaType.getSubtype();
        if("text/html".equals(mimeType)) {
            mimeType = mimeType+";charset=utf-8";
        } else if("application/javascript".equals(mimeType)) {
            mimeType = mimeType+";charset=utf-8";
        }
        return mimeType;
    }

    public static MediaType getMediaType(InputStream is, String fileName) throws Exception {
        Detector detector = new DefaultDetector(MimeTypes.getDefaultMimeTypes());
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY,  fileName);
        MediaType mediaType = detector.detect(is, metadata);
        return mediaType;
    }
}
