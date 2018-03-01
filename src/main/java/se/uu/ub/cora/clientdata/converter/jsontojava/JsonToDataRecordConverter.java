package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.ClientDataActionLinks;
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

		ClientDataGroup clientDataGroup = convertDataGroup();
		ClientDataActionLinks actionlinks = convertDataActionLinks();

		ClientDataRecord clientDataRecord = ClientDataRecord.withClientDataGroup(clientDataGroup);
		clientDataRecord.setActionLinks(actionlinks);
		return clientDataRecord;
	}

	private ClientDataGroup convertDataGroup() {
		JsonObject jsonDataObject = jsonObjectRecord.getValueAsJsonObject("data");
		JsonToDataConverter converter = factory.createForJsonObject(jsonDataObject);
		return (ClientDataGroup) converter.toInstance();
	}

	private ClientDataActionLinks convertDataActionLinks() {
		JsonObject actionLinksObject = jsonObjectRecord.getValueAsJsonObject("actionLinks");
		JsonToDataConverter actionLinksConverter = factory.createForJsonObject(actionLinksObject);
		return (ClientDataActionLinks) actionLinksConverter.toInstance();
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
