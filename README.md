[version]: https://shields.io/maven-metadata/v?metadataUrl=https%3A%2F%2Frepo.mattmalec.com%2Frepository%2Freleases%2Fcom%2Fmattmalec%2FPterodactyl4J%2Fmaven-metadata.xml&color=informational&label=Download
[jenkins]: https://ci.mattmalec.com/job/Pterodactyl4J
[build]: https://ci.mattmalec.com/job/Pterodactyl4J/badge/icon
[javadocs]: https://ci.mattmalec.com/job/Pterodactyl4J/javadoc
[download]: #download
[jenkins-shield]: https://img.shields.io/badge/Download-Jenkins-orange.svg
[discord-shield]: https://discord.com/api/guilds/780230961035608064/widget.png
[discord-invite]: https://discord.gg/7fAabrTJZW
[ ![version][] ][download]
[ ![jenkins-shield][] ][jenkins]
[ ![build][] ][jenkins]
[ ![discord-shield][] ][discord-invite]
# Pterodactyl4J

P4J strives to provide a clean and full wrapping of the Pterodactyl REST API for Java.
<br />P4J fully wraps the application and the client API for the Pterodactyl panel.
<br />**NOTE**: This is an unofficial wrapper. Do not expect the Pterodactyl community to provide support for this wrapper.

## Creating the PteroApplication/PteroClient Object

Creating the PteroApplication or PteroClient object is done via the PteroBuilder class. Depending on your use case, the PteroApplication/PteroClient object is created by calling `createApplication` or `createClient`. Make sure to set your application URL and token as necessary. 

**Application Example**:
```java
PteroApplication api = PteroBuilder.createApplication("https://pterodactyl.app", "abc123");
```

**Client Example**:
```java
PteroClient api = PteroBuilder.createClient("https://pterodactyl.app", "xyz321");
```

### Examples:

**Reading all the users**:
```java
public class UserReader
{
    public static void main(String[] args)
    {
    
      PteroApplication api = PteroBuilder.createApplication("https://pterodactyl.app", "abc123");
      api.retrieveUsers().forEachAsync(user -> 
      {
          System.out.println(user.getFullName());
          return true;
      });
      
    }
}
```
**Creating a server**:
```java
public class ServerCreator
{
    public static void main(String[] args)
    {
        PteroApplication api = PteroBuilder.createApplication("https://pterodactyl.app", "abc123");
        Nest nest = api.retrieveNestById("1").execute();
        Location location = api.retrieveLocationById("1").execute();
        ApplicationEgg egg = api.retrieveEggById(nest, "3").execute();

        Map<String, EnvironmentValue<?>> map = new HashMap<>();
        map.put("SERVER_JARFILE", EnvironmentValue.of("server.jar"));
        map.put("VERSION", EnvironmentValue.of("1.8.8"));

        PteroAction<ApplicationServer> action = api.createServer()
                .setName("My Server")
        		.setDescription("Super awesome wrapper")
        		.setOwner(api.retrieveUserById("1").execute())
        		.setEgg(egg)
        		.setLocation(location)
        		.setAllocations(1L)
        		.setDatabases(0L)
        		.setCPU(0L)
        		.setDisk(3L, DataType.GB)
        		.setMemory(1L, DataType.GB)
        		.setDockerImage("quay.io/pterodactyl/core:java")
        		.setPort(25565)
        		.startOnCompletion(false)
        		.setEnvironment(map);
        ApplicationServer server = action.execute();

    }
}
```
**Reading live console output**:
```java
public class MyApp extends ClientSocketListenerAdapter
{
    public static void main(String[] args)
    {

        PteroClient api = PteroBuilder.createClient("https://pterodactyl.app", "xyz321");
        // if there isn't another thread running, this won't execute. you'll need to grab the server synchronously
        api.retrieveServerByIdentifier("39f09a87").map(ClientServer::getWebSocketBuilder)
               .map(builder -> builder.addEventListeners(new MyApp())).executeAsync(WebSocketBuilder::build);
    
    }

    @Override
    public void onAuthSuccess(AuthSuccessEvent event)
    {

        // if the server is running, this will trigger wings to send the entire console history from the current session
        event.getWebSocketManager().request(WebSocketManager.RequestAction.LOGS);
    
    }

    @Override
    public void onOutput(OutputEvent event)
    {

        // this will output everything from the console
        System.out.println(event.getLine());

    }

    @Override
    public void onConsoleOutput(ConsoleOutputEvent event)
    {

        // this will output everything from the console related to the game
        System.out.println(event.getLine());

    }

    @Override
    public void onInstallOutput(InstallOutputEvent event)
    {

        // this will output everything from the console related to the egg install/docker
        System.out.println(event.getLine());
    
    }

    @Override
    public void onStatsUpdate(StatsUpdateEvent event)
    {

        System.out.println(String.format("Memory Usage: %s/%s", event.getMemoryFormatted(DataType.MB), event.getMaxMemoryFormatted(DataType.MB)));

    }
}
```
**Starting a server**:
```java
public class ServerStarter
{
    public static void main(String[] args)
    {

        PteroClient api = PteroBuilder.createClient("https://pterodactyl.app", "xyz321");
        api.retrieveServerByIdentifier("39f09a87").flatMap(ClientServer::start).executeAsync();
    
    }
}
```

### PteroAction

[PteroAction](https://ci.mattmalec.com/job/Pterodactyl4J/javadoc/com/mattmalec/pterodactyl4j/PteroAction.html) is designed 
to make request handling simple.

It provides lazy request handling by offering asynchronous [callbacks](https://ci.mattmalec.com/job/Pterodactyl4J/javadoc/com/mattmalec/pterodactyl4j/PteroAction.html#executeAsync(java.util.function.Consumer))
and [synchronous](https://ci.mattmalec.com/job/Pterodactyl4J/javadoc/com/mattmalec/pterodactyl4j/PteroAction.html#execute()) execution.

This gives the user with a variety of patterns to use when making a request. The recommended approach is to use the asynchronous methods whenever possible.

The interface also supports several operators to improve quality of life:

- [`map`](https://ci.mattmalec.com/job/Pterodactyl4J/javadoc/com/mattmalec/pterodactyl4j/PteroAction.html#map(java.util.function.Function))
   Allows you to convert the result of a PteroAction to a different value
- [`flatMap`](https://ci.mattmalec.com/job/Pterodactyl4J/javadoc/com/mattmalec/pterodactyl4j/PteroAction.html#flatMap(java.util.function.Function))
   Allows you to chain another PteroAction on the result of the previous one
- [`delay`](https://ci.mattmalec.com/job/Pterodactyl4J/javadoc/com/mattmalec/pterodactyl4j/PteroAction.html#delay(long,java.util.concurrent.TimeUnit))
   Delays execution of the previous PteroAction
  
**Example**:
```java
public void startServer(String identifier) {
    System.out.println("Starting server in 5 seconds...")
    client.retrieveServerByIdentifier(identifier) // retrieve the client server
        .delay(5, TimeUnit.SECONDS) // wait 5 seconds
        .flatMap(ClientServer::start) // start the server
        .executeAsync(__ -> System.out.println("Starting server " + identifier + " now"));
}
```

### Rate limiting

P4J handles rate limiting from Pterodactyl by keeping track of requests, pausing
execution when the limit is hit, and finishing the remaining requests when the limit is lifted. 

P4J can do this by keeping a queue of requests that are waiting to be executed. 
When P4J is not being rate limited, it will continue to poll requests and execute.

When queuing requests with `execute()`, there can only be one request in the queue concurrently. 
Each of the following requests will be handled in order. 

However, requests queued with `executeAsync()` will be handled unordered due to the asychronous nature. 
With this approach, there can be an "unlimited" number of pending requests waiting
to be handled when it is convenient for P4J. 

Queuing requests asynchronously is generally faster than a synchronous approach, which is why the former is preferred to the latter.

## Download
Latest Version: [ ![version][] ][jenkins]

Be sure to replace the **VERSION** key below with the one of the versions shown above!

**Maven**
```xml
<dependency>
    <groupId>com.mattmalec</groupId>
    <artifactId>Pterodactyl4J</artifactId>
    <version>VERSION</version>
</dependency>
```
```xml
<repository>
    <id>mattmalec-repo</id>
    <url>https://repo.mattmalec.com/repository/releases</url>
</repository>

```

**Gradle**
```gradle
dependencies {
    implementation 'com.mattmalec:Pterodactyl4J:VERSION'
}

repositories {
    maven {
      url = 'https://repo.mattmalec.com/repository/releases'
    }
}
```

The builds are distributed using a Nexus instance

### Logging Framework - SLF4J

Pterodactyl4J uses [SLF4J](https://www.slf4j.org/) to log its messages.

That means you should add some SLF4J implementation to your build path in addition to P4J.
If no implementation is found, following message will be printed to the console on startup:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

P4J currently offers a fallback logger if no SLF4J implementation is present. 
I strongly recommend you use one though, as it improves speed and allows you to customize the output.

The most popular implementations are [Log4j 2](https://logging.apache.org/log4j/2.x/) and [Logback](https://logback.qos.ch/)

## Getting Help

For general troubleshooting, you can view some troubleshooting steps in the examples (this will be soon moved to the wiki).
<br>If you need help, or just want to talk with myself and other developers, you can join the [Pterodactyl4J Discord server][discord-invite].

Alternatively, if you need help outside of P4J, you can join the [Pterodactyl Discord server](https://discord.gg/pterodactyl).

## Documentation
The docs are **currently incomplete**, but will have everything you need to know in order to use the wrapper once they are finished.

You can find them on [Jenkins][jenkins] or they can be accessed directly [here][javadocs].

## Contributing to Pterodactyl4J
If you want to contribute to Pterodactyl4J, make sure to base your branch off the **develop** branch and create your PR into that **same** branch.

It is recommended to get in touch with myself before opening Pull Requests (either through an issue or on Discord).<br>

## Dependencies
This project requires **Java 8**.<br>
All dependencies are managed automatically by Gradle.

 * OkHttp
   * Version: **3.13.0**
   * [Github](https://github.com/square/okhttp)
   * [Maven Repository](https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp)
 * org.json
   * Version: **20160810**
   * [Github](https://github.com/douglascrockford/JSON-java)
   * [Maven Repository](https://mvnrepository.com/artifact/org.json/json)
 * slf4j-api
   * Version: **1.7.32**
   * [Website](https://www.slf4j.org/)
   * [Maven Repository](https://mvnrepository.com/artifact/org.slf4j/slf4j-api)
