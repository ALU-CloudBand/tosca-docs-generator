package org.tosca.docs.utils;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    private final ResourceBundle properties;

    public Messages() {
        properties = ResourceBundle.getBundle("org.tosca.docs.messages", Locale.getDefault(), Messages.class.getClassLoader());
    }

    public String getMessage(String key, Object... args) {
        try {
            String message = properties.getString(key);
            return String.format(message, args);
        } catch (MissingResourceException e) {
            return null;
        }
    }
}
