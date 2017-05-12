package com.yn.tools.test;

public class JVM {
    public static void main(String[] args) throws Exception {
        int sum = 0;
        int count = 1000000;
        //warm up
        for (int i = 0; i < count ; i++) {
            sum += fn(i,new Long(i).longValue());
        }
 
        Thread.sleep(500);
 
        for (int i = 0; i < count ; i++) {
            sum += fn(i,new Long(i).longValue());
        }
 
        System.out.println(sum);
        System.in.read();
    }
 
    private static int fn(int age, long a) {
        User user = new User(age, a);
        int i = user.getAge();
        return i;
    }
}
 
class User {
    private final int age;
    private final long a;

    public User(int age, long a) {
        this.age = age;
        this.a = a;
    }
 
    public int getAge() {
        return age;
    }
}