## Reading data into and out of Keanu

This page tells you how to:
- Load a csv data-set into your model
- Save vertex values to csv
- Save samples to csv

### Reading CSV data

Keanu is packaged with a csv reader that allows you to load simple csv files with or without
a header. You can read from a specified file, predefined string or a file on the class path.

We will go through the steps on how to read from a file using a CsvReader and then how to
extract and parse the lines from the reader.

#### Creating a CsvReader from your csv data
To read from a file from your current directory:

```java
CsvReader reader = ReadCsv.fromFile("~/my_filename.csv");

```

If you place the csv file in your src/main/resources folder, you can load it as a resource,
which you would do in order to avoid having to provide a hardcoded file path.

```java
CsvReader reader = ReadCsv.fromResources("my_filename.csv");

```

Once you have a CsvReader, you can call readLines() to start reading each line.
```java
for (List<String> csvLine : csvReader.readLines()) {
    //do something with your csv line
}
```

#### Large CSV files

If you csv is very large, you may not want to load the entire csv file into memory before
processing it. You can stream the lines in order to avoid holding the entire file in memory.

Once you have a CsvReader, you can call streamLines() to start streaming each line. Make sure
to close the stream as it is potentially connected to an open file. Closing the stream can be
done by using try-with-resources, which is shown below.

```java
try (Stream<List<String>> lineStream = csvReader.streamLines()) {
   
    lineStream.forEach(line -> {
       //do something with your line. 
    });
}
```

### Reading CSV as a plain old java object (POJO)

If you don't feel like processing the raw lines from your CSV file, then you 
have the option to read directly to a Java object.

Given a POJO

```java
public class MyClass {
    public String myString;
    public int myInt;
}
```

And some CSV

```
myString,myInt
aString,1
bString,2
cString,3
```

The CSV can be loaded as a Java object by

```java
List<MyClass> myPojos = ReadCsv.fromFile("some/file/path")
        .asRowsDefinedBy(MyClass.class)
        .load();
```

If your csv header names contain illegal characters you have the option to
tag the field with a Java annotation.

Given CSV

```
my-problematic*header,myInt
aString,1
bString,2
cString,3
```

Annotate your class with `@CsvProperty` and the problematic header title.

```java
public class MyOtherClass {
    @CsvProperty("my-problematic*header")
    public String myString;
    public int myInt;
}
```

The CSV parser also supports using setter methods.

```java
public class MyClass {
    private String myString;
    
    public void setMyString(String aString){
        ...
    }
}
```


### Reading into a model

Take a look at [Diabetes Linear Regression](https://github.com/improbable-research/keanu/blob/develop/keanu-project/src/test/java/io/improbable/keanu/e2e/regression/DiabetesLinearRegression.java) 
for a complete example of reading csv data into a model in Keanu.

## Writing out data

Keanu is packaged with a csv writer that allows you to write to csv. This is useful for writing the values stored in vertices
or samples to a file.

### Creating a CsvWriter from vertices

This section assumes knowledge of tensors, please look [here](https://nd4j.org/userguide#intro) to familiarise yourself with them first. 
Only scalar or vector tensors are supported in the csv writer at the moment.

Let's look at the different ways we can write the values stored in vertices to csv, but first we have to decide which format to write out in.

* Column format: each vertex occupies a column in the csv file, with each row denoting the index of the vector.
* Row format:  each vertex occupies a row, with each column denoting the index of the vector.

Let's look at an example of each.

Say that we have ran inference on a model and would like to write the resulting values to csv and 
that the list of inferred variables contains the following three vertices.

```java
List<DoubleVertex> inferredVariables = runMyModel();

inferredVariables.get(0); // [0.5, 1.0, 1.5, 2.0]
inferredVariables.get(1); // [5.0, 10.0, 15.0, 20.0]
inferredVariables.get(2); // [50.0, 100.0, 150.0]
```

Let's write these as columns to a file called columnTest.csv in the root directory.

```java
File file = WriteCsv.asColumns(inferredVariables).toFile("columnTest.csv");
```

The csv file columnTest.csv contains:

```text
0.5,5.0,50.0
1.0,10.0,100.0
1.5,15.0,150.0
2.0,20.0,-
```

Let's write these as rows to a file called rowTest.csv in the root directory.

```java
File file = WriteCsv.asRows(inferredVariables).toFile("rowTest.csv");
```

The csv file rowTest.csv contains:

```text
0.5,1.0,1.5,2.0
5.0,10.0,15.0,20.0
50.0,100.0,150.0,-
```

### Default headers

It's quite difficult trying to decipher and remember what the columns contain without a header. 
Fortunately, the csv writer provides default headers that provide information about each column.
Let's re-run the above examples but using the default headers provided in the csv writer.

As columns.

```java
CsvWriter writer = WriteCsv.asColumns(inferredVariables).withDefaultHeader().toFile("columnTest.csv");
```

The default header sets the column name to the vertex ID.

```text
20, 21, 22
0.5,5.0,50.0
1.0,10.0,100.0
1.5,15.0,150.0
2.0,20.0,-
```

As rows.

```java
File file = WriteCsv.asRows(constants).withDefaultHeader().toFile("rowTest.csv");
```

The default header sets the column name to the index.

```text
[0], [1], [2], [3]
0.5,1.0,1.5,2.0
5.0,10.0,15.0,20.0
50.0,100.0,150.0,-
```

### Creating a CsvWriter from samples

Let's look at how you can write samples to csv. 
First, you'll need some samples, let's take some from a network using the NUTS algorithm. 
To learn more about the parameters being used here, head over to the [NUTS documentation](07-inference-posterior-sampling.md).

```java
NetworkSamples samples = NUTS.getPosteriorSamples(
            aVertex,
            aVertex.getLatentVertices(),
            1000,
            50,
            0.65,
            random
        );
```

Now, we want to save these for analysis later on. How do we write these to csv?
We recommend you write samples to file with the default csv header.

```java
File file = WriteCsv.asSamples(samples, aVertex).withDefaultHeader().toFile("test.csv");
```

These are written to csv in the format of "{vertex ID}[{index}]", with each row representing a sample.

```java
2[0], 2[1], 2[2], 2[3], 2[4], 5[0], 5[1], 5[2], 5[3]
1.0,2.0,3.0,4.0,5.0,5.0,4.0,3.0,2.0
2.0,4.0,6.0,8.0,10.0,10.0,8.0,6.0,4.0
2.5,5.0,7.5,10.0,12.5,12.5,10.0,7.5,5.0
3.0,6.0,9.0,12.0,15.0,15.0,12.0,9.0,6.0
```
