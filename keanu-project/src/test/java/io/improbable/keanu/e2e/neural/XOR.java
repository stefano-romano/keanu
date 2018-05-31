package io.improbable.keanu.e2e.neural;

import io.improbable.keanu.algorithms.NetworkSamples;
import io.improbable.keanu.algorithms.mcmc.Hamiltonian;
import io.improbable.keanu.algorithms.variational.GradientOptimizer;
import io.improbable.keanu.network.BayesNetDoubleAsContinuous;
import io.improbable.keanu.plating.PlateBuilder;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.binary.AdditionVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.binary.MultiplicationVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.unary.SigmoidVertex;
import io.improbable.keanu.vertices.dbl.probabilistic.GaussianVertex;
import io.improbable.keanu.vertices.dbltensor.KeanuRandom;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class XOR {

    KeanuRandom random = new KeanuRandom(1);

    private double mu = 0.0;
    private double sigma = 5;

    //Latent Weights
    private DoubleVertex fooToOne = new GaussianVertex(mu, sigma);
    private DoubleVertex fooToTwo = new GaussianVertex(mu, sigma);
    private DoubleVertex fooToThree = new GaussianVertex(mu, sigma);
    private DoubleVertex barToOne = new GaussianVertex(mu, sigma);
    private DoubleVertex barToTwo = new GaussianVertex(mu, sigma);
    private DoubleVertex barToThree = new GaussianVertex(mu, sigma);

    private DoubleVertex oneToOneG = new GaussianVertex(mu, sigma);
    private DoubleVertex oneToTwoG = new GaussianVertex(mu, sigma);
    private DoubleVertex oneToThreeG = new GaussianVertex(mu, sigma);
    private DoubleVertex twoToOneG = new GaussianVertex(mu, sigma);
    private DoubleVertex twoToTwoG = new GaussianVertex(mu, sigma);
    private DoubleVertex twoToThreeG = new GaussianVertex(mu, sigma);
    private DoubleVertex threeToOneG = new GaussianVertex(mu, sigma);
    private DoubleVertex threeToTwoG = new GaussianVertex(mu, sigma);
    private DoubleVertex threeToThreeG = new GaussianVertex(mu, sigma);

    private DoubleVertex oneToOutput = new GaussianVertex(mu, sigma);
    private DoubleVertex twoToOutput = new GaussianVertex(mu, sigma);
    private DoubleVertex threeToOutput = new GaussianVertex(mu, sigma);

    @Test
    public void XOR() {
        KeanuRandom.setDefaultRandomSeed(1);
        List<List<Double>> data = generateData(500, random);
        createNeuralBayes(data);
        BayesNetDoubleAsContinuous bayesNet = new BayesNetDoubleAsContinuous(oneToOneG.getConnectedGraph());
        optimize(bayesNet);
        //sample(bayesNet);
        runForward();
    }

    private void createNeuralBayes(List<List<Double>> data) {

        new PlateBuilder<List<Double>>()
            .fromIterator(data.iterator())
            .withFactory((plate, values) -> {

                double valueOfFoo = values.get(0);
                double valueOfBar = values.get(1);
                double valueOfXORFooBar = values.get(2);

                SigmoidVertex sigmoid = buildPlate(fooToOne, fooToTwo, fooToThree, barToOne, barToTwo,
                                                   barToThree, oneToOutput, twoToOutput, threeToOutput,
                                                   valueOfFoo, valueOfBar);

                sigmoid.noisyObserve(valueOfXORFooBar, 1);

            }).build();

        System.out.println("Finished building plates.");
    }

    private SigmoidVertex buildPlate(DoubleVertex fooToOne,
                                     DoubleVertex fooToTwo,
                                     DoubleVertex fooToThree,
                                     DoubleVertex barToOne,
                                     DoubleVertex barToTwo,
                                     DoubleVertex barToThree,
                                     DoubleVertex oneToOutput,
                                     DoubleVertex twoToOutput,
                                     DoubleVertex threeToOutput,
                                     double fooValue,
                                     double barValue) {

        //Values to be XOR'd
        DoubleVertex foo = new ConstantDoubleVertex(fooValue);
        DoubleVertex bar = new ConstantDoubleVertex(barValue);

        //Start
        MultiplicationVertex weightFooOne = new MultiplicationVertex(foo, fooToOne);
        MultiplicationVertex weightFooTwo = new MultiplicationVertex(foo, fooToTwo);
        MultiplicationVertex weightFooThree = new MultiplicationVertex(foo, fooToThree);

        MultiplicationVertex weightBarOne = new MultiplicationVertex(bar, barToOne);
        MultiplicationVertex weightBarTwo = new MultiplicationVertex(bar, barToTwo);
        MultiplicationVertex weightBarThree = new MultiplicationVertex(bar, barToThree);

        SigmoidVertex sigmoidOne = new SigmoidVertex(new AdditionVertex(weightFooOne, weightBarOne));
        SigmoidVertex sigmoidTwo = new SigmoidVertex(new AdditionVertex(weightFooTwo, weightBarTwo));
        SigmoidVertex sigmoidThree = new SigmoidVertex(new AdditionVertex(weightFooThree, weightBarThree));

        //Layer
        Layer layer = new Layer();
        layer.createAndConnect(sigmoidOne, sigmoidTwo, sigmoidThree);

        MultiplicationVertex oneToOutputWeight = new MultiplicationVertex(layer.getOne(), oneToOutput);
        MultiplicationVertex twoToOutputWeight = new MultiplicationVertex(layer.getTwo(), twoToOutput);
        MultiplicationVertex threeToOutputWeight = new MultiplicationVertex(layer.getThree(), threeToOutput);

        SigmoidVertex output = new SigmoidVertex(addAll(oneToOutputWeight, twoToOutputWeight, threeToOutputWeight));

        return output;
    }

    private void runForward() {
        List<List<Double>> data = generateData(100, random);
        int correct = 0;

        for (int i = 0; i < data.size(); i++) {
            long predictedResult = buildForwardModel(data.get(i).get(0), data.get(i).get(1), data.get(i).get(2));
            if (predictedResult == Math.round(data.get(i).get(2))) {
                correct++;
            }
        }

        System.out.println(correct + " out of " + data.size() + " correct");
    }

    private long buildForwardModel(double fooValue, double barValue, double result) {
        DoubleVertex foo = new ConstantDoubleVertex(fooValue);
        DoubleVertex bar = new ConstantDoubleVertex(barValue);

        //Start
        MultiplicationVertex weightFooOne = new MultiplicationVertex(foo, new ConstantDoubleVertex(fooToOne.getValue()));
        MultiplicationVertex weightFooTwo = new MultiplicationVertex(foo, new ConstantDoubleVertex(fooToTwo.getValue()));
        MultiplicationVertex weightFooThree = new MultiplicationVertex(foo, new ConstantDoubleVertex(fooToThree.getValue()));

        MultiplicationVertex weightBarOne = new MultiplicationVertex(bar, new ConstantDoubleVertex(barToOne.getValue()));
        MultiplicationVertex weightBarTwo = new MultiplicationVertex(bar, new ConstantDoubleVertex(barToTwo.getValue()));
        MultiplicationVertex weightBarThree = new MultiplicationVertex(bar, new ConstantDoubleVertex(barToThree.getValue()));

        SigmoidVertex sigmoidOne = new SigmoidVertex(new AdditionVertex(weightFooOne, weightBarOne));
        SigmoidVertex sigmoidTwo = new SigmoidVertex(new AdditionVertex(weightFooTwo, weightBarTwo));
        SigmoidVertex sigmoidThree = new SigmoidVertex(new AdditionVertex(weightFooThree, weightBarThree));

        //Layer
        MultiplicationVertex oneToOneWeight = new MultiplicationVertex(sigmoidOne, new ConstantDoubleVertex(oneToOneG.getValue()));
        MultiplicationVertex oneToTwoWeight = new MultiplicationVertex(sigmoidOne, new ConstantDoubleVertex(oneToTwoG.getValue()));
        MultiplicationVertex oneToThreeWeight = new MultiplicationVertex(sigmoidOne, new ConstantDoubleVertex(oneToThreeG.getValue()));
        MultiplicationVertex twoToOneWeight = new MultiplicationVertex(sigmoidTwo, new ConstantDoubleVertex(twoToOneG.getValue()));
        MultiplicationVertex twoToTwoWeight = new MultiplicationVertex(sigmoidTwo, new ConstantDoubleVertex(twoToTwoG.getValue()));
        MultiplicationVertex twoToThreeWeight = new MultiplicationVertex(sigmoidTwo, new ConstantDoubleVertex(twoToThreeG.getValue()));
        MultiplicationVertex threeToOneWeight = new MultiplicationVertex(sigmoidThree, new ConstantDoubleVertex(threeToOneG.getValue()));
        MultiplicationVertex threeToTwoWeight = new MultiplicationVertex(sigmoidThree, new ConstantDoubleVertex(threeToTwoG.getValue()));
        MultiplicationVertex threeToThreeWeight = new MultiplicationVertex(sigmoidThree, new ConstantDoubleVertex(threeToThreeG.getValue()));

        SigmoidVertex newOne = new SigmoidVertex(addAll(oneToOneWeight, twoToOneWeight, threeToOneWeight));
        SigmoidVertex newTwo = new SigmoidVertex(addAll(oneToTwoWeight, twoToTwoWeight, threeToTwoWeight));
        SigmoidVertex newThree = new SigmoidVertex(addAll(oneToThreeWeight, twoToThreeWeight, threeToThreeWeight));

        MultiplicationVertex oneToOutputWeight = new MultiplicationVertex(newOne, new ConstantDoubleVertex(oneToOutput.getValue()));
        MultiplicationVertex twoToOutputWeight = new MultiplicationVertex(newTwo, new ConstantDoubleVertex(twoToOutput.getValue()));
        MultiplicationVertex threeToOutputWeight = new MultiplicationVertex(newThree, new ConstantDoubleVertex(threeToOutput.getValue()));

        SigmoidVertex output = new SigmoidVertex(addAll(oneToOutputWeight, twoToOutputWeight, threeToOutputWeight));

        long predictedResult = Math.round(output.getValue());

        System.out.println("Sigmoid: " + output.getValue() + ". Predicted: " + predictedResult + ". Result: " + result);
        return predictedResult;
    }

    private void optimize(BayesNetDoubleAsContinuous bayesNet) {
        GradientOptimizer optimizer = new GradientOptimizer(bayesNet);
        optimizer.maxLikelihood(15000);
        System.out.println("Inference complete.");
    }

    private void sample(BayesNetDoubleAsContinuous bayesNet) {
        NetworkSamples samples = Hamiltonian.getPosteriorSamples(bayesNet, bayesNet.getContinuousLatentVertices(), 1000, 10, 0.025, random);
//        NetworkSamples samples = NUTS.getPosteriorSamples(bayesNetDoubleAsContinuous, bayesNetDoubleAsContinuous.getContinuousLatentVertices(), 1000, 0.025, random);

        fooToOne.setValue(samples.get(fooToOne).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        fooToTwo.setValue(samples.get(fooToTwo).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        fooToThree.setValue(samples.get(fooToThree).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        barToOne.setValue(samples.get(barToOne).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        barToTwo.setValue(samples.get(barToTwo).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        barToThree.setValue(samples.get(barToThree).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        oneToOneG.setValue(samples.get(oneToOneG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        oneToTwoG.setValue(samples.get(oneToTwoG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        oneToThreeG.setValue(samples.get(oneToThreeG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        twoToOneG.setValue(samples.get(twoToOneG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        twoToTwoG.setValue(samples.get(twoToTwoG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        twoToThreeG.setValue(samples.get(twoToThreeG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        threeToOneG.setValue(samples.get(threeToOneG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        threeToTwoG.setValue(samples.get(threeToTwoG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        threeToThreeG.setValue(samples.get(threeToThreeG).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        oneToOutput.setValue(samples.get(oneToOutput).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        twoToOutput.setValue(samples.get(twoToOutput).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
        threeToOutput.setValue(samples.get(threeToOutput).asList().stream().mapToDouble(sample -> sample).average().getAsDouble());
    }

    private DoubleVertex addAll(DoubleVertex one, DoubleVertex two, DoubleVertex three) {
        return new AdditionVertex(new AdditionVertex(one, two), three);
    }

    private List<List<Double>> generateData(int total, KeanuRandom random) {
        List<List<Double>> data = new ArrayList<>();

        for (int i = 0; i < total; i++) {
            double foo = random.nextDouble();
            double bar = random.nextDouble();

            long roundedFoo = Math.round(foo);
            long roundedBar = Math.round(bar);
            double xor = 0.;

            if (roundedFoo == 1 && roundedBar == 0 || roundedFoo == 0 && roundedBar == 1) {
                xor = 1.;
            }

            double[] x = { foo, bar, xor };

            data.add(Arrays.stream(x)
                .boxed()
                .collect(Collectors.toList()));
        }

        return data;
    }

    private class Layer {

        private SigmoidVertex one;
        private SigmoidVertex two;
        private SigmoidVertex three;

        public Layer() {
        }

        public void createAndConnect(SigmoidVertex fromOne,
                                     SigmoidVertex fromTwo,
                                     SigmoidVertex fromThree) {

            MultiplicationVertex oneToOneWeight = new MultiplicationVertex(fromOne, oneToOneG);
            MultiplicationVertex oneToTwoWeight = new MultiplicationVertex(fromOne, oneToTwoG);
            MultiplicationVertex oneToThreeWeight = new MultiplicationVertex(fromOne, oneToThreeG);
            MultiplicationVertex twoToOneWeight = new MultiplicationVertex(fromTwo, twoToOneG);
            MultiplicationVertex twoToTwoWeight = new MultiplicationVertex(fromTwo, twoToTwoG);
            MultiplicationVertex twoToThreeWeight = new MultiplicationVertex(fromTwo, twoToThreeG);
            MultiplicationVertex threeToOneWeight = new MultiplicationVertex(fromThree, threeToOneG);
            MultiplicationVertex threeToTwoWeight = new MultiplicationVertex(fromThree, threeToTwoG);
            MultiplicationVertex threeToThreeWeight = new MultiplicationVertex(fromThree, threeToThreeG);

            one = new SigmoidVertex(addAll(oneToOneWeight, twoToOneWeight, threeToOneWeight));
            two  = new SigmoidVertex(addAll(oneToTwoWeight, twoToTwoWeight, threeToTwoWeight));
            three = new SigmoidVertex(addAll(oneToThreeWeight, twoToThreeWeight, threeToThreeWeight));
        }

        public SigmoidVertex getOne() {
            return one;
        }

        public SigmoidVertex getTwo() {
            return two;
        }

        public SigmoidVertex getThree() {
            return three;
        }
    }

}
