package com.yn.tools.redis;

import com.yn.tools.utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangnan on 2016/12/9.
 * Redis
 * 官网文档
 *  @link {'https://redis.io/topics/cluster-tutorial'}
 * 集群部署
 *  @link {'http://www.wangerbao.com/?p=532'}
 * 集群设置密码
 *  @link {'http://lee90.blog.51cto.com/10414478/1863243}
 *  查看gems安装到哪了使用命令:gem environment @link {'http://hlee.iteye.com/blog/698607'}
 * 客户端使用
 *  @link {'https://github.com/xetorthio/jedis/blob/master/README.md'}
 * redis-trib-rb详解:
 *  @link {'http://weizijun.cn/2016/01/08/redis%20cluster%E7%AE%A1%E7%90%86%E5%B7%A5%E5%85%B7redis-trib-rb%E8%AF%A6%E8%A7%A3/'}
 *
 */
public class RedisManager {

    private static volatile RedisClient redisClient;

    private static void init() {
        if (redisClient == null) {
            synchronized (RedisClient.class) {
                if (redisClient == null) {
                    redisClient = new RedisClient(getRedises(), "yangnan123");
                }
            }
        }
    }

    private static RedisClient getRedisClient() {
        if (redisClient == null) {
            init();
        }

        return redisClient;
    }

    public static String set(String key, Object value) {
        try {
           return getRedisClient().set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T get(String key, Class<T> tClass) {
        try {
            return getRedisClient().get(key, tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 这种方式更好用,推荐这种方式
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T get(String key) {
        try {
            return getRedisClient().get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static List<Redis> getRedises() {
        String host = "10.211.55.7";
        String host2 = "10.211.55.8";
        List<Redis> redises = new ArrayList<Redis>();

        redises.add(new Redis(host, 7000));
        redises.add(new Redis(host, 7001));
        redises.add(new Redis(host, 7002));
        redises.add(new Redis(host2, 7003));
        redises.add(new Redis(host2, 7004));
        redises.add(new Redis(host2, 7005));

        return redises;
    }

    public static void main(String[] args) {
        String value = set("test", new Data());
        L.d("set value", value);
        Data v = get("test", Data.class);
        L.d("v", v);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("t", 2);
        map.put("e", "我是好人啊");
        value = set("tt", map);
        L.d("map value", value);

        Map<String,Object> result = get("tt");
        System.out.println(result);
        L.d(result);

        Data d = get("test");
        L.d("d", d);

        set("wd", 2);
        L.d("wd", get("wd"));
        L.d("wd2", get("wd", Long.class));

    }

    private static class Data {
        private int i = 0;
        private String name = "杨楠";

        private Test t = new Test();

        public Test getT() {
            return t;
        }

        public void setT(Test t) {
            this.t = t;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private static class Test {
        private int j = 12;

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }
    }

}
