package org.wordcloud.protocol;


import java.io.Reader;

/**
 * Defines functions for asynchronous search
 */
public interface ProtocolCallback {

    /**
     * Is called when the seach was successful
     *
     * @param reader
     */
    void onSuccess(Reader reader);

    /**
     * Is called when error occurs
     *
     * @param ex
     */
    void onFailure(ProtocolException ex);

}
