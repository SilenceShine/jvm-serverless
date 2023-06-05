package io.github.SilenceShine.jvm.serverless.server;

import io.github.SilenceShine.jvm.serverless.server.properties.GlobalProperties;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
@Slf4j
@EnableScheduling
@AllArgsConstructor
@SpringBootApplication
@EnableConfigurationProperties(GlobalProperties.class)
public class ServerApplication {

    private final GlobalProperties properties;

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @PostConstruct
    public void init() {
        log.info(properties.getTimer());
    }


}
