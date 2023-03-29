/*
 * Copyright 2015, 2019, 2021 Uppsala University Library
 * Copyright 2016 Olov McKie
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
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

/**
 * DataToJsonConverter is an interface for classes that convert {@link ClientConvertible} objects to
 * String with json or {@link JsonObjectBuilder} classes.
 */
public interface ClientDataToJsonConverter {
	/**
	 * toJsonObjectBuilder return a {@link JsonObjectBuilder} representation of the
	 * {@link ClientConvertible} that is to be converted.
	 * 
	 * @return A JsonObectBuilder set up to build the Convertible.
	 */
	JsonObjectBuilder toJsonObjectBuilder();

	/**
	 * toJsonCompactFormat return a String with a json String representation of the
	 * {@link ClientConvertible} that is to be converted using as little whitespace as possible.
	 * 
	 * @return A String with the json representation of the Convertible.
	 */
	String toJsonCompactFormat();

	/**
	 * toJson return a String with a json String representation of the {@link ClientConvertible} that is
	 * to be converted using a text representation that is as readable as possible, using whitespace
	 * and indentation to make the json more readable.
	 * 
	 * @return A String with the json representation of the Convertible.
	 */
	String toJson();

}
