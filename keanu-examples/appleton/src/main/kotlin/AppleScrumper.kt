import io.improbable.keanu.kotlin.DoubleOperators

class AppleScrumper<DOUBLE : DoubleOperators<DOUBLE>> (val world: World<DOUBLE>, var xLocation: DOUBLE, var yLocation: DOUBLE,
                                                       var appleStock: DOUBLE, val agressionLevel: DOUBLE,
                                                       val species: ScrumperTypes, val math: ModelMath<DOUBLE>) : IAgent {

    var paralysisTimer = 0

    var localTrees = listOf<AppleTree<DOUBLE>>()
    var localScrumpers = listOf<AppleScrumper<DOUBLE>>()

    // TODO do we need a memory of paralysed scrumpers and empty trees to ensure against just hovering around dud objects each step?

    override fun step() {
        // Check if paralysed, if so decrement the paralysis
        if (paralysisTimer != 0) {
            paralysisTimer -= 1
        } else {
            // Check what's around
            var surroundings = world.lookAround(xLocation, yLocation)
            localTrees = surroundings.appleTrees
            localScrumpers = surroundings.appleScrumpers
        }
    }


    fun assessPriorities() {
        // If there's some things there, determine priority
        // If the object is within one step's travel distance then you can attack or pick...
        if (!localTrees.isEmpty() && !localScrumpers.isEmpty()) {
            prioritiseTrees(localTrees[0])
        } else if (!localTrees.isEmpty()) {
            prioritiseTrees(localTrees[0])
        } else if (!localScrumpers.isEmpty()) {
            prioritiseScrumpers(localScrumpers[0])
        }
    }


    fun prioritiseTrees(appleTree: AppleTree<DOUBLE>) {
        var treeDistance = getDistance(appleTree.xLocation, appleTree.yLocation)
        if (treeDistance < species.speed * 1.0) {
            pickApples(appleTree)
        } else {
            moveTowards(appleTree.xLocation, appleTree.yLocation)
        }

    }


    fun prioritiseScrumpers(appleScrumper: AppleScrumper<DOUBLE>) {
        var scrumperDistance = getDistance(appleScrumper.xLocation, appleScrumper.yLocation)
        if (scrumperDistance < species.speed * 1.0) {
            attackScrumper(appleScrumper)
        } else {
            moveTowards(appleScrumper.xLocation, appleScrumper.yLocation)
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
        if (appleScrumper.paralysisTimer == 0) {
            appleScrumper.paralysisTimer += 10
        } else {
            // Pop that scrumper off the list and reassess priorities
            localScrumpers = localScrumpers.subList(1, localScrumpers.size)
        }
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
        return listOf(xDifference/distance, yDifference/distance)
    }


    fun getDirection(otherX: Double, otherY: Double): List<DOUBLE> {
        var xDifference = xLocation - otherX
        var yDifference = yLocation - otherY
        var distance = getDistance(otherX, otherY)
        return listOf(xDifference/distance, yDifference/distance)
    }
}