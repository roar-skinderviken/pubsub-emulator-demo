# pubsub-emulator-demo
Originally a Micronaut 3.0.2 implementation of nhartner's Spring Boot pubsub-emulator-demo: https://github.com/nhartner/pubsub-emulator-demo

Later migrated to `org.testcontainers.containers.PubSubEmulatorContainer` and tests in Spock.

#### Running PubSubIntegrationTests
Run 
```
mvn clean test
``` 

You should see this in the console:
```
DemoListenerWithAck received message-id: 5 Message: Hello World 4
.
.
.
DemoListenerWithAck received message-id: 1 Message: Hello World 1
```

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



