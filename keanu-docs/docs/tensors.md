# Tensor rank and shape

Tensors in most cases can be thought of as just a nested array of values that can have any number
of dimensions. A tensor with two dimensions can be thought of as a matrix, a tensor
with a single dimension is a vector and a tensor with three dimensions can be thought of as a cube. 
The number of dimensions a tensor has is called its `rank` and the length in each dimension 
describes its `shape`.

For example, a 2 by 3 matrix:

```
1 2 3
4 5 6
```

has a `rank` of `2`, a `shape` of `[2, 3]` and a `length` of 6. 

# Tensors in Keanu

Tensors can be extremely powerful as a way to represent large data sets or a way to very efficiently do the same
operation on many different pieces of data. This is because tensor operations can be done on the GPU.

For example, if we have two lists of numbers and some observation on their product then it's much more efficient
and much cleaner to describe this using tensors.


## Creating Tensors

Nearly everything in Keanu supports Tensors. But how do you create one?

Let's create a vector of doubles, integers and booleans that share the same value.

```java
DoubleTensor dTensor = DoubleTensor.create(5, new int[]{2, 2});      //[5, 5, 5, 5]

IntegerTensor iTensor = IntegerTensor.create(1, new int[]{2, 2});    //[1, 1, 1, 1]

BooleanTensor bTensor = BooleanTensor.create(true, new int[]{2, 2}); //[true, true, true, true]
```


Let's make a 2x2 matrix of doubles, integers and booleans.

```java
DoubleTensor dTensor = DoubleTensor.create(new double[]{0.5, 1.5, 2.5, 3.5}, new int[]{2, 2});

IntegerTensor iTensor = IntegerTensor.create(new int[]{1, 2, 3, 4}, new int[]{2, 2});

BooleanTensor bTensor = BooleanTensor.create(new boolean[]{true, true, false, false}, new int[]{2, 2});
```

Want to change the shape of your tensor on the fly? You have to make sure the proposed shape is the same 
length as the original. For example, you can change a 2x2 tensor to a 1x4 tensor. But you can't change a 2x2 tensor
to a 2x3 tensor.

Here's how to do that in Keanu:

```java
DoubleTensor tensor = DoubleTensor.create(new double[]{0.5, 1.5, 2.5, 3.5}, new int[]{2, 2});
tensor.getShape();       //[2, 2]
tensor.reshape(1, 4);
tensor.getShape();       //[1, 4]
```

## Tensor Operations

What operations can I do on these tensors?

Here's a small example of the power of tensors, all of these operations apply to each value in the tensor.

```java
DoubleTensor tensor = DoubleTensor.create(new double[]{1, 2, 3, 4}, new int[]{2, 2});
tensor.plus(1.0);           // [2, 3, 4, 5]
tensor.times(2.0);          // [4, 6, 8, 10]
tensor.pow(2);              // [16, 36, 64, 100]
tensor.sin();               // [-0.2879, -0.9918, 0.9200, -0.5064]
double sum = tensor.sum();  // -0.86602...
```

A complete list of tensor operations is available here:
- [Double Tensor](https://static.javadoc.io/io.improbable/keanu/0.0.9/io/improbable/keanu/tensor/dbl/DoubleTensor.html)
- [Integer Tensor](https://static.javadoc.io/io.improbable/keanu/0.0.9/io/improbable/keanu/tensor/intgr/IntegerTensor.html)
- [Boolean Tensor](https://static.javadoc.io/io.improbable/keanu/0.0.9/io/improbable/keanu/tensor/bool/package-frame.html)
- [Generic Tensor](https://static.javadoc.io/io.improbable/keanu/0.0.9/io/improbable/keanu/tensor/generic/package-frame.html)


## Creating Vertices with Tensors

Let's say I want to create a vector of 100 Gaussian's all with a mu of 0 and a sigma of 1.
This is how you do that in Keanu:

```java
GaussianVertex vertex = new GaussianVertex(new int[]{1, 100}, 0, 1);
```

That's all there is to it. But where are those 100 Gaussians stored? I thought a vertex had only a single value?

That's not true. This vertex's value is a vector of size 100.

```java
DoubleTensor samples = vertex.sample();
samples.getShape();         //[1, 100]
samples.getLength();        //100
samples.getValue(0, 50);    //Returns the value at the 50th position
```

But what if I want to create a Gaussian where each value has a different value of mu or sigma? Let's do that.

If we want the Gaussian to store a vector values then the parameters, mu and sigma, must either be a scalar
or a vector of the same size. Why? 

If they are a scalar then that value is shared across the entire vertex. If we want a vector of parameters then
the size must match the shape of the gaussian so we can match each vertex to a parameter.

This is how to make a vector of Gaussian's that looks like this:

```java
[Gaussian(mu:1, sigma:0)]
[Gaussian(mu:2, sigma:0)]
[Gaussian(mu:3, sigma:0)]
```

And in Keanu:

```java
int[] shape = new int[]{3, 1};
DoubleVertex mu = new ConstantDoubleVertex(new double[]{1, 2, 3});
GaussianVertex vertex = new GaussianVertex(shape, mu, 0);
```  


# Example of Tensors

Without using tensors we have to iterate over the data and aggregate some results. 
```
a = unknown number around 0.5
b = unknown number around 1.5

c = 3
d = 4

observe that a * c = 6
observe that b * d = 12
```

or with tensors we could describe this as

```
a = [unknown ~0.5, unknown ~1.5]
b = [3, 4]
observe a * b = [6, 12]
```

This is both more efficient computation-wise and more a more succinct way to describe the problem. In Keanu
the above problem would be written as:

```
DoubleVertex muA = ConstantVertex.of(new double[]{0.5, 1.5});
DoubleVertex A = new GaussianVertex(new int[]{1, 2}, muA, 1);
DoubleVertex B = ConstantVertex.of(new double[]{3, 4});

DoubleVertex C = A.times(B);
DoubleVertex CObservation = new GaussianVertex(C, 1);
CObservation.observe(new double[]{6, 12});

//Use algorithm to find MAP or posterior samples for A and/or B
GradientOptimizer optimizer = new GradientOptimizer(new BayesianNetwork(A.getConnectedGraph()));
optimizer.maxLikelihood();

//A.getValue() is the MLE, which is approximately [2, 3]
```