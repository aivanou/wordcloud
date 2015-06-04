package org.wordcloud.protocol.impl;


import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import org.w3c.dom.Document;
import org.wordcloud.protocol.AsyncExecutor;
import org.wordcloud.protocol.Protocol;
import org.wordcloud.protocol.ProtocolCallback;
import org.wordcloud.protocol.ProtocolException;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RssProtocol extends AsyncExecutor implements Protocol {

    private DocumentBuilderFactory builderFactory;
    private DocumentBuilder documentBuilder;

    public RssProtocol(ExecutorService exec) throws ParserConfigurationException {
        super(exec);
        builderFactory = DocumentBuilderFactory.newInstance();
        documentBuilder = builderFactory.newDocumentBuilder();
    }

    public Reader search(String query) throws ProtocolException {
        validateURL(query);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed;
        try {
            feed = input.build(parseDocument(query));
        } catch (FeedException e) {
            throw new ProtocolException(e);
        }
        List<SyndEntry> entries = feed.getEntries();
        return transformEntries(entries);
    }

    public void asyncSearch(String query, ProtocolCallback callback) {
        executeTask(query, callback);
    }

    private Reader transformEntries(List<SyndEntry> entries) {
        StringBuilder sb = new StringBuilder();
        for (SyndEntry entry : entries) {
            sb.append(entry.getDescription().getValue());
            System.out.println(entry.getDescription().getValue());
        }
        return new StringReader(sb.toString());
    }

    private void validateURL(String url) throws ProtocolException {
        try {
            URL u = new URL(url);
        } catch (MalformedURLException e) {
            throw new ProtocolException(e);
        }
    }

    private Document parseDocument(String url) throws ProtocolException {
        Document doc;
        try {
            doc = documentBuilder.parse(url);
        } catch (SAXException | IOException e) {
            throw new ProtocolException(e);
        }
        return doc;
    }

    public static void main(String[] args) throws ParserConfigurationException, ProtocolException {
        Protocol p = new RssProtocol(Executors.newCachedThreadPool());
//        Reader reader = p.search("http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk");
//        p.asyncSearch("http://feeds.bbci.co.uk/news/world/rss.xml?edition=uk", new ProtocolCallback() {
//            @Override
//            public void onSuccess(Reader reader) {
//                try {
//                    System.out.println(new BufferedReader(reader).readLine());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(ProtocolException ex) {
//
//            }
//        });
    }
}
