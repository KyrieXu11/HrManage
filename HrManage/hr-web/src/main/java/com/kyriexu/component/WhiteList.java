package com.kyriexu.component;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author KyrieXu
 * @since 2020/3/28 20:13
 **/
@Component
@ConfigurationProperties(prefix = "white")
@PropertySource("classpath:config/whitelist.properties")
@Data
public class WhiteList {
    private List<String> list;
}
