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

public interface ClientDataAtomic extends ClientDataChild, ClientConvertible {

	/**
	 * getValue returns A String with the current value for this ClientDataAtomic
	 */
	String getValue();

	/**
	 * setValue set the provided value in this ClientDataAtomic.
	 * </p>
	 * 
	 * <b>Note!</b></br>
	 * Null and empty SHOULD never be set as a value, if the atomic does not have a value, remove it
	 * from its parent instead.
	 * 
	 * @param value
	 *            A String with the value to set
	 */
	void setValue(String value);
}
