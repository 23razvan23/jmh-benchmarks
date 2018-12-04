package com.github.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.nio.charset.Charset;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.SingleShotTime)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
@State(Scope.Thread)
@Threads(1)
@Fork(1)
public class ListVsSet {
    private static final String FIRST_ELEMENT = "first";
    private static final String MIDDLE_ELEMENT = "middle";
    private static final String LAST_ELEMENT = "last";
    private static final String SEPARATOR = ",";
    private static final Random RANDOM = new Random();

    private String elementsPlainString;

    @Setup(Level.Iteration)
    public void setUp() {
        elementsPlainString = FIRST_ELEMENT + SEPARATOR +
                elements(1_000_000) +
                MIDDLE_ELEMENT +
                elements(1_000_000) +
                LAST_ELEMENT;
    }

    @Benchmark
    public void measureIsPresentInListFirst() {
        isPresentInList(FIRST_ELEMENT);
    }

    @Benchmark
    public void measureIsPresentInListMiddle() {
        isPresentInList(MIDDLE_ELEMENT);
    }

    @Benchmark
    public void measureIsPresentInListLast() {
        isPresentInList(LAST_ELEMENT);
    }

    @Benchmark
    public void measureIsPresentInSetFirst() {
        isPresentInSet(FIRST_ELEMENT);
    }

    @Benchmark
    public void measureIsPresentInSetMiddle() {
        isPresentInSet(MIDDLE_ELEMENT);
    }

    @Benchmark
    public void measureIsPresentInSetLast() {
        isPresentInSet(LAST_ELEMENT);
    }

    private String elements(int elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < elements; i++) {
            stringBuilder.append(generateRandomString(5)).append(SEPARATOR);
        }
        return stringBuilder.toString();
    }

    private boolean isPresentInList(String term) {
        return Stream.of(elementsPlainString.split(","))
                .map(String::trim)
                .map(element -> element.equals(term))
                .findFirst()
                .orElse(false);
    }

    private boolean isPresentInSet(String term) {
        return Stream.of(elementsPlainString.split(","))
                .map(String::trim)
                .collect(Collectors.toSet())
                .contains(term);
    }

    private String generateRandomString(int length) {
        byte[] array = new byte[length];
        RANDOM.nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ListVsSet.class.getSimpleName())
                .warmupIterations(1)
                .measurementIterations(3)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
