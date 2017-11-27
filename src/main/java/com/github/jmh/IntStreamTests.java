package com.github.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1)
@Measurement(iterations = 1)
@State(Scope.Thread)
@Threads(1)
@Fork(1)
public class IntStreamTests {
    private static final int STEP = 2;

    private static final int START_VALUE = 1;
    private static final int END_VALUE = 1_000_000;

    @Benchmark
    public void measureRange() {
        int sum = IntStream.range(START_VALUE, END_VALUE)
                .filter(value -> value % 2 == 1)
                .sum();
    }

    @Benchmark
    public void measureIterate() {
        int sum = IntStream.iterate(START_VALUE, value -> value + STEP)
                .limit(END_VALUE / STEP)
                .sum();
    }
}
