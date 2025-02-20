/*
 * Copyright 2025 Uppsala University Library
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

import java.util.List;
import java.util.Optional;

/**
 * ClientDataAuthentication defines methods that can be used on a data authentication.
 * <p>
 * 
 * A ClientDataAuthentication consists of up to two major parts:
 * <ul>
 * <li>Information about the authentication that can be read using different get methods</li>
 * <li>a set of Actions that the User is allowed to do with the authentication</li>
 * <p>
 * Links to other DataGroups within the records DataGroup has "read" action added if the User is
 * allowed to read them.
 */
public interface ClientDataAuthentication
		extends ClientData, ClientConvertible, ClientExternallyConvertible {

	/**
	 * getToken returns the authetication token.
	 * 
	 * @return A String with the authetication token.
	 */
	public String getToken();

	/**
	 * getLoginId returns the authetication loginId.
	 * 
	 * @return A String with the authetication loginId.
	 */
	public String getLoginId();

	/**
	 * getUserId returns the authetication userId.
	 * 
	 * @return A String with the authetication userId.
	 */
	public String getUserId();

	/**
	 * getValidUntil returns the authetication validUntil.
	 * 
	 * @return A String with the authetication validUntil.
	 */
	public String getValidUntil();

	/**
	 * getRenewUntil returns the authetication renewUntil.
	 * 
	 * @return A String with the authetication renewUntil.
	 */
	public String getRenewUntil();

	/**
	 * getFirstName returns the authentication firstName.
	 * 
	 * @return A String with the authentication firstName.
	 */
	public String getFirstName();

	/**
	 * getLastName returns the authetication lastName.
	 * 
	 * @return A String with the authetication lastName.
	 */
	public String getLastName();

	/**
	 * getPermissionUnitIds returns a List of Strings with permission unit ids that the
	 * authentication logedin user has can alter data for.
	 * <p>
	 * If the user has no permission units is empty List returned.
	 * 
	 * @return A List of Strings with permission unit ids.
	 */
	public List<String> getPermissionUnitIds();

	/**
	 * getActionLink returns an Optional with an ActionLink representing the requested ClientAction
	 * if the user that got the record from the server can execute the requested action on the
	 * returned record. An empty optional is returned if no matching actionLink exist in the record.
	 * <p>
	 * If there is no matching action for this record an empty Optional should be returned.
	 * 
	 * @param action
	 *            A ClientAction to get an ClientActionLink for
	 * @return An Optional that might contain a ClientActionLink that matches the requested
	 *         ClientAction.
	 */
	public Optional<ClientActionLink> getActionLink(ClientAction action);
}
