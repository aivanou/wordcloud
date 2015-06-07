package org.wordcloud.cache.impl;

import org.wordcloud.cache.Cache;
import org.wordcloud.cache.CacheCallback;
import org.wordcloud.objects.Word;

import java.util.Set;

public class DummyCache implements Cache {
    @Override
    public void set(String key, Set<Word> words) {
        //do nothing
    }

    @Override
    public Set<Word> get(String key) {
        //do nothing
        return null;
    }

    @Override
    public void asyncGet(String key, CacheCallback callback) {
        callback.onFailure(key);
    }
}
