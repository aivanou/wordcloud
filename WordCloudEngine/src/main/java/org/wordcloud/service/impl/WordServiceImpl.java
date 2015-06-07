package org.wordcloud.service.impl;


import org.wordcloud.cache.Cache;
import org.wordcloud.cache.CacheCallback;
import org.wordcloud.handler.WordProcessor;
import org.wordcloud.objects.ErrorMessage;
import org.wordcloud.objects.Word;
import org.wordcloud.protocol.ProtocolCallback;
import org.wordcloud.protocol.ProtocolException;
import org.wordcloud.protocol.ProtocolFactory;
import org.wordcloud.service.WordService;

import javax.ws.rs.container.AsyncResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;

public class WordServiceImpl implements WordService {

    private final ProtocolFactory protocolFactory;
    private final Cache cache;
    private final WordProcessor<Word> wordProcessor;

    public WordServiceImpl(ProtocolFactory protocolFactory, Cache cache, WordProcessor<Word> wordProcessor) {
        this.protocolFactory = protocolFactory;
        this.cache = cache;
        this.wordProcessor = wordProcessor;
    }

    @Override
    public void asyncProcess(final String query, final String protocol, final AsyncResponse response) {
        cache.asyncGet(query, new CacheCallback() {
            @Override
            public void onSuccess(String key, Set<Word> value) {
                response.resume(value);
            }

            @Override
            public void onFailure(String key) {
                protocolFactory.asyncSearch(query, protocol, new ProtocolCallback() {
                    @Override
                    public void onSuccess(Reader reader) {
                        try {
                            Set<Word> words = wordProcessor.process(reader);
                            cache.set(query + "_" + protocol, words);
                            response.resume(words);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (reader != null) {
                                try {
                                    reader.close();
                                } catch (IOException e) {
                                    //do nothing
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(ProtocolException ex) {
                        response.resume(new ErrorMessage("Internal server error", System.nanoTime()));
                    }
                });
            }

            @Override
            public void onError(Exception ex, String key) {
                response.resume(new ErrorMessage(ex.toString(), System.nanoTime()));
            }
        });
    }
}
