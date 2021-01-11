package com.mattmalec.pterodactyl4j.requests;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mattmalec.pterodactyl4j.requests.Method.*;

public class Route {

	private static final String APPLICATION_PREFIX = "application/";
	private static final String CLIENT_PREFIX = "client/";


	public static class Users {

		public static final Route LIST_USERS  			= new Route(GET,    APPLICATION_PREFIX + "users?page={page}&include=servers", "page");
		public static final Route GET_USER    			= new Route(GET,    APPLICATION_PREFIX + "users/{user_id}?include=servers", "user_id");
		public static final Route CREATE_USER 			= new Route(POST,   APPLICATION_PREFIX + "users");
		public static final Route EDIT_USER   			= new Route(PATCH,  APPLICATION_PREFIX + "users/{user_id}", "user_id");
		public static final Route DELETE_USER 			= new Route(DELETE, APPLICATION_PREFIX + "users/{user_id}", "user_id");

	}

	public static class Nodes {

		public static final Route LIST_NODES        	= new Route(GET,    APPLICATION_PREFIX + "nodes?page={page}&include=location,servers,allocations", "page");
		public static final Route GET_NODE          	= new Route(GET,    APPLICATION_PREFIX + "nodes/{node_id}?include=location,servers,allocations", "node_id");
		public static final Route CREATE_NODE       	= new Route(POST,   APPLICATION_PREFIX + "nodes?include=location,servers,allocations");
		public static final Route EDIT_NODE         	= new Route(PATCH,  APPLICATION_PREFIX + "nodes/{node_id}", "node_id");
		public static final Route DELETE_NODE       	= new Route(DELETE, APPLICATION_PREFIX + "nodes/{node_id}", "node_id");
		public static final Route LIST_ALLOCATIONS  	= new Route(GET,    APPLICATION_PREFIX + "nodes/{node_id}/allocations?page={page}&include=server,node", "node_id", "page");
		public static final Route CREATE_ALLOCATION 	= new Route(POST,   APPLICATION_PREFIX + "nodes/{node_id}/allocations", "node_id");
		public static final Route DELETE_ALLOCATION		= new Route(DELETE, APPLICATION_PREFIX + "nodes/{node_id}/allocations/{allocation_id}", "node_id", "allocation_id");

	}

	public static class Locations {

		public static final Route LIST_LOCATIONS  		= new Route(GET,    APPLICATION_PREFIX + "locations?page={page}&include=nodes,servers", "page");
		public static final Route GET_LOCATION    		= new Route(GET,    APPLICATION_PREFIX + "locations/{location_id}?include=nodes,servers", "location_id");
		public static final Route CREATE_LOCATION 		= new Route(POST,   APPLICATION_PREFIX + "locations");
		public static final Route EDIT_LOCATION   		= new Route(PATCH,  APPLICATION_PREFIX + "locations");
		public static final Route DELETE_LOCATION 		= new Route(DELETE, APPLICATION_PREFIX + "locations/{location_id}", "location_id");
	}

	public static class Servers {

		public static final Route LIST_SERVERS 			= new Route(GET,    APPLICATION_PREFIX + "servers?page={page}&include=allocations,user,subusers,nest,egg,location,node,databases", "page");
		public static final Route GET_SERVER 			= new Route(GET,    APPLICATION_PREFIX + "servers/{server_id}?include=allocations,user,subusers,nest,egg,location,node,databases", "server_id");
		public static final Route UPDATE_SERVER_DETAILS = new Route(PATCH,  APPLICATION_PREFIX + "servers/{server_id}/details", "server_id");
		public static final Route UPDATE_SERVER_BUILD   = new Route(PATCH,  APPLICATION_PREFIX + "servers/{server_id}/build", "server_id");
		public static final Route UPDATE_SERVER_STARTUP = new Route(PATCH,  APPLICATION_PREFIX + "servers/{server_id}/startup", "server_id");
		public static final Route CREATE_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers?include=allocations,user,subusers,nest,egg,location,node,databases");
		public static final Route SUSPEND_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers/{server_id}/suspend", "server_id");
		public static final Route UNSUSPEND_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers/{server_id}/unsuspend", "server_id");
		public static final Route REINSTALL_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers/{server_id}/reinstall", "server_id");
		public static final Route SAFE_DELETE_SERVER 	= new Route(DELETE, APPLICATION_PREFIX + "servers/{server_id}", "server_id");
		public static final Route FORCE_DELETE_SERVER	= new Route(DELETE, APPLICATION_PREFIX + "servers/{server_id}/force", "server_id");

	}

	public static class Nests {

		public static final Route LIST_NESTS 			= new Route(GET, 	APPLICATION_PREFIX + "nests?page={page}&include=servers,eggs", "page");
		public static final Route GET_NEST 				= new Route(GET, 	APPLICATION_PREFIX + "nests/{nest_id}?include=servers,eggs", "nest_id");
		public static final Route GET_EGGS 				= new Route(GET, 	APPLICATION_PREFIX + "nests/{nest_id}/eggs?include=variables,nest,servers", "nest_id");
		public static final Route GET_EGG 				= new Route(GET, 	APPLICATION_PREFIX + "nests/{nest_id}/eggs/{egg_id}?include=variables,nest,servers", "nest_id", "egg_id");

	}

	public static class Client {

		public static final Route GET_UTILIZATION 		= new Route(GET,  	CLIENT_PREFIX + "servers/{server_id}/resources", "server_id");
		public static final Route SEND_COMMAND 			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/command", "server_id");
		public static final Route SET_POWER 			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/power", "server_id");
		public static final Route LIST_SERVERS 			= new Route(GET,  	CLIENT_PREFIX + "?page={page}&include=subusers,egg", "page");
		public static final Route GET_SERVER			= new Route(GET,	CLIENT_PREFIX + "servers/{server_id}?include=subusers,egg", "server_id");
		public static final Route GET_WEBSOCKET			= new Route(GET,    CLIENT_PREFIX + "servers/{server_id}/websocket", "server_id");
		public static final Route RENAME_SERVER			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/settings/rename", "server_id");
		public static final Route REINSTALL_SERVER		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/settings/reinstall", "server_id");

	}

	public static class Accounts {

		public static final Route GET_ACCOUNT			= new Route(GET,    CLIENT_PREFIX + "account");
		public static final Route GET_2FA_CODE	   	    = new Route(GET,    CLIENT_PREFIX + "account/two-factor");
		public static final Route ENABLE_2FA	   	    = new Route(POST,   CLIENT_PREFIX + "account/two-factor");
		public static final Route DISABLE_2FA	   	    = new Route(DELETE, CLIENT_PREFIX + "account/two-factor");
		public static final Route UPDATE_EMAIL			= new Route(PUT,    CLIENT_PREFIX + "account/email");
		public static final Route UPDATE_PASSWORD    	= new Route(PUT,    CLIENT_PREFIX + "account/password");
		public static final Route GET_API_KEYS    	    = new Route(GET,    CLIENT_PREFIX + "account/api-keys");
		public static final Route CREATE_API_KEY    	= new Route(POST,   CLIENT_PREFIX + "account/api-keys");
		public static final Route DELETE_API_KEY	   	= new Route(DELETE, CLIENT_PREFIX + "account/api-keys/{identifier}", "identifier");

	}

	public static class Subusers {

		public static final Route LIST_SUBUSERS 		= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/users", "server_id");
		public static final Route GET_SUBUSER 			= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/users/{identifier}", "server_id", "identifier");
		public static final Route CREATE_SUBUSER		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/users", "server_id");
		public static final Route UPDATE_SUBUSER		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/users/{identifier}", "server_id", "identifier");
		public static final Route DELETE_SUBUSER		= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/users/{identifier}", "server_id", "identifier");

	}

	public static class Backups {

		public static final Route LIST_BACKUPS 			= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/backups?page={page}", "server_id", "page");
		public static final Route GET_BACKUP 			= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}", "server_id", "identifier");
		public static final Route DOWNLOAD_BACKUP		= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}/download", "server_id", "identifier");
		public static final Route CREATE_BACKUP			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/backups", "server_id");
		public static final Route DELETE_BACKUP			= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}", "server_id", "identifier");

	}

	public static class Schedules {

		public static final Route LIST_SCHEDULES 		= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/schedules?page={page}", "server_id", "page");
		public static final Route GET_SCHEDULE			= new Route(GET,	CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}", "server_id", "identifier");
		public static final Route CREATE_SCHEDULE		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/schedules", "server_id");
		public static final Route UPDATE_SCHEDULE		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}", "server_id", "identifier");
		public static final Route DELETE_SCHEDULE		= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}", "server_id", "identifier");
		public static final Route CREATE_TASK			= new Route(POST,	CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}/tasks", "server_id", "identifier");
		public static final Route UPDATE_TASK			= new Route(POST,	CLIENT_PREFIX + "servers/{server_id}/schedules/{schedule_id}/tasks/{identifier}", "server_id", "schedule_id", "identifier");
		public static final Route DELETE_TASK			= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}/tasks/{identifier}", "server_id", "schedule_id", "identifier");

	}

	private final Method method;
	private final String route;
	private final String compilableRoute;
	private final List<Integer> paramIndexes = new ArrayList<>();
	private final String rateLimitRoute;
	private final int paramCount;

	private Route(Method method, String route) {

		this(method, route, "");

	}

	private Route(Method method, String route, String... params) {
		this.method = method;
		this.route = route;
		this.paramCount = countMatches(route, '{');

		compilableRoute = route.replaceAll("\\{.*?\\}", "%s");

		if (paramCount != countMatches(route, '}'))
			throw new IllegalArgumentException("An argument does not have both {}'s for route: " + method + "  " + route);

		if (params.length != 0) {
			int paramIndex = 0;
			String replaceRoute = route;
			Pattern keyP = Pattern.compile("\\{(.*?)\\}");
			Matcher keyM = keyP.matcher(route);
			while (keyM.find()) {
				String param = keyM.group(1);
				for (String majorParam : params) {
					if (param.equals(majorParam)) {
						replaceRoute = replaceRoute.replace(keyM.group(0), "%s");
						paramIndexes.add(paramIndex);
					}
				}
				paramIndex++;
			}
			rateLimitRoute = replaceRoute;
		} else {
			rateLimitRoute = route;
		}

	}

	public Method getMethod() {
		return method;
	}

	public String getRoute() {
		return route;
	}

	public String getCompilableRoute() {
		return compilableRoute;
	}

	public String getRateLimitRoute() {
		return rateLimitRoute;
	}
	@Override
	public String toString() {
		return "Route(" + method + ": " + route + ")";
	}
	public CompiledRoute compile(String... params) {
		if (params.length != paramCount) {
			throw new IllegalArgumentException("Error Compiling Route: [" + route + "], incorrect amount of parameters provided. " +
					"Expected: " + paramCount + ", Provided: " + params.length);
		}

		String compiledRoute = String.format(compilableRoute, (Object[]) params);
		String compiledRatelimitRoute = getCompilableRoute();

		if (!paramIndexes.isEmpty()) {
			String[] prms = new String[paramIndexes.size()];
			for (int i = 0; i < prms.length; i++) {
				prms[i] = params[paramIndexes.get(i)];
			}
			compiledRatelimitRoute = String.format(compiledRatelimitRoute, (Object[]) prms);
		}

		return new CompiledRoute(this, compiledRatelimitRoute, compiledRoute, null);
	}
	public CompiledRoute compile() {
		String compiledRatelimitRoute = getCompilableRoute();
		return new CompiledRoute(this, compiledRatelimitRoute, compilableRoute, null);
	}

	public class CompiledRoute {
		private final Route baseRoute;
		private final String ratelimitRoute;
		private final String compiledRoute;
		private final JSONObject json;

		private CompiledRoute(Route baseRoute, String ratelimitRoute, String compiledRoute, JSONObject jsonData) {
			this.baseRoute = baseRoute;
			this.ratelimitRoute = ratelimitRoute;
			this.compiledRoute = compiledRoute;
			this.json = jsonData;
		}


		public CompiledRoute withJSONdata(JSONObject json) {

			return new CompiledRoute(baseRoute, ratelimitRoute, compiledRoute, json);
		}

		public String getCompiledRoute() {
			return compiledRoute;
		}

		public Route getBaseRoute() {
			return baseRoute;
		}

		public Method getMethod() {
			return baseRoute.method;
		}

		public JSONObject getJSONData() {
			return json;
		}
	}
	private static int countMatches(CharSequence seq, char c) {
		int count = 0;
		for (int i = 0; i < seq.length(); i++) {
			if (seq.charAt(i) == c)
				count++;
		}
		return count;
	}
}
