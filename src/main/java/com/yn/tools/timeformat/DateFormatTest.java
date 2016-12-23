package com.yn.tools.timeformat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by yangnan on 2016/12/23.
 *
 * SimpleDateFormat 是非线程安全
 */
public class DateFormatTest {


    public static void main(String[] args) throws Exception {
        //非线程安全
//        testNoSafeFormat();
        //线程安全
        testSafeFormat();
        //线程安全
//        testSafeFormat2();
    }

    public static void testSafeFormat() throws Exception {
        final DateFormatMultipleThread t =new DateFormatMultipleThread();
        Callable<Date> task =new Callable<Date>(){
            public Date call()throws Exception {
                return t.safeConvert("20100811");
            }
        };

        run(task);
    }

    public static void testSafeFormat2() throws Exception {
        final DateFormatMultipleThread t =new DateFormatMultipleThread();
        Callable<Date> task =new Callable<Date>(){
            public Date call()throws Exception {
                return t.jodaSafeConvert("20100811");
            }
        };

        run(task);
    }

    public static void testNoSafeFormat() throws Exception {
        final DateFormatMultipleThread t =new DateFormatMultipleThread();
        Callable<Date> task =new Callable<Date>(){
            public Date call()throws Exception {
                return t.noSafeConvert("20100811");
            }
        };

        run(task);
    }

    public static void run(Callable<Date> task) throws Exception {

        //让我们尝试2个线程的情况
        ExecutorService exec = Executors.newFixedThreadPool(2);
        List<Future<Date>> results =
                new ArrayList<Future<Date>>();

        //实现5次日期转换
        for(int i =0; i <20; i++){
            results.add(exec.submit(task));
        }
        exec.shutdown();

        //查看结果
        for(Future<Date> result : results){
            System.out.println(result.get());
        }
    }
}
