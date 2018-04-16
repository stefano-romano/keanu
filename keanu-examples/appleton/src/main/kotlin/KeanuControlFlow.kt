import io.improbable.keanu.kotlin.BooleanOperators

interface KeanuControlFlow<BOOLEAN_IN, BOOLEAN : BooleanOperators<BOOLEAN_IN, BOOLEAN>> {

    fun If(test: Boolean, _then: () -> Unit, _else: () -> Unit)

    fun If(test: BOOLEAN, _then: () -> Unit, _else: () -> Unit)

    fun If(test: Boolean, _then: () -> Unit)

    fun If(test: BOOLEAN, _then: () -> Unit)

}
