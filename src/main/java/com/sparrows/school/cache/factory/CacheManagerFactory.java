package com.sparrows.school.cache.factory;

import com.sparrows.school.cache.key.CacheKeyGenerator;
import com.sparrows.school.cache.layer.CacheLayer;
import com.sparrows.school.cache.layer.InMemoryCacheLayer;
import com.sparrows.school.cache.layer.MultiLevelCacheManager;
import com.sparrows.school.cache.layer.RedisCacheLayer;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class CacheManagerFactory {

    public static <K, V> MultiLevelCacheManager<K, V> create(
            Duration inMemoryTtl,
            Duration redisTtl,
            RedisTemplate<String, String> redisTemplate,
            CacheKeyGenerator<K> keyGenerator,
            Function<V, String> serializer,
            Function<String, V> deserializer

    ) {
        CacheLayer<K, V> inMemoryLayer = new InMemoryCacheLayer<>(inMemoryTtl, keyGenerator);
        CacheLayer<K, V> redisLayer = new RedisCacheLayer<>(redisTemplate, redisTtl, keyGenerator, serializer, deserializer);

        return new MultiLevelCacheManager<>(List.of(inMemoryLayer, redisLayer));
    }
}
