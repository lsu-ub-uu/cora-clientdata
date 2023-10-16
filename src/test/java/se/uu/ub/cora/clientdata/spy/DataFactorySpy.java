/*
 * Copyright 2019 Uppsala University Library
 * Copyright 2022, 2023 Olov McKie
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
package se.uu.ub.cora.clientdata.spy;

import se.uu.ub.cora.clientdata.ClientAction;
import se.uu.ub.cora.clientdata.ClientActionLink;
import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataChildFilter;
import se.uu.ub.cora.clientdata.ClientDataFactory;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataList;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.clientdata.ClientDataRecordGroup;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;
import se.uu.ub.cora.testutils.mrv.MethodReturnValues;

public class DataFactorySpy implements ClientDataFactory {
	public MethodCallRecorder MCR = new MethodCallRecorder();
	public MethodReturnValues MRV = new MethodReturnValues();
	public ClientDataRecordGroup dataRecordGroup;

	public DataFactorySpy() {
		MCR.useMRV(MRV);
		MRV.setDefaultReturnValuesSupplier("factorActionLinkUsingAction", DataActionLinkSpy::new);
	}

	@Override
	public ClientDataList factorListUsingNameOfDataType(String nameOfDataType) {
		MCR.addCall("nameOfDataType", nameOfDataType);
		ClientDataList dataList = new DataListSpy();
		MCR.addReturned(dataList);
		return dataList;
	}

	@Override
	public ClientDataRecord factorRecordUsingDataRecordGroup(
			ClientDataRecordGroup dataRecordGroup) {
		MCR.addCall("dataRecordGroup", dataRecordGroup);
		this.dataRecordGroup = dataRecordGroup;
		ClientDataRecord dataRecordSpy = new DataRecordSpy();
		MCR.addReturned(dataRecordSpy);
		return dataRecordSpy;
	}

	@Override
	public ClientDataRecordGroup factorRecordGroupUsingNameInData(String nameInData) {
		MCR.addCall("nameInData", nameInData);
		ClientDataRecordGroup dataRecordGroupSpy = new DataRecordGroupSpy();
		MCR.addReturned(dataRecordGroupSpy);
		return dataRecordGroupSpy;
	}

	@Override
	public ClientDataRecordGroup factorRecordGroupFromDataGroup(ClientDataGroup dataGroup) {
		MCR.addCall("dataGroup", dataGroup);
		ClientDataRecordGroup dataRecordGroupSpy = new DataRecordGroupSpy();
		MCR.addReturned(dataRecordGroupSpy);
		return dataRecordGroupSpy;
	}

	@Override
	public ClientDataGroup factorGroupFromDataRecordGroup(ClientDataRecordGroup dataRecordGroup) {
		MCR.addCall("dataRecordGroup", dataRecordGroup);
		ClientDataGroup dataGroupSpy = new DataGroupSpy("nameInData");
		MCR.addReturned(dataGroupSpy);
		return dataGroupSpy;
	}

	@Override
	public ClientDataGroup factorGroupUsingNameInData(String nameInData) {
		MCR.addCall("nameInData", nameInData);
		ClientDataGroup dataGroupSpy = new DataGroupSpy(nameInData);
		MCR.addReturned(dataGroupSpy);
		return dataGroupSpy;
	}

	@Override
	public ClientDataRecordLink factorRecordLinkUsingNameInData(String nameInData) {
		MCR.addCall("nameInData", nameInData);
		ClientDataRecordLink dataLinkSpy = new DataRecordLinkSpy();
		MCR.addReturned(dataLinkSpy);
		return dataLinkSpy;
	}

	@Override
	public ClientDataRecordLink factorRecordLinkUsingNameInDataAndTypeAndId(String nameInData,
			String type, String id) {
		MCR.addCall("nameInData", nameInData, "type", type, "id", id);
		ClientDataRecordLink dataLinkSpy = new DataRecordLinkSpy();
		MCR.addReturned(dataLinkSpy);
		return dataLinkSpy;
	}

	@Override
	public ClientDataResourceLink factorResourceLinkUsingNameInData(String nameInData) {
		MCR.addCall("nameInData", nameInData);
		ClientDataResourceLink dataLinkSpy = new DataResourceLinkSpy();
		MCR.addReturned(dataLinkSpy);
		return dataLinkSpy;
	}

	@Override
	public ClientDataAtomic factorAtomicUsingNameInDataAndValue(String nameInData, String value) {
		MCR.addCall("nameInData", nameInData, "value", value);
		ClientDataAtomic dataAtomic = new DataAtomicSpy();
		MCR.addReturned(dataAtomic);
		return dataAtomic;
	}

	@Override
	public ClientDataAtomic factorAtomicUsingNameInDataAndValueAndRepeatId(String nameInData,
			String value, String repeatId) {
		MCR.addCall("nameInData", nameInData, "value", value, "repeatId", repeatId);
		ClientDataAtomic dataAtomic = new DataAtomicSpy();
		MCR.addReturned(dataAtomic);
		return dataAtomic;
	}

	@Override
	public ClientDataAttribute factorAttributeUsingNameInDataAndValue(String nameInData,
			String value) {
		MCR.addCall("nameInData", nameInData, "value", value);
		ClientDataAttribute dataAttribute = new DataAttributeSpy();
		MCR.addReturned(dataAttribute);
		return dataAttribute;
	}

	@Override
	public ClientActionLink factorActionLinkUsingAction(ClientAction clientAction) {
		return (ClientActionLink) MCR.addCallAndReturnFromMRV("clientAction", clientAction);
	}

	@Override
	public ClientDataChildFilter factorDataChildFilterUsingNameInData(String childNameInData) {
		MCR.addCall("childNameInData", childNameInData);
		ClientDataChildFilter dataChildFilter = new DataChildFilterSpy();
		MCR.addReturned(dataChildFilter);
		return dataChildFilter;
	}

}
