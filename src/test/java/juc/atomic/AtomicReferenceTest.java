package juc.atomic;

import lombok.Data;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使我们定义的对象支持原子性操作； 但是会出现ABA问题
 */
public class AtomicReferenceTest {


    public void test1() {
        AtomicReference<Simple> a = new AtomicReference<>(new Simple("tmo",12));
    }

    @Data
    private class Simple {
        private String name;
        private int age;

        public Simple(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
