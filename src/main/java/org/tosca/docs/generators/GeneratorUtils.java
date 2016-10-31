package org.tosca.docs.generators;

import org.tosca.docs.utils.Messages;

public class GeneratorUtils {
    public static String localize(String messageKey) {
        return localize(messageKey, null);
    }

    public static String localize(String messageKey, String defaultMessage) {
        return getMessage(messageKey, defaultMessage);
    }

    private static String getMessage(String messageKey, String defaultMessage) {
        String message = Messages.getMessage(messageKey);
        if (message == null) {
            message = defaultMessage;
        }
        return message;
    }
}
