/*
 * Copyright 2018 Uppsala University Library
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
package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.Map.Entry;

import se.uu.ub.cora.clientdata.ClientDataActionLinks;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataActionLinksConverter implements JsonToDataConverter {

	private JsonObject jsonObject;
	private JsonToDataConverterFactory factory;

	public JsonToDataActionLinksConverter(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JsonToDataActionLinksConverter(JsonObject jsonObject,
			JsonToDataConverterFactory factory) {
		this.jsonObject = jsonObject;
		this.factory = factory;
	}

	static JsonToDataActionLinksConverter forJsonObject(JsonObject jsonObject) {
		return new JsonToDataActionLinksConverter(jsonObject);
	}

	@Override
	public ClientDataElement toInstance() {
		ClientDataActionLinks clientDataActionLinks = new ClientDataActionLinks();
		for (Entry<String, JsonValue> entry : jsonObject.entrySet()) {
			factory.createForJsonObject(entry.getValue());
		}
		return clientDataActionLinks;
	}

	public static JsonToDataConverter forJsonObjectUsingConverterFactory(JsonObject jsonObject,
			JsonToDataConverterFactory factory) {
		return new JsonToDataActionLinksConverter(jsonObject, factory);
	}

}
