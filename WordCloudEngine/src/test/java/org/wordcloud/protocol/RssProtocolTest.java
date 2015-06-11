package org.wordcloud.protocol;


import org.junit.Test;
import org.wordcloud.protocol.impl.RssProtocol;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RssProtocolTest {

    @Test
    public void testRssSyncSearch() {
        String link = "http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk";
        Protocol protocol = new RssProtocol(Executors.newCachedThreadPool());
        try {
            String result = new BufferedReader(protocol.search(link)).readLine();
            assertTrue(!result.isEmpty());
        } catch (ProtocolException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testAsyncSearch() {
        String link = "http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk";
        final Protocol protocol = new RssProtocol(Executors.newCachedThreadPool());
        protocol.asyncSearch(link, new ProtocolCallback() {
            @Override
            public void onSuccess(Reader reader) {
                try {
                    assertTrue(!(new BufferedReader(reader).readLine().isEmpty()));
                } catch (IOException e) {
                    fail(e.getMessage());
                } finally {
                    protocol.stop();
                }
            }

            @Override
            public void onFailure(ProtocolException ex) {
                fail(ex.getMessage());
                protocol.stop();
            }
        });

    }

}
