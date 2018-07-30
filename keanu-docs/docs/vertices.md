## Vertices

There are several families of vertices. Each family shares a common value type.

### Probabilistic

Probabilistic vertices are vertices that do not depend completely on their parent vertices. An example
of this is a vertex that acts as a probability distribution like the GaussianVertex.

Changing the value of their parent vertices may change the density (from pdf) at their value but it 
will not change the value of the vertex.

### Non-Probabilistic

The value of these vertices are completely dependent on their parent vertices values. For example,
given A * B = C, C is a non-probabilistic vertex. A or B might be probabilistic vertices, which would
make C not a constant value but still completely dependent on A and B.

### The Double Family

A DoubleVertex can be used by most arithmetic operators. They can be used to describe a problem
that can be solved using gradient assent optimization and their value is a double. 

The currently available double vertices are
- [Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/dbl/probabilistic/package-summary.html)
- [Non-Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/dbl/nonprobabilistic/package-summary.html)

### The Integer Family

An IntegerVertex is similar to the DoubleVertex except its value is an integer.

The currently available integer vertices are
- [Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/intgr/probabilistic/package-summary.html)
- [Non-Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/intgr/nonprobabilistic/package-summary.html)

### The Boolean (true/false) Family

A BoolVertex can be used by most boolean operators. These can be observed directly and used in MCMC.

The currently available boolean vertices are
- [Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/bool/probabilistic/package-summary.html)
- [Non-Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/bool/nonprobabilistic/package-summary.html)

### The Generic (everything else) family

These are the vertices that can have any type as a value. For example, this type can be an Enum or any user defined object.
Let's look at an example of this in Keanu with the `SelectVertex` which will return a value of the specified Enum `MyType`.

```java
    public enum MyType {
        A, B, C, D
    }

    public SelectVertex<MyType> getSelectorForMyType() {

        LinkedHashMap<MyType, DoubleVertex> frequency = new LinkedHashMap<>();
        frequency.put(A, new ConstantDoubleVertex(0.25));
        frequency.put(B, new ConstantDoubleVertex(0.25));
        frequency.put(C, new ConstantDoubleVertex(0.25));
        frequency.put(D, new ConstantDoubleVertex(0.25));

        return new SelectVertex<MyType>(frequency);
    }
```

The getSelectorForMyType() method would return a probabilistic vertex that would contain an 
object of type MyType A, B, C or D, 25% of the time respectively.

The currently available generic vertices are
- [Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/generic/probabilistic/package-summary.html)
- [Non-Probabilistic](https://static.javadoc.io/io.improbable/keanu/0.0.10/io/improbable/keanu/vertices/generic/nonprobabilistic/package-frame.html)


### Tensors

Vertices also have a `shape`, which describes the tensor shape contained within them. A vertex with shape
[2,2] represents a matrix of 2 by 2. A vertex of shape [1,3] represents a row vector of length 3. The shape
can be of any amount of dimensions and length.

Read more about tensors [here](06-tensors.md) 