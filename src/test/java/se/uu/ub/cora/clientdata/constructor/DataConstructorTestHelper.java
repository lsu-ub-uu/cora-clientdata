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
package se.uu.ub.cora.clientdata.constructor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import se.uu.ub.cora.clientdata.ClientDataGroup;

public class DataConstructorTestHelper {
	public static void assertCorrectRecordInfoUsingRecordInfoAndDataDividerAndId(
			ClientDataGroup recordInfo, String dataDivider, String id) {
		assertEquals(recordInfo.getFirstAtomicValueWithNameInData("id"), id);
		ClientDataGroup dataDividerGroup = recordInfo.getFirstGroupWithNameInData("dataDivider");
		assertNotNull(dataDividerGroup);
		assertEquals(dataDividerGroup.getFirstAtomicValueWithNameInData("linkedRecordType"),
				"system");
		assertEquals(dataDividerGroup.getFirstAtomicValueWithNameInData("linkedRecordId"),
				dataDivider);
		assertEquals(recordInfo.getChildren().size(), 2);
	}

	public static void assertCorrectDataLinkUsingNameInDataAndRecordTypeAndRecordIdAndRepeatId(
			ClientDataGroup link, String nameInData, String recordType, String recordId,
			String repeatId) {
		assertEquals(link.getNameInData(), nameInData);
		assertEquals(link.getFirstAtomicValueWithNameInData("linkedRecordType"), recordType);
		assertEquals(link.getFirstAtomicValueWithNameInData("linkedRecordId"), recordId);
		assertEquals(link.getRepeatId(), repeatId);
	}
}
