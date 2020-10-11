/*
 * Copyright 2015, 2018 Uppsala University Library
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

package se.uu.ub.cora.clientdata.converter.javatojson;

import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public final class DataAttributeToJsonConverter extends DataToJsonConverter {
	private JsonBuilderFactory jsonBuilderFactory;
	private ClientDataAttribute clientDataAttribute;

	public static DataToJsonConverter usingJsonFactoryForClientDataAttribute(
			JsonBuilderFactory factory, ClientDataAttribute dataAttribute) {
		return new DataAttributeToJsonConverter(factory, dataAttribute);
	}

	private DataAttributeToJsonConverter(JsonBuilderFactory factory,
			ClientDataAttribute dataAttribute) {
		this.jsonBuilderFactory = factory;
		this.clientDataAttribute = dataAttribute;
	}

	@Override
	public JsonObjectBuilder toJsonObjectBuilder() {
		JsonObjectBuilder jsonObjectBuilder = jsonBuilderFactory.createObjectBuilder();

		jsonObjectBuilder.addKeyString(clientDataAttribute.getNameInData(),
				clientDataAttribute.getValue());
		return jsonObjectBuilder;
	}

	JsonBuilderFactory getJsonBuilderFactory() {
		// needed for test
		return jsonBuilderFactory;
	}

	ClientDataElement getClientDataAttribute() {
		// needed for test
		return clientDataAttribute;
	}

}
