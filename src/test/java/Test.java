import com.alibaba.fastjson.JSONObject;
import com.yn.tools.enums.CacheKeyEnum;
import org.apache.commons.lang3.StringUtils;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by yangnan on 16/8/22.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String abc = CacheKeyEnum.TEST.toKey("111","22","4");
        System.out.println(abc);
        System.out.println(CacheKeyEnum.TEST.pattern == CacheKeyEnum.TEST3.pattern);
        System.out.println(CacheKeyEnum.TEST.pattern);
        System.out.println(CacheKeyEnum.TEST3.pattern);

    }



}
