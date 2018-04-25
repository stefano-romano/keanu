package FourDVar

import io.improbable.keanu.kotlin.DoubleOperators
import io.improbable.keanu.vertices.dbl.probabilistic.GaussianVertex

interface IModel<DATA : DoubleOperators<DATA>> {
    fun runWindow() : Iterable<DATA>
    fun step()
    fun getState() : Iterable<DATA>
    fun setState(state : Iterable<DATA>)
    fun getGaussianState(): Iterable<GaussianVertex>
}