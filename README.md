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
      api.retrieveUsers().executeAsync(users -> users.forEach(u -> System.out.println(u.getFullName())));
      
    }
}
```
**Creating a server**:
```java
public class ServerCreator
{
    public static void main(String[] args)
    { 

        Nest nest = api.retrieveNestById("8").execute();
        Location location = api.retrieveLocationById("1").execute();
        Egg egg = api.retrieveEggById(nest, "27").execute();

        Set<Integer> portRange = new HashSet<>();
        portRange.add(25565);

        Map<String, String> map = new HashMap<>();
        map.put("SERVER_JARFILE", "server.jar");
        map.put("MOTD", "Welcome to my Minecraft server");
        map.put("MAXPLAYERS", "10");
        map.put("VERSION", "1.8.8");
        map.put("TYPE", "vanilla");

        ServerAction action = api.createServer().setName("My Server")
        		.setDescription("Super awesome wrapper")
        		.setOwner(api.retrieveUserById("1").execute())
        		.setEgg(egg)
        		.setLocations(Collections.singleton(location))
        		.setAllocations(0L)
        		.setDatabases(0L)
        		.setCPU(0L)
        		.setDisk(3L, DataType.GB)
        		.setMemory(1L, DataType.GB)
        		.setDockerImage("quay.io/pterodactyl/core:java")
        		.setDedicatedIP(false)
        		.setPortRange(portRange)
        		.startOnCompletion(false)
        		.setEnvironment(map).build();
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
        api.retrieveServerByIdentifier("39f09a87").executeAsync(server -> server.getWebSocketBuilder().addEventListeners(new MyApp()).build());
    
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

## Download
Latest Stable Version: [Bintray Release](https://bintray.com/mattmalec/Pterodactyl4J/Pterodactyl4J/1.1/link) <br>
Latest Version: [ ![Download](https://api.bintray.com/packages/mattmalec/Pterodactyl4J/Pterodactyl4J/images/download.svg?version=2.BETA_22) ](https://bintray.com/mattmalec/Pterodactyl4J/Pterodactyl4J/2.BETA_22/link)

Be sure to replace the **VERSION** key below with the one of the versions shown above!

**Maven**
```xml
<dependency>
    <groupId>com.mattmalec.Pterodactyl4J</groupId>
    <artifactId>Pterodactyl4J</artifactId>
    <version>VERSION</version>
</dependency>
```
```xml
<repository>
    <id>jcenter</id>
    <name>jcenter-bintray</name>
    <url>http://jcenter.bintray.com</url>
</repository>

```

**Gradle**
```gradle
dependencies {
    compile 'com.mattmalec.Pterodactyl4J:Pterodactyl4J:VERSION'
}

repositories {
    jcenter()
}
```

The builds are distributed using JCenter through Bintray [Pterodactyl4J JCenter Bintray](https://bintray.com/mattmalec/Pterodactyl4J/Pterodactyl4J/)

### Logging Framework - SLF4J

Pterodactyl4J uses [SLF4J](https://www.slf4j.org/) to log its WebSocket messages.

That means you should add some SLF4J implementation to your build path in addition to P4J.
If no implementation is found, the following message will be printed to the console when building the WebSocket:
```
SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder".
SLF4J: Defaulting to no-operation (NOP) logger implementation
SLF4J: See http://www.slf4j.org/codes.html#StaticLoggerBinder for further details.
```

P4J does **NOT** offer a fallback logger in place of SLF4J. You need an implementation to receive WebSocket log messages.

The most popular implementations are [Log4j 2](https://logging.apache.org/log4j/2.x/) and [Logback](https://logback.qos.ch/)

## Getting Help

For general troubleshooting, you can view some troubleshooting steps in the examples (this will be soon moved to the wiki).
<br>If you need help, or just want to talk with myself and other developers, you can join the [Pterodactyl4J Discord server](https://discord.gg/7fAabrTJZW).

Alternatively, if you need help outside of P4J, you can join the [Pterodactyl Discord server](https://discord.gg/pterodactyl).

## Documentation
The docs will have everything you need to know in order to use the wrapper once finished of course.

## Contributing to Pterodactyl4J
If you want to contribute to Pterodactyl4J, make sure to base your branch off of our **development** branch (develop)
and create your PR into that **same** branch.

It is recommended to get in touch with myself before opening Pull Requests (either through an issue or on Discord).<br>

## Dependencies
This project requires **Java 8**.<br>
All dependencies are managed automatically by Gradle.

 * OkHttp
   * Version: **3.11.0**
   * [Github](https://github.com/square/okhttp)
   * [JCenter Repository](https://bintray.com/bintray/jcenter/com.squareup.okhttp3:okhttp)
 * org.json
   * Version: **20160810**
   * [Github](https://github.com/douglascrockford/JSON-java)
   * [JCenter Repository](https://bintray.com/bintray/jcenter/org.json%3Ajson/view)
 * slf4j-api
   * Version: **1.7.25**
   * [Website](https://www.slf4j.org/)
   * [JCenter Repository](https://bintray.com/bintray/jcenter/org.slf4j%3Aslf4j-api/view)