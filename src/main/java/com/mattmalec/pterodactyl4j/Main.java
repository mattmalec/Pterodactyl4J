package com.mattmalec.pterodactyl4j;

import com.mattmalec.pterodactyl4j.application.PteroApplicationBuilder;
import com.mattmalec.pterodactyl4j.application.entities.Node;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;

import java.util.List;


public class Main {

	public static void main(String[] args)  {
		PteroApplication application = new PteroApplicationBuilder()
				.setApplicationUrl("https://panel.explodingbush.net")
				.setToken("wPdYseNq2hc3okrMx7eomgSAjXV117ozGnt8VJYTImDFqy23").build();
		List<Node> nodes = application.retrieveNodes().execute();
		for (Node node : nodes) {
			System.out.println(node.toString());
		}

	}
}
