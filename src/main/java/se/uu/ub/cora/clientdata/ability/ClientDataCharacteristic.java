/*
 * Copyright 2022 Uppsala University Library
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

package se.uu.ub.cora.clientdata.ability;

import java.util.Collection;
import java.util.Optional;

import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataMissingException;

/**
 * DataCharacteristic adds the ability to handle attributes..
 */
public interface ClientDataCharacteristic {
	/**
	 * addAttributeByIdWithValue adds a DataAttribute with the specified nameInData and value. The
	 * implementation is expected to allow only one attribute with the specified nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the attribute
	 * @param value
	 *            A String with the value of the attribute
	 */
	void addAttributeByIdWithValue(String nameInData, String value);

	/**
	 * hasAttributes returns true if this DataElement has attributes else false is returned
	 * 
	 * @return true if this element has attributes else false
	 */
	boolean hasAttributes();

	/**
	 * getAttribute returns the DataAttribute with the specified nameInData.
	 * <p>
	 * A {@link ClientDataMissingException} SHOULD be thrown if no attribute exists with the
	 * specified nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the attribute
	 * @return A DataAttribute matching the nameInData
	 * 
	 */
	ClientDataAttribute getAttribute(String nameInData);

	/**
	 * getAttributes returns the attributes that this dataElement has.
	 * 
	 * @return A Collection of this elements DataAttributes
	 */
	Collection<ClientDataAttribute> getAttributes();

	/**
	 * getAttributeValue returns an {@link Optional} with the value for the requested attributes
	 * nameInData. If no attribute exist with the specified nameInData is an empty optional
	 * returned.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the attribute
	 * @return An {@link Optional} with the String value of the attribute or empty if no attribute
	 *         with the specified name exists.
	 */
	Optional<String> getAttributeValue(String nameInData);

}
