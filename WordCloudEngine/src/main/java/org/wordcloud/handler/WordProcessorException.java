package org.wordcloud.handler;


public class WordProcessorException extends Exception {
    public WordProcessorException(String message) {
        super(message);
    }

    public WordProcessorException(Throwable cause) {
        super(cause);
    }
}