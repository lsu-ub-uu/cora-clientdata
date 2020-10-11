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
import static org.testng.Assert.assertSame;
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

public class DataToJsonConverterFactoryTest {
	private DataToJsonConverterFactoryImp dataToJsonConverterFactory;
	private JsonBuilderFactory jsonBuilderFactory;

	@BeforeMethod
	public void beforeMethod() {
		jsonBuilderFactory = new JsonBuilderFactorySpy();
		dataToJsonConverterFactory = new DataToJsonConverterFactoryImp(jsonBuilderFactory);
	}

	@Test
	public void testInit() {
		assertSame(dataToJsonConverterFactory.getJsonBuilderFactory(), jsonBuilderFactory);
	}

	@Test
	public void testJsonCreatorFactoryDataGroup() {
		ClientDataElement clientDataElement = ClientDataGroup.withNameInData("groupNameInData");

		DataGroupToJsonConverter dataToJsonConverter = (DataGroupToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);

		assertTrue(dataToJsonConverter instanceof DataToJsonConverter);
		assertTrue(
				dataToJsonConverter.dataToJsonConverterFactory instanceof DataToJsonConverterFactoryImp);

		assertSame(dataToJsonConverter.getClientDataGroup(), clientDataElement);
		assertSame(dataToJsonConverter.getJsonBuilderFactory(), jsonBuilderFactory);
	}

	@Test
	public void testJsonCreatorFactoryDataAtomic() {
		ClientDataElement clientDataElement = ClientDataAtomic
				.withNameInDataAndValue("atomicNameInData", "atomicValue");

		DataAtomicToJsonConverter atomicConverter = (DataAtomicToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);

		assertTrue(atomicConverter instanceof DataAtomicToJsonConverter);
		assertSame(atomicConverter.getClientDataAtomic(), clientDataElement);
		assertSame(atomicConverter.getJsonBuilderFactory(), jsonBuilderFactory);
	}

	@Test
	public void testJsonCreatorFactoryDataAttribute() {
		ClientDataElement clientDataElement = ClientDataAttribute
				.withNameInDataAndValue("attributeNameInData", "attributeValue");

		DataAttributeToJsonConverter attributeConverter = (DataAttributeToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);

		assertSame(attributeConverter.getClientDataAttribute(), clientDataElement);
		assertSame(attributeConverter.getJsonBuilderFactory(), jsonBuilderFactory);
	}

	@Test
	public void testJsonCreateFactoryDataRecordLinkWithActionLinksDefaultMethod() {
		ClientDataRecordLink recordLink = createRecordLink();

		DataRecordLinkToJsonConverter recordLinkConverter = (DataRecordLinkToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(recordLink);

		assertSame(recordLinkConverter.getJsonBuilderFactory(), jsonBuilderFactory);
		assertSame(recordLinkConverter.getClientDataRecordLink(), recordLink);

		DataToJsonConverterFactoryImp converterSentToConverter = (DataToJsonConverterFactoryImp) recordLinkConverter.dataToJsonConverterFactory;
		assertSame(converterSentToConverter.getJsonBuilderFactory(), jsonBuilderFactory);

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
		DataRecordLinkToJsonWithoutActionLinkConverter recordLinkConverter = (DataRecordLinkToJsonWithoutActionLinkConverter) dataToJsonConverterFactory
				.createForClientDataElementIncludingActionLinks(recordLink, false);

		assertSame(recordLinkConverter.getJsonBuilderFactory(), jsonBuilderFactory);
		assertSame(recordLinkConverter.getClientDataRecordLink(), recordLink);

		DataToJsonConverterFactoryImp converterSentToConverter = (DataToJsonConverterFactoryImp) recordLinkConverter.dataToJsonConverterFactory;
		assertSame(converterSentToConverter.getJsonBuilderFactory(), jsonBuilderFactory);

	}

	@Test
	public void testJsonCreateFactoryDataRecordLinkWithActionLinks() {
		ClientDataRecordLink recordLink = createRecordLink();
		DataRecordLinkToJsonConverter recordLinkConverter = (DataRecordLinkToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElementIncludingActionLinks(recordLink, true);

		assertSame(recordLinkConverter.getJsonBuilderFactory(), jsonBuilderFactory);
		assertSame(recordLinkConverter.getClientDataRecordLink(), recordLink);

		DataToJsonConverterFactoryImp converterSentToConverter = (DataToJsonConverterFactoryImp) recordLinkConverter.dataToJsonConverterFactory;
		assertSame(converterSentToConverter.getJsonBuilderFactory(), jsonBuilderFactory);
	}

	@Test
	public void testJsonCreateFactoryDataResourceLink() {
		ClientDataResourceLink resourceLink = createResourceLink();
		DataResourceLinkToJsonConverter dataToJsonConverter = (DataResourceLinkToJsonConverter) dataToJsonConverterFactory
				.createForClientDataElement(resourceLink);

		assertSame(dataToJsonConverter.getJsonBuilderFactory(), jsonBuilderFactory);
		assertSame(dataToJsonConverter.getClientDataResourceLink(), resourceLink);

		DataToJsonConverterFactoryImp converterSentToConverter = (DataToJsonConverterFactoryImp) dataToJsonConverter.dataToJsonConverterFactory;
		assertSame(converterSentToConverter.getJsonBuilderFactory(), jsonBuilderFactory);

	}

	private ClientDataResourceLink createResourceLink() {
		ClientDataResourceLink resourceLink = ClientDataResourceLink
				.withNameInData("recordLinkNameInData");

		resourceLink.addChild(ClientDataAtomic.withNameInDataAndValue("streamId", "someStreamId"));
		resourceLink.addChild(ClientDataAtomic.withNameInDataAndValue("filename", "adele1.png"));
		resourceLink.addChild(ClientDataAtomic.withNameInDataAndValue("filesize", "1234567"));
		resourceLink
				.addChild(ClientDataAtomic.withNameInDataAndValue("mimeType", "application/png"));
		return resourceLink;
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
