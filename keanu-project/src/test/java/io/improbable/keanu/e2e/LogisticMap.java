package io.improbable.keanu.e2e;

import io.improbable.keanu.algorithms.variational.GradientOptimizer;
import io.improbable.keanu.network.BayesianNetwork;
import io.improbable.keanu.vertices.ConstantVertex;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.KeanuRandom;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex;
import io.improbable.keanu.vertices.dbl.probabilistic.GaussianVertex;

public class LogisticMap {

    static double logistic_map(double x, double r) {
        x = r * x * (1 - x);
        return x;
    }

    public static void main(String[] args) {

        KeanuRandom random = new KeanuRandom();

        GaussianVertex r = new GaussianVertex(2.7, 1.0);
        double initialR = r.sample(random).scalar();
        r.setValue(initialR);

        System.out.println("Initial R " + initialR);

        DoubleVertex last_x = new ConstantDoubleVertex(0.5);
        for (int i = 0; i < 200; ++i) {
            DoubleVertex last_x_det = r.times(last_x.times(ConstantVertex.of(1.0).minus(last_x)));

            if (i > 100) {
                last_x = new GaussianVertex(last_x_det, 0.01);
                last_x.observe(0.6);
            } else {
                last_x = last_x_det;
            }
        }

        BayesianNetwork net = new BayesianNetwork(r.getConnectedGraph());
        net.probeForNonZeroMasterP(100, random);
        GradientOptimizer optimizer = new GradientOptimizer(net);
        optimizer.maxLikelihood(10000);

//        Or MAP with this MH method and sampling
//        NetworkSamples posteriorDistSamples = MetropolisHastings.getPosteriorSamples(
//            net,
//            net.getLatentVertices(),
//            100000
//        );
//
//        double inferredR = posteriorDistSamples.drop(1000).getDoubleTensorSamples(r).getAverages().scalar();

        double inferredR = r.getValue().scalar();
        System.out.println("Inferred R " + inferredR);

        double x0 = 0.5;

        for (int i = 0; i < 200; ++i) {
            x0 = logistic_map(x0, inferredR);
        }

        System.out.printf("%f\t%f\n", x0, last_x.getValue().scalar());

    }
}
