package com.iperlon.camel.webapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created by christophe on 01.05.15.
 */
public class Json {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static ObjectNode createObject() {
        return objectMapper.createObjectNode();
    }

    public static ArrayNode createArray() {
        return objectMapper.createArrayNode();
    }
}
