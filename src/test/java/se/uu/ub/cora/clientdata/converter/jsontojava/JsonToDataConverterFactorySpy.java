package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataConverterFactorySpy implements JsonToDataConverterFactory {

	public int numberOfTimesCalled = 0;

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		return null;
	}

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		numberOfTimesCalled++;
		return null;
	}

}
