package io.github.SilenceShine.jvm.serverless.server.manager;

import io.github.SilenceShine.jvm.serverless.server.support.ServerlessClassLoader;

import javax.tools.ForwardingJavaFileManager;
import javax.tools.StandardJavaFileManager;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class ServerlessJavaFileManager extends ForwardingJavaFileManager<StandardJavaFileManager> {

    private final ServerlessClassLoader classLoader;

    public ServerlessJavaFileManager(StandardJavaFileManager fileManager, ServerlessClassLoader classLoader) {
        super(fileManager);
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return classLoader;
    }

}
