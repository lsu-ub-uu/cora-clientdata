package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataConverterFactorySpy implements JsonToDataConverterFactory {

	public int numberOfTimesCalled = 0;
	public List<JsonToDataConverterSpy> factoredConverters = new ArrayList<>();

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		return null;
	}

	@Override
	public JsonToDataActionLinkConverter createActionLinksConverterForJsonString(String json) {
		return null;
	}

	@Override
	public JsonToDataActionLinkConverter createJsonToDataActionLinkConverterForJsonObject(JsonValue jsonValue) {
		return null;
	}

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		numberOfTimesCalled++;
		JsonToDataConverterSpy converterSpy = new JsonToDataConverterSpy((JsonObject) jsonValue);
		factoredConverters.add(converterSpy);
		return converterSpy;
	}

}
