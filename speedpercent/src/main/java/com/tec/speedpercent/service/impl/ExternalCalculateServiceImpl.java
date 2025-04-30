package com.tec.speedpercent.service.impl;

import com.tec.speedpercent.client.ExternalClient;
import com.tec.speedpercent.domain.dto.PercentageResponseDto;
import com.tec.speedpercent.service.ExternalCalculateService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExternalCalculateServiceImpl implements ExternalCalculateService {

    private final CacheManager cacheManager;
    private final ExternalClient externalClient;

    private final String CACHENAME = "percentageCache";
    private final String CACHEKEY = "percentage";

    @Override
    @Retryable(value = {FeignException.NotFound.class},
            maxAttemptsExpression = "#{${tec.retry.maxAttempts:3}}",
            backoff = @Backoff(
                    delayExpression = "#{${tec.retry.delay:1000}}",
                    maxDelayExpression = "#{${tec.retry.max-delay:2000}}"))// 2 SECONDS
    public PercentageResponseDto getPercentage() {
        try {
            PercentageResponseDto response = externalClient.getPercentage();

            cachePercentage(response.percentage());

            return new PercentageResponseDto(response.percentage(), "", true);
        } catch (Exception ex) {
            log.error("[ExternalCalculateServiceImpl] error get percentage: {}", ex.getMessage(), ex.getCause());
            return getCachePercentageOrFail(ex.getMessage());
        }
    }

    private void cachePercentage(double value) {
        Optional.ofNullable(cacheManager.getCache(CACHENAME))
                .ifPresent(cache -> cache.put(CACHEKEY, value));
    }

    private PercentageResponseDto getCachePercentageOrFail(String message) {
        Double cachedPercentage = Optional.ofNullable(cacheManager.getCache(CACHENAME))
                .map(cache -> cache.get(CACHEKEY, Double.class))
                .orElse(null);

        if (cachedPercentage != null) {
            return new PercentageResponseDto(cachedPercentage, message, false);
        } else {
            throw new RuntimeException(message);
        }
    }
}
