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

import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataGroupImp;
import se.uu.ub.cora.clientdata.ClientDataList;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataListToJsonConverterTest {

	@Test
	public void testToJson() {
		ClientDataList clientDataList = ClientDataList.withContainDataOfType("place");
		ClientDataGroupImp clientDataGroup = ClientDataGroupImp.withNameInData("groupId");
		ClientDataRecord clientDataRecord = ClientDataRecord.withClientDataGroup(clientDataGroup);
		clientDataList.addData(clientDataRecord);
		clientDataList.setTotalNo("1");
		clientDataList.setFromNo("0");
		clientDataList.setToNo("1");

		JsonBuilderFactory jsonFactory = new OrgJsonBuilderFactoryAdapter();
		DataToJsonConverterFactorySpy dataToJsonConverterFactory = new DataToJsonConverterFactorySpy();
		;
		DataListToJsonConverter recordListToJsonConverter = DataListToJsonConverter
				.usingJsonFactoryForClientDataList(jsonFactory, clientDataList,
						dataToJsonConverterFactory);
		String jsonString = recordListToJsonConverter.toJson();
		assertEquals(jsonString,
				"{\"dataList\":{\"fromNo\":\"0\",\""
						+ "data\":[{\"record\":{\"data\":{\"name\":\"groupId\"}}}],"
						+ "\"totalNo\":\"1\",\"containDataOfType\":\"place\",\"toNo\":\"1\"}}");
		assertEquals(dataToJsonConverterFactory.calledNumOfTimes, 1);
	}

	@Test
	public void testToJsonWithGroup() {
		ClientDataList clientDataList = ClientDataList.withContainDataOfType("place");
		ClientDataGroupImp clientDataGroup = ClientDataGroupImp.withNameInData("groupId");
		clientDataList.addData(clientDataGroup);
		clientDataList.setTotalNo("1");
		clientDataList.setFromNo("0");
		clientDataList.setToNo("1");

		JsonBuilderFactory jsonFactory = new OrgJsonBuilderFactoryAdapter();
		DataToJsonConverterFactorySpy dataToJsonConverterFactory = new DataToJsonConverterFactorySpy();
		DataListToJsonConverter recordListToJsonConverter = DataListToJsonConverter
				.usingJsonFactoryForClientDataList(jsonFactory, clientDataList,
						dataToJsonConverterFactory);
		String jsonString = recordListToJsonConverter.toJson();
		assertEquals(jsonString,
				"{\"dataList\":{\"fromNo\":\"0\",\"" + "data\":[{\"name\":\"groupId\"}],"
						+ "\"totalNo\":\"1\",\"containDataOfType\":\"place\",\"toNo\":\"1\"}}");
		assertEquals(dataToJsonConverterFactory.calledNumOfTimes, 1);
	}
}
