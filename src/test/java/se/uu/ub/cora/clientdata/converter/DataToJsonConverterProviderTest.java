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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.starter.ClientDataInitializationException;
import se.uu.ub.cora.clientdata.starter.ClientDataToJsonConverterModuleStarter;
import se.uu.ub.cora.clientdata.starter.ClientDataToJsonConverterModuleStarterImp;

public class DataToJsonConverterProviderTest {

	@BeforeMethod
	public void beforeMethod() {
		ClientDataToJsonConverterProvider.setDataToJsonConverterFactoryCreator(null);
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<ClientDataToJsonConverterProvider> constructor = ClientDataToJsonConverterProvider.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void testPrivateConstructorInvoke() throws Exception {
		Constructor<ClientDataToJsonConverterProvider> constructor = ClientDataToJsonConverterProvider.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testCreateImplementingFactory() throws Exception {
		DataToJsonConverterFactoryCreatorSpy converterFactoryCreatorSpy = new DataToJsonConverterFactoryCreatorSpy();
		ClientDataToJsonConverterProvider
				.setDataToJsonConverterFactoryCreator(converterFactoryCreatorSpy);
		ClientDataToJsonConverterFactory factory = ClientDataToJsonConverterProvider
				.createImplementingFactory();
		converterFactoryCreatorSpy.MCR.methodWasCalled("createFactory");
		converterFactoryCreatorSpy.MCR.assertReturn("createFactory", 0, factory);
	}

	@Test
	public void testStartingOfConverterFactoryCreatorCanOnlyBeDoneByOneThreadAtATime()
			throws Exception {
		Method declaredMethod = ClientDataToJsonConverterProvider.class
				.getDeclaredMethod("ensureConverterFactoryCreatorIsSet");
		assertTrue(Modifier.isSynchronized(declaredMethod.getModifiers()));
	}

	@Test
	public void testNonExceptionThrowingStartup() throws Exception {
		DataToJsonConverterModuleStarterSpy starter = startDataGroupModuleInitializerWithStarterSpy();

		ClientDataToJsonConverterProvider.createImplementingFactory();

		assertTrue(starter.startWasCalled);
	}

	private DataToJsonConverterModuleStarterSpy startDataGroupModuleInitializerWithStarterSpy() {
		ClientDataToJsonConverterModuleStarter starter = new DataToJsonConverterModuleStarterSpy();
		ClientDataToJsonConverterProvider.setStarter(starter);
		return (DataToJsonConverterModuleStarterSpy) starter;
	}

	@Test
	public void testInitUsesDefaultModuleStarter() throws Exception {
		makeSureErrorIsThrownAsNoImplementationsExistInThisModule();
		ClientDataToJsonConverterModuleStarter starter = ClientDataToJsonConverterProvider.getStarter();
		assertStarterIsDataGroupModuleStarter(starter);
	}

	private void makeSureErrorIsThrownAsNoImplementationsExistInThisModule() {
		Exception caughtException = null;
		try {
			ClientDataToJsonConverterProvider.createImplementingFactory();
		} catch (Exception e) {
			caughtException = e;
		}
		assertTrue(caughtException instanceof ClientDataInitializationException);
		assertEquals(caughtException.getMessage(),
				"No implementations found for DataToJsonConverterFactoryCreator");
	}

	private void assertStarterIsDataGroupModuleStarter(ClientDataToJsonConverterModuleStarter starter) {
		assertTrue(starter instanceof ClientDataToJsonConverterModuleStarterImp);
	}
}
