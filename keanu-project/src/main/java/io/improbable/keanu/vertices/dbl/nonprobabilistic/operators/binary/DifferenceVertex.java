package io.improbable.keanu.vertices.dbl.nonprobabilistic.operators.binary;

import io.improbable.keanu.vertices.dbl.DoubleVertex;

public class DifferenceVertex extends DoubleBinaryOpVertex {

    /**
     * Subtracts one vertex from another
     *
     * @param left  the vertex that will be subtracted from
     * @param right the vertex to subtract
     */
    public DifferenceVertex(DoubleVertex left, DoubleVertex right) {
        super(left, right,
            (l, r) -> l.minus(r),
            (l, r) -> l.minus(r));
    }
}
