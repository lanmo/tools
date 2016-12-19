package com.yn.tools.serialize;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by yangnan on 16/12/19.
 */
public class FastJsonSerialization implements Serialization {

    public byte[] serialize(Object data) {

        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        serializer.config(SerializerFeature.WriteEnumUsingToString, true);
        serializer.config(SerializerFeature.WriteClassName, true);
        serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
        serializer.write(data);

        return out.toBytes(null);
    }

    public <T> T deserialize(byte[] data, Class<T> clz) {
        return JSONObject.parseObject(data, clz);
    }

    public <T> T deserialize(byte[] data, TypeReference<T> tTypeReference) {
        return JSONObject.parseObject(new String(data), tTypeReference);
    }
}
