package org.wordcloud.rest;

import com.codahale.metrics.annotation.Timed;
import org.wordcloud.handler.WordProcessor;
import org.wordcloud.objects.Word;
import org.wordcloud.protocol.ProtocolCallback;
import org.wordcloud.protocol.ProtocolException;
import org.wordcloud.protocol.ProtocolFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.Reader;
import java.util.Set;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class WordResource {

    private final ProtocolFactory protocolFactory;
    private final WordProcessor<Word> wordProcessor;

    public WordResource(ProtocolFactory protocolFactory, WordProcessor<Word> wordProcessor) {
        this.protocolFactory = protocolFactory;
        this.wordProcessor = wordProcessor;
    }

    @GET
    @Timed
    public void gatherWords(@QueryParam("query") String query, @QueryParam("protocol") String protocolName, @Suspended final AsyncResponse response) {
        protocolFactory.asyncSearch(query, protocolName, new ProtocolCallback() {
            @Override
            public void onSuccess(Reader reader) {
                try {
                    Set<Word> words = wordProcessor.process(reader);
                    response.resume(words);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(ProtocolException ex) {

            }
        });
    }
}