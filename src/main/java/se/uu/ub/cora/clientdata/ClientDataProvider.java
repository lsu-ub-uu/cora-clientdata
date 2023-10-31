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
package se.uu.ub.cora.clientdata;

import java.util.ServiceLoader;

import se.uu.ub.cora.clientdata.starter.ClientDataModuleStarter;
import se.uu.ub.cora.clientdata.starter.ClientDataModuleStarterImp;

/**
 * DataProvider provides a means for other classes in the system to create instances of different
 * data classes while maintaing an easy possiblility to replace the implementing classes.
 * <p>
 * DataProvider uses javas module system to load an implementation of a DataFactory which is
 * expected to be implemented in a different module. Changing of implementations of data classes in
 * the entire system is then as easy as providing a different module implementing the data
 * interfaces.
 * <p>
 * DataProvider has a number of static methods that start with createX that is intended to be used
 * to create all instances of data classes in the system.
 * <p>
 * To help with testing is there a metod
 * {@link ClientDataProvider#onlyForTestSetDataFactory(ClientDataFactory)} that makes it possible to
 * change the implementing data classes while testing.
 */
public class ClientDataProvider {

	private static ClientDataFactory dataFactory;
	private static ClientDataModuleStarter dataModuleStarter = new ClientDataModuleStarterImp();

	private ClientDataProvider() {
		// not called
		throw new UnsupportedOperationException();
	}

	/**
	 * onlyForTestSetDataRecordFactory sets a DataFactory that will be used to factor data classes
	 * when other classes needs to create new instances of data classes. This possibility to set a
	 * DataRecordFactory is provided to enable testing of data creation in other classes and is not
	 * intented to be used in production.
	 * <p>
	 * The DataFactory to use in production should be provided through an implementation of
	 * DataFactory in a seperate java module.
	 * 
	 * @param dataFactory
	 *            A DataFactory to use to create data classes for testing
	 */
	public static void onlyForTestSetDataFactory(ClientDataFactory dataFactory) {
		ClientDataProvider.dataFactory = dataFactory;
	}

	private static void getDataFactoryImpUsingModuleStarter() {
		Iterable<ClientDataFactory> dataFactoryImplementations = ServiceLoader
				.load(ClientDataFactory.class);
		dataModuleStarter.startUsingDataFactoryImplementations(dataFactoryImplementations);
		dataFactory = dataModuleStarter.getDataFactory();
	}

	static void setStarter(ClientDataModuleStarter starter) {
		dataModuleStarter = starter;
	}

	static ClientDataModuleStarter getStarter() {
		return dataModuleStarter;
	}

	private static synchronized void ensureDataFactoryIsSet() {
		if (null == dataFactory) {
			getDataFactoryImpUsingModuleStarter();
		}
	}

	public static ClientDataList createListWithNameOfDataType(String nameOfDataType) {
		ensureDataFactoryIsSet();
		return dataFactory.factorListUsingNameOfDataType(nameOfDataType);
	}

	public static ClientDataRecord createRecordWithDataRecordGroup(
			ClientDataRecordGroup dataRecordGroup) {
		ensureDataFactoryIsSet();
		return dataFactory.factorRecordUsingDataRecordGroup(dataRecordGroup);
	}

	public static ClientDataRecordGroup createRecordGroupUsingNameInData(String nameInData) {
		ensureDataFactoryIsSet();
		return dataFactory.factorRecordGroupUsingNameInData(nameInData);
	}

	/**
	 * createRecordGroupFromDataGroup creates a {@link ClientDataRecordGroup} from a
	 * {@link ClientDataGroup}.
	 * 
	 * @param dataGroup
	 *            A DataGroup to turn into a DataRecordGroup.
	 * @return A DataRecordGroup with the same nameInData, attributes and children as the provided
	 *         DataGroup
	 */
	public static ClientDataRecordGroup createRecordGroupFromDataGroup(ClientDataGroup dataGroup) {
		ensureDataFactoryIsSet();
		return dataFactory.factorRecordGroupFromDataGroup(dataGroup);
	}

	/**
	 * createGroupFromRecordGroup creates a {@link ClientDataGroup} from a
	 * {@link ClientDataRecordGroup}.
	 * 
	 * @param dataRecordGroup
	 *            A DataRecordGroup to turn into a DataGroup.
	 * @return A DataGroup with the same nameInData, attributes and children as the provided
	 *         DataRecordGroup
	 */
	public static ClientDataGroup createGroupFromRecordGroup(
			ClientDataRecordGroup dataRecordGroup) {
		ensureDataFactoryIsSet();
		return dataFactory.factorGroupFromDataRecordGroup(dataRecordGroup);
	}

	public static ClientDataGroup createGroupUsingNameInData(String nameInData) {
		ensureDataFactoryIsSet();
		return dataFactory.factorGroupUsingNameInData(nameInData);
	}

	public static ClientDataRecordLink createRecordLinkUsingNameInData(String nameInData) {
		ensureDataFactoryIsSet();
		return dataFactory.factorRecordLinkUsingNameInData(nameInData);
	}

	public static ClientDataRecordLink createRecordLinkUsingNameInDataAndTypeAndId(
			String nameInData, String recordType, String recordId) {
		ensureDataFactoryIsSet();
		return dataFactory.factorRecordLinkUsingNameInDataAndTypeAndId(nameInData, recordType,
				recordId);
	}

	public static ClientDataResourceLink createResourceLinkUsingNameInDataAndMimeType(String nameInData,
			String mimeType) {
		ensureDataFactoryIsSet();
		return dataFactory.factorResourceLinkUsingNameInDataAndMimeType(nameInData, mimeType);
	}

	public static ClientDataAtomic createAtomicUsingNameInDataAndValue(String nameInData,
			String value) {
		ensureDataFactoryIsSet();
		return dataFactory.factorAtomicUsingNameInDataAndValue(nameInData, value);
	}

	public static ClientDataAtomic createAtomicUsingNameInDataAndValueAndRepeatId(String nameInData,
			String value, String repeatId) {
		ensureDataFactoryIsSet();
		return dataFactory.factorAtomicUsingNameInDataAndValueAndRepeatId(nameInData, value,
				repeatId);
	}

	public static ClientDataAttribute createAttributeUsingNameInDataAndValue(String nameInData,
			String value) {
		ensureDataFactoryIsSet();
		return dataFactory.factorAttributeUsingNameInDataAndValue(nameInData, value);
	}

	/**
	 * createActionLinkUsingAction creates an {@link ClientActionLink} using the provided
	 * {@link ClientAction}
	 * 
	 * @param clientAction
	 *            A ClientAction to use as action for the link
	 * @return A ClientActionLink with the provided ClientAction
	 */
	public static ClientActionLink createActionLinkUsingAction(ClientAction clientAction) {
		ensureDataFactoryIsSet();
		return dataFactory.factorActionLinkUsingAction(clientAction);
	}

	public static ClientDataChildFilter createDataChildFilterUsingChildNameInData(
			String childNameInData) {
		ensureDataFactoryIsSet();
		return dataFactory.factorDataChildFilterUsingNameInData(childNameInData);
	}

}
