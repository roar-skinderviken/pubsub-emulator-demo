# pubsub-emulator-demo
A Micronaut 4.x app demonstrating pubsub emulator with testcontainers. Examples for both Spock and Kotest. 

#### Running PubSubIntegrationTests
Run
```shell
./gradlew clean check
``` 

You should see something like this in the console:
```
DemoListenerWithAck received message-id: 4 Message: Hello World 4
DemoListenerWithAck received message-id: 2 Message: Hello World 2
DemoListenerWithAck received message-id: 3 Message: Hello World 3
DemoListenerWithAck received message-id: 1 Message: Hello World 1
DemoListenerWithAck received message-id: 5 Message: Hello World 5
```




