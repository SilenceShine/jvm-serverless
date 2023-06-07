package io.github.SilenceShine.jvm.serverless.server.runner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Tuple;
import cn.hutool.extra.spring.SpringUtil;
import io.github.SilenceShine.jvm.serverless.common.web.ServerlessRequest;
import io.github.SilenceShine.jvm.serverless.server.properties.GlobalProperties;
import io.github.SilenceShine.jvm.serverless.server.properties.SpaceProperties;
import io.github.SilenceShine.jvm.serverless.server.support.ServerlessClassLoader;
import io.github.SilenceShine.jvm.serverless.server.util.JarUtil;
import io.github.SilenceShine.jvm.serverless.server.util.JavaCompilerUtil;
import io.github.SilenceShine.shine.util.log.LogUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.*;
import java.util.function.Function;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
@Slf4j
@Getter
@Setter
@Component
public class ServerlessRunner implements CommandLineRunner {

    private final Map<String, String> JAR_VERSION = new HashMap<>();
    private final Map<String, String> SPACE_BEANS = new HashMap<>();
    private final Map<String, String> INLAY_JAR_VERSION = new HashMap<>();
    private final Map<String, ServerlessClassLoader> SPACE_CLASSLOADER = new HashMap<>();

    @Autowired
    private GlobalProperties properties;

    @Override
    public void run(String... args) throws Exception {
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        Optional.ofNullable(classLoader)
                .flatMap(loader -> Optional.ofNullable(loader.getResource("")))
                .flatMap(url -> Optional.ofNullable(url.getProtocol()))
                .filter(protocol -> Objects.equals(protocol, "jar"))
                .flatMap(protocol -> Optional.ofNullable(classLoader.getResource("BOOT-INF/classpath.idx")))
                .map(FileUtil::readUtf8Lines)
                .stream()
                .flatMap(Collection::stream)
                .map(idx -> idx.substring(idx.lastIndexOf("/") + 1, idx.length() - 1))
                .forEach(jarName -> {
                    Tuple tuple = JarUtil.getDesc(jarName);
                    INLAY_JAR_VERSION.put(tuple.get(0).toString(), tuple.get(1).toString());
                });
        LogUtil.debug(this, "inlay_jar_version:{}", INLAY_JAR_VERSION);
    }

    @Scheduled(cron = "${serverless.timer}")
    private void updateJarAndClass() throws Exception {
        log.info("registrationOrUninstall 定时任务执行");
        properties.getSpaces().forEach((space, prop) -> {
            ServerlessClassLoader classLoader = SPACE_CLASSLOADER.getOrDefault(space, new ServerlessClassLoader());
            loadJar(classLoader, prop.getLibs());
            loadClass(classLoader, space, prop);
        });
    }

    private void loadJar(ServerlessClassLoader classLoader, String libPath) {
        // 查看是否有jar被加载和被卸载
        FileUtil.loopFiles(libPath)
                .stream()
                .filter(file -> file.getName().endsWith(".jar"))
                .filter(file -> {
                    Tuple tuple = JarUtil.getDesc(file.getName());
                    String jarName = tuple.get(0);
                    return !JAR_VERSION.containsKey(jarName) && !INLAY_JAR_VERSION.containsKey(jarName);
                })
                .peek(file -> LogUtil.debug(ServerlessRunner.class, "加载jar:{}", file.getName()))
                .peek(classLoader::addJar)
                .forEach(file -> {
                    Tuple tuple = JarUtil.getDesc(file.getName());
                    JAR_VERSION.put(tuple.get(0).toString(), tuple.get(1).toString());
                });
    }

    private void loadClass(ServerlessClassLoader classLoader, String space, SpaceProperties prop) {
        FileUtil.loopFiles(prop.getFunctions())
                .stream()
                .filter(file -> file.getName().endsWith(".java"))
                .forEach(file -> {
                    try {
                        String path = file.getAbsolutePath();
                        Class<?> functionClass = JavaCompilerUtil.compiler(classLoader, prop.getLibs(), path);
                        if (functionClass != null) {
                            Object obj = functionClass.getDeclaredConstructor().newInstance();
                            if (obj instanceof Function function) {
                                ServerlessRequest request = new ServerlessRequest();
                                request.setUrl("aaaaaaaaaaaaaaa");
                                function.apply(request);
                            }
                            SpringUtil.registerBean(space + functionClass.getName(), obj);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        SPACE_CLASSLOADER.put("space", classLoader);
    }

}
