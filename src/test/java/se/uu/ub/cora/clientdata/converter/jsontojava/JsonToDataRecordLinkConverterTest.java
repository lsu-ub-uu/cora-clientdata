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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class JsonToDataRecordLinkConverterTest {

	private JsonToDataConverterFactoryForDataRecordSpy factory;

	@Test
	public void testToClass() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\"}";
		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);
		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");
	}

	private ClientDataRecordLink createClientDataRecordLinkForJsonString(String json) {
		OrgJsonParser jsonParser = new OrgJsonParser();
		JsonValue jsonValue = jsonParser.parseString(json);
		factory = new JsonToDataConverterFactoryForDataRecordSpy();

		JsonToDataRecordLinkConverter jsonToDataConverter = JsonToDataRecordLinkConverter
				.forJsonObjectUsingConverterFactory((JsonObject) jsonValue, factory);
		ClientDataElement clientDataElement = jsonToDataConverter.toInstance();
		ClientDataRecordLink clientDataRecordLink = (ClientDataRecordLink) clientDataElement;
		return clientDataRecordLink;
	}

	@Test
	public void testToClassWithAttribute() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\",\"attributes\":{\"type\":\"someType\"}}";
		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);
		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");
		String attributeValue = clientDataRecordLink.getAttributes().get("type");
		assertEquals(attributeValue, "someType");
	}

	@Test
	public void testToClassWithRepeatId() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\",\"repeatId\":\"3\"}";
		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);
		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");
		assertEquals(clientDataRecordLink.getRepeatId(), "3");
	}

	@Test
	public void testToClassWithRepeatIdAndAttribute() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\",\"attributes\":{\"type\":\"someType\"},\"repeatId\":\"3\"}";
		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);
		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");

		String attributeValue = clientDataRecordLink.getAttributes().get("type");
		assertEquals(attributeValue, "someType");
		assertEquals(clientDataRecordLink.getRepeatId(), "3");
	}

	@Test
	public void testToClassWithActionLinks() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"},{\"name\":\"linkedRepeatId\",\"value\":\"2\"}],\"name\":\"defTextId\",\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/system/systemOne\",\"accept\":\"application/vnd.uub.record+json\"}}}";

		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);
		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");

		assertCorrectChildren(clientDataRecordLink);
		Map<String, ActionLink> actionLinks = clientDataRecordLink.getActionLinks();
		assertEquals(actionLinks.size(), 1);
		assertNotNull(actionLinks.get("read"));
		assertEquals(factory.numOfTimesFactoryCalled, 4);
		assertEquals(factory.factoredActionLinksConverters.get(0).returnedElement, actionLinks.get("read"));
	}

	@Test
	public void testToClassWithRepeatIdAndAttributeAndActionLinks() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"},{\"name\":\"linkedRepeatId\",\"value\":\"2\"}],\"name\":\"defTextId\",\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/system/systemOne\",\"accept\":\"application/vnd.uub.record+json\"}},\"attributes\":{\"type\":\"someType\"},\"repeatId\":\"3\"}";

		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);
		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");

		String attributeValue = clientDataRecordLink.getAttributes().get("type");
		assertEquals(attributeValue, "someType");
		assertEquals(clientDataRecordLink.getRepeatId(), "3");
		Map<String, ActionLink> actionLinks = clientDataRecordLink.getActionLinks();
		assertEquals(actionLinks.size(), 1);
	}

	@Test
	public void testToClassWithLinkedRecordTypeAndLinkedRecordIdInLinkPart() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\",\"attributes\":{\"type\":\"someType\"},\"repeatId\":\"3\"}";

		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);
		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");

		List<ClientDataElement> children = clientDataRecordLink.getChildren();

		assertCorrectLinkedRecordType(children);
		assertCorrectLinkedRecordId(children);
	}

	@Test
	public void testToClassWithLinkedRecordTypeAndLinkedRecordIdAndLinkedRepeatIdInLinkPart() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"},{\"name\":\"linkedRepeatId\",\"value\":\"2\"}],\"name\":\"defTextId\",\"attributes\":{\"type\":\"someType\"},\"repeatId\":\"3\"}";
		ClientDataRecordLink clientDataRecordLink = createClientDataRecordLinkForJsonString(json);

		assertEquals(clientDataRecordLink.getNameInData(), "defTextId");

		assertCorrectChildren(clientDataRecordLink);
	}

	private void assertCorrectChildren(ClientDataRecordLink clientDataRecordLink) {
		List<ClientDataElement> children = clientDataRecordLink.getChildren();
		assertCorrectLinkedRecordType(children);

		assertCorrectLinkedRecordId(children);

		ClientDataAtomic linkedRepeatId = (ClientDataAtomic) children.get(2);
		JsonToDataConverterSpy jsonToDataConverterSpy3 = factory.factoredConverters.get(2);
		assertEquals(jsonToDataConverterSpy3.returnedElement, linkedRepeatId);
	}

	private void assertCorrectLinkedRecordId(List<ClientDataElement> children) {
		ClientDataAtomic linkedRecordId = (ClientDataAtomic) children.get(1);
		JsonToDataConverterSpy jsonToDataConverterSpy2 = factory.factoredConverters.get(1);
		assertEquals(jsonToDataConverterSpy2.returnedElement, linkedRecordId);
	}

	private void assertCorrectLinkedRecordType(List<ClientDataElement> children) {
		ClientDataAtomic linkedRecordType = (ClientDataAtomic) children.get(0);
		JsonToDataConverterSpy jsonToDataConverterSpy = factory.factoredConverters.get(0);
		assertEquals(jsonToDataConverterSpy.returnedElement, linkedRecordType);
	}

	@Test(expectedExceptions = JsonParseException.class, expectedExceptionsMessageRegExp = "Group data must contain key: name")
	public void testToClassWrongJsonTopLevelNoName() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}]}";
		createClientDataRecordLinkForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class, expectedExceptionsMessageRegExp = "Group data must contain key: children")
	public void testToClassWrongJsonTopLevelNoChildren() {
		String json = "{\"NOTchildren\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\"}";
		createClientDataRecordLinkForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonKeyTopLevel() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\",\"someExtraKey\":{\"name\":\"extraKey\",\"value\":\"extraValue\"}}";
		createClientDataRecordLinkForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonKeyTopLevelWithAttributes() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"}],\"name\":\"defTextId\",\"attributes\":{\"type\":\"someType\"},\"someExtraKey\":{\"name\":\"extraKey\",\"value\":\"extraValue\"}}";
		createClientDataRecordLinkForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWithRepeatIdAndAttributeAndActionLinksAndExtra() {
		String json = "{\"children\":[{\"name\":\"linkedRecordType\",\"value\":\"coraText\"},{\"name\":\"linkedRecordId\",\"value\":\"someDefText\"},{\"name\":\"linkedRepeatId\",\"value\":\"2\"}],\"name\":\"defTextId\",\"attributes\":{\"type\":\"someType\"},\"repeatId\":\"3\",\"actionLinks\":{\"read\":{\"requestMethod\":\"GET\",\"rel\":\"read\",\"url\":\"https://cora.epc.ub.uu.se/systemone/rest/record/system/systemOne\",\"accept\":\"application/vnd.uub.record+json\"}},\"extraKey\":{\"name\":\"extraName\",\"value\":\"extraValue\"}}";
		createClientDataRecordLinkForJsonString(json);
	}

}
