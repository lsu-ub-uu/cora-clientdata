/*
 * Copyright 2015, 2019 Uppsala University Library
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

package se.uu.ub.cora.clientdata.converter;

import se.uu.ub.cora.clientdata.ClientConvertible;

/**
 * JsonToDataConverter converts a json string to a DataPart object.
 */
public interface JsonToDataConverter {

	/**
	 * toInstance method is responsible for converting a json String to a DataPart object.
	 * <p>
	 * If an exception occurs during conversion MUST an exception implementing
	 * {@link ConversionException} be thrown.
	 * 
	 * @return a Convertible converted from the json String
	 */
	ClientConvertible toInstance();

}
