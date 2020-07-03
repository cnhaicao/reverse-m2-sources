package com.reverse.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * reverse spring boot application
 *
 * @author guoguoqiang
 * @since 2020年07月02日
 */
@Slf4j
@SpringBootApplication
public class ReverseApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ReverseApplication.class, args);
        log.info("hello world.");
    }
}
