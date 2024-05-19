package com.example.aiserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.impl.async.CloseableHttpAsyncClient;
import org.apache.hc.client5.http.impl.async.HttpAsyncClients;
import org.apache.hc.client5.http.impl.routing.DefaultProxyRoutePlanner;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gpt.proxy")
@Setter
@Slf4j
public class ChatGptConfig {
    private String host;
    private Integer port;

    @Bean
    public CloseableHttpAsyncClient httpAsyncClient() {
        if (host != null && port != null) {
            log.info("use proxy, proxy host is {}, proxy port is {}", host, port);
            HttpHost proxy = new HttpHost(host, port);
            DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
            return HttpAsyncClients.custom().setRoutePlanner(routePlanner).build();
        }
        return HttpAsyncClients.createHttp2Default();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
