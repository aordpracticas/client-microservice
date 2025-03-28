
package com.example.client.feign;

import com.example.client.dto.MerchantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name = "merchant-service", url = "http://localhost:8081")
public interface MerchantFeignClient {
    @GetMapping("/api/merchant/client/{clientId}")
    List<MerchantDto> getMerchantsByClientId(@PathVariable String clientId);
}
