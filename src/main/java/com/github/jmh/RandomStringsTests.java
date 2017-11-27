package com.github.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode({Mode.Throughput, Mode.SingleShotTime})
@State(Scope.Thread)
@Threads(1)
public class RandomStringsTests {

    private static final String CHARS_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
    private static final int LENGTH = 32;
    //Constructor is invoked only once per Fork (right before first Warmup Iteration)
    private Random random = new Random();

    @Benchmark
    public String measureStringBuilderCollector() {
        return IntStream.range(0, LENGTH)
                .map(i -> randomChar())
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    @Benchmark
    public String measureStringBuilderForEachAppend() {
        StringBuilder stringBuilder = new StringBuilder();
        IntStream.range(0, LENGTH)
                .map(i -> randomChar())
                .forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    @Benchmark
    public String measureStringConcatenationForLoop() {
        String randomString = "";

        for (int i = 0; i < LENGTH; i++) {
            randomString += randomChar();
        }

        return randomString;
    }

    @Benchmark
    public String measureStringBuilderAppendForLoop() {
        StringBuilder randomString = new StringBuilder();

        for (int i = 0; i < LENGTH; i++) {
            randomString.append(randomChar());
        }

        return randomString.toString();
    }

    private char randomChar() {
        return CHARS_POOL.charAt(random.nextInt(CHARS_POOL.length()));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RandomStringsTests.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(2)
                .forks(2)
                .build();

        new Runner(opt).run();
    }
}
