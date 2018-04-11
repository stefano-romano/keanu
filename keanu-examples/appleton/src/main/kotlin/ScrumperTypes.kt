import io.improbable.keanu.kotlin.ArithmeticDouble
import io.improbable.keanu.kotlin.DoubleOperators
import io.improbable.keanu.vertices.dbl.DoubleVertex
import io.improbable.keanu.vertices.dbl.nonprobabilistic.ConstantDoubleVertex

class ScrumperTypes<DOUBLE : DoubleOperators<DOUBLE>> (val speed: DOUBLE, visionRange: DOUBLE) {

    var martianSpeed = 0.38
    var martianVisionRange = 7.0
    var venusianSpeed = 0.9
    var venusianVisionRange = 3.0
    var earthlingSpeed = 1.0
    var earthlingVisionRange = 10.0

    lateinit var MARTIAN: ScrumperTypes<DOUBLE>
    lateinit var VENUSIAN: ScrumperTypes<DOUBLE>
    lateinit var EARTHLING: ScrumperTypes<DOUBLE>


    init {
        if (speed is ArithmeticDouble) {
            MARTIAN = ScrumperTypes(ArithmeticDouble(martianSpeed) as DOUBLE, ArithmeticDouble(martianVisionRange) as DOUBLE)
            VENUSIAN = ScrumperTypes(ArithmeticDouble(venusianSpeed) as DOUBLE, ArithmeticDouble(venusianVisionRange) as DOUBLE)
            EARTHLING = ScrumperTypes(ArithmeticDouble(earthlingSpeed) as DOUBLE, ArithmeticDouble(earthlingVisionRange) as DOUBLE)
        } else if (speed is DoubleVertex) {
            MARTIAN = ScrumperTypes(ConstantDoubleVertex(martianSpeed) as DOUBLE, ConstantDoubleVertex(martianVisionRange) as DOUBLE)
            VENUSIAN = ScrumperTypes(ConstantDoubleVertex(venusianSpeed) as DOUBLE, ConstantDoubleVertex(venusianVisionRange) as DOUBLE)
            EARTHLING = ScrumperTypes(ConstantDoubleVertex(earthlingSpeed) as DOUBLE, ConstantDoubleVertex(earthlingVisionRange) as DOUBLE)
        }
    }
}