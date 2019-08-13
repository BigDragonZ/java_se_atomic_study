package juc.atomic;

public class AtomicIntegerTest3 {

    private final static CompareAndSetLock lock = new CompareAndSetLock();

    public static void main(String[] args) {

        for (int i =0;i<5;i++){
            new Thread(){
                @Override
                public void run() {
                    try {
                        doSome();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (GetLockException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private static void doSome() throws InterruptedException, GetLockException {
        synchronized (AtomicIntegerTest2.class){
            try {
                lock.tryLock();
                System.out.println(Thread.currentThread().getName() +"get lock");
                Thread.sleep(100000);
            } finally {
                lock.unlock();
            }

        }
    }
}
