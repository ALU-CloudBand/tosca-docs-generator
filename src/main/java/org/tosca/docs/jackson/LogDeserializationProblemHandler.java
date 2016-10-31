package org.tosca.docs.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class LogDeserializationProblemHandler extends DeserializationProblemHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogDeserializationProblemHandler.class);

    @Override
    public boolean handleUnknownProperty(DeserializationContext context, JsonParser p, JsonDeserializer<?> deserializer, Object beanOrClass, String propertyName) throws IOException {

        LOGGER.warn("Ignoring deserialization unknown property '" + propertyName + "' of class '" + beanOrClass + "'");

        // skip any unknown property
//        context.getParser().skipChildren();

        // Return true to indicate issue is resolved
        return true;
    }
}
