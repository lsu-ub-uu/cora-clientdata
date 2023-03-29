/*
 * Copyright 2015 Uppsala University Library
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

import se.uu.ub.cora.json.parser.JsonObject;

public interface JsonToClientDataConverterFactory {

	/**
	 * factorUsingString factors a new JsonToDataConverter for a given json string. The converter
	 * converts Json to Datagroups.
	 * </p>
	 * All implementations of this interface MUST be thread safe.
	 * 
	 * @param json
	 *            a String with a Json that will be converted to DataGroup in the converter.
	 * @return A newly created JsonToClientDataConverter.
	 */
	JsonToClientDataConverter factorUsingString(String json);

	/**
	 * factorUsingJsonObject factors a new JsonToDataConverter for a given json JsonObject. The
	 * converter converts JsonObject to Datagroups.
	 * </p>
	 * All implementations of this interface MUST be thread safe.
	 * 
	 * @param json
	 *            the JsonObject that will be converted to DataGroup in the converter.
	 * @return A newly created JsonToClientDataConverter.
	 */
	JsonToClientDataConverter factorUsingJsonObject(JsonObject jsonObject);

}
