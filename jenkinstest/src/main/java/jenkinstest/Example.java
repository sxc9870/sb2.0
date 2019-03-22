package jenkinstest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Example {

    @RequestMapping("/")
    String home() {

        return "Hello World!";
    }

    public static void main(String[] args) throws Exception {
        // SpringApplication.run(Example.class, args);

        // 核心线程数,最大线程数,空闲时间,秒,任务队列,线程工厂,饱和策略
        // 核心线程满了以后放任务队列,队列满了就开到最大线程,在满的话走饱和策略
        RejectedExecutionHandler defaultHandler = new AbortPolicy();
        BlockingQueue<Runnable> aq = new java.util.concurrent.ArrayBlockingQueue<>(10);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 20, 20, TimeUnit.SECONDS, aq,
                Executors.defaultThreadFactory(), defaultHandler);

//        pool.execute(new MyRun());
//        pool.execute(new MyRun());
//        pool.execute(new MyRun());
//        pool.execute(new MyRun());
//        pool.execute(new MyRun());
//
//        pool.shutdown();
        //核心0，最大maxint，1分钟空闲，SynchronousQueue
        //可以同时执行MAXINT个线程，任务直接从SynchronousQueue里面拿，其特性保证吞吐量
         //线程可以maxint
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //核心线程=最大线程=入参3，空闲时间为0，LinkedBlockingQueue(maxint) 会蹦
        //队列可以maxint
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        
        //1,1，0，maxint
        //核心线程=最大线程=1，空闲时间为0，LinkedBlockingQueue(maxint) 会蹦
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        
        //X,max,0,DelayedWorkQueue(无界)
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        
        //ForkJoinPool
        ExecutorService  workStealingPool = Executors.newWorkStealingPool();
        
        ExecutorService singleThreadScheduledExecutor=Executors.newSingleThreadScheduledExecutor();
        //饱和策略：
        //Abort策略：默认策略，新任务提交时直接抛出未检查的异常RejectedExecutionException，该异常可由调用者捕获。
        //CallerRuns策略：为调节机制，既不抛弃任务也不抛出异常，而是将某些任务回退到调用者。不会在线程池的线程中执行新的任务，而是在调用exector的线程中运行新的任务。
        // Discard策略：新提交的任务被抛弃。
        System.out.println("-------");
        fixedThreadPool.execute(new MyRun());
        fixedThreadPool.execute(new MyRun());  
        fixedThreadPool.execute(new MyRun());  
        fixedThreadPool.execute(new MyRun());
        fixedThreadPool.shutdown();
    }

    private static class MyRun implements Runnable {

        @Override
        public void run() {System.out.println("2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }System.out.println("3");
        }

    }

}