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

package se.uu.ub.cora.clientdata;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.annotations.Test;

public class ClientDataListTest {
	@Test
	public void testInit() {
		String containDataOfType = "metadata";
		ClientDataList clientDataList = ClientDataList.withContainDataOfType(containDataOfType);
		assertEquals(clientDataList.getContainDataOfType(), "metadata");
	}

	@Test
	public void testAddRecord() {
		ClientDataList clientDataList = ClientDataList.withContainDataOfType("metadata");
		ClientDataGroupImp clientDataGroup = ClientDataGroupImp.withNameInData("clientDataGroupId");
		ClientDataRecord record = ClientDataRecord.withClientDataGroup(clientDataGroup);
		clientDataList.addData(record);
		List<ClientData> records = clientDataList.getDataList();
		assertEquals(records.get(0), record);
	}

	@Test
	public void testAddGroup() {
		ClientDataList clientDataList = ClientDataList.withContainDataOfType("metadata");
		ClientDataGroupImp clientDataGroup = ClientDataGroupImp.withNameInData("clientDataGroupId");
		clientDataList.addData(clientDataGroup);
		List<ClientData> groups = clientDataList.getDataList();
		assertEquals(groups.get(0), clientDataGroup);
	}

	@Test
	public void testTotalNo() {
		ClientDataList clientDataList = ClientDataList.withContainDataOfType("metadata");
		clientDataList.setTotalNo("2");
		assertEquals(clientDataList.getTotalNo(), "2");
	}

	@Test
	public void testFromNo() {
		ClientDataList clientDataList = ClientDataList.withContainDataOfType("metadata");
		clientDataList.setFromNo("0");
		assertEquals(clientDataList.getFromNo(), "0");
	}

	@Test
	public void testToNo() {
		ClientDataList clientDataList = ClientDataList.withContainDataOfType("metadata");
		clientDataList.setToNo("2");
		assertEquals(clientDataList.getToNo(), "2");
	}
}
