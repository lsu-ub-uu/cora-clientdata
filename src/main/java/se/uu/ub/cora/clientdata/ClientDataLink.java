/*
 * Copyright 2016 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.uu.ub.cora.clientdata;

import java.util.Optional;

/**
 * DataLink contains information linking the {@link ClientDataRecord} this link is a part of to
 * another entity in the system.
 */
public interface ClientDataLink extends ClientDataChild {

	/**
	 * hasReadAction returns true if the current user is allowed to read the resource this link
	 * points to. This is also known as: link has read action.
	 * <p>
	 * Note! The action information is not always present. If no action information is present
	 * SHOULD hasReadAction return false.
	 * 
	 * @return a boolean true if this link has read as one of its actions else false
	 */
	boolean hasReadAction();

	/**
	 * addActionLink adds an ActionLink to the to this record. ActionLinks represents possible
	 * actions that the user that got the record from the server can take on the returned record.
	 * 
	 * @param actionLink
	 *            is the ClientActionLink to be added to the record.
	 */
	public void addActionLink(ClientActionLink actionLink);

	/**
	 * getActionLink returns an Optional with an ActionLink representing the requested ClientAction
	 * if the user that got the record from the server can execute the requested action on the
	 * returned record. An empty optional is returned if no matching actionLink exist in the record.
	 * <p>
	 * If there is no matching action for this record an empty Optional should be returned.
	 * 
	 * @return An Optional that might contain a ClientActionLink that matches the requested
	 *         ClientAction.
	 */
	public Optional<ClientActionLink> getActionLink(ClientAction action);

}
