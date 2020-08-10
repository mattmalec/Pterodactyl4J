# Pterodactyl4J

P4J strives to provide a clean and full wrapping of the Pterodactyl REST API for Java.
<br />P4J fully wraps the application and the client API for the Pterodactyl panel.
<br />**NOTE**: This is an unofficial wrapper. Do not expect the Pterodactyl community to provide support for this wrapper.

## Creating the PteroAPI object

Creating the PteroAPI Object is done via the PteroBuilder class. After setting the token and application URL via setters, the PteroAPI Object is then created by calling the `build()` method. When `build()` returns, the PteroAPI will be ready to go. With the PteroAPI Object, depending on which type of token you're using, you'll need to call `asClient()` or `asApplication()` as necessary. 

**Example**:
```java
PteroAPI api = new PteroBuilder().setApplicationUrl("https://pterodactyl.app").setToken("abc123").build();
```

#### Examples:

**Reading all the users**:
```java
public class UserReader
{
    public static void main(String[] args)
    {
    
      PteroApplication api = new PteroBuilder().setApplicationUrl("https://pterodactyl.app").setToken("abc123").build().asApplication();
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
		Map<String, String> map = new HashMap<>();
		Set<String> portRange = new HashSet<>();
		portRange.add("25565");
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
				.setEnvironment(map);
				.build():
                                ApplicationServer server = action.execute();
    }
}
```
## Download
Latest Stable Version: [Bintray Release](https://bintray.com/mattmalec/Pterodactyl4J/Pterodactyl4J/1.1/link) <br>
Latest Version: [ ![Download](https://api.bintray.com/packages/mattmalec/Pterodactyl4J/Pterodactyl4J/images/download.svg?version=1.1) ](https://bintray.com/mattmalec/Pterodactyl4J/Pterodactyl4J/1.1/link)

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

## Getting Help

For general troubleshooting, you can view some troubleshooting steps in the examples (this will be soon moved to the wiki)
<br>If you need help, please contact me directly on Discord, or just want to talk with the Pterodactyl community, you can join the [Pterodactyl Discord server](https://discord.gg/pterodactyl)

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
