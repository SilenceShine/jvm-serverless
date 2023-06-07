package serverless;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class HelloWorld implements Function<Void, String> {

    @Override
    public String apply(Void unused) {
        StringUtils.isEmpty("");
        return "Hello World!";
    }

}
