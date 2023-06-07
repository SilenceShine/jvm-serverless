package io.github.SilenceShine.jvm.serverless.server.util;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

    public static String getNameWithoutExt(String filePath) {
        String name = cn.hutool.core.io.FileUtil.getName(filePath);
        return name.substring(0, name.lastIndexOf("."));
    }

}
