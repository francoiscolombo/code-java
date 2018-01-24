## Rest API sample

### What is this?

this is a very simple sample of how to code a REST api in Java, with usage of two
very lights frameworks: [Spark](http://sparkjava.com) for the REST api and [Nitrite](http://www.dizitart.org/nitrite-database) for the persistence.

### How to build the .jar?

if your **JAVA_HOME** is not set, set it.

clean the project:

    $ ./gradlew clean

    BUILD SUCCESSFUL in 1s
    1 actionable task: 1 up-to-date

run the gradle **fatJar** task:

    $ ./gradlew fatJar
    
    BUILD SUCCESSFUL in 4s
    2 actionable tasks: 2 executed

you will find the resulting jar in the directory:

    $ ls -lh build/libs/
    total 5.7M
    -rw-rw-r-- 1 acolombo acolombo 5.7M Jan 24 15:07 user-manager-1.0-SNAPSHOT.jar

### How to run?

Simply run the jar or directly the class `com.fc.samples.restservice.UserManagement`.

    java -jar build/libs/user-manager-1.0-SNAPSHOT.jar 
    [main] INFO org.eclipse.jetty.util.log - Logging initialized @157ms to org.eclipse.jetty.util.log.Slf4jLog
    [Thread-1] INFO spark.embeddedserver.jetty.EmbeddedJettyServer - == Spark has ignited ...
    [Thread-1] INFO spark.embeddedserver.jetty.EmbeddedJettyServer - >> Listening on 0.0.0.0:8080
    [Thread-1] INFO org.eclipse.jetty.server.Server - jetty-9.4.z-SNAPSHOT
    [Thread-1] INFO org.eclipse.jetty.server.session - DefaultSessionIdManager workerName=node0
    [Thread-1] INFO org.eclipse.jetty.server.session - No SessionScavenger set, using defaults
    [Thread-1] INFO org.eclipse.jetty.server.session - Scavenging every 600000ms
    [Thread-1] INFO org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@5b641bea{HTTP/1.1,[http/1.1]}{0.0.0.0:8080}
    [Thread-1] INFO org.eclipse.jetty.server.Server - Started @1018ms
 
to stop the server, just hit `CTRL + C`

### How to call the API from the command line?

##### add an account

    $ curl -i -X POST http://localhost:8080/api/user/add -d 'account=fcolombo&password=mypassword&name=Francois%20Colombo&email=francois.colombo%40nestle.com&description=cool%20guy'

    HTTP/1.1 201 Created
    Date: Wed, 24 Jan 2018 13:31:43 GMT
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(9.4.6.v20170531)
    Proxy-Connection: keep-alive
    Connection: keep-alive
    
    {"account":"fcolombo","password":"mypassword","name":"Francois Colombo","email":"francois.colombo@nestle.com","description":"cool guy"}⏎                                                                                                                              

##### search for an existing account

    $ curl -i -X GET http://localhost:8080/api/user/account/fcolombo

    HTTP/1.1 200 OK
    Date: Wed, 24 Jan 2018 13:31:57 GMT
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(9.4.6.v20170531)
    Proxy-Connection: keep-alive
    Connection: keep-alive
    
    {"account":"fcolombo","password":"mypassword","name":"Francois Colombo","email":"francois.colombo@nestle.com","description":"cool guy"}⏎ 

##### change data on an existing account

you must give an existing account. all the others fields can be changed.

    $ curl -i -X PUT http://localhost:8080/api/user/change -d 'account=fcolombo&password=mynewpassword&name=Francois%20Colombo&email=francois.colombo%40nestle.com&description=not%20so%20cool%20guy'
    
    HTTP/1.1 201 Created
    Date: Wed, 24 Jan 2018 13:32:06 GMT
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(9.4.6.v20170531)
    Proxy-Connection: keep-alive
    Connection: keep-alive
    
    {"account":"fcolombo","password":"mynewpassword","name":"Francois Colombo","email":"francois.colombo@nestle.com","description":"not so cool guy"}⏎

##### list all the accounts

    $ curl -i -X GET http://localhost:8080/api/user/list
    
    HTTP/1.1 200 OK
    Date: Wed, 24 Jan 2018 13:32:18 GMT
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(9.4.6.v20170531)
    Proxy-Connection: keep-alive
    Connection: keep-alive
    
    [{"account":"fcolombo","password":"mynewpassword","name":"Francois Colombo","email":"francois.colombo@nestle.com","description":"not so cool guy"}]⏎

##### remove an account

    $ curl -i -X DELETE http://localhost:8080/api/user/remove -d 'account=fcolombo'
    
    HTTP/1.1 200 OK
    Date: Wed, 24 Jan 2018 13:38:50 GMT
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(9.4.6.v20170531)
    Proxy-Connection: keep-alive
    Connection: keep-alive
    
    {"message":"account fcolombo removed"}⏎

##### what happens if you search, update or remove an unexisting account?

    $ curl -i -X PUT http://localhost:8080/api/user/change -d 'account=fcolombo&password=mynewpassword&name=Francois%20Colombo&email=francois.colombo%40nestle.com&description=not%20so%20cool%20guy'
    HTTP/1.1 404 Not Found
    Date: Wed, 24 Jan 2018 13:39:21 GMT
    Content-Type: application/json
    Transfer-Encoding: chunked
    Server: Jetty(9.4.6.v20170531)
    Proxy-Connection: keep-alive
    Connection: keep-alive
    
    {"message":"404: account fcolombo not found"}⏎

you just get an error 404: user not found.

##### how to stop properly the server?
                                                                                 
    $ curl -i -X GET http://localhost:8080/api/service/stop
    curl: (52) Empty reply from server

be careful, however. this is definitively NOT something you will do in a real project. this
is only here as a sample, remember!

### What are the classes used by this application?

For this sample, all the classes are in the same package, but you should choose a better structure 
on a real project.

| Classe | Description |
| ----- | ----- |
| **RequestLogFactory**, **EmbeddedJettyFactoryConstructor**, **SparkUtils**| these classes allow you to pass a SLF4j logger to the embedded Jetty |
| **Message** | simple classe that allow you to produce a message to send back by the Rest API |
| **User** | this is the Pojo class that describe our user. |
| **UsersApi** | this class implements the actions published by the API |
| **UserManagement** | main class, define some parameters for the server, then listen to the routes. |

this is a very simple sample, and it misses a lot of things, but you can see how easy it is to implement
REST api with Java 8.