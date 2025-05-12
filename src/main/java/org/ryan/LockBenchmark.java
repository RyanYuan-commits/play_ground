package org.ryan;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author Ryan Yuan
 * @Description
 * @Create 2025-04-18 12:08
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@Threads(2) // 设置并发线程数为 10，模拟竞态条件
public class LockBenchmark {

    private static int syncCounter = 0;
    private static int reentrantLockCounter = 0;
    private static int commonCounter = 0;
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    @Benchmark
    public void testSynchronized() {
        synchronized (LockBenchmark.class) {
            syncCounter++;
        }
    }

    @Benchmark
    public void testReentrantLock() {
        reentrantLock.lock();
        try {
            reentrantLockCounter++;
        } finally {
            reentrantLock.unlock();
        }
    }

    @Benchmark
    public void testCommon() {
        commonCounter++;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
