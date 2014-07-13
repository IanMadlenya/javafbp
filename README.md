JavaFBP
===

Java Implementation of Flow-Based Programming (FBP)


General

---

In computer programming, flow-based programming (FBP) is a programming paradigm that defines applications as networks of "black box" processes, which exchange data across predefined connections by message passing, where the connections are specified externally to the processes. These black box processes can be reconnected endlessly to form different applications without having to be changed internally. FBP is thus naturally component-oriented.

FBP is a particular form of dataflow programming based on bounded buffers, information packets with defined lifetimes, named ports, and separate definition of connections.


Building
---

The project requires Maven for building (testing with version 3.2.2). For building the project simply run the followng command:

    mvn package

As a result a `fbp-1.0-SNAPSHOT.jar` file will be created in the `./target` directory. It will include the JavaFBP core (runtime) and all the examples from the source code (sub-package `com.jpmorrsn.fbp.examples`). 

For running any of the examples use the following command:

    java -cp target/fbp-1.0-SNAPSHOT.jar com.jpmorrsn.fbp.examples.networks.<Class name of the network>

For example:

    java -cp target/fbp-1.0-SNAPSHOT.jar com.jpmorrsn.fbp.examples.networks.TestIPCounting

