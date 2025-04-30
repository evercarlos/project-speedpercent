package com.tec.speedpercent.client;

import com.tec.speedpercent.domain.dto.PercentageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "externalClient", url = "${tec.external.endPoint.percentage}")
public interface ExternalClient {

    @GetMapping(value = "${tec.external.endPoint.percentage.getPercentage}", produces = MediaType.APPLICATION_JSON_VALUE)
    PercentageResponseDto getPercentage();
}
