package io.github.SilenceShine.jvm.serverless.server.util;

import cn.hutool.core.lang.Tuple;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class JarUtil {

    public static Tuple getDesc(String jarName) {
        int index = jarName.lastIndexOf("-");
        if (jarName.contains("SNAPSHOT")) {
            index = jarName.lastIndexOf("-", index - 1);
        }
        if (index > 0) {
            String version = jarName.substring(index + 1, jarName.lastIndexOf("."));
            String name = jarName.substring(0, index);
            return new Tuple(name, version);
        }
        return new Tuple(jarName, "");
    }

    public static void main(String[] args) {
        System.out.println(getDesc("serverless-server-1.0.0.jar"));
        System.out.println(getDesc("serverless-server-1.0.0-SNAPSHOT.jar"));
    }

}
