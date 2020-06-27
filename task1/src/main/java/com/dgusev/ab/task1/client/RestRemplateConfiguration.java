package com.dgusev.ab.task1.client;

import com.dgusev.ab.task1.config.Task1Config;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.security.KeyStore;

@Configuration
public class RestRemplateConfiguration {

    @Autowired
    private Task1Config task1Config;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new ClassPathResource(task1Config.getCertPath()).getInputStream(), task1Config.getCertPassword().toCharArray());
        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, task1Config.getCertPassword().toCharArray())
                .loadTrustMaterial(null, new TrustAllStrategy()).build();

        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
        return builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }
}
