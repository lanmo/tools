package com.yn.tools.monitor;

import com.yn.tools.utils.L;

/**
 * Created by yangnan on 16/12/13.
 */
public final class Monitor {

    private final static ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<Long>();

    public static void begin() {
        THREAD_LOCAL.set(System.currentTimeMillis());
    }

    public static void end(String desc) {
        long end = System.currentTimeMillis();
        Long start = THREAD_LOCAL.get();
        if (start == null) {
            return;
        }

        L.d(desc, end - start);
        THREAD_LOCAL.remove();
    }
}
