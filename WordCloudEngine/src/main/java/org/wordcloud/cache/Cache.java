package org.wordcloud.cache;

import org.wordcloud.objects.Word;

import java.util.Set;

public interface Cache {

    void set(String key, Set<Word> words);

    Set<Word> get(String key);

}
