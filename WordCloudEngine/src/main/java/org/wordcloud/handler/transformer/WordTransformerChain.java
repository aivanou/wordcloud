package org.wordcloud.handler.transformer;


public abstract class WordTransformerChain implements WordTransformer {

    private final WordTransformer next;

    public WordTransformerChain(WordTransformer next) {
        this.next = next;
    }

    public WordTransformerChain() {
        this.next = null;
    }

    protected abstract String transform(String word) throws Exception;


    public String transformChain(String word) {
        try {
            String newWord = transform(word);
            if (next != null)
                return next.transformChain(newWord);
            else return newWord;
        } catch (Exception ex) {
            return "";
        }
    }

}
