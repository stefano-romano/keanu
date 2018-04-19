package com.research.pocketKeanu.autoDiff

import io.improbable.operators.math.Log
import io.improbable.operators.math.Pow
import io.improbable.operators.trig.*

class AutodiffMath {

    companion object {

        fun sin(x: DifferentiableDouble): DifferentiableDouble {
            return Sin(x)
        }

        fun asin(x: DifferentiableDouble): DifferentiableDouble {
            return Asin(x)
        }

        fun sinh(x: DifferentiableDouble): DifferentiableDouble {
            return Sinh(x)
        }

        fun cos(x: DifferentiableDouble): DifferentiableDouble {
            return Cos(x)
        }

        fun acos(x: DifferentiableDouble): DifferentiableDouble {
            return Acos(x)
        }

        fun cosh(x: DifferentiableDouble): DifferentiableDouble {
            return Cosh(x)
        }

        fun tan(x: DifferentiableDouble): DifferentiableDouble {
            return Tan(x)
        }

        fun atan(x: DifferentiableDouble): DifferentiableDouble {
            return Atan(x)
        }

        fun tanh(x: DifferentiableDouble): DifferentiableDouble {
            return Tanh(x)
        }

        fun pow(a: DifferentiableDouble, b: DifferentiableDouble): DifferentiableDouble {
            return Pow(a, b)
        }

        fun exp(a: DifferentiableDouble): DifferentiableDouble {
            return Pow(DifferentiableDouble(Math.E), a)
        }

        fun sqrt(a: DifferentiableDouble): DifferentiableDouble {
            return Pow(a, DifferentiableDouble(0.5))
        }

        fun log(x: DifferentiableDouble, base: DifferentiableDouble): DifferentiableDouble {
            return Log(x, base)
        }

        fun log(x: DifferentiableDouble): DifferentiableDouble {
            return Log(x, DifferentiableDouble(Math.E))
        }

        fun log10(x: DifferentiableDouble): DifferentiableDouble {
            return Log(x, DifferentiableDouble(10.0))
        }
    }

}
