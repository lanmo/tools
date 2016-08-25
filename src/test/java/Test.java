import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by yangnan on 16/8/22.
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {

        Test t = new Test();

        B b = t.new B();

        System.out.println(JSONObject.toJSONString(b));
       Method[] ms = b.getClass().getMethods();
        for (Method m : ms) {
            System.out.println(m.getName());
        }

    }

    class A {
        private int i;

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }
    }

    class B extends A {
        private int b;

        public int getB() {
            return b;
        }

        public void setB(int b) {
            this.b = b;
        }
    }

}
