import com.mattmalec.pterodactyl4j.DataType;
import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import com.mattmalec.pterodactyl4j.client.entities.Utilization;
import com.mattmalec.pterodactyl4j.entities.PteroAPI;

public class Main {

	public static void main(String[] args) {

		PteroAPI ptero = new PteroBuilder()
				.setApplicationUrl("https://dev.mattmalec.com")
				.setToken("cz85PDLVPn4LPd3oburhMid1jsKr47K3NMubozWBS1mu5Lfj")
				.build();

		PteroClient client = ptero.asClient();

		ClientServer server = client.retrieveServerByIdentifier("39f09a87").execute();
		Utilization utilization = client.retrieveUtilization(server).execute();
		System.out.println(utilization.getMemoryFormatted(DataType.MB) + " / " + server.getLimits().getMemoryLong() / 1024 + " GB");


	}
}
