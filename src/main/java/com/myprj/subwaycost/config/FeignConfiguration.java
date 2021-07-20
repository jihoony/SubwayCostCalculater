package com.myprj.subwaycost.config;

import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {

    @Bean
    public Decoder feignDecoder(){
        return new FeignResultDecoder();
    }
}
