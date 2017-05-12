package com.yn.tools.exceptions;

/**
 * Created by yangnan on 17/5/11.
 */
public class ExceptionTest {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long end = 0;
        int num = 10000000;

        for (int i=0; i<num; i++) {
            Integer a = new Integer(i);
//            try {
//                test1();
//            } catch (Exception e) {
//            }
        }

        for (int i=0; i<num; i++) {
            try {
                test();
            } catch (NullPointerException e) {

            }

        }

        end = System.currentTimeMillis();
        System.out.println("花费:" + (end - startTime));
        startTime = System.currentTimeMillis();
        for (int i=0; i<num; i++) {
            try {
                test1();
            } catch (Exception e) {

            }
        }
        end = System.currentTimeMillis();
        System.out.println("花费:" + (end - startTime));
    }

    public static void test() throws NullPointerException {
//        Integer a = null;
//        a.equals(2);

        throw new NullPointerException("eee");
    }

    public static void test1() throws Exception {

//        try {
//            Integer a = null;
//            a.equals(2);
//        } catch (Exception e) {
//            throw new Exception(e);
            throw new NullPointerException("dddd");
//        }
    }

}
