package com.example.client.feign;


import com.example.client.Client.infrastructure.controller.DTO.MerchantOutputDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "merchant-service", url = "http://localhost:8081")
public interface MerchantFeignClient {

    @GetMapping("/api/merchant/findByClientId")
    List<MerchantOutputDto> getMerchantsByClientId(@RequestParam String clientId);
}