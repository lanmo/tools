package com.yn.tools.xml;

import com.yn.tools.utils.L;

/**
 * Created by yangnan on 2016/12/9.
 */
public class ConfigTest {
    public static void main(String[] args) {
        Example example = ConfigManager.get(Example.class);
        L.d(example);
    }
}
