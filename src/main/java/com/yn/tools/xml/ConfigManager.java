package com.yn.tools.xml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.InputStream;

/**
 * Created by yangnan on 2016/12/9.
 * @link {'http://simple.sourceforge.net/download/stream/doc/tutorial/tutorial.php#map'}
 * XML解析
 */
public class ConfigManager {

    public static <T> T get(Class<T> tClass) {
        Serializer serializer = new Persister();

        InputStream is = tClass.getClassLoader().getResourceAsStream("example.xml");
        T t = null;
        try {
            t = serializer.read(tClass, is);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return t;
    }
}
