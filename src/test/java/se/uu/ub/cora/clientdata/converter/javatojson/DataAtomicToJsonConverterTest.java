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

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.converter.javatojson.DataAtomicToJsonConverter;
import se.uu.ub.cora.clientdata.converter.javatojson.DataToJsonConverter;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataAtomicToJsonConverterTest {
	private ClientDataAtomic clientDataAtomic;
	private DataToJsonConverter converter;

	@BeforeMethod
	public void beforeMethod() {
		clientDataAtomic = ClientDataAtomic.withNameInDataAndValue("atomicNameInData", "atomicValue");
		OrgJsonBuilderFactoryAdapter factory = new OrgJsonBuilderFactoryAdapter();
		converter = DataAtomicToJsonConverter.usingJsonFactoryForClientDataAtomic(factory,
				clientDataAtomic);
	}

	@Test
	public void testToJson() {
		String json = converter.toJson();

		Assert.assertEquals(json, "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\"}");
	}

	@Test
	public void testToJsonWithRepeatId() {
		clientDataAtomic.setRepeatId("2");
		String json = converter.toJson();

		Assert.assertEquals(json,
				"{\"repeatId\":\"2\",\"name\":\"atomicNameInData\",\"value\":\"atomicValue\"}");
	}

	@Test
	public void testToJsonWithEmptyRepeatId() {
		clientDataAtomic.setRepeatId("");
		String json = converter.toJson();

		Assert.assertEquals(json, "{\"name\":\"atomicNameInData\",\"value\":\"atomicValue\"}");
	}

	@Test
	public void testToJsonEmptyValue() {
		ClientDataAtomic clientDataAtomic = ClientDataAtomic.withNameInDataAndValue("atomicNameInData",
				"");
		OrgJsonBuilderFactoryAdapter factory = new OrgJsonBuilderFactoryAdapter();
		converter = DataAtomicToJsonConverter.usingJsonFactoryForClientDataAtomic(factory,
				clientDataAtomic);
		String json = converter.toJson();

		Assert.assertEquals(json, "{\"name\":\"atomicNameInData\",\"value\":\"\"}");
	}
}
