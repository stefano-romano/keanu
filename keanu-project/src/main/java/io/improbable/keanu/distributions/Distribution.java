package io.improbable.keanu.distributions;

import io.improbable.keanu.tensor.Tensor;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.vertices.dbl.KeanuRandom;
import org.apache.commons.lang3.NotImplementedException;

public interface Distribution<T extends Tensor> extends BaseDistribution {
    T sample(int[] shape, KeanuRandom random);
    DoubleTensor logProb(T x);
    default Support<T> getSupport() {
        throw new NotImplementedException("getSupport is not implemented for this distribution");
    }
}
