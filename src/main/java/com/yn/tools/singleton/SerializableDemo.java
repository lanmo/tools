package com.yn.tools.singleton;

import java.io.*;

/**
 * Created by yangnan on 16/12/21.
 */
public class SerializableDemo {

    //为了便于理解，忽略关闭流操作及删除文件操作。真正编码时千万不要忘记
    //Exception直接抛出
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //双重校验
        Singleton i = newInstance(Singleton.getSingleton());
        System.out.println(i == Singleton.getSingleton());
        SingletonInner inner = newInstance(SingletonInner.getInstance());
        System.out.println(inner == SingletonInner.getInstance());
    }

    public static <T> T newInstance(T t) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(t);
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois =  new ObjectInputStream(new FileInputStream(file));
        T newInstance = (T) ois.readObject();
        return newInstance;
    }

}