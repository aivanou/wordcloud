package org.wordcloud.cache;


import com.sun.corba.se.impl.ior.ByteBuffer;
import org.wordcloud.objects.Word;

import java.util.Set;

/**
 * The callback function for asynchronous cache computation
 */
public interface CacheCallback {

    /**
     * When cache has data onSuccess will be called
     *
     * @param key
     * @param value
     */
    void onSuccess(String key, Set<Word> value);

    /**
     * When cache has no data or stale data, onFailure will be called
     *
     * @param key
     */
    void onFailure(String key);

    /**
     * When the error occured while connecting to the cache
     *
     * @param ex
     * @param key
     */
    void onError(Exception ex, String key);

}
