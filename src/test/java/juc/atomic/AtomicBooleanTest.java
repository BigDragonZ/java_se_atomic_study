package juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 底层是通过AtomicInteger实现的  1 0 代表true false
 */
public class AtomicBooleanTest {

    @Test
    public void testCreate(){
        AtomicBoolean a = new AtomicBoolean();
        System.out.println(a.get());
    }
}
