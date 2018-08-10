package io.improbable.keanu.benchmarking;

import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.probabilistic.GaussianVertex;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ValuePropagationPerf {

    @State(Scope.Benchmark)
    public static class TestGraph {

        volatile DoubleVertex start;

        public TestGraph(){
            addLinks(100000);
        }

        private DoubleVertex addLinks(int links) {

            start = new GaussianVertex(0, 1);
            DoubleVertex end = start;

            for (int i = 0; i < links; i++) {
                DoubleVertex left = end.sigmoid();
                DoubleVertex right = end.exp();
                end = left.times(right);
            }

            start.setAndCascade(1.0);

            return end;
        }

    }

    @Benchmark
    public void methodOne(TestGraph testGraph) {
        testGraph.start.setAndCascade(testGraph.start.getValue(0) + 1);
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
