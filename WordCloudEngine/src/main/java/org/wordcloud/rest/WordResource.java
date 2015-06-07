package org.wordcloud.rest;

import com.codahale.metrics.annotation.Timed;
import org.wordcloud.handler.WordProcessor;
import org.wordcloud.objects.ErrorMessage;
import org.wordcloud.objects.Word;
import org.wordcloud.protocol.ProtocolCallback;
import org.wordcloud.protocol.ProtocolException;
import org.wordcloud.protocol.ProtocolFactory;
import org.wordcloud.service.WordService;

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


/**
 * Resourse for query
 */
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public class WordResource {

    WordService wordService;

    public WordResource(WordService wordService) {
        this.wordService = wordService;
    }

    @GET
    @Timed
    public void gatherWords(@QueryParam("query") String query, @QueryParam("protocol") String protocolName, @Suspended final AsyncResponse response) {
        wordService.asyncProcess(query, protocolName, response);
    }
}