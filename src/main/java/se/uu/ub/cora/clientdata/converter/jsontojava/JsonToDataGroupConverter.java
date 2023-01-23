/*
 * Copyright 2015, 2018 Uppsala University Library
 *
 * This file is part of Cora.
 *
 *     Cora is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Cora is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Cora.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.uu.ub.cora.clientdata.converter.jsontojava;

import java.util.Map.Entry;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.converter.javatojson.Convertible;
import se.uu.ub.cora.json.parser.JsonArray;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonString;
import se.uu.ub.cora.json.parser.JsonValue;

public class JsonToDataGroupConverter implements JsonToDataConverter {

	protected JsonToDataConverterFactory factory;
	private static final int ONE_OPTIONAL_KEY_IS_PRESENT = 3;
	private static final String CHILDREN = "children";
	private static final String ATTRIBUTES = "attributes";
	private static final int NUM_OF_ALLOWED_KEYS_AT_TOP_LEVEL = 4;
	protected JsonObject jsonObject;
	private ClientDataGroup clientDataGroup;

	public JsonToDataGroupConverter(JsonObject jsonObject, JsonToDataConverterFactory factory) {
		this.jsonObject = jsonObject;
		this.factory = factory;
	}

	public static JsonToDataGroupConverter forJsonObjectUsingConverterFactory(JsonObject jsonObject,
			JsonToDataConverterFactory factory) {
		return new JsonToDataGroupConverter(jsonObject, factory);
	}

	@Override
	public Convertible toInstance() {
		try {
			return tryToInstanciate();
		} catch (Exception e) {
			throw new JsonParseException("Error parsing jsonObject: " + e.getMessage(), e);
		}
	}

	private Convertible tryToInstanciate() {
		validateOnlyCorrectKeysAtTopLevel();
		return createDataGroupInstance();
	}

	protected String getNameInDataFromJsonObject() {
		return jsonObject.getValueAsJsonString("name").getStringValue();
	}

	protected void validateOnlyCorrectKeysAtTopLevel() {

		if (!jsonObject.containsKey("name")) {
			throw new JsonParseException("Group data must contain key: name");
		}

		if (!hasChildren()) {
			throw new JsonParseException("Group data must contain key: children");
		}

		validateNoOfKeysAtTopLevel();
	}

	protected void validateNoOfKeysAtTopLevel() {
		if (threeKeysAtTopLevelButAttributeAndRepeatIdIsMissing()) {
			throw new JsonParseException(
					"Group data must contain name and children, and may contain "
							+ "attributes or repeatId" + jsonObject.keySet().toString());
		}
		if (maxKeysAtTopLevelButAttributeOrRepeatIdIsMissing()) {
			throw new JsonParseException("Group data must contain key: attributes");
		}

		if (moreKeysAtTopLevelThanAllowed()) {
			throw new JsonParseException(
					"Group data can only contain keys: name, children and attributes");
		}
	}

	private boolean threeKeysAtTopLevelButAttributeAndRepeatIdIsMissing() {
		int oneOptionalKeyPresent = ONE_OPTIONAL_KEY_IS_PRESENT;
		return jsonObject.keySet().size() == oneOptionalKeyPresent && !hasAttributes()
				&& !hasRepeatId();
	}

	private boolean maxKeysAtTopLevelButAttributeOrRepeatIdIsMissing() {
		return jsonObject.keySet().size() == NUM_OF_ALLOWED_KEYS_AT_TOP_LEVEL
				&& (!hasAttributes() || !hasRepeatId());
	}

	protected boolean moreKeysAtTopLevelThanAllowed() {
		return jsonObject.keySet().size() > NUM_OF_ALLOWED_KEYS_AT_TOP_LEVEL;
	}

	private Convertible createDataGroupInstance() {
		String nameInData = getNameInDataFromJsonObject();
		clientDataGroup = ClientDataGroup.withNameInData(nameInData);
		possiblyAddRepeatId();
		possiblyAddAttributes();
		addChildrenToGroup();
		return getMainDataGroup();
	}

	protected void possiblyAddAttributes() {
		if (hasAttributes()) {
			addAttributesToGroup();
		}
	}

	protected boolean hasAttributes() {
		return jsonObject.containsKey(ATTRIBUTES);
	}

	protected ClientDataGroup getMainDataGroup() {
		return clientDataGroup;
	}

	protected void possiblyAddRepeatId() {
		if (hasRepeatId()) {
			getMainDataGroup()
					.setRepeatId(jsonObject.getValueAsJsonString("repeatId").getStringValue());
		}
	}

	protected boolean hasRepeatId() {
		return jsonObject.containsKey("repeatId");
	}

	protected void addAttributesToGroup() {
		JsonObject attributes = jsonObject.getValueAsJsonObject(ATTRIBUTES);
		for (Entry<String, JsonValue> attributeEntry : attributes.entrySet()) {
			addAttributeToGroup(attributeEntry);
		}
	}

	private void addAttributeToGroup(Entry<String, JsonValue> attributeEntry) {
		String value = ((JsonString) attributeEntry.getValue()).getStringValue();
		getMainDataGroup().addAttributeByIdWithValue(attributeEntry.getKey(), value);
	}

	private boolean hasChildren() {
		return jsonObject.containsKey(CHILDREN);
	}

	protected void addChildrenToGroup() {
		JsonArray children = jsonObject.getValueAsJsonArray(CHILDREN);
		for (JsonValue child : children) {
			addChildToGroup((JsonObject) child);
		}
	}

	private void addChildToGroup(JsonObject child) {
		JsonToDataConverter childJsonToDataConverter = factory.createForJsonObject(child);
		getMainDataGroup().addChild((ClientDataElement) childJsonToDataConverter.toInstance());
	}

}
