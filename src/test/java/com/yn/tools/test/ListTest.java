package com.yn.tools.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yangnan on 17/3/27.
 */
public class ListTest {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        List<Integer> l = list.subList(0,list.size());
        System.out.println(Arrays.toString(l.toArray(new Integer[0])));
//       l.remove(1);
        list.add(5);
//        l.add(5);

//        System.out.println(Arrays.toString(l.toArray(new Integer[0])));

        System.out.println(Arrays.toString(list.toArray(new Integer[0])));

//        System.out.println("list1'size：" + list.size());
//        System.out.println("list3'size：" + l.size());

    }

}
