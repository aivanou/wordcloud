package org.wordcloud.protocol;

import java.io.Reader;
import java.util.concurrent.ExecutorService;

/**
 * Defines methods for asynchronous execution, aggregates ExecutorService
 */
public abstract class AsyncExecutor implements Protocol {

    private final ExecutorService exec;

    public AsyncExecutor(ExecutorService exec) {
        this.exec = exec;
    }

    protected void executeTask(final String query, final ProtocolCallback callback) {
        exec.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Reader reader = search(query);
                    callback.onSuccess(reader);
                } catch (ProtocolException e) {
                    callback.onFailure(e);
                }
            }
        });
    }

    public void stop() {
        exec.shutdown();
    }

}
