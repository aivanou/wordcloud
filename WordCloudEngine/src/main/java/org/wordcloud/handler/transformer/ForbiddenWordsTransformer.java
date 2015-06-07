package org.wordcloud.handler.transformer;


import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ForbiddenWordsTransformer extends WordTransformerChain {

    private final Set<String> forbiddenWords = new HashSet<>();

    public ForbiddenWordsTransformer(String file) throws IOException {
        super();
        fill(file);
    }

    public ForbiddenWordsTransformer(WordTransformerChain next, String file) throws IOException {
        super(next);
        fill(file);
    }

    private void fill(String filename) throws IOException {
        ClassLoader classLoader = ForbiddenWordsTransformer.class.getClassLoader();
        BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filename)));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                forbiddenWords.add(line);
            }
        } finally {
            reader.close();
        }
    }

    @Override
    protected String transform(String word) {
        if (forbiddenWords.contains(word))
            return "";
        return word;
    }

}
