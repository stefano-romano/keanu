package io.improbable.keanu.benchmarking;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ValuePropagationPerf {

    @Benchmark
    public void methodOne() {

    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
            .include(".*" + ValuePropagationPerf.class.getSimpleName() + ".*")
            .warmupIterations(5)
            .measurementIterations(5)
            .threads(1)
            .forks(1)
            .build();

        new Runner(opt).run();
    }
}
