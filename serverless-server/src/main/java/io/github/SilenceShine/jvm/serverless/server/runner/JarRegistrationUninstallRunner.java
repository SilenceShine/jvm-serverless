package io.github.SilenceShine.jvm.serverless.server.runner;

import cn.hutool.core.io.FileUtil;
import io.github.SilenceShine.shine.util.log.LogUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.*;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class JarRegistrationUninstallRunner implements CommandLineRunner {

    private final List<String> jars = new ArrayList<>();

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
                .forEach(text -> LogUtil.info(JarRegistrationUninstallRunner.class, text));
    }

    @Scheduled(cron = "${serverless.timer:0/5 * * * * ?}")
    private void registrationOrUninstall() throws Exception {
        log.info("registrationOrUninstall 定时任务执行");

    }

}
