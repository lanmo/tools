package com.yn.tools.xml;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import sun.applet.Main;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yangnan on 2016/12/9.
 * @link {'http://simple.sourceforge.net/download/stream/doc/tutorial/tutorial.php#map'}
 * XML解析
 */
public class ConfigManager {

    private static final ConcurrentHashMap<Class<?>, Object> configMap = new ConcurrentHashMap<Class<?>, Object>();
    private static final String SUFFIX = ".xml";

    public static <T> T get(Class<T> tClass, String filePath) {
        Object result = configMap.get(tClass);
        if (result == null) {
            synchronized (tClass) {
                if (result == null) {
                    String fileName = null;
                    Root root = tClass.getAnnotation(Root.class);
                    if (root == null) {
                        String className = tClass.getName();
                        fileName = className.substring(className.lastIndexOf(".") + 1);
                    } else {
                        fileName = root.name();
                    }

                    fileName += SUFFIX;

                    try {
                        File file = new File(filePath, fileName);
                        T t = read(tClass, file);
                        configMap.put(tClass, t);
                        return t;
                    } catch (Exception e) {
                        throw new IllegalArgumentException(e);
                    }
                }
            }
        }

        return (T) result;
    }

    public static <T> T get(Class<T> tClass) {
        String defaultFath = null;
        try {
            defaultFath = tClass.getClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
        return get(tClass, defaultFath);
    }

    private static <T> T read(Class<T> tClass, File filePath) throws Exception {
        Serializer serializer = new Persister();
        return serializer.read(tClass, filePath);
    }

}
