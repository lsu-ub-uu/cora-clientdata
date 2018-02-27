package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataConverterFactoryOnlyGroupConverterSpy implements JsonToDataConverterFactory {

	@Override
	public JsonToDataConverter createForJsonObject(JsonValue jsonValue) {
		return JsonToDataGroupConverter.forJsonObject((JsonObject) jsonValue);
	}

	@Override
	public JsonToDataConverter createForJsonString(String json) {
		// TODO Auto-generated method stub
		return null;
	}

}
