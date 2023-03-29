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

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataGroupImp;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataToJsonConverterFactoryTest {
	private DataToJsonConverterFactoryImp dataToJsonConverterFactory;

	@BeforeMethod
	public void beforeMethod() {
		dataToJsonConverterFactory = new DataToJsonConverterFactoryImp();
	}

	@Test
	public void testJsonCreatorFactoryDataGroup() {
		Convertible clientDataElement = ClientDataGroupImp.withNameInData("groupNameInData");

		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);

		DataGroupToJsonConverter dataGroupConverter = (DataGroupToJsonConverter) dataToJsonConverter;
		assertTrue(
				dataGroupConverter.dataToJsonConverterFactory instanceof DataToJsonConverterFactoryImp);
		assertTrue(dataGroupConverter
				.onlyForTestGetJsonBuilderFactory() instanceof OrgJsonBuilderFactoryAdapter);
	}

	@Test
	public void testJsonCreatorFactoryDataAtomic() {
		Convertible clientDataElement = ClientDataAtomic.withNameInDataAndValue("atomicNameInData",
				"atomicValue");

		DataAtomicToJsonConverter dataToJsonConverter = (DataAtomicToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);

		JsonBuilderFactory onlyForTestGetJsonBuilderFactory = dataToJsonConverter
				.onlyForTestGetJsonBuilderFactory();
		assertTrue(onlyForTestGetJsonBuilderFactory instanceof OrgJsonBuilderFactoryAdapter);
		assertNotNull(onlyForTestGetJsonBuilderFactory);
	}

	@Test
	public void testJsonCreatorFactoryDataAttribute() {
		Convertible clientDataElement = ClientDataAttribute
				.withNameInDataAndValue("attributeNameInData", "attributeValue");

		DataAttributeToJsonConverter dataToJsonConverter = (DataAttributeToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);

		JsonBuilderFactory onlyForTestGetJsonBuilderFactory = dataToJsonConverter
				.onlyForTestGetJsonBuilderFactory();
		assertTrue(onlyForTestGetJsonBuilderFactory instanceof OrgJsonBuilderFactoryAdapter);
		assertNotNull(onlyForTestGetJsonBuilderFactory);
	}

	@Test
	public void testJsonCreateFactoryDataRecordLinkWithActionLinksDefaultMethod() {
		ClientDataRecordLink recordLink = createRecordLink();
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(recordLink);

		assertTrue(dataToJsonConverter instanceof DataRecordLinkToJsonConverter);
		DataRecordLinkToJsonConverter dataLinkConverter = (DataRecordLinkToJsonConverter) dataToJsonConverter;
		assertTrue(
				dataLinkConverter.dataToJsonConverterFactory instanceof DataToJsonConverterFactoryImp);

	}

	private ClientDataRecordLink createRecordLink() {
		ClientDataRecordLink recordLink = ClientDataRecordLink
				.withNameInData("recordLinkNameInData");
		ClientDataAtomic linkedRecordType = ClientDataAtomic
				.withNameInDataAndValue("linkedRecordType", "someRecordType");
		recordLink.addChild(linkedRecordType);

		ClientDataAtomic linkedRecordId = ClientDataAtomic.withNameInDataAndValue("linkedRecordId",
				"someRecordId");
		recordLink.addChild(linkedRecordId);
		return recordLink;
	}

	@Test
	public void testJsonCreateFactoryDataRecordLinkWithoutActionLinks() {
		ClientDataRecordLink recordLink = createRecordLink();
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElementIncludingActionLinks(recordLink, false);

		assertTrue(dataToJsonConverter instanceof DataRecordLinkToJsonWithoutActionLinkConverter);
		DataRecordLinkToJsonConverter dataLinkConverter = (DataRecordLinkToJsonConverter) dataToJsonConverter;
		assertTrue(
				dataLinkConverter.dataToJsonConverterFactory instanceof DataToJsonConverterFactoryImp);

	}

	@Test
	public void testJsonCreateFactoryDataRecordLinkWithActionLinks() {
		ClientDataRecordLink recordLink = createRecordLink();
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElementIncludingActionLinks(recordLink, true);

		assertTrue(dataToJsonConverter instanceof DataRecordLinkToJsonConverter);
		DataRecordLinkToJsonConverter dataLinkConverter = (DataRecordLinkToJsonConverter) dataToJsonConverter;
		assertTrue(
				dataLinkConverter.dataToJsonConverterFactory instanceof DataToJsonConverterFactoryImp);
		assertTrue(dataLinkConverter
				.onlyForTestGetJsonBuilderFactory() instanceof OrgJsonBuilderFactoryAdapter);
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

		DataResourceLinkToJsonConverter dataToJsonConverter = (DataResourceLinkToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(resourceLink);

		assertTrue(dataToJsonConverter
				.onlyForTestGetJsonBuilderFactory() instanceof OrgJsonBuilderFactoryAdapter);
	}

	@Test
	public void testGetConverterFactory() {
		DataToJsonConverterFactory converterFactory = dataToJsonConverterFactory
				.getConverterFactory();

		assertTrue(converterFactory instanceof DataToJsonConverterFactoryImp);
	}

	@Test
	public void testDefaultIncludeActionLinks() {
		assertTrue(dataToJsonConverterFactory.getIncludeActionLinks());
	}

	@Test
	public void testSetIncludeActionLinks() {
		dataToJsonConverterFactory.setIncludeActionLinks(false);
		assertFalse(dataToJsonConverterFactory.getIncludeActionLinks());
	}

	@Test
	public void testCreateForClientDataElementUsesBooleanIncludeActionLinks() {
		ClientDataRecordLink recordLink = createRecordLink();
		dataToJsonConverterFactory.setIncludeActionLinks(false);
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(recordLink);
		assertTrue(dataToJsonConverter instanceof DataRecordLinkToJsonWithoutActionLinkConverter);
	}

	@Test
	public void testCreateForClientDataElementIncludingActionLinksSetsBooleanIncludeActionLinks() {
		dataToJsonConverterFactory.setIncludeActionLinks(true);
		assertTrue(dataToJsonConverterFactory.getIncludeActionLinks());

		ClientDataRecordLink recordLink = createRecordLink();
		dataToJsonConverterFactory.createForClientDataElementIncludingActionLinks(recordLink,
				false);

		assertFalse(dataToJsonConverterFactory.getIncludeActionLinks());
	}
}
