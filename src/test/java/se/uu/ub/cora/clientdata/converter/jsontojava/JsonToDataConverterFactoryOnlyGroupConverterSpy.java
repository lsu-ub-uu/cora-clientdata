package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonValue;

import java.util.ArrayList;
import java.util.List;

public class JsonToDataConverterFactoryOnlyGroupConverterSpy implements JsonToDataConverterFactory {

	public List<JsonObject> jsonObjects = new ArrayList<>();

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		jsonObjects.add((JsonObject) jsonValue);
		return JsonToDataGroupConverter.forJsonObject((JsonObject) jsonValue);
	}

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
