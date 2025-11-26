/*
 * Copyright 2019, 2025 Uppsala University Library
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

/**
 * DataRecordLink contains information linking the {@link ClientDataRecord} this link is a part of
 * to another {@link ClientDataRecord}
 */
public interface ClientDataRecordLink extends ClientDataLink, ClientConvertible {
	/**
	 * getLinkedRecordId returns the record id of the record this link refers to
	 * <p>
	 * This information is expected to be present, if this link does not have information about what
	 * the linked record id is, MUST a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return A String with the id of the record type that this link refers to.
	 */
	public String getLinkedRecordId();

	/**
	 * setLinkedRecordId set the provided value as the recordId in this ClientDataRecordLink.
	 * </p>
	 * 
	 * <b>Note!</b></br>
	 * Null and empty SHOULD never be set as a value, if the link does not have a linkedRecordId,
	 * remove it from its parent instead.
	 * 
	 * @param value
	 *            A String with the value to set
	 */
	void setLinkedRecordId(String value);

	/**
	 * getLinkedRecordType returns the record type of the record this link refers to.
	 * <p>
	 * This information is expected to be present, if this link does not have information about what
	 * the linked record type is, MUST a {@link ClientDataMissingException} be thrown.
	 * 
	 * @return A String with the record type that this link refers to.
	 */
	public String getLinkedRecordType();

}
