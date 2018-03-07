package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataConverterFactoryForDataRecordSpy implements JsonToDataConverterFactory {

	public List<JsonObject> jsonObjects = new ArrayList<>();
	public int numOfTimesFactoryCalled = 0;

	public List<JsonToDataConverterSpy> factoredConverters = new ArrayList<>();
	public List<JsonToDataActionLinkConverterSpy> factoredActionLinksConverters = new ArrayList<>();

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		numOfTimesFactoryCalled++;
		jsonObjects.add((JsonObject) jsonValue);
		JsonToDataConverterSpy converterSpy = new JsonToDataConverterSpy((JsonObject) jsonValue);
		factoredConverters.add(converterSpy);
		return converterSpy;
	}

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		return null;
	}

	@Override
	public JsonToDataActionLinkConverter createActionLinksConverterForJsonString(String json) {
		return null;
	}

	@Override
	public JsonToDataActionLinkConverter createJsonToDataActionLinkConverterForJsonObject(
			JsonValue jsonValue) {
		numOfTimesFactoryCalled++;
		JsonToDataActionLinkConverterSpy actionLinkConverterSpy = new JsonToDataActionLinkConverterSpy(
				(JsonObject) jsonValue);
		factoredActionLinksConverters.add(actionLinkConverterSpy);
		return actionLinkConverterSpy;
	}

}
