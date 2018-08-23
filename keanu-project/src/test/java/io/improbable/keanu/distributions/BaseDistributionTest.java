package io.improbable.keanu.distributions;

import io.improbable.keanu.distributions.discrete.Bernoulli;
import io.improbable.keanu.distributions.discrete.Binomial;
import io.improbable.keanu.distributions.discrete.Categorical;
import io.improbable.keanu.distributions.discrete.Poisson;
import io.improbable.keanu.distributions.discrete.UniformInt;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.tensor.intgr.IntegerTensor;
import org.apache.commons.math3.util.CombinatoricsUtils;
import static org.apache.commons.math3.util.CombinatoricsUtils.factorial;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.LinkedHashMap;

public class BaseDistributionTest {
    @Test(expected = IllegalArgumentException.class)
    public void cannotComputeKLDivergenceIfQHasSmallerSupport() {
        DiscreteDistribution q = Binomial.withParameters(DoubleTensor.scalar(1.0), IntegerTensor.scalar(1));
        DiscreteDistribution p = Poisson.withParameters(DoubleTensor.scalar(1.0));
        p.computeKLDivergence(q);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotComputeKLDivergenceWithNonScalarButDifferentShapes() {
        DiscreteDistribution q = Poisson.withParameters(DoubleTensor.create(1.0, new int[] {2, 2}));
        DiscreteDistribution p = Binomial.withParameters(
            DoubleTensor.create(1.0, new int[] {1, 2}),
            IntegerTensor.create(1, new int[] {1, 2}));
        p.computeKLDivergence(q);
    }

    @Test
    public void computeKLDivergenceBetweenBinomialAndPoisson() {
        int n = 1;
        double prob = 0.5;
        double lambda = 1.0;

        DiscreteDistribution p = Binomial.withParameters(DoubleTensor.scalar(prob), IntegerTensor.scalar(n));
        DiscreteDistribution q = Poisson.withParameters(DoubleTensor.scalar(lambda));

        DoubleTensor divergence = p.computeKLDivergence(q);
        double p1 = binomialProbability(n, prob, 0);
        double p2 = binomialProbability(n, prob, 1);

        double q1 = poissonProbability(lambda, 0);
        double q2 = poissonProbability(lambda, 1);

        double expectedDivergence = KLDivergence(new double[] {p1, p2}, new double[] {q1, q2}, 2);

        assertEquals(expectedDivergence, divergence.scalar(), 1e-06);
    }

    @Test
    public void computeKLDivergenceBetweenBinomialAndBinomial() {
        int pn = 2;
        double pProb = 0.5;

        int qn = 3;
        double qProb = 0.8;

        DiscreteDistribution p = Binomial.withParameters(DoubleTensor.scalar(pProb), IntegerTensor.scalar(pn));
        DiscreteDistribution q = Binomial.withParameters(DoubleTensor.scalar(qProb), IntegerTensor.scalar(qn));

        DoubleTensor divergence = p.computeKLDivergence(q);

        double p1 = binomialProbability(pn, pProb, 0);
        double p2 = binomialProbability(pn, pProb, 1);
        double p3 = binomialProbability(pn, pProb, 2);

        double q1 = binomialProbability(qn, qProb, 0);
        double q2 = binomialProbability(qn, qProb, 1);
        double q3 = binomialProbability(qn, qProb, 2);

        double expectedDivergence = KLDivergence(new double[] {p1, p2, p3}, new double[] {q1, q2, q3}, 3);

        assertEquals(expectedDivergence, divergence.scalar(), 1e-06);
    }

    @Test
    public void computeKLDivergenceBetweenBernoulliAndBernoulli() {
        double pProb = 0.3;
        double qProb = 0.4;

        Bernoulli p = Bernoulli.withParameters(DoubleTensor.scalar(pProb));
        Bernoulli q = Bernoulli.withParameters(DoubleTensor.scalar(qProb));

        DoubleTensor divergence = p.computeKLDivergence(q);
        double expectedDivergence = KLDivergence(new double[] {pProb, 1 - pProb}, new double[] {qProb, 1 - qProb}, 2);

        assertEquals(expectedDivergence, divergence.scalar(), 1e-06);
    }

    @Test
    public void computeKLDivergenceBetweenCategoricalAndCategorical() {
        LinkedHashMap<TestEnum, DoubleTensor> pSelectableValues = new LinkedHashMap<>();
        pSelectableValues.put(TestEnum.A, DoubleTensor.scalar(1.));

        LinkedHashMap<TestEnum, DoubleTensor> qSelectableValues = new LinkedHashMap<>();
        qSelectableValues.put(TestEnum.A, DoubleTensor.scalar(0.25));
        qSelectableValues.put(TestEnum.B, DoubleTensor.scalar(0.75));

        Categorical<TestEnum> p = Categorical.withParameters(pSelectableValues);
        Categorical<TestEnum> q = Categorical.withParameters(qSelectableValues);

        DoubleTensor divergence = p.computeKLDivergence(q);
        double expectedDivergence = KLDivergence(new double[] {1.0}, new double[] {0.25}, 1);

        assertEquals(expectedDivergence, divergence.scalar(), 1e-06);
    }

    @Test
    public void computeKLDivergenceBetweenPoissonAndPoisson() {
        double pLambda = 0.4;
        double qLambda = 0.8;

        DiscreteDistribution p = Poisson.withParameters(DoubleTensor.scalar(pLambda));
        DiscreteDistribution q = Poisson.withParameters(DoubleTensor.scalar(qLambda));

        DoubleTensor divergence = p.computeKLDivergence(q);
        double expectedDivergence = pLambda * Math.log(pLambda / qLambda) + qLambda - pLambda;

        assertEquals(expectedDivergence, divergence.scalar(), 1e-06);
    }

    @Test
    public void computeKLDivergenceBetweenUniformAndUniform() {
        Integer pMin = 2;
        Integer pMax = 5;
        Integer qMin = 1;
        Integer qMax = 6;

        DiscreteDistribution p = UniformInt.withParameters(IntegerTensor.scalar(pMin), IntegerTensor.scalar(pMax));
        DiscreteDistribution q = UniformInt.withParameters(IntegerTensor.scalar(qMin), IntegerTensor.scalar(qMax));

        DoubleTensor divergence = p.computeKLDivergence(q);

        double pProb = 1. / (pMax.doubleValue() - pMin.doubleValue());
        double qProb = 1. / (qMax.doubleValue() - qMin.doubleValue());
        double expectedDivergence = KLDivergence(new double[]{pProb, pProb, pProb}, new double[]{qProb, qProb, qProb}, 3);

        assertEquals(expectedDivergence, divergence.scalar(), 1e-05);
    }

    private double KLDivergence(double[] p, double[] q, int n) {
        double divergence = 0;
        for (int i = 0; i < n; i++) {
            divergence += p[i] * (Math.log(p[i]) - Math.log(q[i]));
        }
        return divergence;
    }

    private double binomialProbability(int n, double prob, int k) {
        return CombinatoricsUtils.binomialCoefficient(n, k) * Math.pow(prob, k) * Math.pow(1 - prob, n - k);
    }

    private double poissonProbability(double lambda, int k) {
        return (Math.pow(lambda, k) / factorial(k)) * Math.exp(-lambda);
    }

    private enum TestEnum {
        A, B, C
    }
}
