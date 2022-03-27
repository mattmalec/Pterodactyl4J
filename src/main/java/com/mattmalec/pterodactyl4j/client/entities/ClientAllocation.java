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

package com.mattmalec.pterodactyl4j.client.entities;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.entities.Allocation;

/**
 * Represents an {@link Allocation} for a {@link ClientServer}
 */
public interface ClientAllocation extends Allocation {

	/**
	 * Retrieve if this is the default {@link Allocation} for the server.
	 *
	 * @return Whether the allocation is the default one
	 */
	boolean isDefault();

	/**
	 * Set the note for this {@link Allocation}.
	 *
	 * @param note Note
	 */
	PteroAction<ClientAllocation> setNote(String note);

	/**
	 * Set if this {@link Allocation} should be the primary one.
	 */
	PteroAction<ClientAllocation> setPrimary();

	PteroAction<Void> unassign();

}
