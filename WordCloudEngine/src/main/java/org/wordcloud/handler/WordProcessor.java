package org.wordcloud.handler;


import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * Retrieves words from the Reader and transforms them into relevant set of words
 *
 * @param <T>
 */
public interface WordProcessor<T> {

    /**
     * Processes words
     * This method should not close the reader, it should be closed somewhere outside
     *
     * @param reader
     * @return
     * @throws IOException
     */
    Set<T> process(Reader reader) throws IOException;

}
