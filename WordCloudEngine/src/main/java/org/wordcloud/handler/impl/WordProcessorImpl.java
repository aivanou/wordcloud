package org.wordcloud.handler.impl;

import org.wordcloud.handler.WordProcessor;
import org.wordcloud.handler.transformer.WordTransformerChain;
import org.wordcloud.objects.Word;

import java.io.*;
import java.util.*;

/**
 * The default implementation of WordProcessor which uses @see Word
 * as object
 */
public class WordProcessorImpl implements WordProcessor<Word> {

    private final static int MAX_SIZE = 100;

    private WordTransformerChain transformerChain;

    public WordProcessorImpl(WordTransformerChain chain) {
        this.transformerChain = chain;
    }

    public Set<Word> process(Reader reader) throws IOException {
        if (reader == null)
            throw new NullPointerException("reader cannot be null");
        Map<String, Integer> words = new TreeMap<String, Integer>();
        BufferedReader bufReader = new BufferedReader(reader);
        String line;
        while ((line = bufReader.readLine()) != null) {
            String[] lineWords = line.trim().split("[ \t]+");
            for (String lineWord : lineWords) {
                lineWord = transformerChain.transformChain(lineWord);
                if (lineWord.isEmpty()) continue;
                if (!words.containsKey(lineWord)) {
                    words.put(lineWord, 1);
                } else {
                    words.put(lineWord, words.get(lineWord) + 1);
                }
            }
        }
        TreeSet<Word> sortedWords = new TreeSet<>(new WordComparator());
        for (Map.Entry<String, Integer> entry : words.entrySet()) {
            sortedWords.add(new Word(entry.getKey(), entry.getValue()));
        }
        return sortedWords;
    }

    private static class WordComparator implements Comparator<Word> {
        @Override
        public int compare(Word o1, Word o2) {
            if (o1 == null)
                return -1;
            else if (o2 == null)
                return 1;
            else {
                if (o1.getWord().equals(o2.getWord()))
                    return 0;
                else {
                    if (o1.getCount() == o2.getCount())
                        return 1;
                    else return -1 * Integer.compare(o1.getCount(), o2.getCount());
                }
            }
        }
    }

    public static void main(String[] args) {
    }
}
