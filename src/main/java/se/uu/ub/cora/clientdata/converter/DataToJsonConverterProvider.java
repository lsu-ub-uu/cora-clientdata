/*
 * Copyright 2019, 2021 Uppsala University Library
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
package se.uu.ub.cora.clientdata.converter;

import java.util.ServiceLoader;

import se.uu.ub.cora.clientdata.starter.DataToJsonConverterModuleStarter;
import se.uu.ub.cora.clientdata.starter.DataToJsonConverterModuleStarterImp;

/**
 * 
 * DataToJsonConverterProvider provides a means to get instances of a plugged in implementation of
 * {@link DataToJsonConverterFactory}. This class will with the help of ServiceLoader search for
 * implementations of {@link DataToJsonConverterFactoryCreator}s and as long as only one
 * implementation is found use the found implementation to create new DataToJsonConverterFactories.
 * <p>
 * New factory instances can be created using the {@link #createImplementingFactory()} method.
 */
public class DataToJsonConverterProvider {
	private static DataToJsonConverterModuleStarter jsonToDataConverterModuleStarter = new DataToJsonConverterModuleStarterImp();
	private static DataToJsonConverterFactoryCreator converterFactoryCreator;

	private DataToJsonConverterProvider() {
		// not called
		throw new UnsupportedOperationException();
	}

	/**
	 * createImplementingFactory method provides a new factory on each call of type
	 * {@link DataToJsonConverterFactory}
	 * 
	 * @return a new factory of type {@link DataToJsonConverterFactory}
	 */
	public static DataToJsonConverterFactory createImplementingFactory() {
		ensureConverterFactoryCreatorIsSet();
		return converterFactoryCreator.createFactory();
	}

	private static synchronized void ensureConverterFactoryCreatorIsSet() {
		if (null == converterFactoryCreator) {
			getConverterFactoryImpUsingModuleStarter();
		}
	}

	private static void getConverterFactoryImpUsingModuleStarter() {
		Iterable<DataToJsonConverterFactoryCreator> dataToJsonConverterFactoryCreatorImplementations = ServiceLoader
				.load(DataToJsonConverterFactoryCreator.class);
		jsonToDataConverterModuleStarter.startUsingConverterFactoryImplementations(
				dataToJsonConverterFactoryCreatorImplementations);
		converterFactoryCreator = jsonToDataConverterModuleStarter
				.getDataToJsonConverterFactoryCreator();
	}

	/**
	 * Sets a setDataToJsonConverterFactoryCreator that will be used to factor jsonToDataConverters
	 * for Classes. This possibility to set a DataToJsonConverterFactoryCreator is provided to
	 * enable testing of logging in other classes and is not intented to be used in production. The
	 * DataGroupFactory to use should be provided through an implementation of
	 * DataToJsonConverterFactoryCreator in a seperate java module.
	 * 
	 * @param dataToJsonConverterFactoryCreator
	 *            A DataToJsonConverterFactoryCreator to use to create jsonToDataConverterFactories
	 *            for testing
	 */
	public static void setDataToJsonConverterFactoryCreator(
			DataToJsonConverterFactoryCreator converterFactoryCreator) {
		DataToJsonConverterProvider.converterFactoryCreator = converterFactoryCreator;

	}

	static DataToJsonConverterModuleStarter getStarter() {
		return jsonToDataConverterModuleStarter;
	}

	static void setStarter(DataToJsonConverterModuleStarter starter) {
		jsonToDataConverterModuleStarter = starter;
	}

}
