package com.yn.tools.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by yangnan on 16/10/27.
 *
 * 打控制台log日志
 */
public final class L {

    private static final SerializerFeature[] FEATURES = {
            SerializerFeature.DisableCircularReferenceDetect,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.PrettyFormat
    };

    public static void d(Object obj) {
        System.out.println(JSONObject.toJSONString(obj, FEATURES));
    }
    public static void d(String key, Object obj) {
        System.out.println(key + ":" + JSONObject.toJSONString(obj, FEATURES));
    }
}
