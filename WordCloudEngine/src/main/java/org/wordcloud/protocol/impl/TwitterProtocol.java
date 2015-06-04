package org.wordcloud.protocol.impl;


import org.wordcloud.protocol.AsyncExecutor;
import org.wordcloud.protocol.Protocol;
import org.wordcloud.protocol.ProtocolCallback;
import org.wordcloud.protocol.ProtocolException;
import twitter4j.*;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwitterProtocol extends AsyncExecutor implements Protocol {

    private final Twitter twitter = TwitterFactory.getSingleton();

    public TwitterProtocol(ExecutorService exec) {
        super(exec);
    }

    public Reader search(String queryText) throws ProtocolException {
        Query query = new Query(transformQuery(queryText));
        try {
            QueryResult result = twitter.search(query);
            return transformResults(result);
        } catch (TwitterException e) {
            throw new ProtocolException(e);
        }
    }

    public void asyncSearch(final String query, final ProtocolCallback callback) {
        executeTask(query, callback);
    }

    private String transformQuery(String query) {
        return "%23" + query;
    }

    private Reader transformResults(QueryResult result) {
        StringBuilder builder = new StringBuilder();
        for (Status status : result.getTweets()) {
            builder.append(status.getText());
        }
        return new StringReader(builder.toString());
    }

    public static void main(String[] args) throws ProtocolException {
        Protocol p = new TwitterProtocol(Executors.newCachedThreadPool());
        p.asyncSearch("#something", new ProtocolCallback() {
            public void onSuccess(Reader reader) {
                try {
                    System.out.println(new BufferedReader(reader).readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(ProtocolException ex) {
                System.out.println(ex.toString());
            }
        });
//        p.search("#something");
    }

}
