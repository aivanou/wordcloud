package org.wordcloud.service;


import org.wordcloud.objects.Word;
import org.wordcloud.protocol.ProtocolCallback;

import javax.ws.rs.container.AsyncResponse;
import java.util.Set;

/**
 * Service that executes asynchronous queries
 */
public interface WordService {

    /**
     * Processes asynchronous computation, results will be written into response object
     *
     * @param query
     * @param protocol -- protocol name, is used by protocol factory in order to find the appropriate protocol implementation
     * @param response -- the response in which results will be written
     */
    void asyncProcess(String query, String protocol, AsyncResponse response);

}
