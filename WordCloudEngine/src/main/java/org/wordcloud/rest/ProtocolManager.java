package org.wordcloud.rest;

import io.dropwizard.lifecycle.Managed;
import org.wordcloud.protocol.ProtocolCallback;
import org.wordcloud.protocol.ProtocolFactory;

/**
 * Manages protocol factory resources
 */
public class ProtocolManager implements Managed {

    private final ProtocolFactory factory;

    public ProtocolManager(ProtocolFactory factory) {
        this.factory = factory;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {
        factory.stop();
    }

}
