package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;

public class JsonToDataRecordConverter {

	private JsonObject jsonObject;
	private JsonObject jsonObjectRecord;
	private JsonToDataConverterFactory factory;

	public JsonToDataRecordConverter(JsonObject jsonObject, JsonToDataConverterFactory factory) {
		this.jsonObject = jsonObject;
		this.factory = factory;
	}

	public static JsonToDataRecordConverter forJsonObjectUsingConverterFactory(
			JsonObject jsonObject, JsonToDataConverterFactory factory) {
		return new JsonToDataRecordConverter(jsonObject, factory);
	}

	public ClientDataRecord toInstance() {
		try {
			return tryToInstanciate();
		} catch (Exception e) {
			throw new JsonParseException("Error parsing jsonRecord: " + e.getMessage(), e);
		}

	}

	private ClientDataRecord tryToInstanciate() {
		validateOnlyRecordKeyAtTopLevel();
		jsonObjectRecord = jsonObject.getValueAsJsonObject("record");
		validateOnlyCorrectKeysAtSecondLevel();

		JsonObject jsonDataObject = jsonObjectRecord.getValueAsJsonObject("data");

		JsonToDataConverter converter = factory.createForJsonObject(jsonDataObject);

		ClientDataGroup clientDataGroup = (ClientDataGroup) converter.toInstance();

		JsonObject actionLinks = jsonObjectRecord.getValueAsJsonObject("actionLinks");
		JsonToDataConverter actionLinksConverter = factory.createForJsonObject(actionLinks);
		return ClientDataRecord.withClientDataGroup(clientDataGroup);
	}

	private void validateOnlyRecordKeyAtTopLevel() {
		if (!jsonObject.containsKey("record")) {
			throw new JsonParseException("Record data must contain key: record");
		}
		if (jsonObject.keySet().size() != 1) {
			throw new JsonParseException("Record data must contain only key: record");
		}
	}

	private void validateOnlyCorrectKeysAtSecondLevel() {

		if (!jsonObjectRecord.containsKey("data")) {
			throw new JsonParseException("Record data must contain child with key: data");
		}
		if (!jsonObjectRecord.containsKey("actionLinks")) {
			throw new JsonParseException("Record data must contain child with key: actionLinks");
		}
		if (jsonObjectRecord.keySet().size() != 2) {
			throw new JsonParseException(
					"Record data must contain only keys: data and actionLinks");
		}
	}

}
