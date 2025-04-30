package com.tec.speedpercent.service;

import com.tec.speedpercent.client.ExternalClient;
import com.tec.speedpercent.domain.dto.PercentageResponseDto;
import com.tec.speedpercent.service.impl.ExternalCalculateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExternalCalculateServiceTest {

    @Mock
    private CacheManager cacheManager;

    @Mock
    private ExternalClient externalClient;

    @Mock
    private Cache cache;

    @InjectMocks
    ExternalCalculateServiceImpl externalCalculateService;

    private final String CACHEKEY = "percentage";

    @BeforeEach
    public void setup() {
        when(cacheManager.getCache(anyString())).thenReturn(cache);
    }

    @Test
    void shouldGetPercentageSuccessfully() {
        PercentageResponseDto externalResponse = new PercentageResponseDto(75.0, "", true);
        when(externalClient.getPercentage()).thenReturn(externalResponse);

        PercentageResponseDto result = externalCalculateService.getPercentage();

        assertNotNull(result);
        assertEquals(75.0, result.percentage());
        verify(externalClient, times(1)).getPercentage();
        verify(cache, times(1)).put(CACHEKEY, 75.0);
    }

    @Test
    void shouldUseCacheWhenExternalServiceFails() {
        when(externalClient.getPercentage()).thenThrow(new RuntimeException("External service failed"));
        when(cache.get(CACHEKEY, Double.class)).thenReturn(50.0);

        PercentageResponseDto result = externalCalculateService.getPercentage();

        assertNotNull(result);
        assertEquals(50.0, result.percentage());
        verify(cache, times(0)).put(CACHEKEY, 50.0);
    }
}
