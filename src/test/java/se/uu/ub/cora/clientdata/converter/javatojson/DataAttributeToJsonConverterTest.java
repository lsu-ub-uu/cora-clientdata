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

import se.uu.ub.cora.clientdata.ClientDataAttribute;

public class DataAttributeToJsonConverterTest {
	private DataToJsonConverterFactory dataToJsonConverterFactory;
	private JsonBuilderFactorySpy jsonBuilderFactory;

	@BeforeMethod
	public void beforeMethod() {
		jsonBuilderFactory = new JsonBuilderFactorySpy();
		dataToJsonConverterFactory = new DataToJsonConverterFactoryImp(jsonBuilderFactory);

	}

	@Test
	public void testToJson() {
		ClientDataAttribute clientDataAttribute = ClientDataAttribute
				.withNameInDataAndValue("attributeNameInData", "attributeValue");
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(clientDataAttribute);
		String json = dataToJsonConverter.toJson();

		JsonObjectBuilderSpy factoredJsonObjectBuilder = (JsonObjectBuilderSpy) jsonBuilderFactory.factoredJsonObjectBuilder;

		assertEquals(factoredJsonObjectBuilder.key, clientDataAttribute.getNameInData());
		assertEquals(factoredJsonObjectBuilder.value, clientDataAttribute.getValue());
		assertEquals(json, factoredJsonObjectBuilder.jsonToReturn);
	}

}
