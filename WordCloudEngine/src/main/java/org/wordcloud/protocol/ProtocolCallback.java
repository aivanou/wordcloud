package org.wordcloud.protocol;


import java.io.Reader;

public interface ProtocolCallback {

    void onSuccess(Reader reader);

    void onFailure(ProtocolException ex);

}
