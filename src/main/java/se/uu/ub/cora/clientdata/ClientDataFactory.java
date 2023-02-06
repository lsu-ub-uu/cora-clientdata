/*
 * Copyright 2019 Uppsala University Library
 * Copyright 2022 Olov McKie
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

/**
 * DataFactory is used to factor instances of data classes in a Cora system.
 */
public interface ClientDataFactory {

	ClientDataList factorListUsingNameOfDataType(String nameOfDataType);

	ClientDataRecord factorRecordUsingDataRecordGroup(ClientDataRecordGroup dataRecordGroup);

	ClientDataRecordGroup factorRecordGroupUsingNameInData(String nameInData);

	ClientDataRecordGroup factorRecordGroupFromDataGroup(ClientDataGroup dataGroup);

	ClientDataGroup factorGroupFromDataRecordGroup(ClientDataRecordGroup dataRecordGroup);

	ClientDataGroup factorGroupUsingNameInData(String nameInData);

	ClientDataRecordLink factorRecordLinkUsingNameInData(String nameInData);

	ClientDataRecordLink factorRecordLinkUsingNameInDataAndTypeAndId(String nameInData, String recordType,
			String recordId);

	ClientDataResourceLink factorResourceLinkUsingNameInData(String nameInData);

	ClientDataAtomic factorAtomicUsingNameInDataAndValue(String nameInData, String value);

	ClientDataAtomic factorAtomicUsingNameInDataAndValueAndRepeatId(String nameInData, String value,
			String repeatId);

	ClientDataAttribute factorAttributeUsingNameInDataAndValue(String nameInData, String value);

	ClientDataChildFilter factorDataChildFilterUsingNameInData(String childNameInData);

}
