package juc.atomic;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongTest {
    @Test
    public void testCreate() {
        AtomicLong l = new AtomicLong(100L);

        /**
         * AtomicLong 中有一个静态属性  VM_SUPPORTS_LONG_CAS
         * 这个属性专门供JVM调用  因为Long是64位 在COU传输的时候是按照高位低位传输的 此时不能保证原子操作，因为执行时CPU会查看这个参数 看是否支持CAS操作，支持的话会将数据总线加锁。
         *
         */
    }
}
