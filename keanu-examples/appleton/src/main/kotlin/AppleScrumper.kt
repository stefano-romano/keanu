import com.sun.tools.jdi.DoubleValueImpl
import io.improbable.keanu.kotlin.DoubleOperators

class AppleScrumper<DOUBLE : DoubleOperators<DOUBLE>> (val world: World<DOUBLE>, var xLocation: DOUBLE, var yLocation: DOUBLE,
                                                       var appleStock: DOUBLE, val agressionLevel: DOUBLE,
                                                       val species: ScrumperTypes) : IAgent {

    var paralysisTimer = 0
    var attackRange = species.visionRange / 2.0

    var localTrees = listOf<AppleTree<DOUBLE>>()
    var localScrumpers = listOf<AppleScrumper<DOUBLE>>()

    override fun step() {

        // Check if paralysed, if so decrement the paralysis
        if (paralysisTimer != 0) {
            paralysisTimer -= 1
        } else {
            // Check what's around
            var surroundings = world.lookAround(xLocation, yLocation, species.visionRange)
            localTrees = world.Perception.appleTrees
            localScrumpers = world.Perception.appleScrumpers
        }
    }


    fun assessPriorities() {
        // If there's some things there, determine priority
        if (!localTrees.isEmpty() && !localScrumpers.isEmpty()) {

        } else if (!localTrees.isEmpty()) {
            var scrumperDistance = getDistance(appleScrumper.xLocation, appleScrumper.yLocation)

        } else if (!localScrumpers.isEmpty()) {

        }

    }


    fun pickApples(appleTree: AppleTree<DOUBLE>) {
        if (appleTree.appleCount > 1.0) {
            appleTree.appleCount -= 1.0
            appleStock += 1.0
        } else {
            // Pop that tree of the list and reassess priorities
            localTrees = localTrees.subList(1, localTrees.size)
            assessPriorities()
        }
    }

    fun attackScrumper(appleScrumper: AppleScrumper<DOUBLE>) {
        // if the scrumper isn't paralysed, attack them! (+10 to their paralysis timer
    }

    fun moveTowards() {
        // move at the species' speed toward the objective
    }

    fun getDistance(otherX: DOUBLE, otherY: DOUBLE): DOUBLE {
        var xDifference2 = (xLocation - otherX).pow(2.0)
        var yDifference2 = (yLocation - otherY).pow(2.0)
        return (xDifference2 + yDifference2).pow(0.5)
    }


}