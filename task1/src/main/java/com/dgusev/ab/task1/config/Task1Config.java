package com.dgusev.ab.task1.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "task1")
@Getter
@Setter
public class Task1Config {

    private String certPath;

    private String certPassword;

    private String clientKey;

    private String apiPath;

    private String socketUrl;

}
