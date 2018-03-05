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

import static org.testng.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.json.parser.JsonParseException;

public class JsonToDataConverterFactoryTest {
	private JsonToDataConverterFactory jsonToDataConverterFactory;

	@BeforeMethod
	public void beforeMethod() {
		jsonToDataConverterFactory = new JsonToDataConverterFactoryImp();
	}

	@Test
	public void testFactorOnJsonStringDataGroupEmptyChildren() {
		String json = "{\"name\":\"groupNameInData\", \"children\":[]}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataGroupConverter);
		assertTrue(
				((JsonToDataGroupConverter) jsonToDataConverter).factory instanceof JsonToDataConverterFactoryImp);

	}

	@Test
	public void testFactorOnJsonStringDataGroupAtomicChild() {
		String json = "{\"name\":\"id\", \"children\":[{\"id2\":\"value\"}]}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataGroupConverter);
	}

	@Test
	public void testFactorOnJsonStringDataAtomic() {
		String json = "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\"}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataAtomicConverter);
	}

	@Test
	public void testFactorOnJsonStringDataAttribute() {
		String json = "{\"attributeNameInData\":\"attributeValue\"}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataAttributeConverter);
	}

	@Test
	public void testFactorOnJsonStringDataRecordLink() {
		String json = "{\"name\":\"link\", \"children\":[{\"name\": \"linkedRecordId\", \"value\": \"myLinkedRecordId\"} ]}";

		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataGroupConverter);
	}

	@Test
	public void testFactorOnJsonStringActionLinks() {
		String json = "{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/presentationGroup/loginFormNewPGroup\",\"accept\":\"application/vnd.uub.record+json\"}}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataActionLinksConverter);
		assertTrue(
				((JsonToDataActionLinksConverter) jsonToDataConverter).factory instanceof JsonToDataConverterFactoryImp);
	}

	@Test
	public void testFactorOnJsonStringActionLink() {
		String json = "{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/presentationGroup/loginFormNewPGroup\",\"accept\":\"application/vnd.uub.record+json\"}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataActionLinkConverter);
		assertTrue(
				((JsonToDataActionLinkConverter) jsonToDataConverter).factory instanceof JsonToDataConverterFactoryImp);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testFactorOnJsonObjectNullJson() {
		jsonToDataConverterFactory.createForJsonString(null);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testClassCreatorGroupNotAGroup() {
		String json = "[{\"id\":{\"id2\":\"value\"}}]";
		jsonToDataConverterFactory.createForJsonString(json);
	}

	@Test
	public void testFactorOnJsonStringRecordLink() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"linkedRecordPresentationPresentationLinkDefText\"}],\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/coraText/linkedRecordPresentationPresentationLinkDefText\",\"accept\":\"application/vnd.uub.record+json\"}},\"name\":\"defTextId\"}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataRecordLinkConverter);
		assertTrue(
				((JsonToDataRecordLinkConverter) jsonToDataConverter).factory instanceof JsonToDataConverterFactoryImp);

	}

	@Test
	public void testFactorOnJsonStringRecordLinkMissingRecordTypeReturnsGroupConverter() {
		String json = "{\"children\":[{\"name\":\"NOTlinkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"linkedRecordPresentationPresentationLinkDefText\"}],\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/coraText/linkedRecordPresentationPresentationLinkDefText\",\"accept\":\"application/vnd.uub.record+json\"}},\"name\":\"defTextId\"}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataGroupConverter);
	}

	@Test
	public void testFactorOnJsonStringRecordLinkMissingRecordIdReturnsGroupConverter() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"NOTlinkedRecordId\",\"value\":\"linkedRecordPresentationPresentationLinkDefText\"}],\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/coraText/linkedRecordPresentationPresentationLinkDefText\",\"accept\":\"application/vnd.uub.record+json\"}},\"name\":\"defTextId\"}";
		JsonToDataConverter jsonToDataConverter = jsonToDataConverterFactory
				.createForJsonString(json);
		assertTrue(jsonToDataConverter instanceof JsonToDataGroupConverter);
	}
}
