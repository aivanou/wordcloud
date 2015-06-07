package org.wordcloud.objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Word class, used in @see org.wordcloud.handler.WordProcessor
 */
public class Word implements Serializable {
    private String word;
    private int count;

    public Word(String word, int count) {
        this.word = word;
        this.count = count;
    }

    @JsonProperty
    public String getWord() {
        return word;
    }

    @JsonProperty
    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word1 = (Word) o;

        if (count != word1.count) return false;
        return !(word != null ? !word.equals(word1.word) : word1.word != null);

    }

    @Override
    public int hashCode() {
        int result = word != null ? word.hashCode() : 0;
        result = 31 * result + count;
        return result;
    }
}
