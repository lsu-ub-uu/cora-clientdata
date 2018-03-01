package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataConverterFactoryOnlyGroupConverterSpy implements JsonToDataConverterFactory {

	public List<JsonObject> jsonObjects = new ArrayList<>();
	public int numOfTimesFactoryCalled = 0;

	public List<JsonToDataConverterSpy> factoredConverters = new ArrayList<>();

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		numOfTimesFactoryCalled++;
		jsonObjects.add((JsonObject) jsonValue);
		JsonToDataConverterSpy converterSpy = new JsonToDataConverterSpy(
				(JsonObject) jsonValue);
		factoredConverters.add(converterSpy);
		return converterSpy;
	}

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
