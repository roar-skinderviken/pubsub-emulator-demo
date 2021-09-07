# pubsub-emulator-demo
A Micronaut 3.0.0 implementation of nhartner's Spring Boot pubsub-emulator-demo: https://github.com/nhartner/pubsub-emulator-demo

This repo is also serving the purpose of getting team members in a current project up-and-running on Micronaut 3, hence the step-by-step approach below.

Tools using the creation of this repo:
- JDK 11 or higher
- Maven 3.x
- IntelliJ

We'll start as always at https://micronaut.io/launch. Use the following settings on the front page:
- **Application Type** = Micronaut Application
- **Java Version** = 11
- **Name** = pubsub-emulator-demo
- **Micronaut Version** = 3.0.0
- **Language** = Java
- **Build Tool** = Maven
- **Test Framework** = JUnit

Then click **Features** and select the following features:
- http-client
- testcontainers
- assertj
- lombok
- logback
- gcp-pubsub
- google-cloud-function

Select _Generate Project_ | _Download Zip_, and save the exploded zip to your project folder.

You should now be able to run
```
mvn function:run
``` 
in the project root, and _http://localhost:8080/pubsubEmulatorDemo_ should respond with _Example Response_.

### Adding Pubsub emulator support

#### Config
We'll need to pass environment variable `PUBSUB_EMULATOR_HOST` to our pubsub integration tests. This can be done 
using `maven-surefire-plugin`. Add the following to the POM in the plugins section:
```      
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <configuration>
      <environmentVariables>
        <PUBSUB_EMULATOR_HOST>localhost:8085</PUBSUB_EMULATOR_HOST>
      </environmentVariables>
    </configuration>
</plugin>
```
Add this to ``application.yml``:
```
gcp:
  project-id: test-project

pub-sub:
  emulator-host: ${PUBSUB_EMULATOR_HOST}
  topic-name: topic-test
  subscription-names:
    - subscription-one
    - subscription-two
```
#### Config Property Classes
`GcpConfigProperties` and `PubSubConfigProperties` inside package `com.example.configuration` are for accessing properties inside `application.yml`.
Classes are annotated with Lombok `@Data` in order to avoid getters/setters.

#### Implementation
Except for addition of a more complex subscriber, content in package `com.example.pubsub` is basically a rewrite/refactoring of _nhartner's_ code.

#### Running PubSubIntegrationTests
Run 
```
mvn clean test
``` 

You should see this in the console:
```
DemoListenerWithAck received ID: 1 Message: Hello 1
DemoListenerWithAck received ID: 2 Message: Hello 2
DemoListenerWithAck received ID: 3 Message: Hello 3
DemoListenerWithAck received ID: 4 Message: Hello 4
DemoListenerWithAck received ID: 5 Message: Hello 5
DemoListenerWithAck received ID: 6 Message: Hello 6
DemoListenerWithAck received ID: 7 Message: Hello 7
DemoListenerWithAck received ID: 8 Message: Hello 8
DemoListenerWithAck received ID: 9 Message: Hello 9
DemoListenerWithAck received ID: 10 Message: Hello 10
```

### What we have so far
- `DemoController` delegates payload processing to `ControllerService`
- `ControllerService` may do some pre-processing and then publishes the payload using `DemoPublisher`
- Payload is received by `DemoListenerWithAck` which delegates processing to `ReceiverService`
- Payload is also received by `SimpleDemoListener` which does nothing but printing some info to the output 

### Playing with the app with e.g. Postman

1. Configure a Maven profile in IntelliJ with the following settings: 
   - Command line: `function:run`
   - Environment variables: `MICRONAUT_ENVIRONMENTS=dev;PUBSUB_EMULATOR_HOST=localhost:8085`

2. Start pubsub emulator on localhost:
    ```
    gcloud beta emulators pubsub start --project=test-project --host-port=0.0.0.0:8085
    ```

3. Start your Maven profile. Topic and subscriptions will be created during startup.

4. Do a POST request with body on the following format using `Content-Type = application/json`:
    ```
    {
       "name": "Foo Bar"
    }
    ```

You should now see a console output like this:
```
SimpleDemoListener received message-id: 15 Message: Hello Foo Bar, thank you for sending the message
DemoListenerWithAck received message-id: 15 Message: Hello Foo Bar, thank you for sending the message
```





