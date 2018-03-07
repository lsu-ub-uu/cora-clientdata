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

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class JsonToDataConverterFactoryImp implements JsonToDataConverterFactory {

	private static final String CHILDREN_STRING = "children";

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		JsonToDataConverterFactoryImp factory = new JsonToDataConverterFactoryImp();
		if (!(jsonValue instanceof JsonObject)) {
			throw new JsonParseException("Json value is not an object, can not convert");
		}
		JsonObject jsonObject = (JsonObject) jsonValue;
		if (isRecordLink(jsonObject)) {
			return JsonToDataRecordLinkConverter.forJsonObjectUsingConverterFactory(jsonObject,
					factory);
		}
		if (isGroup(jsonObject)) {
			return JsonToDataGroupConverter.forJsonObjectUsingConverterFactory(jsonObject, factory);
		}
		if (isAtomicData(jsonObject)) {
			return JsonToDataAtomicConverter.forJsonObject(jsonObject);
		}

		return JsonToDataAttributeConverter.forJsonObject(jsonObject);
	}

	private boolean isRecordLink(JsonObject jsonObject) {
		if (jsonObject.containsKey(CHILDREN_STRING)) {
			return checkIfChildrenContainRecordLink(jsonObject);
		}
		return false;
	}

	private boolean checkIfChildrenContainRecordLink(JsonObject jsonObject) {
		List<String> foundNames = collectNameInDataFromChildren(jsonObject);
		return childrenNamesIndicatesRecordLink(foundNames);
	}

	private List<String> collectNameInDataFromChildren(JsonObject jsonObject) {
		JsonArray children = jsonObject.getValueAsJsonArray(CHILDREN_STRING);
		List<String> foundNames = new ArrayList<>();
		for (JsonValue child : children) {
			String name = getNameInDataFromChild(child);
			foundNames.add(name);
		}
		return foundNames;
	}

	private String getNameInDataFromChild(JsonValue child) {
		JsonObject value = (JsonObject) child;
		return value.getValueAsJsonString("name").getStringValue();
	}

	private boolean childrenNamesIndicatesRecordLink(List<String> foundNames) {
		List<String> requiredNames = getRequiredNames();
		return foundNames.containsAll(requiredNames);
	}

	private List<String> getRequiredNames() {
		List<String> requiredNames = new ArrayList<>();
		requiredNames.add("linkedRecordType");
		requiredNames.add("linkedRecordId");
		return requiredNames;
	}

	private boolean isAtomicData(JsonObject jsonObject) {
		return jsonObject.containsKey("value");
	}

	private boolean isGroup(JsonObject jsonObject) {
		return jsonObject.containsKey(CHILDREN_STRING);
	}

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		OrgJsonParser jsonParser = new OrgJsonParser();
		JsonValue jsonValue = jsonParser.parseString(json);
		return createForJsonObject(jsonValue);
	}

	@Override
	public JsonToDataActionLinkConverter createActionLinksConverterForJsonString(String json) {
		OrgJsonParser jsonParser = new OrgJsonParser();
		JsonValue jsonValue = jsonParser.parseString(json);
		return createJsonToDataActionLinkConverterForJsonObject(jsonValue);
	}

	@Override
	public JsonToDataActionLinkConverter createJsonToDataActionLinkConverterForJsonObject(
			JsonValue jsonValue) {
		JsonToDataConverterFactoryImp factory = new JsonToDataConverterFactoryImp();
		if (!(jsonValue instanceof JsonObject)) {
			throw new JsonParseException("Json value is not an object, can not convert");
		}
		return JsonToDataActionLinkConverterImp.forJsonObjectUsingFactory((JsonObject) jsonValue,
				factory);

	}
}
