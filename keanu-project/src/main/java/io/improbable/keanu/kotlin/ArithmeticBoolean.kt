package io.improbable.keanu.kotlin


class ArithmeticBoolean(val value: Boolean) : BooleanOperators<ArithmeticBoolean, ArithmeticBoolean> {

    override fun and(vararg those: ArithmeticBoolean): ArithmeticBoolean {
        var isTrue = this.value
        for (bool in those) {
            if (!isTrue) {
                break;
            }

            if (!bool.value) {
                isTrue = false
            }
        }

        return ArithmeticBoolean(isTrue)
    }

    override fun or(vararg those: ArithmeticBoolean): ArithmeticBoolean {
        var isTrue = this.value
        for (bool in those) {
            if (bool.value) {
                isTrue = true;
            }
        }

        return ArithmeticBoolean(isTrue)
    }

}
