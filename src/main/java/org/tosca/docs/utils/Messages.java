package org.tosca.docs.utils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    private static final ResourceBundle PROPERTIES;

    static {
        try {
            PROPERTIES = ResourceBundle.getBundle("messages");
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static String getMessage(String key, Object... args) {
        try {
            String message = PROPERTIES.getString(key);
            return String.format(message, args);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
