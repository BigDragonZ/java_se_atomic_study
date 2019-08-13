package juc.atomic;

import org.junit.Test;
import sun.misc.Unsafe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class UnsafeFooTest {
    public static Unsafe getUnsafe() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe o = (Unsafe) f.get(null);
            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }

    static class Simple {
        private long l = 0;

        public Simple() {
            this.l = l;
            System.out.println("=====");
        }

        public long getL() {
            return l;
        }
    }

    static class Guard {
        private int ACCESS_ALLOWED = 1;

        private boolean allow() {
            return 42 == ACCESS_ALLOWED;
        }

        public void work() {
            if (allow()) {
                System.out.println("i am working by allowed");
            }
        }
    }

    @Test
    public void test1() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
//        Simple sim = new Simple();
//        System.out.println(sim.getL());

//        反射获取
//        Simple simple = Simple.class.newInstance();


//        Class.forName("juc.atomic.UnsafeFooTest$Simple");
//        unsafe可以跳过类的初始化
        Unsafe unsafe = getUnsafe();
//        利用这个方法可以直接开辟内存 绕过类的初始化 不适用构造方法
        Simple o = (Simple) unsafe.allocateInstance(Simple.class);
        System.out.println(o.getL());
        System.out.println(o.getClass());
        System.out.println(o.getClass().getClassLoader());
    }

    @Test
    public void test2() throws NoSuchFieldException {

        Guard guard = new Guard();
        guard.work();

        Field f = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
        Unsafe unsafe = getUnsafe();
        unsafe.putInt(guard, unsafe.objectFieldOffset(f), 42);

        guard.work();
    }


    private static byte[] loadClassContene() throws Exception {
        File f = new File("/Users/zhangdalong/IdeaProjects/wangwenjun_three/target/classes/A.class");
        FileInputStream fis = new FileInputStream(f);
        byte[] content = new byte[(int) f.length()];
        fis.read(content);
        fis.close();
        return content;
    }

    @Test
    public void test3() throws Exception {
        byte[] bytes = loadClassContene();
        Unsafe unsafe = getUnsafe();
        Class aClass = unsafe.defineClass(null, bytes, 0, bytes.length, null, null);
        Integer v = (Integer) aClass.getMethod("getI").invoke(aClass.newInstance(), null);
        System.out.println(v);

    }

//    通过这个方法可以获取到类似以C C++ sizeOf 方法的大概值 不是特别精确
    private static long sizeOf(Object o) {
        Unsafe unsafe = getUnsafe();
        Set<Field> fields =  new HashSet<Field>();
        Class<?> c = o.getClass();
        while (c != Object.class) {
            Field[] declaredFields = c.getDeclaredFields();
            for (Field f : declaredFields) {
                if ((f.getModifiers() & Modifier.STATIC) == 0) {
                    fields.add(f);
                }
            }
            c = c.getSuperclass();
        }
        long maxOffSet = 0;
        for (Field f : fields) {
            long l = unsafe.objectFieldOffset(f);
            if (l > maxOffSet) {
                maxOffSet = l;
            }
        }

        return ((maxOffSet / 8) + 1) / 8;
    }

    @Test
    public void test4(){

    }
}