package org.wordcloud.protocol;


import java.io.Reader;

public interface Protocol {

    Reader search(String query) throws ProtocolException;

    void asyncSearch(String query, ProtocolCallback callback);

}
