package com.yn.tools.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yangnan on 16-8-8.
 *
 * 缓存key
 */
public enum CacheKeyEnum {

    /* 白名单缓存 */
    TEST(1, "ccc:whitelist:${city}:${dd}:${cc}", 0),
    TEST3(1, "ccc:whitelist:${city}:${dd}:${cc}", 2);
    CacheKeyEnum(int id, String key, int expireTime) {
        this.expireTime = expireTime;
        this.key = key;
        this.id = id;
    }

    public Pattern pattern = Pattern.compile("\\$\\{\\w+\\}");

    /**
     * 获取模板对应的key
     *
     * @param params
     * @return
     */
    public String toKey(Object... params) {

        Matcher m = pattern.matcher(getKey());
        int count = 0;
        while(m.find()) {
            count++;
        }

        if (count != params.length)
            throw new IllegalArgumentException(String.format("参数不正确 count:%d param.length:%d", count, params.length));

        if (count == 0)
            return getKey();

        StringBuffer buffer = new StringBuffer();
        m.reset();
        int index = 0;
        while(m.find()) {
            m.appendReplacement(buffer, String.valueOf(params[index++]));
        }
        m.appendTail(buffer);

        return buffer.toString();
    }

    private int id;

    /* 缓存key */
    private String key;

    /* 过期时间 */
    private int expireTime;

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public int getExpireTime() {
        return expireTime;
    }
}
