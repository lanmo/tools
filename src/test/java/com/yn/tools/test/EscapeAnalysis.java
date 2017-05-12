package com.yn.tools.test;
  
/** 
 * Created by yangzl2008 on 2015/1/29. 
 */  
class EscapeAnalysis {  
    private static class Foo {  
        private int x;  
        private static int counter;  
  
        public Foo() {  
            x = (++counter);  
        }  
    }

    private static void test() {
        long start = System.nanoTime();
        for (int i = 0; i < 1000 * 1000 * 10; ++i) {
            Foo foo = new Foo();
        }
        long end = System.nanoTime();
        System.out.println("Time cost is " + (end - start));
    }
  
    public static void main(String[] args) {

//        long[] abc = {64981000, 59948000, 60313000, 66861000, 61668000};
//        long r = 0;
//        for (long a : abc) {
//            r += a;
//        }
//
//        System.out.println(r / abc.length);

        test();
    }
  
}  