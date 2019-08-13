package juc.atomic;


import org.junit.Test;

import java.lang.reflect.Member;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 主要使用场景：针对对象的属性进行原子性封装
 * 缺陷：不能访问对象的私有变量  包装对象为空时出现ClassCastException异常  filed名称非法时出现NoSuchFieldException异常说明底层是使用反射取值的
 *      字段类型不一致时出现ClassCastException异常 字段必须被volatile修饰
 */

public class AtomicIntegerFieldUpdaterTest {

    @Test
    public void test() {
        AtomicIntegerFieldUpdater<TestMe> a = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe t = new TestMe();

        for (int i = 0; i < 5; i++) {
            new Thread() {
                @Override
                public void run() {
                    final int MAX = 20;
                    for (int i = 0; i < MAX; i++) {
                        int andIncrement = a.getAndIncrement(t);
                        System.out.println(Thread.currentThread().getName() + "  " + andIncrement);
                    }
                }
            }.start();
        }
    }

    static class TestMe {
        volatile int i;
    }
}
