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

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataAttributeToJsonConverterTest {
	private DataToJsonConverterFactory dataToJsonConverterFactory;
	private JsonBuilderFactory factory;

	@BeforeMethod
	public void beforeMethod() {
		dataToJsonConverterFactory = new DataToJsonConverterFactoryImp();
		factory = new OrgJsonBuilderFactoryAdapter();

	}

	@Test
	public void testToJson() {
		Convertible clientDataElement = ClientDataAttribute
				.withNameInDataAndValue("attributeNameInData", "attributeValue");
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);
		String json = dataToJsonConverter.toJson();

		Assert.assertEquals(json, "{\"attributeNameInData\":\"attributeValue\"}");
	}

	@Test
	public void testToJsonEmptyValue() {
		Convertible clientDataElement = ClientDataAttribute
				.withNameInDataAndValue("attributeNameInData", "");
		DataToJsonConverter dataToJsonConverter = dataToJsonConverterFactory
				.createForClientDataElement(clientDataElement);
		String json = dataToJsonConverter.toJson();

		Assert.assertEquals(json, "{\"attributeNameInData\":\"\"}");
	}
}
