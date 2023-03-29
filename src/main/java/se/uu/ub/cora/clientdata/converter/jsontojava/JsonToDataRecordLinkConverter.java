package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataGroupImp;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.clientdata.converter.javatojson.Convertible;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataRecordLinkConverter extends JsonToDataGroupConverter {

	private static final String ACTION_LINKS = "actionLinks";
	private ClientDataRecordLink clientDataRecordLink;

	public JsonToDataRecordLinkConverter(JsonObject jsonObject,
			JsonToDataConverterFactory factory) {
		super(jsonObject, factory);
	}

	public static JsonToDataRecordLinkConverter forJsonObjectUsingConverterFactory(
			JsonObject jsonObject, JsonToDataConverterFactory factory) {
		return new JsonToDataRecordLinkConverter(jsonObject, factory);
	}

	@Override
	public Convertible toInstance() {
		validateOnlyCorrectKeysAtTopLevel();
		String nameInData = getNameInDataFromJsonObject();
		clientDataRecordLink = ClientDataRecordLink.withNameInData(nameInData);
		addChildrenToGroup();
		possiblyAddAdditionalContent();
		return clientDataRecordLink;
	}

	@Override
	protected void validateNoOfKeysAtTopLevel() {
		Set<String> allowedKeys = getAllowedKeys();
		if (jsonObjectContainsNotAllowedKeys(allowedKeys)) {
			throw new JsonParseException(
					"Group data must contain name and children, and may contain "
							+ "attributes or repeatId or actionLinks"
							+ jsonObject.keySet().toString());
		}
	}

	private boolean jsonObjectContainsNotAllowedKeys(Set<String> allowedKeys) {
		return !allowedKeys.containsAll(jsonObject.keySet());
	}

	private Set<String> getAllowedKeys() {
		Set<String> allowedKeys = new LinkedHashSet<>();
		allowedKeys.add("attributes");
		allowedKeys.add("repeatId");
		allowedKeys.add(ACTION_LINKS);
		allowedKeys.add("name");
		allowedKeys.add("children");
		return allowedKeys;
	}

	private void possiblyAddAdditionalContent() {
		possiblyAddRepeatId();
		possiblyAddAttributes();
		possiblyAddActionLinks();
	}

	private void possiblyAddActionLinks() {
		if (hasActionLinks()) {
			JsonObject actionLinks = jsonObject.getValueAsJsonObject(ACTION_LINKS);
			for (Map.Entry<String, JsonValue> actionLinkEntry : actionLinks.entrySet()) {
				convertAndAddActionLink(actionLinkEntry);
			}
		}
	}

	private void convertAndAddActionLink(Map.Entry<String, JsonValue> actionLinkEntry) {
		JsonToDataActionLinkConverter actionLinkConverter = factory
				.createJsonToDataActionLinkConverterForJsonObject(actionLinkEntry.getValue());
		ActionLink actionLink = (ActionLink) actionLinkConverter.toInstance();
		clientDataRecordLink.addActionLink(actionLinkEntry.getKey(), actionLink);
	}

	private boolean hasActionLinks() {
		return jsonObject.containsKey(ACTION_LINKS);
	}

	@Override
	protected ClientDataGroupImp getMainDataGroup() {
		return clientDataRecordLink;
	}

}
