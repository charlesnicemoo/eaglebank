package com.eaglebank.aggregate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ClearCache {

    @Autowired
    private CacheManager cacheManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(ClearCache.class);

    // Runs every 10 minutes
    @Scheduled(fixedRate = 600000)
    public void clearAccountCache() {
        Cache accounts = cacheManager.getCache("accounts");
        if (accounts != null) {
            accounts.clear();
            LOGGER.debug("Account cache cleared at {}", Instant.now());
        }
    }
}