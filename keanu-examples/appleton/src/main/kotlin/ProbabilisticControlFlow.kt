import io.improbable.keanu.vertices.Vertex
import io.improbable.keanu.vertices.bool.BoolVertex
import io.improbable.keanu.vertices.bool.nonprobabilistic.ConstantBoolVertex

class ProbabilisticControlFlow : KeanuControlFlow<Vertex<Boolean>, BoolVertex> {

    override fun If(test: Boolean, _then: () -> Unit, _else: () -> Unit) {
        If(ConstantBoolVertex(test), _then, _else)
    }

    override fun If(test: BoolVertex, _then: () -> Unit, _else: () -> Unit) {
        // TODO Implementing this with require changes to the keanu BoolVertex
    }

    override fun If(test: Boolean, _then: () -> Unit) {
        If(ConstantBoolVertex(test), _then)
    }

    override fun If(test: BoolVertex, _then: () -> Unit) {
        // TODO Implementing this with require changes to the keanu BoolVertex
    }

}
