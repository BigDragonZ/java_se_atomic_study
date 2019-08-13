package juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class FailedAtomicIntegerFieldUpdaterTest {

    /**
     * 不能访问对象的私有变量
     */
    @Test(expected = IllegalAccessException.class)
    public void test1() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        updater.compareAndSet(testMe, 0, 1);
    }


    @Test
    public void test2() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "i");
        TestMe testMe = new TestMe();
        updater.compareAndSet(null, 0, 1);
    }

    @Test
    public void test3() {
        AtomicIntegerFieldUpdater<TestMe> updater = AtomicIntegerFieldUpdater.newUpdater(TestMe.class, "a");
        TestMe testMe = new TestMe();
        updater.compareAndSet(testMe, 0, 1);
    }

    @Test
    public void test4() {
        AtomicReferenceFieldUpdater<TestMe2,Integer> updater = AtomicReferenceFieldUpdater.newUpdater(TestMe2.class,Integer.class, "a");
        TestMe2 testMe = new TestMe2();
//        正确的
//        updater.compareAndSet(testMe, null, 1);
        updater.compareAndSet(testMe, null, 1);
    }


    static class TestMe2{
        volatile Integer i;
    }
}
