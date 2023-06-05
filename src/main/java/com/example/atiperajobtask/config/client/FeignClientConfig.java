package com.example.atiperajobtask.config.client;

import com.example.atiperajobtask.config.exception.MessageErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignClientConfig {
    @Bean
    ErrorDecoder errorDecoder(){return new MessageErrorDecoder();}
}
