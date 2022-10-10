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

package com.mattmalec.pterodactyl4j.requests.action.impl;

import com.mattmalec.pterodactyl4j.entities.P4J;
import com.mattmalec.pterodactyl4j.requests.Request;
import com.mattmalec.pterodactyl4j.requests.Response;
import com.mattmalec.pterodactyl4j.requests.Route;
import com.mattmalec.pterodactyl4j.utils.PaginatedEntity;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import org.json.JSONObject;

public class PaginationResponseImpl<T> extends PaginationActionImpl<T> {

	protected final Function<JSONObject, T> handler;

	private PaginationResponseImpl(P4J api, Route.CompiledRoute route, Function<JSONObject, T> handler) {
		super(api, route);
		this.handler = handler;
	}

	public static <T> PaginationResponseImpl<T> onPagination(
			P4J api, Route.CompiledRoute route, Function<JSONObject, T> handler) {
		return new PaginationResponseImpl<>(api, route, handler);
	}

	@Override
	public void handleSuccess(Response response, Request<List<T>> request) {
		JSONObject object = response.getObject();

		PaginatedEntity paginatedEntity = PaginatedEntity.create(object);
		totalPages = paginatedEntity.getTotalPages();

		List<T> entities = new LinkedList<>();
		for (Object o : object.getJSONArray("data")) {
			T entity = handler.apply(new JSONObject(o.toString()));
			entities.add(entity);

			if (useCache) cached.add(entity);

			last = entity;
		}

		PAGINATION_LOG.trace("Successfully retrieved {} entities", entities.size());

		if (useCache)
			PAGINATION_LOG.debug("Cache enabled: caching {} entities, cache size: {}", entities.size(), cached.size());

		currentPage = getCurrentPage() + 1;
		request.onSuccess(entities);
	}
}
