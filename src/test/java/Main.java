import com.mattmalec.pterodactyl4j.AccountType;
import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.application.entities.Egg;
import com.mattmalec.pterodactyl4j.application.entities.Location;
import com.mattmalec.pterodactyl4j.application.entities.Nest;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		PteroApplication api = new PteroBuilder(AccountType.CLIENT)
				.setApplicationUrl("https://panel.explodingbush.net")
				.setToken("[redacted]")
				.build().asApplication();
		Nest nest = api.retrieveNestById("8").execute();
		Location location = api.retrieveLocationById("1").execute();
		Egg egg = api.retrieveEggById(nest, "27").execute();
		Map<String, String> map = new HashMap<>();
		Set<String> portRange = new HashSet<>();
		portRange.add("25563");
		map.put("DL_VERSION", "1.12.2");
			api.createServer().setName("poop")
					.setDescription("ass")
					.setOwner(api.retrieveUserById("1").execute())
					.setEgg(egg)
					.setLocations(Collections.singleton(location))
					.setAllocations(0L)
					.setDatabases(0L)
					.setCPU(0L)
					.setDisk(2L, DataType.MB)
					.setMemory(2L, DataType.MB)
					.setDockerImage("quay.io/pterodactyl/core:java")
//					.setStartupCommand()
					.setDedicatedIP(false)
					.setPortRange(portRange)
					.startOnCompletion(false)
//					.setEnvironment(map)
					.build().execute();
	}
}
