/*
 * Copyright 2019 Uppsala University Library
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

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

/**
 * DataRecord defines methods that can be used on a data record. The contents of the DataRecord is
 * adapted to the User who in an interaction (read, update, etc.) with the server got the DataRecord
 * in return.
 * <p>
 * The DataRecord consists of three major parts, a DataGroup holding the data for the record, a set
 * of Actions that the User is allowed to do with the record, and permissions devided into read
 * permissions that the User has for parts of this record, write permissions that the User has for
 * parts of this record.
 * <p>
 * Links to other DataGroups within the records DataGroup has "read" action added if the User is
 * allowed to read them.
 *
 */
public interface ClientDataRecord
		extends ClientData, ClientConvertible, ClientExternallyConvertible {

	/**
	 * getType returns the record type for this record.
	 * <p>
	 * If the records type is unknown SHOULD a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return String with the type of this record
	 */
	String getType();

	/**
	 * getId returns the record id for this record.
	 * <p>
	 * If the records id is unknown SHOULD a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return String with the id of this record
	 */
	String getId();

	/**
	 * setDataRecordGroup sets the DataRecordGroup in the DataRecord replacing any preexisting
	 * DataRecordGroup
	 * 
	 * @param dataRecordGroup
	 *            that is governed by the record
	 */
	void setDataRecordGroup(ClientDataRecordGroup dataRecordGroup);

	/**
	 * getDataRecordGroup returns the DataRecordGroup governed by the record. Multiple calls to
	 * getDataGroup should return the same instance.
	 * 
	 * @return the DataRecordGroup governed by the record
	 */
	ClientDataRecordGroup getDataRecordGroup();

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

	/**
	 * addReadPermission adds a permission to the preexisting read permission that the User has for
	 * this record
	 * 
	 * @param readPermission
	 *            is the permission to be added.
	 */
	void addReadPermission(String readPermission);

	/**
	 * addReadPermission adds a Collection of permissions to the preexisting read permission that
	 * the User has for this record.
	 * 
	 * @param readPermission
	 *            is the permissions to be added.
	 */
	void addReadPermissions(Collection<String> readPermissions);

	/**
	 * getReadPermissions returns a Set with the read permissions that the User has for this record
	 * 
	 * @return a Set of Strings containing the read permissions
	 */
	Set<String> getReadPermissions();

	/**
	 * hasReadPermissions returns true if this record has at least one read permission, otherwise
	 * false.
	 * 
	 * @return boolean whether this record has read permissions or not
	 */
	boolean hasReadPermissions();

	/**
	 * addWritePermission adds a permission to the preexisting write permission hat the User has for
	 * this record
	 * 
	 * @param writePermission
	 *            A String with the
	 */
	void addWritePermission(String writePermission);

	/**
	 * addWritePermissions adds a Collection of permissions to the preexisting write permission that
	 * the User has for this record.
	 * 
	 * @param writePermissions
	 *            is the permissions to be added.
	 */
	void addWritePermissions(Collection<String> writePermissions);

	/**
	 * getWritePermissions returns a Set with the write permissions that the User has for this
	 * record
	 * 
	 * @return a Set of Strings containing the write permissions
	 */
	Set<String> getWritePermissions();

	/**
	 * hasWritePermissions returns true if this record has at least one write permission, otherwise
	 * false.
	 * 
	 * @return boolean whether this record has read permissions or not.
	 */
	boolean hasWritePermissions();

	/**
	 * getSearchId returns a search id if the data represents a recordType or a search.
	 * <ul>
	 * <li>For a recordType is the search id the linked search.</li>
	 * <li>For a search is the search id the id of the record.</li>
	 * </ul>
	 * If a searchId does not exist, a {@link ClientDataMissingException} MUST be thrown.
	 * 
	 * @return A String with the search id
	 */
	String getSearchId();

}
