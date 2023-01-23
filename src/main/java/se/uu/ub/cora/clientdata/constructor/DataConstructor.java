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

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataGroup;

public class DataConstructor {

	protected String dataDivider;

	protected ClientDataGroup createRecordInfoGroupForId(String textId) {
		ClientDataGroup recordInfo = ClientDataGroup.withNameInData("recordInfo");
		recordInfo.addChild(createAtomicForId(textId));
		recordInfo.addChild(createDataDivierGroup());
		return recordInfo;
	}

	private ClientDataAtomic createAtomicForId(String textId) {
		return ClientDataAtomic.withNameInDataAndValue("id", textId);
	}

	private ClientDataGroup createDataDivierGroup() {
		return createDataLinkUsingNameInDataAndRecordTypeAndRecordId("dataDivider", "system",
				dataDivider);
	}

	protected ClientDataGroup createDataLinkUsingNameInDataAndRecordTypeAndRecordId(
			String nameInData, String linkedRecordType, String linkedRecordId) {
		ClientDataGroup dataDividerGroup = ClientDataGroup.withNameInData(nameInData);
		dataDividerGroup.addChild(createAtomicForLinkedRecordType(linkedRecordType));
		dataDividerGroup.addChild(createAtomicForLinkedRecordId(linkedRecordId));
		return dataDividerGroup;
	}

	protected ClientDataAtomic createAtomicForLinkedRecordType(String linkedRecordType) {
		return ClientDataAtomic.withNameInDataAndValue("linkedRecordType", linkedRecordType);
	}

	protected ClientDataAtomic createAtomicForLinkedRecordId(String linkedRecordId) {
		return ClientDataAtomic.withNameInDataAndValue("linkedRecordId", linkedRecordId);
	}

	protected ClientDataGroup createDataLinkUsingNameInDataAndRecordTypeAndRecordIdAndRepeatId(
			String nameInData, String linkedRecordType, String linkedRecordId, String repeatId) {
		ClientDataGroup cdg = createDataLinkUsingNameInDataAndRecordTypeAndRecordId(nameInData,
				linkedRecordType, linkedRecordId);
		cdg.setRepeatId(repeatId);
		return cdg;
	}

}
