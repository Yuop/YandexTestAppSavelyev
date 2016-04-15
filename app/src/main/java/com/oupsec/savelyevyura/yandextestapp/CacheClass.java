package com.oupsec.savelyevyura.yandextestapp;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by savelyevyura on 12/04/16.
 */
public class CacheClass extends LruCache<String,Bitmap> {
    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     *
     */
    private static final int maxSize = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private static final int cacheSize = maxSize / 8;
    public CacheClass() {
        super(cacheSize);

    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getByteCount() / 1024;
    }
}

