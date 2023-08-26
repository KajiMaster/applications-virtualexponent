package com.booklisting.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        int bufferSizeInBytes = 1024 * 1024 * 10; // 10MB, adjust as necessary
        
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(bufferSizeInBytes))
                .build();
        
        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl("https://openlibrary.org") // Or other base URL if necessary
                .build();
    }
}
