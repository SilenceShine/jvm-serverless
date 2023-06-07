package io.github.SilenceShine.jvm.serverless.server.util;

import io.github.SilenceShine.jvm.serverless.server.support.ServerlessClassLoader;
import io.github.SilenceShine.shine.util.log.LogUtil;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class JavaCompilerUtil {

    public static Class<?> compiler(ServerlessClassLoader classLoader, String libs, String filePath) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String dir = filePath.substring(0, filePath.lastIndexOf("\\"));
        String fileName = FileUtil.getNameWithoutExt(filePath);
        String[] options = new String[]{
                "-classpath",
                libs,
//                "-d",
//                buildClassPath(filePath),
                filePath
        };
        compiler.run(null, null, null, options);

        FileUtil.loopFiles(dir, pathname -> pathname.getName().contains(fileName) && pathname.getName().endsWith(".class"))
                .stream()
                .peek(file -> LogUtil.debug(JavaCompilerUtil.class, "load class:{}", file.getAbsolutePath()))
                .forEach(classLoader::addURL);

        return null;
    }

    public static Class<?> compilerDir(ServerlessClassLoader classLoader, String filePath) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null);
        Iterable<? extends JavaFileObject> iterable = manager.getJavaFileObjects(filePath);
        compiler.getTask(null, manager, null, null, null, iterable).call();
        manager.close();
        String classPath = filePath.substring(0, filePath.lastIndexOf(".")) + ".class";
        return classLoader.loadClass(classPath);
    }

    private static String buildClassPath(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("\\") + 1) + "target";
    }

}
