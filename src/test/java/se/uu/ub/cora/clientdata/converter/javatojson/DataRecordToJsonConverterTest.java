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

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.Action;
import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataGroupImp;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataRecordToJsonConverterTest {

	private DataToJsonConverterFactorySpy dataToJsonConverterFactory;
	private DataRecordToJsonConverter dataRecordToJsonConverter;
	private ClientDataRecord clientDataRecord;
	private JsonBuilderFactory jsonFactory;

	@BeforeMethod
	public void setUp() {
		ClientDataGroupImp clientDataGroup = ClientDataGroupImp.withNameInData("groupNameInData");
		clientDataRecord = ClientDataRecord.withClientDataGroup(clientDataGroup);
		jsonFactory = new OrgJsonBuilderFactoryAdapter();
		dataToJsonConverterFactory = new DataToJsonConverterFactorySpy();

	}

	@Test
	public void testToJson() {
		dataRecordToJsonConverter = DataRecordToJsonConverter.usingJsonFactoryForClientDataRecord(
				jsonFactory, clientDataRecord, dataToJsonConverterFactory);
		String jsonString = dataRecordToJsonConverter.toJson();
		assertEquals(jsonString, "{\"record\":{\"data\":{\"name\":\"groupNameInData\"}}}");
		assertEquals(dataToJsonConverterFactory.calledNumOfTimes, 1);
	}

	@Test
	public void testToJsonWithDataActionLinksIsNull() {
		clientDataRecord.setActionLinks(null);

		dataRecordToJsonConverter = DataRecordToJsonConverter.usingJsonFactoryForClientDataRecord(
				jsonFactory, clientDataRecord, dataToJsonConverterFactory);
		String jsonString = dataRecordToJsonConverter.toJson();

		assertEquals(jsonString, "{\"record\":{\"data\":{\"name\":\"groupNameInData\"}}}");
		assertEquals(dataToJsonConverterFactory.calledNumOfTimes, 1);
	}

	@Test
	public void testToJsonWithDataActionLinksButActionLinksIsEmpty() {
		ClientDataGroupImp clientDataGroup = ClientDataGroupImp.withNameInData("groupNameInData");
		ClientDataRecord clientDataRecord = ClientDataRecord.withClientDataGroup(clientDataGroup);

		clientDataRecord.setActionLinks(new HashMap<>());

		dataRecordToJsonConverter = DataRecordToJsonConverter.usingJsonFactoryForClientDataRecord(
				jsonFactory, clientDataRecord, dataToJsonConverterFactory);
		String jsonString = dataRecordToJsonConverter.toJson();

		assertEquals(jsonString, "{\"record\":{\"data\":{\"name\":\"groupNameInData\"}}}");
		assertEquals(dataToJsonConverterFactory.calledNumOfTimes, 1);
	}

	@Test
	public void testToJsonWithActionLinks() {
		clientDataRecord.addActionLink("read", createReadActionLink());
		dataRecordToJsonConverter = DataRecordToJsonConverter.usingJsonFactoryForClientDataRecord(
				jsonFactory, clientDataRecord, dataToJsonConverterFactory);
		String jsonString = dataRecordToJsonConverter.toJson();

		assertEquals(jsonString,
				"{\"record\":{\"data\":{\"name\":\"groupNameInData\"},\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"contentType\":\"application/metadata_record+json\",\"url\":\"http://localhost:8080/theclient/client/record/place/place:0001\",\"accept\":\"application/metadata_record+json\"}}}}");
		assertEquals(dataRecordToJsonConverter.actionLinkConverter.dataToJsonConverterFactory,
				dataToJsonConverterFactory);
	}

	private ActionLink createReadActionLink() {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		actionLink.setAccept("application/metadata_record+json");
		actionLink.setContentType("application/metadata_record+json");
		actionLink.setRequestMethod("GET");
		actionLink.setURL("http://localhost:8080/theclient/client/record/place/place:0001");
		return actionLink;
	}

	@Test
	public void testToJsonWithReadPermissions() {
		clientDataRecord.addReadPermission("readPermissionOne");
		clientDataRecord.addReadPermission("readPermissionTwo");

		dataRecordToJsonConverter = DataRecordToJsonConverter.usingJsonFactoryForClientDataRecord(
				jsonFactory, clientDataRecord, dataToJsonConverterFactory);
		String jsonString = dataRecordToJsonConverter.toJson();
		assertEquals(jsonString,
				"{\"record\":{\"data\":{\"name\":\"groupNameInData\"},\"permissions\":{\"read\":[\"readPermissionOne\",\"readPermissionTwo\"]}}}");
	}

	@Test
	public void testToJsonWithWritePermissions() {
		clientDataRecord.addWritePermission("writePermissionOne");
		clientDataRecord.addWritePermission("writePermissionTwo");

		dataRecordToJsonConverter = DataRecordToJsonConverter.usingJsonFactoryForClientDataRecord(
				jsonFactory, clientDataRecord, dataToJsonConverterFactory);
		String jsonString = dataRecordToJsonConverter.toJson();
		assertEquals(jsonString,
				"{\"record\":{\"data\":{\"name\":\"groupNameInData\"},\"permissions\":{\"write\":[\"writePermissionOne\",\"writePermissionTwo\"]}}}");
	}

	@Test
	public void testToJsonWithReadAndWritePermissions() {
		clientDataRecord.addReadPermission("readPermissionOne");
		clientDataRecord.addReadPermission("readPermissionTwo");
		clientDataRecord.addWritePermission("writePermissionOne");
		clientDataRecord.addWritePermission("writePermissionTwo");

		dataRecordToJsonConverter = DataRecordToJsonConverter.usingJsonFactoryForClientDataRecord(
				jsonFactory, clientDataRecord, dataToJsonConverterFactory);
		String jsonString = dataRecordToJsonConverter.toJson();
		assertEquals(jsonString,
				"{\"record\":{\"data\":{\"name\":\"groupNameInData\"},\"permissions\":{\"read\":[\"readPermissionOne\",\"readPermissionTwo\"],\"write\":[\"writePermissionOne\",\"writePermissionTwo\"]}}}");
	}

}
