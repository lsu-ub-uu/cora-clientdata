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

package se.uu.ub.cora.clientdata.converter.javatojson;

import java.util.Map.Entry;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public class DataGroupToJsonConverter extends DataToJsonConverter {

	protected ClientDataGroup clientDataGroup;
	protected JsonObjectBuilder dataGroupJsonObjectBuilder;
	protected JsonBuilderFactory jsonBuilderFactory;

	protected DataGroupToJsonConverter(JsonBuilderFactory factory, ClientDataGroup clientDataGroup) {
		this.jsonBuilderFactory = factory;
		this.clientDataGroup = clientDataGroup;
		dataGroupJsonObjectBuilder = factory.createObjectBuilder();
	}

	public static DataGroupToJsonConverter usingJsonFactoryForClientDataGroup(
			JsonBuilderFactory factory, ClientDataGroup clientDataGroup) {
		return new DataGroupToJsonConverter(factory, clientDataGroup);
	}

	@Override
	public JsonObjectBuilder toJsonObjectBuilder() {
		possiblyAddRepeatId();
		if (hasAttributes()) {
			addAttributesToGroup();
		}
		if (hasChildren()) {
			addChildrenToGroup();
		}
		dataGroupJsonObjectBuilder.addKeyString("name", clientDataGroup.getNameInData());
		return dataGroupJsonObjectBuilder;
	}

	private void possiblyAddRepeatId() {
		if (hasNonEmptyRepeatId()) {
			dataGroupJsonObjectBuilder.addKeyString("repeatId", clientDataGroup.getRepeatId());
		}
	}

	private boolean hasNonEmptyRepeatId() {
		return clientDataGroup.getRepeatId() != null && !clientDataGroup.getRepeatId().equals("");
	}

	private boolean hasAttributes() {
		return !clientDataGroup.getAttributes().isEmpty();
	}

	private void addAttributesToGroup() {
		JsonObjectBuilder attributes = jsonBuilderFactory.createObjectBuilder();
		for (Entry<String, String> attributeEntry : clientDataGroup.getAttributes().entrySet()) {
			attributes.addKeyString(attributeEntry.getKey(), attributeEntry.getValue());
		}
		dataGroupJsonObjectBuilder.addKeyJsonObjectBuilder("attributes", attributes);
	}

	private boolean hasChildren() {
		return !clientDataGroup.getChildren().isEmpty();
	}

	private void addChildrenToGroup() {
		DataToJsonConverterFactory dataToJsonConverterFactory = new DataToJsonConverterFactoryImp();
		JsonArrayBuilder childrenArray = jsonBuilderFactory.createArrayBuilder();
		for (ClientDataElement clientDataElement : clientDataGroup.getChildren()) {
			childrenArray.addJsonObjectBuilder(dataToJsonConverterFactory
					.createForClientDataElement(jsonBuilderFactory, clientDataElement)
					.toJsonObjectBuilder());
		}
		dataGroupJsonObjectBuilder.addKeyJsonArrayBuilder("children", childrenArray);
	}

}
