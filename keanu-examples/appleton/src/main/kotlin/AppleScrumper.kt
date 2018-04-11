import io.improbable.keanu.kotlin.DoubleOperators

class AppleScrumper<DOUBLE : DoubleOperators<DOUBLE>> (val world: World, var xLocation: DOUBLE, var yLocation: DOUBLE,
                                                       hunger: DOUBLE, val agressionLevel: DOUBLE,
                                                       val species: ScrumperTypes) : IAgent {

    override fun step() {

        // Check what's around
        var surroundings = world.lookAround(species.visionRange)

        // If there's some things there, determine priority


            // If not in range to action that priority

                // Move toward

            // Else action the priority (attack competitor or pick apples)

        // Else continue in previous direction of travel

    }


}