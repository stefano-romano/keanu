package io.improbable.keanu.vertices.intgr;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Function;

import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.tensor.dbl.Nd4jDoubleTensor;
import io.improbable.keanu.tensor.intgr.Nd4jIntegerTensor;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.probabilistic.GaussianVertex;
import org.junit.Before;
import org.junit.Test;

import io.improbable.keanu.tensor.intgr.IntegerTensor;
import io.improbable.keanu.vertices.ConstantVertex;
import io.improbable.keanu.vertices.intgr.probabilistic.BinomialVertex;
import io.improbable.keanu.vertices.intgr.probabilistic.PoissonVertex;

public class IntegerVertexTest {

    IntegerVertex v1;
    IntegerVertex v2;

    @Before
    public void setup() {
        v1 = ConstantVertex.of(3);
        v2 = ConstantVertex.of(2);
    }

    @Test
    public void doesMultiply() {
        IntegerVertex result = v1.multiply(v2);
        result.eval();
        Integer expected = 6;
        assertEquals(result.getValue().scalar(), expected);
    }

    @Test
    public void doesAdd() {
        IntegerVertex result = v1.plus(v2);
        result.eval();
        Integer expected = 5;
        assertEquals(result.getValue().scalar(), expected);
    }

    @Test
    public void doesSubtract() {
        IntegerVertex result = v1.minus(v2);
        result.eval();
        Integer expected = 1;
        assertEquals(result.getValue().scalar(), expected);
    }

    @Test
    public void doesEqualTo() {
        IntegerVertex v3 = ConstantVertex.of(3);

        assertFalse(v1.equalTo(v2).eval().scalar());
        assertTrue(v1.notEqualTo(v2).eval().scalar());
        assertFalse(v2.equalTo(v3).eval().scalar());
        assertTrue(v2.notEqualTo(v3).eval().scalar());
    }

    @Test
    public void doesObserve() {
        PoissonVertex testIntegerVertex = new PoissonVertex(1.0);
        testIntegerVertex.observe(5);

        Integer expected = 5;
        assertEquals(testIntegerVertex.getValue().scalar(), expected);
        assertTrue(testIntegerVertex.isObserved());
    }

    @Test
    public void doesLambda() {
        Function<IntegerTensor, IntegerTensor> op = val -> val.plus(5);

        IntegerVertex result = v1.lambda(op);
        result.eval();
        Integer expected = 8;
        assertEquals(result.getValue().scalar(), expected);
    }

    @Test
    public void canObserveArrayOfValues() {
        IntegerVertex binomialVertex = new BinomialVertex(0.5, 20);
        int[] observation = new int[]{1, 2, 3};
        binomialVertex.observe(observation);
        assertArrayEquals(observation, binomialVertex.getValue().asFlatIntegerArray());
    }

    @Test
    public void canObserveTensor() {
        IntegerVertex binomialVertex = new BinomialVertex(0.5, 20);
        IntegerTensor observation = Nd4jIntegerTensor.create(new int[]{1, 2, 3, 4}, new int[]{2, 2});
        binomialVertex.observe(observation);
        assertArrayEquals(observation.asFlatIntegerArray(), binomialVertex.getValue().asFlatIntegerArray());
        assertArrayEquals(observation.getShape(), binomialVertex.getShape());

    }

    @Test
    public void canSetAndCascadeArrayOfValues() {
        IntegerVertex binomialVertex = new BinomialVertex(0.5, 20);
        int[] values = new int[]{1, 2, 3};
        binomialVertex.setAndCascade(values);
        assertArrayEquals(values, binomialVertex.getValue().asFlatIntegerArray());
    }

    @Test
    public void canSetValueAsArrayOfValues() {
        IntegerVertex binomialVertex = new BinomialVertex(0.5, 20);
        int[] values = new int[]{1, 2, 3};
        binomialVertex.setValue(values);
        assertArrayEquals(values, binomialVertex.getValue().asFlatIntegerArray());
    }

    @Test
    public void canSetValueAsScalarOnNonScalarVertex() {
        IntegerVertex binomialVertex = new BinomialVertex(new int[]{2, 1}, 0.5, 20);
        binomialVertex.setValue(2);
        assertArrayEquals(new int[]{2}, binomialVertex.getValue().asFlatIntegerArray());
    }

    @Test
    public void canSetAndCascadeAsScalarOnNonScalarVertex() {
        IntegerVertex binomialVertex = new BinomialVertex(new int[]{2, 1}, 0.5, 20);
        binomialVertex.setAndCascade(2);
        assertArrayEquals(new int[]{2}, binomialVertex.getValue().asFlatIntegerArray());
    }

    @Test
    public void canPluckValue() {
        IntegerVertex binomialVertex = new BinomialVertex(0.5, 20);
        int[] values = new int[]{1, 2, 3};
        binomialVertex.setValue(values);
        assertEquals(1, (long) binomialVertex.take(0, 0).getValue().scalar());
    }

}
