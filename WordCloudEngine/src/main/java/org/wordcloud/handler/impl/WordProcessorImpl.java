package org.wordcloud.handler.impl;

import org.wordcloud.handler.WordProcessor;
import org.wordcloud.objects.Word;

import java.io.*;
import java.util.*;

public class WordProcessorImpl implements WordProcessor<Word> {

    private final static int MAX_SIZE = 100;

    private Set<String> forbiddenWords = new HashSet<>();

    public WordProcessorImpl(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        fillForbiddenWords(reader);
    }

    public WordProcessorImpl(File fWords) throws IOException {
        this(new FileInputStream(fWords));
    }

    public WordProcessorImpl() {
        this.forbiddenWords = new HashSet<>();
    }

    public Set<Word> process(Reader reader) throws IOException {
        Map<String, Integer> words = new TreeMap<String, Integer>();
        BufferedReader bufReader = new BufferedReader(reader);
        String line;
        while ((line = bufReader.readLine()) != null) {
            String[] lineWords = line.trim().split("[ \t]+");
            for (String lineWord : lineWords) {
                lineWord = processWord(lineWord);
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
        return subset(sortedWords, MAX_SIZE);
    }

    private Set<Word> subset(NavigableSet<Word> words, int maxSize) {
        if (words.isEmpty())
            return words;
        int ind = 0;
        Iterator<Word> it = words.descendingIterator();
        Word lastWord = null;
        Set<Word> hashSet = new HashSet<>();
        for (; it.hasNext(); ind++) {
            Word w = it.next();
            hashSet.add(w);
            if (ind == maxSize) {
                lastWord = w;
                break;
            }
        }
        if (lastWord == null)
            return words;
        return hashSet;
    }

    private String processWord(String unprocessedWord) {
        unprocessedWord = unprocessedWord.trim().toLowerCase();
        if (forbiddenWords.contains(unprocessedWord))
            return "";
        return unprocessedWord;
    }

    private void fillForbiddenWords(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            forbiddenWords.add(line.trim().toLowerCase());
        }
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
}
