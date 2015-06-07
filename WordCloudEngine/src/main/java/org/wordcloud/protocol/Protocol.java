package org.wordcloud.protocol;


import java.io.Reader;

/**
 * Defines methods for implementing search protocol,
 * which retrieves text using query
 */
public interface Protocol {

    /**
     * Synchronous method, freezes until execution is completed
     *
     * @param query
     * @return
     * @throws ProtocolException
     */
    Reader search(String query) throws ProtocolException;

    /**
     * Asynchronous method, calls callback function onSuccess if the search was successful
     * otherwise calls onFailure
     *
     * @param query
     * @param callback
     */
    void asyncSearch(String query, ProtocolCallback callback);

    /**
     * Stops and releases the appropriate resources connected with search protocol
     */
    void stop();

}
