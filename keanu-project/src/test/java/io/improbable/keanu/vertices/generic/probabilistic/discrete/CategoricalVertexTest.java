package io.improbable.keanu.vertices.generic.probabilistic.discrete;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.improbable.keanu.vertices.ConstantVertex;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.KeanuRandom;

public class CategoricalVertexTest {
    private final Logger log = LoggerFactory.getLogger(CategoricalVertexTest.class);

    private static double epsilon = 0.01;
    private static int N = 100000;

    private KeanuRandom random;

    @Before
    public void setup() {
        random = new KeanuRandom(1);
    }

    @Test
    public void fourValuesEquallyWeightedSummingToOne() {

        LinkedHashMap<TestEnum, DoubleVertex> selectableValues = new LinkedHashMap<>();
        selectableValues.put(TestEnum.A, ConstantVertex.of(0.25));
        selectableValues.put(TestEnum.B, ConstantVertex.of(0.25));
        selectableValues.put(TestEnum.C, ConstantVertex.of(0.25));
        selectableValues.put(TestEnum.D, ConstantVertex.of(0.25));

        LinkedHashMap<TestEnum, Double> proportions = testSample(selectableValues, random);
        assertProportionsWithinExpectedRanges(selectableValues, proportions);
    }

    @Test
    public void fourValuesNotEquallyWeightedSummingToOne() {

        LinkedHashMap<TestEnum, DoubleVertex> selectableValues = new LinkedHashMap<>();
        selectableValues.put(TestEnum.A, ConstantVertex.of(0.1));
        selectableValues.put(TestEnum.B, ConstantVertex.of(0.2));
        selectableValues.put(TestEnum.C, ConstantVertex.of(0.3));
        selectableValues.put(TestEnum.D, ConstantVertex.of(0.4));

        LinkedHashMap<TestEnum, Double> proportions = testSample(selectableValues, random);
        assertProportionsWithinExpectedRanges(selectableValues, proportions);
    }

    @Test
    public void fourValuesEquallyWeightedSummingToFour() {

        LinkedHashMap<TestEnum, DoubleVertex> selectableValues = new LinkedHashMap<>();
        selectableValues.put(TestEnum.A, ConstantVertex.of(1.0));
        selectableValues.put(TestEnum.B, ConstantVertex.of(1.0));
        selectableValues.put(TestEnum.C, ConstantVertex.of(1.0));
        selectableValues.put(TestEnum.D, ConstantVertex.of(1.0));

        LinkedHashMap<TestEnum, Double> proportions = testSample(selectableValues, random);
        LinkedHashMap<TestEnum, DoubleVertex> normalisedSelectableValues = normaliseSelectableValues(selectableValues, 4.0);
        assertProportionsWithinExpectedRanges(normalisedSelectableValues, proportions);
    }

    @Test
    public void fourValuesNotEquallyWeightedSummingToFour() {

        LinkedHashMap<TestEnum, DoubleVertex> selectableValues = new LinkedHashMap<>();
        selectableValues.put(TestEnum.A, ConstantVertex.of(0.25));
        selectableValues.put(TestEnum.B, ConstantVertex.of(0.75));
        selectableValues.put(TestEnum.C, ConstantVertex.of(1.25));
        selectableValues.put(TestEnum.D, ConstantVertex.of(1.75));

        LinkedHashMap<TestEnum, Double> proportions = testSample(selectableValues, random);
        LinkedHashMap<TestEnum, DoubleVertex> normalisedSelectableValues = normaliseSelectableValues(selectableValues, 4.0);
        assertProportionsWithinExpectedRanges(normalisedSelectableValues, proportions);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotSampleIfProbabilitiesSumToZero() {
        double probA = 0.0;
        double probB = 0.0;

        LinkedHashMap<TestEnum, DoubleVertex> selectableValues = new LinkedHashMap<>();
        selectableValues.put(TestEnum.A, ConstantVertex.of(probA));
        selectableValues.put(TestEnum.B, ConstantVertex.of(probB));

        CategoricalVertex<TestEnum> select = new CategoricalVertex<>(selectableValues);
        select.sample(random);
    }

    @Test
    public void logProbOfCategoryIsEquivalentToItsLogProbabilityDividedBySum() {
        double probA = 0.25;
        double probB = 0.75;
        double probC = 1.25;
        double probD = 1.75;

        double total = probA + probB + probC + probD;

        LinkedHashMap<TestEnum, DoubleVertex> selectableValues = new LinkedHashMap<>();
        selectableValues.put(TestEnum.A, ConstantVertex.of(0.25));
        selectableValues.put(TestEnum.B, ConstantVertex.of(0.75));
        selectableValues.put(TestEnum.C, ConstantVertex.of(1.25));
        selectableValues.put(TestEnum.D, ConstantVertex.of(1.75));

        CategoricalVertex<TestEnum> select = new CategoricalVertex<>(selectableValues);

        assertEquals(Math.log(probA / total), select.logProb(TestEnum.A), 1e-6);
        assertEquals(Math.log(probB / total), select.logProb(TestEnum.B), 1e-6);
        assertEquals(Math.log(probC / total), select.logProb(TestEnum.C), 1e-6);
        assertEquals(Math.log(probD / total), select.logProb(TestEnum.D), 1e-6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotComputeLogProbIfProbabilitiesSumToZero() {
        double probA = 0.0;
        double probB = 0.0;

        LinkedHashMap<TestEnum, DoubleVertex> selectableValues = new LinkedHashMap<>();
        selectableValues.put(TestEnum.A, ConstantVertex.of(probA));
        selectableValues.put(TestEnum.B, ConstantVertex.of(probB));

        CategoricalVertex<TestEnum> select = new CategoricalVertex<>(selectableValues);
        select.logProb(TestEnum.A);
    }

    private LinkedHashMap<TestEnum, Double> testSample(LinkedHashMap<TestEnum, DoubleVertex> selectableValues,
                                                       KeanuRandom random) {

        CategoricalVertex<TestEnum> select = new CategoricalVertex<>(selectableValues);

        LinkedHashMap<TestEnum, Integer> sampleFrequencies = new LinkedHashMap<>();
        sampleFrequencies.put(TestEnum.A, 0);
        sampleFrequencies.put(TestEnum.B, 0);
        sampleFrequencies.put(TestEnum.C, 0);
        sampleFrequencies.put(TestEnum.D, 0);

        for (int i = 0; i < N; i++) {
            TestEnum s = select.sample(random);
            sampleFrequencies.put(s, sampleFrequencies.get(s) + 1);
        }

        return calculateProportions(sampleFrequencies, N);
    }

    private LinkedHashMap<TestEnum, Double> calculateProportions(LinkedHashMap<TestEnum, Integer> sampleFrequencies, int n) {
        LinkedHashMap<TestEnum, Double> proportions = new LinkedHashMap<>();
        for (Map.Entry<TestEnum, Integer> entry : sampleFrequencies.entrySet()) {
            double proportion = (double) entry.getValue() / n;
            proportions.put(entry.getKey(), proportion);
        }

        return proportions;
    }

    private void assertProportionsWithinExpectedRanges(LinkedHashMap<TestEnum, DoubleVertex> selectableValues,
                                                       HashMap<TestEnum, Double> proportions) {

        for (Map.Entry<TestEnum, Double> entry : proportions.entrySet()) {
            log.info(entry.getKey() + ": " + entry.getValue());
            double p = entry.getValue();
            double expected = selectableValues.get(entry.getKey()).getValue().scalar();
            assertEquals(p, expected, epsilon);
        }
    }

    private LinkedHashMap<TestEnum, DoubleVertex> normaliseSelectableValues(LinkedHashMap<TestEnum, DoubleVertex> selectableValues,
                                                                            double sum) {
        LinkedHashMap<TestEnum, DoubleVertex> normalised = new LinkedHashMap<>();
        for (Map.Entry<TestEnum, DoubleVertex> entry : selectableValues.entrySet()) {
            double normalizedProbability = entry.getValue().getValue().scalar() / sum;
            normalised.put(entry.getKey(), ConstantVertex.of(normalizedProbability));
        }
        return normalised;
    }

    private enum TestEnum {
        A, B, C, D
    }
}