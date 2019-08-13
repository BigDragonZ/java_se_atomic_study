package juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AIFUTest {
    private volatile int i;
    private AtomicIntegerFieldUpdater<AIFUTest>  a = AtomicIntegerFieldUpdater.newUpdater(AIFUTest.class,"i");


    public void update(int value){
        a.compareAndSet(this,i,value);
    }

    public int get(){
        return i;
    }

    @Test
    public void test(){
        AIFUTest aifuTest = new AIFUTest();
        aifuTest.update(10);
        System.out.println(aifuTest.get());
    }
}
