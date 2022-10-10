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

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationAllocation;
import java.util.Optional;

/**
 * Represents a value used in communication with relationship models
 *
 * <p>Methods that return an instance of Relationed require an additional step
 * to get the final result. Thus the user needs to append a follow-up method.
 *
 * <p>Certain Pterodactyl models allow for their relationships with other objects to be included in the
 * same request. For example, instead of retreving the {@link com.mattmalec.pterodactyl4j.application.entities.Node Node} of an
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} using the node id, you can include the
 * {@link com.mattmalec.pterodactyl4j.application.entities.Node Node} object in the same request that retrieved the
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer}
 *
 * <p>However, you cannot include the relationship of a relationship object, like when attempting to get the
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg ApplicationEgg} of an
 * {@link com.mattmalec.pterodactyl4j.application.entities.ApplicationServer ApplicationServer} from an
 * {@link ApplicationAllocation Allocation}. The egg relationship will not be included in server relationship from the allocation.
 * You will need to retrieve the egg instead.
 *
 * @param <T>
 *        An entity from an object's relationship
 *
 */
public abstract class Relationed<T> {

	/**
	 * Retrieves the entity using its id
	 * <br>This will retrieve the relationed entity using its id instead of using the object from the entity's relationships
	 *
	 * @return {@link com.mattmalec.pterodactyl4j.PteroAction PteroAction} - Type of the relationed entity
	 *
	 * @see Relationed#get()
	 **/
	public abstract PteroAction<T> retrieve();

	/**
	 * Gets the entity from the object's relationships
	 * <br>This will get the relationed entity from the relationship object instead of retrieving using the id.
	 *
	 * <p>Note if the optional is not present, you'll need to retrieve the object using {@link Relationed#retrieve()}
	 *
	 * @return Possibly-present {@link java.util.Optional Optional} - Type of the relationed entity
	 *
	 * @see Relationed#retrieve()
	 **/
	public abstract Optional<T> get();

	/**
	 * Whether the entity is available in the object's relationships
	 * <br>This will always be false if you're attempting to get a relationship from a relationship object
	 *
	 * @return True, if the entity is present
	 */
	public boolean isPresent() {
		return get().isPresent();
	}
}
