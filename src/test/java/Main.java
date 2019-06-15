import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;

public class Main {

	public static void main(String[] args) {
		PteroAPI ptero = new PteroBuilder()
				.setApplicationUrl("https://panel.explodingbush.net")
				.setToken("[redacted]")
				.build();
		PteroApplication api = ptero.asApplication();
		PteroClient client = ptero.asClient();
		client.retrieveServers().execute().forEach(s -> {
			Utilization utilization = client.retrieveUtilization(s).execute();
			System.out.printf("%s: %sMB/%sMB\n", s.getName(), utilization.getCurrentMemory(), utilization.getMaxMemory());
		});
//		ApplicationServer server = api.retrieveServersByName("Venomous - Factions", true).execute().get(0);
//		Nest nest = api.retrieveNestById("8").execute();
//		Location location = api.retrieveLocationById("1").execute();
//		Egg egg = api.retrieveEggById(nest, "27").execute();
//		Map<String, String> map = new HashMap<>();
//		Set<String> portRange = new HashSet<>();
//		portRange.add("25563");
//		map.put("SERVER_JARFILE", "server.jar");
//		map.put("MOTD", "Welcome to my asspoop");
//		map.put("MAXPLAYERS", "10");
//		map.put("VERSION", "1.8.8");
//		map.put("TYPE", "vanilla");
//		ServerAction server1 = api.createServer().setName("poop")
//				.setDescription("ass")
//				.setOwner(api.retrieveUserById("1").execute())
//				.setEgg(egg)
//				.setLocations(Collections.singleton(location))
//				.setAllocations(0L)
//				.setDatabases(0L)
//				.setCPU(0L)
//				.setDisk(1L, DataType.GB)
//				.setMemory(1L, DataType.GB)
//				.setDockerImage("quay.io/pterodactyl/core:java")
//				.setDedicatedIP(false)
//					.setPortRange(portRange)
//				.startOnCompletion(false)
//				.setEnvironment(map);
//				.build().execute();
//		System.out.println(server1);
	}
}
