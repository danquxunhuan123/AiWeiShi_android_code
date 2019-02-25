package com.trs.aiweishi;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        linkDemoTest();
//        sort();
//        threadPoolTest();
//        refrernceTest();
//        threadTest();
//        linkedHashMapTest();
    }

    public static class SingleInstance {
        private static SingleInstance instance = new SingleInstance();

        private SingleInstance() {
        }

//        public static SingleInstance getInstance() {
//            if (instance == null) {
//                synchronized (SingleInstance.class) {
//                    if (instance == null) {
//                        instance = new SingleInstance();
//                    }
//                }
//            }
//            return instance;
//        }
    }

    class Note {
        int data;
        Note next;

        public Note(int data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "Note{" +
                    "data=" + data +
                    ", next=" + next +
                    '}';
        }
    }

    private void linkDemoTest() {
//        head.next = n;
    }

    private void linkedHashMapTest() {
//        Bitmap bitmap = BitmapFactory.decodeFile("");
//        bitmap.getByteCount();
        LinkedHashMap map = new LinkedHashMap(0, 0.75f, true);
        map.put(0, 0);
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        map.put(6, 6);
        map.get(1);
        map.get(2);

        Set<Map.Entry> set = map.entrySet();
        for (Map.Entry<Integer, Integer> entry : set) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private void threadTest() {
        try {
            DemoRunnable demoRunnable = new DemoRunnable();
            new Thread(demoRunnable).start();
            new Thread(demoRunnable).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ExecutorService service = Executors.newCachedThreadPool();
//        Future<Integer> submit1 = service.submit(new DemoCallable());
//        Future submit2 = service.submit(new DemoRunnable());
//        service.shutdown();
//        System.out.println("currentThread:" + Thread.currentThread().getName());
//        try {
////            Thread.sleep(1000);
//            System.out.println("CallableResult:" + submit1.get());
//            System.out.println("RunnableResult:" + submit2.get());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    class DemoCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int a = 0;
            for (int i = 0; i < 3; i++) {
                a++;
            }
            Thread.sleep(3000);
            return a;
        }
    }

    class DemoRunnable implements Runnable {
        int a = 0;

        @Override
        public void run() {
            try {
                synchronized (this) {
                    a++;
                    System.out.println("a:" + a);
                    System.out.println("name:" + Thread.currentThread().getName());

                    Thread.sleep(2000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void refrernceTest() {
        WeakReference<String> weakReference = new WeakReference<>(new String("weak"));
        SoftReference<String> softReference = new SoftReference<>(new String("spft"));
        System.gc();
        System.out.println(softReference.get());
        System.out.println(weakReference.get());
    }


    private void threadPoolTest() {
//        ExecutorService service = Executors.newCachedThreadPool();
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        ExecutorService service = Executors.newFixedThreadPool(3);
        ScheduledExecutorService service = Executors.newScheduledThreadPool(3);
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(3,
//                5,
//                1000,
//                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<Runnable>(1)); //
//
        for (int i = 0; i < 6; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("threadName:" + Thread.currentThread());
                }
            });
        }

        service.shutdown();
    }

    private void sort() {
        int[] array = {2, 15, 6, 7, 8, 243, 65, 73, 5, 235, 32, 4};
        System.out.println(Arrays.toString(array));
        //选择排序
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = i + 1; j < array.length; j++) {
                int temp;
                if (array[i] > array[j]) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }

        //冒泡排序
//        for (int i = 0; i < array.length - 1; i++) {
//            for (int j = 0; j < array.length - i - 1; j++) {
//                int temp;
//                if (array[j] > array[j + 1]) {
//                    temp = array[j];
//                    array[j] = array[j + 1];
//                    array[j + 1] = temp;
//                }
//            }
//        }
//        System.out.println(Arrays.toString(array));
    }


}