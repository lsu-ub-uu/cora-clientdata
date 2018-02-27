/*
 * Copyright 2015 Uppsala University Library
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

import java.util.Map;

import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public final class DataRecordToJsonConverter {

	private JsonBuilderFactory jsonBuilderFactory;
	private ClientDataRecord clientDataRecord;
	private JsonObjectBuilder recordJsonObjectBuilder;

	public static DataRecordToJsonConverter usingJsonFactoryForClientDataRecord(
			JsonBuilderFactory jsonFactory, ClientDataRecord clientDataRecord) {
		return new DataRecordToJsonConverter(jsonFactory, clientDataRecord);
	}

	private DataRecordToJsonConverter(JsonBuilderFactory jsonFactory,
			ClientDataRecord clientDataRecord) {
		this.jsonBuilderFactory = jsonFactory;
		this.clientDataRecord = clientDataRecord;
		recordJsonObjectBuilder = jsonFactory.createObjectBuilder();
	}

	public String toJson() {
		return toJsonObjectBuilder().toJsonFormattedString();
	}

	JsonObjectBuilder toJsonObjectBuilder() {
		convertMainClientDataGroup();
		convertActionLinks();
		convertKeys();
		return createTopLevelJsonObjectWithRecordAsChild();
	}

	private void convertMainClientDataGroup() {
		DataToJsonConverterFactory dataToJsonConverterFactory = new DataToJsonConverterFactoryImp();
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(jsonBuilderFactory, clientDataRecord.getClientDataGroup());
		JsonObjectBuilder jsonDataGroupObjectBuilder = dataToJsonConverter.toJsonObjectBuilder();
		recordJsonObjectBuilder.addKeyJsonObjectBuilder("data", jsonDataGroupObjectBuilder);
	}

	private void convertActionLinks() {
		if (recordHasActionLinks()) {
			addActionLinksToRecord();
		}
	}

	private boolean recordHasActionLinks() {
		return !clientDataRecord.getActionLinks().isEmpty();
	}

	private void addActionLinksToRecord() {
		Map<String, ActionLink> actionLinks = clientDataRecord.getActionLinks();
		ActionLinksToJsonConverter actionLinkConverter = new ActionLinksToJsonConverter(
				jsonBuilderFactory, actionLinks);
		JsonObjectBuilder actionLinksObject = actionLinkConverter.toJsonObjectBuilder();
		recordJsonObjectBuilder.addKeyJsonObjectBuilder("actionLinks", actionLinksObject);
	}

	private void convertKeys() {
		if (recordHasKeys()) {
			addKeysToRecord();
		}
	}

	private boolean recordHasKeys() {
		return !clientDataRecord.getKeys().isEmpty();
	}

	private void addKeysToRecord() {
		JsonArrayBuilder keyBuilder = jsonBuilderFactory.createArrayBuilder();
		for (String key : clientDataRecord.getKeys()) {
			keyBuilder.addString(key);
		}
		recordJsonObjectBuilder.addKeyJsonArrayBuilder("keys", keyBuilder);
	}

	private JsonObjectBuilder createTopLevelJsonObjectWithRecordAsChild() {
		JsonObjectBuilder rootWrappingJsonObjectBuilder = jsonBuilderFactory.createObjectBuilder();
		rootWrappingJsonObjectBuilder.addKeyJsonObjectBuilder("record", recordJsonObjectBuilder);
		return rootWrappingJsonObjectBuilder;
	}

}
