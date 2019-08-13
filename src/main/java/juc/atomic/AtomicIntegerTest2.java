package juc.atomic;

public class AtomicIntegerTest2 {
    public static void main(String[] args) {

        for (int i =0;i<5;i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        doSome();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private static void doSome() throws InterruptedException {
        synchronized (AtomicIntegerTest2.class){
            System.out.println(Thread.currentThread().getName() +"get lock");
            Thread.sleep(100000);
        }
    }
}
