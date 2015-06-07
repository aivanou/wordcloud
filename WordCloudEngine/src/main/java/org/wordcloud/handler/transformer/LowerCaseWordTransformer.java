package org.wordcloud.handler.transformer;


public class LowerCaseWordTransformer extends WordTransformerChain {

    public LowerCaseWordTransformer(WordTransformer next) {
        super(next);
    }

    public LowerCaseWordTransformer() {
        super();
    }


    @Override
    protected String transform(String word) {
        if (word != null)
            return word.toLowerCase();
        return "";
    }
}
