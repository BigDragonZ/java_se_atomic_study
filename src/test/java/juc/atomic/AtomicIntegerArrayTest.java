package juc.atomic;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayTest {


    @Test
    public void test1(){
        AtomicIntegerArray a = new AtomicIntegerArray(10);
        System.out.println(a.length());
    }
}
