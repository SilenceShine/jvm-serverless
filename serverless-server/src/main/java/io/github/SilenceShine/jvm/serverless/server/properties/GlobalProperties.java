package io.github.SilenceShine.jvm.serverless.server.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "serverless")
public class GlobalProperties {

    private String timer = "0/5 * * * * ?";

    private String jarPath = "D:\\workspace\\GitHub\\SilenceShine\\jvm-serverless\\lib";

}
