package com.iperlon.camel.webapp;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Christophe Rodriguez on 27.04.15.
 */
public class CamelWebappUtils {

    public static String printStackTrace(Throwable ex) {
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();

    }
}
