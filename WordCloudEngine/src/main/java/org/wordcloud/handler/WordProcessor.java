package org.wordcloud.handler;


import java.io.IOException;
import java.io.Reader;
import java.util.Set;

public interface WordProcessor<T> {

    Set<T> process(Reader reader) throws IOException;

}
