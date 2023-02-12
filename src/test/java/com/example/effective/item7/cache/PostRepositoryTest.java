package com.example.effective.item7.cache;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest {

    @Test
    @DisplayName("WeakHashMap, WeakReference 등을 사용하면 참조가 없어질 때 바로 메모리 정리")
    void cache() throws InterruptedException {
        PostRepository postRepository = new PostRepository();
        CacheKey key1 = new CacheKey(1);
        postRepository.getPosById(key1);

        assertFalse(postRepository.getCache().isEmpty());

//        key1 = null;

        System.out.println("run gc");
        System.gc();
        System.out.println("wait");
        Thread.sleep(3000L);

        assertTrue(postRepository.getCache().isEmpty());
    }

    @Test
    void backgroundThread() throws InterruptedException {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        PostRepository postRepository = new PostRepository();
        CacheKey key1 = new CacheKey(1);
        postRepository.getPosById(key1);

        Runnable removeOldCache = () -> {
            System.out.println("running removeOldCache task");
            Map<CacheKey, Post> cache = postRepository.getCache();
            Set<CacheKey> cacheKeySet = cache.keySet();
            Optional<CacheKey> key = cacheKeySet.stream().min(Comparator.comparing(CacheKey::getCreated));
            key.ifPresent((k) -> {
                System.out.println("removing " + k);
                cache.remove(k);
            });
        };

        System.out.println("The time is : " + new Date());

        executor.scheduleAtFixedRate(removeOldCache, 1, 3, TimeUnit.SECONDS);

        Thread.sleep(20000L);

        executor.shutdown();;


    }

}