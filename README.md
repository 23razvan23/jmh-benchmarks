# jmh-benchmarks
This Github project contains various JMH micro-benchmarks build using **jmh** code tools - see more <a href="http://openjdk.java.net/projects/code-tools/jmh/">here</a>.

###Setting up a benchmarking project.

`$ mvn archetype:generate \
          -DinteractiveMode=false \
          -DarchetypeGroupId=org.openjdk.jmh \
          -DarchetypeArtifactId=jmh-java-benchmark-archetype \
          -DgroupId=com.github.23razvan23.projects \
          -DartifactId=jmh-benchmarks \
          -Dversion=1.0`
          
###Building the benchmarks
Use the following Maven command:

`$ mvn clean install`

###Running the benchmarks

`java -jar target/benchmarks.jar`

###More convenient alternative for running the benchmarks - without using any Maven commands
use <a href="http://javadox.com/org.openjdk.jmh/jmh-core/1.6.2/org/openjdk/jmh/runner/Runner.html">org.openjdk.jmh.runner.Runner</a>

`public static void main(String[] args) throws RunnerException {`\
`Options opt = new OptionsBuilder()`\
                `.include(RandomStringsTests.class.getSimpleName())`\
                `.warmupIterations(5)`\
                `.measurementIterations(5)`\
                `.forks(1)`\
                `.build();`

`new Runner(opt).run();`\
`}`

###Sample output

 `Warmup: 1 iterations, 1 s each`\
 `Measurement: 1 iterations, 1 s each`\
 `Threads: 1 thread, will synchronize iterations`\
 `Benchmark mode: Throughput, ops/time`\
 `Benchmark: com.github.jmh.IntStreamTests.measureIterate`

 `Run progress: 0.00% complete, ETA 00:00:04`\
 `Fork: 1 of 1`\
 `Warmup Iteration   1: 0.905 ops/ms`\
 `Iteration   1: 0.892 ops/ms`


`Run result: 0.89 ops/ms (<= 2 samples)`


 `VM invoker: /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/jre/bin/java`\
 `VM options: <none>`\
 `Warmup: 1 iterations, 1 s each`\
 `Measurement: 1 iterations, 1 s each`\
 `Threads: 1 thread, will synchronize iterations`\
 `Benchmark mode: Throughput, ops/time`\
 `Benchmark: com.github.jmh.IntStreamTests.measureRange`

 `Run progress: 50.00% complete, ETA 00:00:02`\
 `Fork: 1 of 1`\
 `Warmup Iteration   1: 0.818 ops/ms`\
 `Iteration   1: 0.846 ops/ms`


`Run result: 0.85 ops/ms (<= 2 samples)`


 `Run complete. Total time: 00:00:05`

`Benchmark                               Mode  Samples  Score  Score error   Units`\
`c.g.j.IntStreamTests.measureIterate    thrpt        1  0.892          NaN  ops/ms`\
`c.g.j.IntStreamTests.measureRange      thrpt        1  0.846          NaN  ops/ms`
