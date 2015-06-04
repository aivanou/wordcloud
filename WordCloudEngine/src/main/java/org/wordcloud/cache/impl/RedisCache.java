package org.wordcloud.cache.impl;


import org.wordcloud.cache.Cache;
import org.wordcloud.objects.Word;

import java.util.Set;

public class RedisCache implements Cache {
    public void set(String key, Set<Word> words) {

    }

    public Set<Word> get(String key) {
        return null;
    }
}
