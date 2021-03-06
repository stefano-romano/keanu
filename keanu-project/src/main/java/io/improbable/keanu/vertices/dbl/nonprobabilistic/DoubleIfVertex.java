package io.improbable.keanu.vertices.dbl.nonprobabilistic;

import java.util.Map;

import io.improbable.keanu.tensor.bool.BooleanTensor;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.vertices.NonProbabilistic;
import io.improbable.keanu.vertices.Vertex;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.KeanuRandom;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.diff.DualNumber;

public class DoubleIfVertex extends DoubleVertex implements NonProbabilistic<DoubleTensor> {

    private final Vertex<? extends BooleanTensor> predicate;
    private final Vertex<? extends DoubleTensor> thn;
    private final Vertex<? extends DoubleTensor> els;

    public DoubleIfVertex(int[] shape,
                          Vertex<? extends BooleanTensor> predicate,
                          Vertex<? extends DoubleTensor> thn,
                          Vertex<? extends DoubleTensor> els) {
        this.predicate = predicate;
        this.thn = thn;
        this.els = els;
        setParents(predicate, thn, els);
        setValue(DoubleTensor.placeHolder(shape));
    }

    @Override
    public DoubleTensor sample(KeanuRandom random) {
        return op(predicate.sample(random), thn.sample(random), els.sample(random));
    }

    @Override
    public DualNumber calculateDualNumber(Map<Vertex, DualNumber> dualNumbers) {
        return DualNumber.ifThenElse(predicate.getValue(), dualNumbers.get(thn), dualNumbers.get(els));
    }

    @Override
    public DoubleTensor calculate() {
        return op(predicate.getValue(), thn.getValue(), els.getValue());
    }

    private DoubleTensor op(BooleanTensor predicate, DoubleTensor thn, DoubleTensor els) {
        return predicate.setDoubleIf(thn, els);
    }
}
