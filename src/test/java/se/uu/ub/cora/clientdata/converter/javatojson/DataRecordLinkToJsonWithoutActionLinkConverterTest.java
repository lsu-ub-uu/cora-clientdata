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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.Action;
import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataGroupImp;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataRecordLinkToJsonWithoutActionLinkConverterTest {
	private ClientDataRecordLink recordLink;
	private DataRecordLinkToJsonConverter converter;
	private DataToJsonConverterFactorySpy dataToJsonConverterFactory;

	@BeforeMethod
	public void setUp() {
		recordLink = ClientDataRecordLink.withNameInData("nameInData");

		ClientDataAtomic linkedRecordType = ClientDataAtomic
				.withNameInDataAndValue("linkedRecordType", "aRecordType");
		recordLink.addChild(linkedRecordType);

		ClientDataAtomic linkedRecordId = ClientDataAtomic.withNameInDataAndValue("linkedRecordId",
				"aRecordId");
		recordLink.addChild(linkedRecordId);

		JsonBuilderFactory jsonFactory = new OrgJsonBuilderFactoryAdapter();
		dataToJsonConverterFactory = new DataToJsonConverterFactorySpy();
		converter = DataRecordLinkToJsonWithoutActionLinkConverter
				.usingJsonFactoryForClientDataLink(jsonFactory, recordLink,
						dataToJsonConverterFactory);

	}

	@Test
	public void testToJson() {
		String jsonString = converter.toJson();

		String jsonFromSpy = "{\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"}],\"name\":\"nameInData\"}";
		assertEquals(jsonString, jsonFromSpy);
	}

	@Test
	public void testToJsonGroupWithAttribute() {
		recordLink.addAttributeByIdWithValue("attributeNameInData", "attributeValue");
		String jsonString = converter.toJson();
		String jsonFromSpy = "{\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"}],\"name\":\"nameInData\",\"attributes\":{\"attributeNameInData\":\"attributeValue\"}}";

		assertEquals(jsonString, jsonFromSpy);
	}

	@Test
	public void testToJsonWithRepeatId() {
		recordLink.setRepeatId("22");
		String jsonString = converter.toJson();
		String jsonFromSpy = "{\"repeatId\":\"22\",\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"}],\"name\":\"nameInData\"}";

		assertEquals(jsonString, jsonFromSpy);
	}

	@Test
	public void testToJsonWithEmptyRepeatId() {
		recordLink.setRepeatId("");
		String jsonString = converter.toJson();
		String jsonFromSpy = "{\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"}],\"name\":\"nameInData\"}";

		assertEquals(jsonString, jsonFromSpy);
	}

	@Test
	public void testToJsonWithLinkedRepeatId() {
		ClientDataAtomic linkedRepeatId = ClientDataAtomic.withNameInDataAndValue("linkedRepeatId",
				"linkedOne");
		recordLink.addChild(linkedRepeatId);
		String jsonString = converter.toJson();
		String jsonFromSpy = "{\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"},{\"name\":\"linkedRepeatId\"}],\"name\":\"nameInData\"}";

		assertEquals(jsonString, jsonFromSpy);
	}

	@Test
	public void testToJsonWithEmptyLinkedRepeatId() {
		ClientDataAtomic linkedRepeatId = ClientDataAtomic.withNameInDataAndValue("linkedRepeatId",
				"");
		recordLink.addChild(linkedRepeatId);
		String jsonString = converter.toJson();
		String jsonFromSpy = "{\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"}],\"name\":\"nameInData\"}";

		assertEquals(jsonString, jsonFromSpy);
	}

	@Test
	public void testToJsonWithLinkedPath() {
		ClientDataGroupImp linkedPathDataGroup = ClientDataGroupImp.withNameInData("linkedPath");
		recordLink.addChild(linkedPathDataGroup);
		String jsonString = converter.toJson();
		String jsonFromSpy = "{\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"},{\"name\":\"linkedPath\"}],\"name\":\"nameInData\"}";

		assertEquals(jsonString, jsonFromSpy);
	}

	@Test
	public void testToJsonWithActionLink() {
		recordLink.addActionLink("read", createReadActionLink());

		String jsonString = converter.toJson();
		String jsonFromSpy = "{\"children\":[{\"name\":\"linkedRecordType\"},{\"name\":\"linkedRecordId\"}],\"name\":\"nameInData\"}";
		assertEquals(jsonString, jsonFromSpy);
	}

	private ActionLink createReadActionLink() {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		actionLink.setAccept("application/metadata_record+json");
		actionLink.setContentType("application/metadata_record+json");
		actionLink.setRequestMethod("GET");
		actionLink.setURL("http://localhost:8080/theclient/client/record/place/place:0001");
		return actionLink;
	}
}
