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

import java.util.Arrays;

import se.uu.ub.cora.clientdata.Action;
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
		if (isActionLinks()) {
			JsonToDataConverterFactoryImp factory = new JsonToDataConverterFactoryImp();
			return JsonToDataActionLinksConverter.forJsonObjectUsingConverterFactory(jsonObject, factory);
		}
		if (isActionLink()) {
			JsonToDataConverterFactoryImp factory = new JsonToDataConverterFactoryImp();
			return JsonToDataActionLinkConverter.forJsonObjectUsingFactory(jsonObject, factory);
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
		String firstKey = getFirstKeyInJsonObject();
		return keyIsFoundInActionEnum(firstKey);
	}

	private String getFirstKeyInJsonObject() {
		return jsonObject.keySet().iterator().next();
	}

	private boolean keyIsFoundInActionEnum(String firstKey) {
		return Arrays.stream(Action.values()).anyMatch(action -> action.name().equals(firstKey.toUpperCase()));
	}

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		OrgJsonParser jsonParser = new OrgJsonParser();
		JsonValue jsonValue = jsonParser.parseString(json);

		return createForJsonObject(jsonValue);
	}

	public boolean isActionLink() {
		return jsonObject.containsKey("rel");
	}
}
