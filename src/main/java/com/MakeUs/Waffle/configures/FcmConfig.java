package com.MakeUs.Waffle.configures;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "fcm")
public class FcmConfig {

    private String api_url;
}
