package io.github.SilenceShine.jvm.serverless.server.support;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.JarClassLoader;

/**
 * @author SilenceShine
 * @github <a href="https://github.com/SilenceShine">SilenceShine</a>
 * @since 1.0
 */
public class ServerlessClassLoader extends JarClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> aClass = super.findClass(name);
        if (null != aClass) {
            return aClass;
        }
        byte[] bytes = IoUtil.readBytes(FileUtil.getInputStream(name), true);
        if (null != bytes) {
            return defineClass(null, bytes, 0, bytes.length);
        }
        return super.findClass(name);
    }

}
