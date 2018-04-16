import io.improbable.keanu.kotlin.ArithmeticBoolean

class RandomControlFlow : KeanuControlFlow<ArithmeticBoolean, ArithmeticBoolean> {

    override fun If(test: Boolean, _then: () -> Unit, _else: () -> Unit) {
        If(ArithmeticBoolean(test), _then, _else)
    }

    override fun If(test: ArithmeticBoolean, _then: () -> Unit, _else: () -> Unit) {
        if (test.value) {
            _then()
        } else {
            _else()
        }
    }

    override fun If(test: Boolean, _then: () -> Unit) {
        If(ArithmeticBoolean(test), _then)
    }

    override fun If(test: ArithmeticBoolean, _then: () -> Unit) {
        if (test.value) {
            _then()
        }
    }

}
