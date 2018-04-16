import io.improbable.keanu.kotlin.BooleanOperators
import io.improbable.keanu.kotlin.DoubleOperators

class AppleScrumper<DOUBLE : DoubleOperators<DOUBLE>, BOOLEAN_IN, BOOLEAN : BooleanOperators<BOOLEAN_IN, BOOLEAN>>(
        val world: World<DOUBLE, BOOLEAN_IN, BOOLEAN>, var xLocation: DOUBLE, var yLocation: DOUBLE, var appleStock: DOUBLE,
        val agressionLevel: DOUBLE, val species: ScrumperTypes, val math: KeanuMath<DOUBLE, BOOLEAN_IN, BOOLEAN>,
        val flow: KeanuControlFlow<BOOLEAN_IN, BOOLEAN>) : IAgent {

    var paralysisTimer = 0

    var localTrees = listOf<AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>>()
    var localScrumpers = listOf<AppleScrumper<DOUBLE, BOOLEAN_IN, BOOLEAN>>()

    // TODO do we need a memory of paralysed scrumpers and empty trees to ensure against just hovering around dud objects each step?

    override fun step() {
        // Check if paralysed, if so decrement the paralysis
        flow.If(paralysisTimer != 0, {
            paralysisTimer -= 1
        }, _else = {
            // Check what's around
            var surroundings = world.lookAround(xLocation, yLocation, species.visionRange)
            localTrees = surroundings.appleTrees
            localScrumpers = surroundings.appleScrumpers
            assessPriorities()
        })

        println("SCRUMP!")
    }


    fun assessPriorities() {
        // If there's some things there, determine priority
        // If the object is within one step's travel distance then you can attack or pick...
        flow.If(!localTrees.isEmpty() && !localScrumpers.isEmpty(), {
            prioritiseTrees(localTrees[0])
        }, _else = {
            flow.If(!localTrees.isEmpty(), {
                prioritiseTrees(localTrees[0])
            }, _else = {
                prioritiseScrumpers(localScrumpers[0])
            })
        })
    }


    fun prioritiseTrees(appleTree: AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>) {
        var treeDistance = getDistance(appleTree.xLocation, appleTree.yLocation)
        flow.If(math.lessThan(treeDistance, species.speed), {
            pickApples(appleTree)
        }, _else = {
            moveTowards(appleTree.xLocation, appleTree.yLocation)
        })

    }


    fun prioritiseScrumpers(appleScrumper: AppleScrumper<DOUBLE, BOOLEAN_IN, BOOLEAN>) {
        var scrumperDistance = getDistance(appleScrumper.xLocation, appleScrumper.yLocation)
        flow.If(math.lessThan(scrumperDistance, species.speed), {
            attackScrumper(appleScrumper)
        }, _else = {
            moveTowards(appleScrumper.xLocation, appleScrumper.yLocation)
        })

    }


    fun pickApples(appleTree: AppleTree<DOUBLE, BOOLEAN_IN, BOOLEAN>) {
        flow.If(math.greaterThan(appleTree.appleCount, 1.0), {
            appleTree.appleCount -= 1.0
            appleStock += 1.0
        }, _else = {
            // Pop that tree of the list and reassess priorities
            localTrees = localTrees.subList(1, localTrees.size)
            assessPriorities()
        })
    }


    fun attackScrumper(appleScrumper: AppleScrumper<DOUBLE, BOOLEAN_IN, BOOLEAN>) {
        // if the scrumper isn't paralysed, attack them! (+10 to their paralysis timer
        flow.If (appleScrumper.paralysisTimer == 0, {
            appleScrumper.paralysisTimer += 10
        }, _else = {
            // Pop that scrumper off the list and reassess priorities
            localScrumpers = localScrumpers.subList(1, localScrumpers.size)
            assessPriorities()
        })
    }


    fun moveTowards(x: DOUBLE, y: DOUBLE) {
        // move at the species' speed toward the objective
        var direction = getDirection(x, y)
        var xLocation = direction[0] * species.speed
        var yLocation = direction[1] * species.speed
    }

    fun moveTowards(x: Double, y: Double) {
        // move at the species' speed toward the objective
        var direction = getDirection(x, y)
        var xLocation = direction[0] * species.speed
        var yLocation = direction[1] * species.speed
    }


    fun getDistance(otherX: DOUBLE, otherY: DOUBLE): DOUBLE {
        var xDifference2 = math.pow(xLocation - otherX, 2.0)
        var yDifference2 = math.pow(yLocation - otherY, 2.0)
        return math.pow(xDifference2 + yDifference2, 0.5)
    }


    fun getDistance(otherX: Double, otherY: Double): DOUBLE {
        var xDifference2 = math.pow(xLocation - otherX, 2.0)
        var yDifference2 = math.pow(yLocation - otherY, 2.0)
        return math.pow(xDifference2 + yDifference2, 0.5)
    }


    fun getDirection(otherX: DOUBLE, otherY: DOUBLE): List<DOUBLE> {
        var xDifference = xLocation - otherX
        var yDifference = yLocation - otherY
        var distance = getDistance(otherX, otherY)
        return listOf(xDifference / distance, yDifference / distance)
    }


    fun getDirection(otherX: Double, otherY: Double): List<DOUBLE> {
        var xDifference = xLocation - otherX
        var yDifference = yLocation - otherY
        var distance = getDistance(otherX, otherY)
        return listOf(xDifference / distance, yDifference / distance)
    }
}
