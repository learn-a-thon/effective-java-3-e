package com.example.effective.item7.cache;

import java.util.Map;
import java.util.WeakHashMap;

public class PostRepository {

    private Map<CacheKey, Post> cache;

    public PostRepository() {
        this.cache = new WeakHashMap<>();
    }

    public Post getPosById(CacheKey key) {
        if (cache.containsKey(key)) {
            return cache.get(key);
        } else {
            // DB에서 가져오거나 api를 통해 읽어올 수 있다.
            Post post = new Post();
            cache.put(key, post);
            return post;
        }
    }

    public Map<CacheKey, Post> getCache() {
        return cache;
    }

}
