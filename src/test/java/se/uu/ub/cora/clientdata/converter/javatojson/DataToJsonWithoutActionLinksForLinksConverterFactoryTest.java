/*
 * Copyright 2018 Uppsala University Library
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
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataToJsonWithoutActionLinksForLinksConverterFactoryTest {
	private DataToJsonWithoutActionLinksForLinksConverterFactory dataToJsonConverterFactory;
	private JsonBuilderFactory factory;

	@BeforeMethod
	public void beforeMethod() {
		dataToJsonConverterFactory = new DataToJsonWithoutActionLinksForLinksConverterFactory();
		factory = new OrgJsonBuilderFactoryAdapter();
	}

	@Test
	public void testJsonCreatorFactoryDataGroup() {
		ClientDataElement clientDataElement = ClientDataGroup.withNameInData("groupNameInData");

		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(factory, clientDataElement);

		assertTrue(dataToJsonConverter instanceof DataGroupToJsonConverter);
	}

	@Test
	public void testJsonCreatorFactoryDataAtomic() {
		ClientDataElement clientDataElement = ClientDataAtomic
				.withNameInDataAndValue("atomicNameInData", "atomicValue");

		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(factory, clientDataElement);

		assertTrue(dataToJsonConverter instanceof DataAtomicToJsonConverter);
	}

	@Test
	public void testJsonCreatorFactoryDataAttribute() {
		ClientDataElement clientDataElement = ClientDataAttribute
				.withNameInDataAndValue("attributeNameInData", "attributeValue");

		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(factory, clientDataElement);

		assertTrue(dataToJsonConverter instanceof DataAttributeToJsonConverter);
	}

	@Test
	public void testJsonCreateFactoryDataRecordLink() {
		ClientDataRecordLink recordLink = ClientDataRecordLink
				.withNameInData("recordLinkNameInData");
		ClientDataAtomic linkedRecordType = ClientDataAtomic
				.withNameInDataAndValue("linkedRecordType", "someRecordType");
		recordLink.addChild(linkedRecordType);

		ClientDataAtomic linkedRecordId = ClientDataAtomic.withNameInDataAndValue("linkedRecordId",
				"someRecordId");
		recordLink.addChild(linkedRecordId);
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(factory, recordLink);

		assertTrue(dataToJsonConverter instanceof DataRecordLinkToJsonWithoutActionLinkConverter);

	}

	@Test
	public void testJsonCreateFactoryDataResourceLink() {
		ClientDataResourceLink resourceLink = ClientDataResourceLink
				.withNameInData("recordLinkNameInData");

		resourceLink.addChild(ClientDataAtomic.withNameInDataAndValue("streamId", "someStreamId"));
		resourceLink.addChild(ClientDataAtomic.withNameInDataAndValue("filename", "adele1.png"));
		resourceLink.addChild(ClientDataAtomic.withNameInDataAndValue("filesize", "1234567"));
		resourceLink
				.addChild(ClientDataAtomic.withNameInDataAndValue("mimeType", "application/png"));
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(factory, resourceLink);

		assertTrue(dataToJsonConverter instanceof DataResourceLinkToJsonConverter);

	}

	@Test
	public void testGetConverterFactory() {
		DataToJsonConverterFactory converterFactory = dataToJsonConverterFactory
				.getConverterFactory();

		assertTrue(
				converterFactory instanceof DataToJsonWithoutActionLinksForLinksConverterFactory);

	}

	@Test
	public void testGetDataRecordLinkConverter() {
		ClientDataRecordLink recordLink = ClientDataRecordLink
				.withNameInData("recordLinkNameInData");
		DataToJsonConverterFactorySpy factorySpy = new DataToJsonConverterFactorySpy();
		DataRecordLinkToJsonWithoutActionLinkConverter dataRecordLinkToJsonConverter = (DataRecordLinkToJsonWithoutActionLinkConverter) dataToJsonConverterFactory
				.getDataRecordLinkToJsonConverter(factory, recordLink, factorySpy);

		assertTrue(
				dataRecordLinkToJsonConverter instanceof DataRecordLinkToJsonWithoutActionLinkConverter);
		assertEquals(dataRecordLinkToJsonConverter.dataToJsonConverterFactory, factorySpy);

	}
}
