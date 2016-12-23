package com.yn.tools.timeformat;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by yangnan on 2016/12/23.
 *
 * SimpleDateFormat不是线程安全的
 * 提供线程安全的写法
 *
 */
public class DateFormatMultipleThread {

    /**
     * 非线程安全的方式
     */
    private final DateFormat format = new SimpleDateFormat("yyyyMMdd");

    /**
     * Joda-Time DateTimeFormat 是线程安全而且不变的。
     */
    private final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");

    /**
     * 非线程安全
     *
     * @param source
     * @return
     * @throws ParseException
     */
    public Date noSafeConvert(String source)
            throws ParseException {
        Date d = format.parse(source);
        return d;
    }

    public Date safeConvert(String souce) throws ParseException {
        Date d = df.get().parse(souce);
//        System.out.println(df.get());
        return d;
    }

    /**
     * joda 线程安全
     *
     * @param souce
     * @return
     * @throws ParseException
     */
    public Date jodaSafeConvert(String souce) throws ParseException {
        DateTime d = fmt.parseDateTime(souce);
        System.out.println("111");
        return d.toDate();
    }

    private static AtomicLong count = new AtomicLong(0);

    /**
     * 线程安全的方式
     */
    public static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        protected DateFormat initialValue() {
            System.out.println(count.incrementAndGet());
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
}
