package org.wordcloud.service;

import org.junit.Test;
import org.mockito.Mockito;
import org.wordcloud.cache.Cache;
import org.wordcloud.cache.CacheCallback;
import org.wordcloud.cache.impl.DummyCache;
import org.wordcloud.handler.WordProcessor;
import org.wordcloud.objects.Word;
import org.wordcloud.protocol.ProtocolCallback;
import org.wordcloud.protocol.ProtocolException;
import org.wordcloud.protocol.ProtocolFactory;
import org.wordcloud.service.impl.WordServiceImpl;

import javax.ws.rs.container.AsyncResponse;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class WordServiceTest {

    private ProtocolFactory protocolFactory = mock(ProtocolFactory.class);
    private WordProcessor<Word> wordProcessor = mock(WordProcessor.class);
    private AsyncResponse response = mock(AsyncResponse.class);
    private CacheCallback cacheCallback = mock(CacheCallback.class);
    private ProtocolCallback protocolCallback = mock(ProtocolCallback.class);

    private WordService service;

    @Test
    public void invokationTest() {
        try {
            String query = "testQuery";
            String protocol = "testProtocol";
            Cache cache = new DummyCache();
            this.service = new WordServiceImpl(protocolFactory, cache, wordProcessor);
            Set<Word> words = new HashSet<>(Arrays.asList(new Word("test", 1)));
            when(protocolFactory.search(query, protocol)).thenReturn(new StringReader("test"));
            when(wordProcessor.process(new StringReader("test"))).thenReturn(words);
            service.asyncProcess(query, protocol, response);
            verify(protocolFactory).asyncSearch(eq(query), eq(protocol), any(ProtocolCallback.class));
        } catch (ProtocolException | IOException e) {
            fail(e.getMessage());
        }
    }

}
