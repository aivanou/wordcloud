package org.wordcloud.protocol;


/**
 * Defines common exception for classes that implement Protocol interface
 *
 * @see Protocol
 */
public class ProtocolException extends Exception {
    public ProtocolException(String message) {
        super(message);
    }

    public ProtocolException(Throwable cause) {
        super(cause);
    }
}
