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

package se.uu.ub.cora.clientdata.converter.jsontojava;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataActionLinks;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParser;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class JsonToDataActionLnksConverterTest {
	private JsonParser jsonParser;
	private JsonToDataConverterFactorySpy factory;

	@BeforeMethod
	public void beforeMethod() {
		jsonParser = new OrgJsonParser();
	}

	@Test
	public void testToClassWithNoAction() {
		String json = "{}";
		createClientDataAttributeForJsonString(json);
		assertEquals(factory.numberOfTimesCalled, 0);
	}

	private ClientDataActionLinks createClientDataAttributeForJsonString(String json) {
		factory = new JsonToDataConverterFactorySpy();
		JsonValue jsonValue = jsonParser.parseString(json);

		JsonToDataConverter jsonToDataConverter = JsonToDataActionLinksConverter
				.forJsonObjectUsingConverterFactory((JsonObject) jsonValue, factory);
		ClientDataElement clientDataElement = jsonToDataConverter.toInstance();
		return (ClientDataActionLinks) clientDataElement;
	}

	@Test
	public void testToClassWithOneAction() {
		String json = "{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/presentationGroup/loginFormNewPGroup\",\"accept\":\"application/vnd.uub.record+json\"}}";
		createClientDataAttributeForJsonString(json);
		assertEquals(factory.numberOfTimesCalled, 1);
	}

	@Test
	public void testToClassWithThreeAction() {
		String json = "{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/textSystemOne/refItemText\",\"accept\":\"application/vnd.uub.record+json\"},\"read_incoming_links\":{\"requestMethod\":\"GET\",\"rel\":\"read_incoming_links\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/textSystemOne/refItemText/incomingLinks\",\"accept\":\"application/vnd.uub.recordList+json\"},\"update\":{\"requestMethod\":\"POST\",\"rel\":\"update\",\"contentType\":\"application/vnd.uub.record+json\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/textSystemOne/refItemText\",\"accept\":\"application/vnd.uub.record+json\"}}";
		createClientDataAttributeForJsonString(json);
		assertEquals(factory.numberOfTimesCalled, 3);
	}
	// @Test
	// public void testToClassWithNoAction() {
	// ClientDataAttribute clientDataAttribute =
	// createClientDataAttributeForJsonString(json);
	// Assert.assertEquals(clientDataAttribute.getNameInData(),
	// "attributeNameInData");
	// Assert.assertEquals(clientDataAttribute.getValue(), "attributeValue");
	// }
	//
	// @Test
	// public void testToClassEmptyValue() {
	// String json = "{\"attributeNameInData\":\"\"}";
	// ClientDataAttribute clientDataAttribute =
	// createClientDataAttributeForJsonString(json);
	// Assert.assertEquals(clientDataAttribute.getNameInData(),
	// "attributeNameInData");
	// Assert.assertEquals(clientDataAttribute.getValue(), "");
	// }
	//
	// @Test(expectedExceptions = JsonParseException.class)
	// public void testToClassWrongJson() {
	// String json = "{\"id\":[]}";
	//
	// JsonValue jsonValue = jsonParser.parseString(json);
	// JsonToDataConverter jsonToDataConverter = JsonToDataAttributeConverter
	// .forJsonObject((JsonObject) jsonValue);
	// jsonToDataConverter.toInstance();
	// }
	//
	// @Test(expectedExceptions = JsonParseException.class)
	// public void testToClassWrongJsonExtraKeyValuePair() {
	// String json =
	// "{\"attributeNameInData\":\"attributeValue\",\"id2\":\"value2\"}";
	// JsonValue jsonValue = jsonParser.parseString(json);
	// JsonToDataConverter jsonToDataConverter = JsonToDataAttributeConverter
	// .forJsonObject((JsonObject) jsonValue);
	// jsonToDataConverter.toInstance();
	// }
	//
	// @Test(expectedExceptions = JsonParseException.class)
	// public void testToClassWrongJsonExtraArray() {
	// String json = "{\"attributeNameInData\":\"attributeValue\",\"id2\":[]}";
	// JsonValue jsonValue = jsonParser.parseString(json);
	// JsonToDataConverter jsonToDataConverter = JsonToDataAttributeConverter
	// .forJsonObject((JsonObject) jsonValue);
	// jsonToDataConverter.toInstance();
	// }
	//
}
