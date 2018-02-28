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

package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.Map.Entry;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class JsonToDataConverterFactoryImp implements JsonToDataConverterFactory {

	private JsonObject jsonObject;

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		if (!(jsonValue instanceof JsonObject)) {
			throw new JsonParseException("Json value is not an object, can not convert");
		}
		jsonObject = (JsonObject) jsonValue;

		if (isGroup()) {
			return JsonToDataGroupConverter.forJsonObject(jsonObject);
		}
		if (isAtomicData()) {
			return JsonToDataAtomicConverter.forJsonObject(jsonObject);
		}
		// TODO: hur avgöra att detta är actionLinks??
		if (isActionLinks()) {
			return JsonToDataActionLinksConverter.forJsonObject(jsonObject);
		}
		return JsonToDataAttributeConverter.forJsonObject(jsonObject);
	}

	private boolean isAtomicData() {
		return jsonObject.containsKey("value");
	}

	private boolean isGroup() {
		return jsonObject.containsKey("children");
	}

	private boolean isActionLinks() {
		for (Entry<String, JsonValue> entry : jsonObject.entrySet()) {
			if (entry.getValue() instanceof JsonObject) {
				JsonObject child = (JsonObject) entry.getValue();
				if (child.containsKey("requestMethod")) {
					return true;
				}
			}
		}
		return false;
		// return jsonObject.containsKey("read") || jsonObject.containsKey("update");
	}

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		OrgJsonParser jsonParser = new OrgJsonParser();
		JsonValue jsonValue = jsonParser.parseString(json);

		return createForJsonObject(jsonValue);
	}
}
