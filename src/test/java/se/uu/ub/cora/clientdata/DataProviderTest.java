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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.spy.DataFactorySpy;
import se.uu.ub.cora.clientdata.spy.DataGroupSpy;
import se.uu.ub.cora.clientdata.spy.DataModuleStarterSpy;
import se.uu.ub.cora.clientdata.spy.DataRecordGroupSpy;
import se.uu.ub.cora.clientdata.starter.ClientDataInitializationException;
import se.uu.ub.cora.clientdata.starter.ClientDataModuleStarter;
import se.uu.ub.cora.clientdata.starter.ClientDataModuleStarterImp;

public class DataProviderTest {

	private DataGroupSpy dataGroup;

	@BeforeMethod
	public void beforeMethod() {
		ClientDataProvider.onlyForTestSetDataFactory(null);
		dataGroup = new DataGroupSpy("someNameInData");
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<ClientDataProvider> constructor = ClientDataProvider.class.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void testPrivateConstructorInvoke() throws Exception {
		Constructor<ClientDataProvider> constructor = ClientDataProvider.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testStartingOfDataRecordFactoryCanOnlyBeDoneByOneThreadAtATime() throws Exception {
		Method declaredMethod = ClientDataProvider.class.getDeclaredMethod("ensureDataFactoryIsSet");
		assertTrue(Modifier.isSynchronized(declaredMethod.getModifiers()));
	}

	@Test
	public void testInitUsesDefaultLoggerModuleStarter() throws Exception {
		makeSureErrorIsThrownAsNoImplementationsExistInThisModule();
		ClientDataModuleStarter starter = ClientDataProvider.getStarter();
		assertStarterIsModuleStarter(starter);
	}

	private void makeSureErrorIsThrownAsNoImplementationsExistInThisModule() {
		ClientDataProvider.setStarter(new ClientDataModuleStarterImp());
		Exception caughtException = null;
		try {
			ClientDataProvider.createRecordWithDataGroup(dataGroup);
		} catch (Exception e) {
			caughtException = e;
		}
		assertTrue(caughtException instanceof ClientDataInitializationException);
		assertEquals(caughtException.getMessage(), "No implementations found for DataFactory");
	}

	private void assertStarterIsModuleStarter(ClientDataModuleStarter starter) {
		assertTrue(starter instanceof ClientDataModuleStarterImp);
	}

	@Test
	public void testOnlyForTestetSetDataFactory() throws Exception {
		DataFactorySpy dataFactorySpy = new DataFactorySpy();
		ClientDataProvider.onlyForTestSetDataFactory(dataFactorySpy);

		ClientDataProvider.createRecordWithDataGroup(dataGroup);

		assertEquals(dataFactorySpy.dataGroup, dataGroup);
	}

	private DataModuleStarterSpy startDataRecordModuleInitializerWithStarterSpy() {
		ClientDataProvider.onlyForTestSetDataFactory(null);
		ClientDataModuleStarter starter = new DataModuleStarterSpy();
		ClientDataProvider.setStarter(starter);
		return (DataModuleStarterSpy) starter;
	}

	private void assertStarterWasCalled(DataModuleStarterSpy starter) {
		starter.MCR.assertMethodWasCalled("startUsingDataFactoryImplementations");
	}

	private DataFactorySpy getFactorySpyFromStarterSpy(DataModuleStarterSpy starter) {
		DataFactorySpy dataFactorySpy = (DataFactorySpy) starter.MCR
				.getReturnValue("getDataFactory", 0);
		return dataFactorySpy;
	}

	@Test
	public void testCreate_DataList() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataList dataList = ClientDataProvider.createListWithNameOfDataType("dataType");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorListUsingNameOfDataType", 0, "dataType");
		dataFactorySpy.MCR.assertReturn("factorListUsingNameOfDataType", 0, dataList);
	}

	@Test
	public void testCreate_Record() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataRecord dataRecord = ClientDataProvider.createRecordWithDataGroup(dataGroup);

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorRecordUsingDataGroup", 0, dataGroup);
		dataFactorySpy.MCR.assertReturn("factorRecordUsingDataGroup", 0, dataRecord);
	}

	@Test
	public void testCreate_RecordGroupUsingNameInData() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataRecordGroup dataRecordGroup = ClientDataProvider
				.createRecordGroupUsingNameInData("dataRecordGroup");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorRecordGroupUsingNameInData", 0,
				"dataRecordGroup");
		dataFactorySpy.MCR.assertReturn("factorRecordGroupUsingNameInData", 0, dataRecordGroup);
	}

	@Test
	public void testCreate_RecordGroupFromDataGroup() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataRecordGroup dataRecordGroup = ClientDataProvider.createRecordGroupFromDataGroup(dataGroup);

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorRecordGroupFromDataGroup", 0, dataGroup);
		dataFactorySpy.MCR.assertReturn("factorRecordGroupFromDataGroup", 0, dataRecordGroup);
	}

	@Test
	public void testCreate_GroupFromRecordGroup() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();
		ClientDataRecordGroup dataRecordGroup = new DataRecordGroupSpy();

		ClientDataGroup dataGroup = ClientDataProvider.createGroupFromRecordGroup(dataRecordGroup);

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorGroupFromDataRecordGroup", 0, dataRecordGroup);
		dataFactorySpy.MCR.assertReturn("factorGroupFromDataRecordGroup", 0, dataGroup);
	}

	@Test
	public void testCreate_GroupUsingNameInData() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataGroup dataRecordGroup = ClientDataProvider.createGroupUsingNameInData("dataGroup");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorGroupUsingNameInData", 0, "dataGroup");
		dataFactorySpy.MCR.assertReturn("factorGroupUsingNameInData", 0, dataRecordGroup);
	}

	@Test
	public void testCreate_RecordLinkUsingNameInData() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataRecordLink dataRecordLink = ClientDataProvider.createRecordLinkUsingNameInData("recordLink");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorRecordLinkUsingNameInData", 0, "recordLink");
		dataFactorySpy.MCR.assertReturn("factorRecordLinkUsingNameInData", 0, dataRecordLink);
	}

	@Test
	public void testCreate_RecordLinkUsingNameInDataAndTypeAndId() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataRecordLink dataRecordLink = ClientDataProvider
				.createRecordLinkUsingNameInDataAndTypeAndId("recordLink", "type", "id");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorRecordLinkUsingNameInDataAndTypeAndId", 0,
				"recordLink", "type", "id");
		dataFactorySpy.MCR.assertReturn("factorRecordLinkUsingNameInDataAndTypeAndId", 0,
				dataRecordLink);
	}

	@Test
	public void testCreate_ResourceLinkUsingNameInData() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataResourceLink dataResourceLink = ClientDataProvider
				.createResourceLinkUsingNameInData("resourceLink");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorResourceLinkUsingNameInData", 0, "resourceLink");
		dataFactorySpy.MCR.assertReturn("factorResourceLinkUsingNameInData", 0, dataResourceLink);
	}

	@Test
	public void testCreate_AtomicUsingNameInData() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataAtomic dataAtomic = ClientDataProvider.createAtomicUsingNameInDataAndValue("atomic", "value");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorAtomicUsingNameInDataAndValue", 0, "atomic",
				"value");
		dataFactorySpy.MCR.assertReturn("factorAtomicUsingNameInDataAndValue", 0, dataAtomic);
	}

	@Test
	public void testCreate_AtomicUsingNameInDataAndValueAndRepeatId() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataAtomic dataAtomic = ClientDataProvider
				.createAtomicUsingNameInDataAndValueAndRepeatId("atomic", "value", "repeatid");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorAtomicUsingNameInDataAndValueAndRepeatId", 0,
				"atomic", "value", "repeatid");
		dataFactorySpy.MCR.assertReturn("factorAtomicUsingNameInDataAndValueAndRepeatId", 0,
				dataAtomic);
	}

	@Test
	public void testCreate_AttributeUsingNameInDataAndValue() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		ClientDataAttribute dataAttribute = ClientDataProvider
				.createAttributeUsingNameInDataAndValue("attribute", "value");

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorAttributeUsingNameInDataAndValue", 0,
				"attribute", "value");
		dataFactorySpy.MCR.assertReturn("factorAttributeUsingNameInDataAndValue", 0, dataAttribute);
	}

	@Test
	public void testCreateDataChildFilterUsingChildNameInData() throws Exception {
		DataModuleStarterSpy starter = startDataRecordModuleInitializerWithStarterSpy();

		String childNameInData = "someChildNameInData";
		ClientDataChildFilter dataChildFilter = ClientDataProvider
				.createDataChildFilterUsingChildNameInData(childNameInData);

		assertStarterWasCalled(starter);
		DataFactorySpy dataFactorySpy = getFactorySpyFromStarterSpy(starter);
		dataFactorySpy.MCR.assertParameters("factorDataChildFilterUsingNameInData", 0,
				childNameInData);
		dataFactorySpy.MCR.assertReturn("factorDataChildFilterUsingNameInData", 0, dataChildFilter);
	}
}
