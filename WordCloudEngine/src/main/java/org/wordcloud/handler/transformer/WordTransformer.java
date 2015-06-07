package org.wordcloud.handler.transformer;


/**
 * Transforms the word from one state to another
 * Used by  @see WordProcessor for transforming the words into desirable format
 */
public interface WordTransformer {

    String transformChain(String word);

}
