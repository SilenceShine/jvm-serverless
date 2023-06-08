package test;

import cn.hutool.core.util.StrUtil;
import io.github.SilenceShine.jvm.serverless.common.web.ServerlessRequest;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class HelloWorld implements Function<ServerlessRequest, String> {

    @Override
    public String apply(ServerlessRequest unused) {
        StringUtils.isEmpty("");
        StrUtil.isEmpty("");
        return "Hello World!";
    }

}
