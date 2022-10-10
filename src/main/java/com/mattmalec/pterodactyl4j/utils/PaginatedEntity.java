/*
 *    Copyright 2021-2022 Matt Malec, and the Pterodactyl4J contributors
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

package com.mattmalec.pterodactyl4j.utils;

import org.json.JSONObject;

public class PaginatedEntity {

	private final int total;
	private final int entitiesPerPage;
	private final int currentPage;
	private final int totalPages;

	private PaginatedEntity(int total, int entitiesPerPage, int currentPage, int totalPages) {
		this.total = total;
		this.entitiesPerPage = entitiesPerPage;
		this.currentPage = currentPage;
		this.totalPages = totalPages;
	}

	public static PaginatedEntity create(JSONObject json) {
		JSONObject object = json.getJSONObject("meta").getJSONObject("pagination");
		return new PaginatedEntity(
				object.getInt("total"),
				object.getInt("per_page"),
				object.getInt("current_page"),
				object.getInt("total_pages"));
	}

	public int getTotal() {
		return total;
	}

	public int getEntitiesPerPage() {
		return entitiesPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}
}
