package com.yn.tools.test;

import com.google.common.reflect.ClassPath;

/**
 * Created by yangnan on 2016/12/23.
 */
public class ClassInfo {
    public static void main(String[] args) {

        String[] basePackages = {"com.yn.tools.test"};
        try {
            ClassPath cph = ClassPath.from(ClassInfo.class.getClassLoader());
            for (String basePackage : basePackages) {
                for (ClassPath.ClassInfo cif : cph.getTopLevelClasses(basePackage)) {
                    Class<?> clz = Class.forName(cif.getName());
                    System.out.println(clz.getName());
                    System.out.println("===" + cif.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
