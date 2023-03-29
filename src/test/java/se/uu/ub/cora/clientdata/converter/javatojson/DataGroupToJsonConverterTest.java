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

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataGroupImp;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataGroupToJsonConverterTest {
	private JsonBuilderFactory factory;
	private ClientDataGroupImp clientDataGroup;
	private DataGroupToJsonConverter dataGroupToJsonConverter;
	private DataToJsonConverterFactorySpy dataToJsonFactory;

	@BeforeMethod
	public void beforeMethod() {
		factory = new OrgJsonBuilderFactoryAdapter();
		clientDataGroup = ClientDataGroupImp.withNameInData("groupNameInData");
		dataToJsonFactory = new DataToJsonConverterFactorySpy();
	}

	@Test
	public void testToJsonNoChildren() {
		initConverter();
		String json = dataGroupToJsonConverter.toJson();
		assertEquals(dataToJsonFactory.calledNumOfTimes, 0);
		assertEquals(json, "{\"name\":\"groupNameInData\"}");
	}

	private void initConverter() {
		dataGroupToJsonConverter = DataGroupToJsonConverter
				.usingJsonFactoryAndConverterFactoryForClientDataGroup(factory, dataToJsonFactory,
						clientDataGroup);
	}

	@Test
	public void testToJsonWithRepeatId() {
		clientDataGroup.setRepeatId("4");
		initConverter();
		String json = dataGroupToJsonConverter.toJson();

		assertEquals(dataToJsonFactory.calledNumOfTimes, 0);
		assertEquals(json, "{\"repeatId\":\"4\",\"name\":\"groupNameInData\"}");
	}

	@Test
	public void testToJsonWithEmptyRepeatId() {
		clientDataGroup.setRepeatId("");
		initConverter();
		String json = dataGroupToJsonConverter.toJson();

		assertEquals(dataToJsonFactory.calledNumOfTimes, 0);
		assertEquals(json, "{\"name\":\"groupNameInData\"}");
	}

	@Test
	public void testToJsonGroupWithAttribute() {
		clientDataGroup.addAttributeByIdWithValue("attributeNameInData", "attributeValue");
		initConverter();
		String json = dataGroupToJsonConverter.toJson();

		assertEquals(dataToJsonFactory.calledNumOfTimes, 0);
		assertEquals(json,
				"{\"name\":\"groupNameInData\",\"attributes\":{\"attributeNameInData\":\"attributeValue\"}}");
	}

	@Test
	public void testToJsonGroupWithAttributes() {
		clientDataGroup.addAttributeByIdWithValue("attributeNameInData", "attributeValue");
		clientDataGroup.addAttributeByIdWithValue("attributeNameInData2", "attributeValue2");
		initConverter();
		String json = dataGroupToJsonConverter.toJson();

		assertEquals(dataToJsonFactory.calledNumOfTimes, 0);
		assertEquals(json,
				"{\"name\":\"groupNameInData\",\"attributes\":{"
						+ "\"attributeNameInData2\":\"attributeValue2\","
						+ "\"attributeNameInData\":\"attributeValue\"" + "}}");
	}

	@Test
	public void testToJsonGroupWithAtomicChild() {
		clientDataGroup.addChild(
				ClientDataAtomic.withNameInDataAndValue("atomicNameInData", "atomicValue"));
		initConverter();
		String json = dataGroupToJsonConverter.toJson();
		assertEquals(dataToJsonFactory.calledNumOfTimes, 1);

		String jsonFromSpy = "{\"children\":[{\"name\":\"atomicNameInData\"}],\"name\":\"groupNameInData\"}";
		assertEquals(json, jsonFromSpy);
	}

	@Test
	public void testToJsonGroupWithAtomicChildAndGroupChildWithAtomicChild() {
		clientDataGroup.addChild(
				ClientDataAtomic.withNameInDataAndValue("atomicNameInData", "atomicValue"));

		ClientDataGroupImp clientDataGroup2 = ClientDataGroupImp.withNameInData("groupNameInData2");
		clientDataGroup.addChild(clientDataGroup2);

		clientDataGroup2.addChild(
				ClientDataAtomic.withNameInDataAndValue("atomicNameInData2", "atomicValue2"));
		initConverter();
		String json = dataGroupToJsonConverter.toJson();

		assertEquals(dataToJsonFactory.calledNumOfTimes, 2);
		String jsonFomSpy = "{\"children\":[{\"name\":\"atomicNameInData\"},{\"name\":\"groupNameInData2\"}],\"name\":\"groupNameInData\"}";

		assertEquals(json, jsonFomSpy);
	}

	@Test
	public void testToJsonGroupWithAttributesAndAtomicChildAndGroupChildWithAtomicChild() {
		clientDataGroup.addAttributeByIdWithValue("attributeNameInData", "attributeValue");
		clientDataGroup.addAttributeByIdWithValue("attributeNameInData2", "attributeValue2");

		ClientDataGroupImp recordInfo = ClientDataGroupImp.withNameInData("recordInfo");
		recordInfo.addChild(ClientDataAtomic.withNameInDataAndValue("id", "place:0001"));
		ClientDataGroupImp type = ClientDataGroupImp.withNameInData("type");
		type.addChild(ClientDataAtomic.withNameInDataAndValue("linkedRecordType", "recordType"));
		type.addChild(ClientDataAtomic.withNameInDataAndValue("linkedRecordId", "place"));
		recordInfo.addChild(type);

		recordInfo.addChild(ClientDataAtomic.withNameInDataAndValue("createdBy", "userId"));
		clientDataGroup.addChild(recordInfo);

		clientDataGroup.addChild(
				ClientDataAtomic.withNameInDataAndValue("atomicNameInData", "atomicValue"));

		ClientDataGroupImp dataGroup2 = ClientDataGroupImp.withNameInData("groupNameInData2");
		dataGroup2.addAttributeByIdWithValue("g2AttributeNameInData", "g2AttributeValue");
		clientDataGroup.addChild(dataGroup2);

		dataGroup2.addChild(
				ClientDataAtomic.withNameInDataAndValue("atomicNameInData2", "atomicValue2"));
		initConverter();
		String json = dataGroupToJsonConverter.toJson();
		assertEquals(dataToJsonFactory.calledNumOfTimes, 3);
		String jsonFomSpy = "{\"children\":[{\"name\":\"recordInfo\"},{\"name\":\"atomicNameInData\"},{\"name\":\"groupNameInData2\"}],\"name\":\"groupNameInData\",\"attributes\":{\"attributeNameInData2\":\"attributeValue2\",\"attributeNameInData\":\"attributeValue\"}}";

		assertEquals(json, jsonFomSpy);
	}

}
