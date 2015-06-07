package org.wordcloud.cache;

import org.wordcloud.objects.Word;

import java.util.Set;

public interface Cache {

    /**
     * Synchronous method
     *
     * @param key
     * @param words
     */
    void set(String key, Set<Word> words);

    /**
     * Synchronous get method
     * Returns null if the key is not found
     * @param key
     * @return
     */
    Set<Word> get(String key);


    /**
     * Asynchronous get Method
     *
     * @param key
     * @param callback
     */
    void asyncGet(String key, CacheCallback callback);

}
