package ua.testprojectsolveast.threading;

import ua.testprojectsolveast.threading.base.PerformanceTester;

import java.util.LinkedList;

/**
 * Created by Anton on 04.11.2015.
 */
public class PerformanceTesterImpl implements PerformanceTester {

    private Double minTime;
    private Double maxTime;
    private Double totalTime;

    private LinkedList<Runnable> queue = new LinkedList();

    private void setMinTime(Double time) {
        synchronized (minTime) {
            if (minTime > time) minTime = time;
        }
    }

    private void setMaxTime(Double time) {
        synchronized (maxTime) {
            if (maxTime < time) maxTime = time;
        }
    }

    private void addTotalTime(Double time) {
        synchronized (totalTime) {
            totalTime += time;
        }
    }

    private Double nanosecondsToMilliseconds(Long nanoseconds) {
        return nanoseconds / 1_000_000D;
    }


    private Thread buildThread() {
        return new Thread() {

            @Override
            public void run() {
                while (true) {
                    Runnable r;
                    synchronized (queue) {
                        if (queue.isEmpty()) break;
                        r = queue.removeFirst();
                    }
                    try {
                        long timeBegin = System.nanoTime();
                        r.run();
                        long timeEnd = System.nanoTime();
                        long liveTime = timeEnd - timeBegin;
                        if (liveTime < 1000)
                            setMaxTime(nanosecondsToMilliseconds(liveTime));
                        setMinTime(nanosecondsToMilliseconds(liveTime));
                        addTotalTime(nanosecondsToMilliseconds(liveTime));
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public synchronized PerformanceTestResult runPerformanceTest(Runnable task, int executionCount, int threadPoolSize) throws InterruptedException {
        minTime = Double.MAX_VALUE;
        maxTime = Double.MIN_VALUE;
        totalTime = 0D;

        queue.clear();
        for (int i = 0; i < executionCount; i++) {
            queue.addLast(task);
        }
        Thread[] threads = new Thread[threadPoolSize];
        for (int i = 0; i < threadPoolSize; i++) {
            threads[i] = buildThread();
            threads[i].start();
        }
        for (Thread thread : threads) {
            while (thread.isAlive()) ;
        }

//        ExecutorService service = Executors.newFixedThreadPool(threadPoolSize);
//        for(int i = 0; i < executionCount; i++) {
//            service.submit(() -> {
//                long timeBegin = System.nanoTime();
//                task.run();
//                long timeEnd = System.nanoTime();
//                long liveTime = timeEnd - timeBegin;
//                setMaxTime(nanosecondsToMilliseconds(liveTime));
//                setMinTime(nanosecondsToMilliseconds(liveTime));
//                addTotalTime(nanosecondsToMilliseconds(liveTime));
//            });
//        }

        return new PerformanceTestResult(totalTime, minTime, maxTime);
    }


}

