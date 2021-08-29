/*
 *    Copyright 2021 Matt Malec, and the Pterodactyl4J contributors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.mattmalec.pterodactyl4j.requests;

import static com.mattmalec.pterodactyl4j.requests.Method.*;

public class Route {

	private static final String APPLICATION_PREFIX = "application/";
	private static final String CLIENT_PREFIX      = "client/";

	public static class Users {

		public static final Route LIST_USERS  			= new Route(GET,    APPLICATION_PREFIX + "users?page={page}&include=servers");
		public static final Route GET_USER    			= new Route(GET,    APPLICATION_PREFIX + "users/{user_id}?include=servers");
		public static final Route CREATE_USER 			= new Route(POST,   APPLICATION_PREFIX + "users");
		public static final Route EDIT_USER   			= new Route(PATCH,  APPLICATION_PREFIX + "users/{user_id}");
		public static final Route DELETE_USER 			= new Route(DELETE, APPLICATION_PREFIX + "users/{user_id}");

	}

	public static class Nodes {

		public static final Route LIST_NODES        	= new Route(GET,    APPLICATION_PREFIX + "nodes?page={page}&include=location,servers,allocations");
		public static final Route GET_NODE          	= new Route(GET,    APPLICATION_PREFIX + "nodes/{node_id}?include=location,servers,allocations");
		public static final Route GET_CONFIGURATION     = new Route(GET,    APPLICATION_PREFIX + "nodes/{node_id}/configuration");
		public static final Route CREATE_NODE       	= new Route(POST,   APPLICATION_PREFIX + "nodes?include=location,servers,allocations");
		public static final Route EDIT_NODE         	= new Route(PATCH,  APPLICATION_PREFIX + "nodes/{node_id}");
		public static final Route DELETE_NODE       	= new Route(DELETE, APPLICATION_PREFIX + "nodes/{node_id}");
		public static final Route LIST_ALLOCATIONS  	= new Route(GET,    APPLICATION_PREFIX + "nodes/{node_id}/allocations?page={page}&include=server,node");
		public static final Route CREATE_ALLOCATION 	= new Route(POST,   APPLICATION_PREFIX + "nodes/{node_id}/allocations");
		public static final Route DELETE_ALLOCATION		= new Route(DELETE, APPLICATION_PREFIX + "nodes/{node_id}/allocations/{allocation_id}");

	}

	public static class Locations {

		public static final Route LIST_LOCATIONS  		= new Route(GET,    APPLICATION_PREFIX + "locations?page={page}&include=nodes,servers");
		public static final Route GET_LOCATION    		= new Route(GET,    APPLICATION_PREFIX + "locations/{location_id}?include=nodes,servers");
		public static final Route CREATE_LOCATION 		= new Route(POST,   APPLICATION_PREFIX + "locations");
		public static final Route EDIT_LOCATION   		= new Route(PATCH,  APPLICATION_PREFIX + "locations/{location_id}");
		public static final Route DELETE_LOCATION 		= new Route(DELETE, APPLICATION_PREFIX + "locations/{location_id}");
	}

	public static class Servers {

		public static final Route LIST_SERVERS 			= new Route(GET,    APPLICATION_PREFIX + "servers?page={page}&include=allocations,user,subusers,nest,egg,location,node,databases");
		public static final Route GET_SERVER 			= new Route(GET,    APPLICATION_PREFIX + "servers/{server_id}?include=allocations,user,subusers,nest,egg,location,node,databases");
		public static final Route UPDATE_SERVER_DETAILS = new Route(PATCH,  APPLICATION_PREFIX + "servers/{server_id}/details");
		public static final Route UPDATE_SERVER_BUILD   = new Route(PATCH,  APPLICATION_PREFIX + "servers/{server_id}/build");
		public static final Route UPDATE_SERVER_STARTUP = new Route(PATCH,  APPLICATION_PREFIX + "servers/{server_id}/startup");
		public static final Route CREATE_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers?include=allocations,user,subusers,nest,egg,location,node,databases");
		public static final Route SUSPEND_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers/{server_id}/suspend");
		public static final Route UNSUSPEND_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers/{server_id}/unsuspend");
		public static final Route REINSTALL_SERVER 		= new Route(POST,   APPLICATION_PREFIX + "servers/{server_id}/reinstall");
		public static final Route SAFE_DELETE_SERVER 	= new Route(DELETE, APPLICATION_PREFIX + "servers/{server_id}");
		public static final Route FORCE_DELETE_SERVER	= new Route(DELETE, APPLICATION_PREFIX + "servers/{server_id}/force");

	}

	public static class Nests {

		public static final Route LIST_NESTS 			= new Route(GET, 	APPLICATION_PREFIX + "nests?page={page}&include=servers,eggs");
		public static final Route GET_NEST 				= new Route(GET, 	APPLICATION_PREFIX + "nests/{nest_id}?include=servers,eggs");
		public static final Route GET_EGGS 				= new Route(GET, 	APPLICATION_PREFIX + "nests/{nest_id}/eggs?include=variables,nest,servers");
		public static final Route GET_EGG 				= new Route(GET, 	APPLICATION_PREFIX + "nests/{nest_id}/eggs/{egg_id}?include=variables,nest,servers");

	}

	public static class Client {

		public static final Route GET_UTILIZATION 		= new Route(GET,  	CLIENT_PREFIX + "servers/{server_id}/resources");
		public static final Route SEND_COMMAND 			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/command");
		public static final Route SET_POWER 			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/power");
		public static final Route LIST_SERVERS 			= new Route(GET,  	CLIENT_PREFIX + "?page={page}&include=subusers,egg");
		public static final Route GET_SERVER			= new Route(GET,	CLIENT_PREFIX + "servers/{server_id}?include=subusers,egg");
		public static final Route GET_WEBSOCKET			= new Route(GET,    CLIENT_PREFIX + "servers/{server_id}/websocket");
		public static final Route RENAME_SERVER			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/settings/rename");
		public static final Route REINSTALL_SERVER		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/settings/reinstall");

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
		public static final Route DELETE_API_KEY	   	= new Route(DELETE, CLIENT_PREFIX + "account/api-keys/{identifier}");

	}

	public static class Subusers {

		public static final Route LIST_SUBUSERS 		= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/users");
		public static final Route GET_SUBUSER 			= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/users/{identifier}");
		public static final Route CREATE_SUBUSER		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/users");
		public static final Route UPDATE_SUBUSER		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/users/{identifier}");
		public static final Route DELETE_SUBUSER		= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/users/{identifier}");

	}

	public static class Backups {

		public static final Route LIST_BACKUPS 			= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/backups?page={page}");
		public static final Route GET_BACKUP 			= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}");
		public static final Route DOWNLOAD_BACKUP		= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}/download");
		public static final Route CREATE_BACKUP			= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/backups");
		public static final Route RESTORE_BACKUP		= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}/restore?truncate=true");
		public static final Route LOCK_BACKUP			= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}/lock");
		public static final Route DELETE_BACKUP			= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/backups/{identifier}");

	}

	public static class Schedules {

		public static final Route LIST_SCHEDULES 		= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/schedules");
		public static final Route GET_SCHEDULE			= new Route(GET,	CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}");
		public static final Route CREATE_SCHEDULE		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/schedules");
		public static final Route UPDATE_SCHEDULE		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}");
		public static final Route DELETE_SCHEDULE		= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}");
		public static final Route CREATE_TASK			= new Route(POST,	CLIENT_PREFIX + "servers/{server_id}/schedules/{identifier}/tasks");
		public static final Route UPDATE_TASK			= new Route(POST,	CLIENT_PREFIX + "servers/{server_id}/schedules/{schedule_id}/tasks/{identifier}");
		public static final Route DELETE_TASK			= new Route(DELETE, CLIENT_PREFIX + "servers/{server_id}/schedules/{schedule_id}/tasks/{identifier}");

	}

	public static class Files {

		public static final Route LIST_FILES 			= new Route(GET,    CLIENT_PREFIX + "servers/{server_id}/files/list?directory={directory}");
		public static final Route GET_CONTENTS 			= new Route(GET,    CLIENT_PREFIX + "servers/{server_id}/files/contents?file={file}");
		public static final Route DOWNLOAD_FILE 		= new Route(GET,    CLIENT_PREFIX + "servers/{server_id}/files/download?file={file}");
		public static final Route RENAME_FILES 			= new Route(PUT,    CLIENT_PREFIX + "servers/{server_id}/files/rename");
		public static final Route COPY_FILE 			= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/files/copy");
		public static final Route WRITE_FILE 			= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/files/write?file={file}");
		public static final Route COMPRESS_FILES 		= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/files/compress");
		public static final Route DECOMPRESS_FILE 		= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/files/decompress");
		public static final Route DELETE_FILES 			= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/files/delete");
		public static final Route CREATE_FOLDER 		= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/files/create-folder");
		public static final Route UPLOAD_FILE 			= new Route(GET,    CLIENT_PREFIX + "servers/{server_id}/files/upload");
		public static final Route UPLOAD_FILE_BY_URL 	= new Route(POST,   CLIENT_PREFIX + "servers/{server_id}/files/pull?directory={directory}&url={url}");

	}

	public static class Databases {

		public static final Route LIST_DATABASES 		= new Route(GET, 	CLIENT_PREFIX + "servers/{server_id}/databases?include=password");
		public static final Route CREATE_DATABASE 		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/databases");
		public static final Route ROTATE_PASSWORD 		= new Route(POST, 	CLIENT_PREFIX + "servers/{server_id}/databases/{database_id}/rotate-password");
		public static final Route DELETE_DATABASE 		= new Route(DELETE, 	CLIENT_PREFIX + "servers/{server_id}/databases/{database_id}");

	}

	private final Method method;
	private final String route;
	private final String compilableRoute;
	private final int paramCount;

	private Route(Method method, String route) {
		this.method = method;
		this.route = route;
		this.paramCount = countMatches(route, '{');

		compilableRoute = route.replaceAll("\\{.*?\\}", "%s");

		if (paramCount != countMatches(route, '}'))
			throw new IllegalArgumentException("An argument does not have both {}'s for route: " + method + "  " + route);

	}

	public String getRoute() {
		return route;
	}

	@Override
	public String toString() {
		return "Route(" + method + ": " + route + ")";
	}
	public CompiledRoute compile(String... params) {
		if (params.length != paramCount)
			throw new IllegalArgumentException("Error Compiling Route: [" + route + "], incorrect amount of parameters provided. " +
					"Expected: " + paramCount + ", Provided: " + params.length);

		if (paramCount == 0)
			return new CompiledRoute(this, compilableRoute);

		String compiledRoute = String.format(compilableRoute, (Object[]) params);

		return new CompiledRoute(this, compiledRoute);
	}

	public static class CompiledRoute {
		private final Route baseRoute;
		private final String compiledRoute;

		private CompiledRoute(Route baseRoute, String compiledRoute) {
			this.baseRoute = baseRoute;
			this.compiledRoute = compiledRoute;
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
