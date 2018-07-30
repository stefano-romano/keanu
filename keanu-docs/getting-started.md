## Getting Started

### Simple Example

If you're not familiar with the wet grass example then read this article on 
[Bayesian Networks](https://en.wikipedia.org/wiki/Bayesian_network). 

If you have some prior belief that it has rained (20% chance), and some prior belief that a
water sprinkler was on (1% chance if it rained and 40% chance if it has not rained) then if
you observe that a patch of grass near the water sprinkler is wet then what is the probability
that it rained?

Here is how you'd express this in Keanu.

```java
public class WetGrass {

    public static void main(String[] args) {

        //There's a simple 20% chance of rain and for the purposes
        //of this example, that doesn't depend on any other variables.
        BoolVertex rain = new Flip(0.2);

        //The probability of the sprinkler being on is dependent on
        //whether or not it has rained. It's very unlikely that the
        //sprinkler comes on if it's raining.
        BoolVertex sprinkler = new Flip(
            If.isTrue(rain)
                .then(0.01)
                .orElse(0.4)
        );

        //The grass being wet is dependent on whether or not it rained or
        //the sprinkler was on.
        BoolVertex wetGrass = new Flip(
            ConditionalProbabilityTable.of(sprinkler, rain)
                .when(false, false).then(0.001)
                .when(false, true).then(0.8)
                .when(true, false).then(0.9)
                .orDefault(0.99)
        );

        //We observe that the grass is wet
        wetGrass.observe(true);

        //What does that observation say about the probability that it rained or that
        //the sprinkler was on?
        NetworkSamples posteriorSamples = MetropolisHastings.getPosteriorSamples(
            new BayesianNetwork(wetGrass.getConnectedGraph()),
            Arrays.asList(sprinkler, rain),
            100000
        ).drop(10000).downSample(2);

        double probabilityOfRainGivenWetGrass = posteriorSamples.get(rain).probability(isRaining -> isRaining.scalar() == true);

        System.out.println(probabilityOfRainGivenWetGrass);
    }
}
```

## Building a new model with Keanu

You should use the [Keanu starter project](https://github.com/improbable-research/keanu-starter)
 as a starting point for building new models on Keanu. The starter project includes the recommended file layout as 
 well as a properly configured build script using [Gradle](https://gradle.org/)

To create a new project from the starter project simply run the following command:
```
git clone --depth 1 https://github.com/improbable-research/keanu-starter.git
```

This clones the Keanu starter repo.

Now that you have the starter project, head over to [describing your model](./docs/overview.md) to get started.

## Using Keanu in an existing model (JVM based)

Keanu can be used with any build system that can pull artifacts from Maven Central (i.e. gradle, maven).

If you would like to start using Keanu in an existing project, simply add Keanu as a dependency 
in your gradle or maven build file.

#### Gradle

In your project's `build.gradle`

```
compile group: 'io.improbable', name: 'keanu', version: '0.0.10'
```

#### Maven

In your project's `pom.xml`

```
<dependency>
    <groupId>io.improbable</groupId>
    <artifactId>keanu</artifactId>
    <version>0.0.10</version>
</dependency>
```
