package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.PteroApplicationBuilder;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;


public class Main {

	public static void main(String[] args)  {
		PteroApplication application = new PteroApplicationBuilder()
				.setApplicationUrl("https://panel.explodingbush.net")
				.setToken("wPdYseNq2hc3okrMx7eomgSAjXV117ozGnt8VJYTImDFqy23").build();
		Node node = application.retrieveNodeById(10).execute();
		application.getNodeManager().deleteNode(node).execute();
//		Location location = application.retrieveLocationsByShortCode("us.loc.main", false).execute().get(0);
//		Node node = nodeAction
//				.setName("Matts Test")
//				.setDaemonBase("/srv/daemon-data")
//				.setDaemonListenPort("8080")
//				.setDaemonSFTPPort("2022")
//				.setMemory("2048")
//				.setDiskSpace("1024")
//				.setFQDN("daemon.ec2.explodingbush.net")
//				.setScheme(true)
//				.setBehindProxy(false)
//				.setPublic(true)
//				.setThrottle(false)
//				.setDiskSpaceOverallocate("-1")
//				.setMemoryOverallocate("-1")
//				.setLocation(location).build().execute();
//		System.out.println(node.toString());
//		Node node = application.retrieveNodesByName("us.node.ovh", false).execute().get(0);
//		Location location = node.retrieveLocation().execute();
//		System.out.println(location.getDescription());

	}
}
