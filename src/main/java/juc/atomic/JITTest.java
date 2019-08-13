package juc.atomic;

public class JITTest {
    private static boolean init = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(){
            @Override
            public void run() {
                while (!init){

                }
                /**
                 * 当循环中为空语句时  JIT会进行优化 因为空语句不会对变量进行任何修改 因此优化为 while(true){}
                 * 此时线程不会自动退出 解决办法 init加volatile 关键字 或者在循环中添加一句任意执行语句 比如打印
                 */
            }
        }.start();
        Thread.sleep(1000);
        new Thread(){
            @Override
            public void run() {
                init = true;
                System.out.println("set init is true");
            }
        }.start();
    }
}
