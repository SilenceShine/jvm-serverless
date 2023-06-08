package io.github.SilenceShine.jvm.serverless.server.util;

import io.github.SilenceShine.jvm.serverless.server.support.ServerlessClassLoader;
import io.github.SilenceShine.shine.util.log.LogUtil;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.util.Objects;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class JavaCompilerUtil {

    public static Class<?> compiler(ServerlessClassLoader classLoader, String filePath) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String dir = filePath.substring(0, filePath.lastIndexOf(File.separator));
        String fileName = FileUtil.getNameWithoutExt(filePath);
        String[] options = new String[]{
                "-encoding",
                "UTF-8",
                "-classpath",
                buildClassPath(classLoader),
//                "-d",
//                buildClassPath(filePath),
                filePath
        };
        compiler.run(null, null, null, options);
        return FileUtil.loopFiles(dir, pathname -> pathname.getName().contains(fileName) && pathname.getName().endsWith(".class"))
                .stream()
                .peek(file -> LogUtil.debug(JavaCompilerUtil.class, "compiler class:{}", file.getAbsolutePath()))
                .map(file -> loadClass(classLoader, file.getAbsolutePath()))
                .filter(Objects::nonNull)
                .filter(clazz -> !clazz.getName().contains("$"))
                .peek(clazz -> LogUtil.debug(JavaCompilerUtil.class, "load class:{}", clazz.getName()))
                .findFirst()
                .orElse(null);
    }

    private static String buildClassPath(ServerlessClassLoader classLoader) {
        StringBuilder sb = new StringBuilder();
        URL[] urls = classLoader.getURLs();
        for (URL url : urls) {
            String path = url.getPath();
            sb.append(path.substring(1)).append(File.pathSeparator);
        }
        return sb.toString();
    }

    private static Class<?> loadClass(ServerlessClassLoader classLoader, String classPath) {
        try {
            return classLoader.loadClass(classPath);
        } catch (Exception e) {
            LogUtil.info(JavaCompilerUtil.class, "loadClass error:{}", e.getMessage());
        }
        return null;
    }

}
