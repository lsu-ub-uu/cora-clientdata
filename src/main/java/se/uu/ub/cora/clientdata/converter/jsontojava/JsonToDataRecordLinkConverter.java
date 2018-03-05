package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonValue;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class JsonToDataRecordLinkConverter extends JsonToDataGroupConverter {

	private	ClientDataRecordLink clientDataRecordLink;
	private static final int NUM_OF_ALLOWED_KEYS_AT_TOP_LEVEL = 5;
	private static final int ONE_OPTIONAL_KEY_IS_PRESENT = 3;

	public JsonToDataRecordLinkConverter(JsonObject jsonObject) {
		super(jsonObject);
	}

	public JsonToDataRecordLinkConverter(JsonObject jsonObject, JsonToDataConverterFactoryImp factory) {
		super(jsonObject, factory);
	}

	public static JsonToDataRecordLinkConverter forJsonObject(JsonObject jsonObject) {
		return new JsonToDataRecordLinkConverter(jsonObject);
	}

	public static JsonToDataRecordLinkConverter forJsonObjectUsingConverterFactory(JsonObject jsonObject, JsonToDataConverterFactoryImp factory) {
		return new JsonToDataRecordLinkConverter(jsonObject, factory);
	}

	@Override
	public ClientDataElement toInstance() {
		validateOnlyCorrectKeysAtTopLevel();
		String nameInData = getNameInDataFromJsonObject();
		clientDataRecordLink = ClientDataRecordLink.withNameInData(nameInData);
		addRepeatIdToGroup();
		if (hasAttributes()) {
			addAttributesToGroup();
		}
		addChildrenToGroup();
		if(hasActionLinks()){
			JsonObject actionLinks = jsonObject.getValueAsJsonObject("actionLinks");
			for (Map.Entry<String, JsonValue> actionLinkEntry : actionLinks.entrySet()) {

			}
		}
		return clientDataRecordLink;
	}

	@Override
	protected void validateNoOfKeysAtTopLevel() {
		if(jsonObject.keySet().size() >= ONE_OPTIONAL_KEY_IS_PRESENT && jsonObject.keySet().size()<= NUM_OF_ALLOWED_KEYS_AT_TOP_LEVEL){
			Set<String> optionalKeys = new LinkedHashSet<>();
			optionalKeys.add("attributes");
			optionalKeys.add("repeatId");
			optionalKeys.add("actionLinks");
			optionalKeys.add("name");
			optionalKeys.add("children");
//			for(String key :jsonObject.keySet()){
				if(!optionalKeys.containsAll(jsonObject.keySet())){
					throw new JsonParseException(
							"Group data must contain name and children, and may contain "
									+ "attributes or repeatId or actionLinks" + jsonObject.keySet().toString());
				}
//			}
		}


		if (threeKeysAtTopLevelButAttributeAndRepeatIdAndActionLinksIsMissing()) {
			throw new JsonParseException(
					"Group data must contain name and children, and may contain "
							+ "attributes or repeatId or actionLinks" + jsonObject.keySet().toString());
		}
		if (maxKeysAtTopLevelButAttributeOrRepeatIdIsMissing()) {
			throw new JsonParseException("Group data must contain key: attributes");
		}

//		if (moreKeysAtTopLevelThanAllowed()) {
//			throw new JsonParseException(
//					"Group data can only contain keys: name, children and attributes");
//		}
	}

	private boolean threeKeysAtTopLevelButAttributeAndRepeatIdAndActionLinksIsMissing() {
		int oneOptionalKeyPresent = ONE_OPTIONAL_KEY_IS_PRESENT;
		return jsonObject.keySet().size() == oneOptionalKeyPresent && !hasAttributes()
				&& !hasRepeatId() && !hasActionLinks();
	}

	private boolean hasActionLinks() {
		return jsonObject.containsKey("actionLinks");
	}


	private boolean maxKeysAtTopLevelButAttributeOrRepeatIdIsMissing() {
		return jsonObject.keySet().size() == NUM_OF_ALLOWED_KEYS_AT_TOP_LEVEL
				&& (!hasAttributes() || !hasRepeatId() || !hasActionLinks());
	}

	@Override
	protected ClientDataGroup getMainDataGroup() {
		return clientDataRecordLink;
	}

}
