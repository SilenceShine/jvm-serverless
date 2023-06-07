package io.github.SilenceShine.jvm.serverless.server;

import io.github.SilenceShine.jvm.serverless.server.properties.GlobalProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import reactor.core.publisher.Flux;

import java.util.function.Function;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
@Slf4j
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(GlobalProperties.class)
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    public Function<Flux<String>, Flux<String>> uppercase() {
        return flux -> flux.map(String::toUpperCase);
    }

    @Bean
    public Function<String, String> uppercase2() {
        return String::toUpperCase;
    }

}
