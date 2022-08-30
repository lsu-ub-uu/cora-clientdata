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

import java.util.Map;

import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.converter.javatojson.Convertible;
import se.uu.ub.cora.json.parser.JsonObject;
import se.uu.ub.cora.json.parser.JsonParseException;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class JsonToDataAtomicConverterTest {

	@Test
	public void testToClass() {
		String json = "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\"}";
		ClientDataAtomic clientDataAtomic = createClientDataAtomicForJsonString(json);
		assertEquals(clientDataAtomic.getNameInData(), "atomicNameInData");
		assertEquals(clientDataAtomic.getValue(), "atomicValue");
	}

	@Test
	public void testAtomicWithTwoAttributes() {
		String json = "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\","
				+ " \"attributes\": {\"exampleAttributeChoice\": \"one\","
				+ " \"exampleAttributeChoice2\": \"two\"}}";

		ClientDataAtomic clientDataAtomic = createClientDataAtomicForJsonString(json);

		assertEquals(clientDataAtomic.getNameInData(), "atomicNameInData");
		assertEquals(clientDataAtomic.getValue(), "atomicValue");
		Map<String, String> attributes = clientDataAtomic.getAttributes();
		assertEquals(attributes.size(), 2);
		assertEquals(attributes.get("exampleAttributeChoice"), "one");
		assertEquals(attributes.get("exampleAttributeChoice2"), "two");
	}

	private ClientDataAtomic createClientDataAtomicForJsonString(String json) {
		OrgJsonParser jsonParser = new OrgJsonParser();
		JsonValue jsonValue = jsonParser.parseString(json);
		JsonToDataConverter jsonToDataConverter = JsonToDataAtomicConverter
				.forJsonObject((JsonObject) jsonValue);
		Convertible clientDataElement = jsonToDataConverter.toInstance();

		return (ClientDataAtomic) clientDataElement;
	}

	@Test
	public void testToClassWithRepeatId() {
		String json = "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\",\"repeatId\":\"5\"}";
		ClientDataAtomic clientDataAtomic = createClientDataAtomicForJsonString(json);
		assertEquals(clientDataAtomic.getNameInData(), "atomicNameInData");
		assertEquals(clientDataAtomic.getValue(), "atomicValue");
		assertEquals(clientDataAtomic.getRepeatId(), "5");
	}

	@Test
	public void testToClassEmptyValue() {
		String json = "{\"name\":\"atomicNameInData\",\"value\":\"\"}";
		ClientDataAtomic clientDataAtomic = createClientDataAtomicForJsonString(json);
		assertEquals(clientDataAtomic.getNameInData(), "atomicNameInData");
		assertEquals(clientDataAtomic.getValue(), "");
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonValueIsNotString() {
		String json = "{\"name\":\"id\",\"value\":[]}";
		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonNameIsNotString() {
		String json = "{\"name\":{},\"value\":\"atomicValue\"}";
		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonNotName() {
		String json = "{\"nameNOT\":\"id\",\"value\":\"atomicValue\"}";

		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonMissingValue() {

		String json = "{\"name\":\"id\",\"valueNOT\":\"atomicValue\"}";
		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonExtraKey() {
		String json = "{\"name\":\"id\",\"value\":\"atomicValue\",\"repeatId\":\"5\""
				+ ",\"extra\":\"extra\"}";
		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonExtraKey2() {
		String json = "{\"name\":\"id\",\"value\":\"atomicValue\",\"repeatId\":\"5\""
				+ ",\"extra\":\"extra\",\"extra2\":\"extra\"}";
		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonExtraKeyMissingRepeatId() {
		String json = "{\"name\":\"id\",\"value\":\"atomicValue\",\"NOTrepeatId\":\"5\"" + "}";
		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonExtraKeyValuePair() {
		String json = "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\","
				+ "\"name\":\"id2\",\"value\":\"value2\"}";
		createClientDataAtomicForJsonString(json);
	}

	@Test(expectedExceptions = JsonParseException.class)
	public void testToClassWrongJsonExtraArray() {
		String json = "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\","
				+ "\"name\":\"id2\",\"value\":[]}";
		createClientDataAtomicForJsonString(json);
	}

}
