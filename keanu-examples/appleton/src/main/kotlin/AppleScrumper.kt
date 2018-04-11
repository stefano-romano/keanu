import io.improbable.keanu.kotlin.DoubleOperators

class AppleScrumper<DOUBLE : DoubleOperators<DOUBLE>> (initialXLocation: DOUBLE, initialYLocation: DOUBLE, initialHunger: DOUBLE,
                                                       val agressionLevel: DOUBLE) : IAgent {

    var xLocation = initialXLocation
    var yLocation = initialYLocation
    var huger = initialHunger

    override fun step() {

    }
}