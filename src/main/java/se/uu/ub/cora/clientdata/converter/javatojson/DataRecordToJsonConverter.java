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

import java.util.Map;
import java.util.Set;

import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public final class DataRecordToJsonConverter {

	private JsonBuilderFactory jsonBuilderFactory;
	private ClientDataRecord clientDataRecord;
	private JsonObjectBuilder recordJsonObjectBuilder;
	private DataToJsonConverterFactory dataToJsonConverterFactory;
	ActionLinksToJsonConverter actionLinkConverter;

	private DataRecordToJsonConverter(JsonBuilderFactory jsonFactory,
			ClientDataRecord clientDataRecord,
			DataToJsonConverterFactory dataToJsonConverterFactory) {
		this.jsonBuilderFactory = jsonFactory;
		this.clientDataRecord = clientDataRecord;
		this.dataToJsonConverterFactory = dataToJsonConverterFactory;
		recordJsonObjectBuilder = jsonFactory.createObjectBuilder();
	}

	public static DataRecordToJsonConverter usingJsonFactoryForClientDataRecord(
			JsonBuilderFactory jsonFactory, ClientDataRecord clientDataRecord,
			DataToJsonConverterFactory dataToJsonConverterFactory) {
		return new DataRecordToJsonConverter(jsonFactory, clientDataRecord,
				dataToJsonConverterFactory);
	}

	public String toJson() {
		return toJsonObjectBuilder().toJsonFormattedString();
	}

	JsonObjectBuilder toJsonObjectBuilder() {
		convertMainClientDataGroup();
		convertActionLinks();
		possiblyConvertPermissions();
		return createTopLevelJsonObjectWithRecordAsChild();
	}

	private JsonArrayBuilder createJsonForPermissions(Set<String> permissions) {
		JsonArrayBuilder permissionsBuilder = jsonBuilderFactory.createArrayBuilder();
		for (String permission : permissions) {
			permissionsBuilder.addString(permission);
		}
		return permissionsBuilder;
	}

	private void convertMainClientDataGroup() {
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(jsonBuilderFactory,
						clientDataRecord.getClientDataGroup());
		JsonObjectBuilder jsonDataGroupObjectBuilder = dataToJsonConverter.toJsonObjectBuilder();
		recordJsonObjectBuilder.addKeyJsonObjectBuilder("data", jsonDataGroupObjectBuilder);
	}

	private void convertActionLinks() {
		if (recordHasActionLinks()) {
			addActionLinksToRecord();
		}
	}

	private boolean recordHasActionLinks() {
		return clientDataRecord.getActionLinks() != null
				&& !clientDataRecord.getActionLinks().isEmpty();
	}

	private void addActionLinksToRecord() {
		Map<String, ActionLink> actionLinks = clientDataRecord.getActionLinks();
		actionLinkConverter = new ActionLinksToJsonConverter(jsonBuilderFactory, actionLinks,
				dataToJsonConverterFactory);
		JsonObjectBuilder actionLinksObject = actionLinkConverter.toJsonObjectBuilder();
		recordJsonObjectBuilder.addKeyJsonObjectBuilder("actionLinks", actionLinksObject);
	}

	private void possiblyConvertPermissions() {
		if (recordHasReadPermissions() || recordHasWritePermissions()) {
			convertPermissions();
		}
	}

	private boolean recordHasReadPermissions() {
		return !clientDataRecord.getReadPermissions().isEmpty();
	}

	private boolean recordHasWritePermissions() {
		return !clientDataRecord.getWritePermissions().isEmpty();
	}

	private void convertPermissions() {
		JsonObjectBuilder permissionsJsonObjectBuilder = jsonBuilderFactory.createObjectBuilder();
		possiblyAddReadPermissions(permissionsJsonObjectBuilder);
		possiblyAddWritePermissions(permissionsJsonObjectBuilder);
		recordJsonObjectBuilder.addKeyJsonObjectBuilder("permissions",
				permissionsJsonObjectBuilder);
	}

	private void possiblyAddReadPermissions(JsonObjectBuilder permissionsJsonObjectBuilder) {
		if (recordHasReadPermissions()) {
			JsonArrayBuilder readPermissionsArray = createJsonForPermissions(
					clientDataRecord.getReadPermissions());
			permissionsJsonObjectBuilder.addKeyJsonArrayBuilder("read", readPermissionsArray);
		}
	}

	private void possiblyAddWritePermissions(JsonObjectBuilder permissionsJsonObjectBuilder) {
		if (recordHasWritePermissions()) {
			JsonArrayBuilder writePermissionsArray = createJsonForPermissions(
					clientDataRecord.getWritePermissions());
			permissionsJsonObjectBuilder.addKeyJsonArrayBuilder("write", writePermissionsArray);
		}
	}

	private JsonObjectBuilder createTopLevelJsonObjectWithRecordAsChild() {
		JsonObjectBuilder rootWrappingJsonObjectBuilder = jsonBuilderFactory.createObjectBuilder();
		rootWrappingJsonObjectBuilder.addKeyJsonObjectBuilder("record", recordJsonObjectBuilder);
		return rootWrappingJsonObjectBuilder;
	}

}
