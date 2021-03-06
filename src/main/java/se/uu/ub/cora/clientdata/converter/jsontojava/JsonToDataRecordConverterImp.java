package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.Map;

import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.clientdata.DataRecord;
import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonString;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataRecordConverterImp implements JsonToDataRecordConverter {

	private static final String PERMISSIONS = "permissions";
	private static final String ACTION_LINKS = "actionLinks";
	private static final int NUM_OF_ALLOWED_KEYS = 3;
	private JsonObject jsonObject;
	private JsonObject jsonObjectRecord;
	private JsonToDataConverterFactory factory;
	private ClientDataRecord clientDataRecord;

	public JsonToDataRecordConverterImp(JsonToDataConverterFactory factory) {
		this.factory = factory;
	}

	public static JsonToDataRecordConverterImp usingConverterFactory(
			JsonToDataConverterFactory factory) {
		return new JsonToDataRecordConverterImp(factory);
	}

	@Override
	public DataRecord toInstance(JsonObject jsonObject) {
		try {
			this.jsonObject = jsonObject;
			return tryToInstanciate();
		} catch (Exception e) {
			throw new JsonParseException("Error parsing jsonRecord: " + e.getMessage(), e);
		}
	}

	private DataRecord tryToInstanciate() {
		validateOnlyRecordKeyAtTopLevel();
		jsonObjectRecord = jsonObject.getValueAsJsonObject("record");
		validateOnlyCorrectKeysAtSecondLevel();

		ClientDataGroup clientDataGroup = convertDataGroup();

		clientDataRecord = ClientDataRecord.withClientDataGroup(clientDataGroup);
		possiblyAddActionLinks();
		possiblyAddPermissions();
		return clientDataRecord;
	}

	private void possiblyAddPermissions() {
		if (jsonObjectRecord.containsKey(PERMISSIONS)) {
			JsonObject permissions = jsonObjectRecord.getValueAsJsonObject(PERMISSIONS);
			possiblyAddReadPermissions(permissions);
			possiblyAddWritePermissions(permissions);
		}
	}

	private void possiblyAddReadPermissions(JsonObject permissions) {
		if (permissions.containsKey("read")) {
			JsonArray readPermissions = permissions.getValueAsJsonArray("read");
			addReadPermissions(readPermissions);
		}
	}

	private void addReadPermissions(JsonArray readPermissions) {
		for (JsonValue value : readPermissions) {
			String permission = getJsonValueAsString((JsonString) value);
			clientDataRecord.addReadPermission(permission);
		}
	}

	private String getJsonValueAsString(JsonString value) {
		return value.getStringValue();
	}

	private void possiblyAddWritePermissions(JsonObject permissions) {
		if (permissions.containsKey("write")) {
			JsonArray writePermissions = permissions.getValueAsJsonArray("write");
			addWritePermissions(writePermissions);
		}
	}

	private void addWritePermissions(JsonArray writePermissions) {
		for (JsonValue value : writePermissions) {
			String permission = getJsonValueAsString((JsonString) value);
			clientDataRecord.addWritePermission(permission);
		}
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
		if (moreKeysThanAllowed() || maxNumOfKeysButPermissionsIsMissing()) {
			throw new JsonParseException(
					"Record data must contain only keys: data and actionLinks and permissions");

		}
		if (!jsonObjectRecord.containsKey("data")) {
			throw new JsonParseException("Record data must contain child with key: data");
		}
		if (!jsonObjectRecord.containsKey(ACTION_LINKS)) {
			throw new JsonParseException("Record data must contain child with key: actionLinks");
		}
	}

	private boolean moreKeysThanAllowed() {
		return jsonObjectRecord.keySet().size() > NUM_OF_ALLOWED_KEYS;
	}

	private boolean maxNumOfKeysButPermissionsIsMissing() {
		return jsonObjectRecord.keySet().size() == NUM_OF_ALLOWED_KEYS
				&& !jsonObjectRecord.containsKey(PERMISSIONS);
	}

	private ClientDataGroup convertDataGroup() {
		JsonObject jsonDataObject = jsonObjectRecord.getValueAsJsonObject("data");
		JsonToDataConverter converter = factory.createForJsonObject(jsonDataObject);
		return (ClientDataGroup) converter.toInstance();
	}

	private void possiblyAddActionLinks() {
		JsonObject actionLinks = jsonObjectRecord.getValueAsJsonObject(ACTION_LINKS);
		for (Map.Entry<String, JsonValue> actionLinkEntry : actionLinks.entrySet()) {
			convertAndAddActionLink(actionLinkEntry);
		}
	}

	private void convertAndAddActionLink(Map.Entry<String, JsonValue> actionLinkEntry) {
		JsonToDataActionLinkConverter actionLinkConverter = factory
				.createJsonToDataActionLinkConverterForJsonObject(actionLinkEntry.getValue());
		ActionLink actionLink = (ActionLink) actionLinkConverter.toInstance();
		clientDataRecord.addActionLink(actionLinkEntry.getKey(), actionLink);
	}

	public JsonToDataConverterFactory getConverterFactory() {
		// needed for test
		return factory;
	}

}
