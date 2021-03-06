package com.ligen.config;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CommonConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .followRedirects(true)
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(30,TimeUnit.SECONDS).build();
    }
}
