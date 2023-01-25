/*
 * Copyright 2019 Uppsala University Library
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
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.starter.ClientDataInitializationException;
import se.uu.ub.cora.clientdata.starter.JsonToClientDataConverterModuleStarter;
import se.uu.ub.cora.clientdata.starter.JsonToClientDataConverterModuleStarterImp;
import se.uu.ub.cora.json.parser.JsonValue;
import se.uu.ub.cora.json.parser.org.OrgJsonParser;

public class JsonToDataConverterProviderTest {

	@BeforeMethod
	public void beforeMethod() {
		JsonToClientDataConverterProvider.setJsonToDataConverterFactory(null);
	}

	@Test
	public void testPrivateConstructor() throws Exception {
		Constructor<JsonToClientDataConverterProvider> constructor = JsonToClientDataConverterProvider.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
	}

	@Test(expectedExceptions = InvocationTargetException.class)
	public void testPrivateConstructorInvoke() throws Exception {
		Constructor<JsonToClientDataConverterProvider> constructor = JsonToClientDataConverterProvider.class
				.getDeclaredConstructor();
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testGetConverterUsesExistingConverterFactory() throws Exception {
		JsonToDataConverterFactorySpy converterFactorySpy = new JsonToDataConverterFactorySpy();
		JsonToClientDataConverterProvider.setJsonToDataConverterFactory(converterFactorySpy);
		JsonValue jsonValue = createJsonValue();
		JsonToClientDataConverter converter = JsonToClientDataConverterProvider
				.getConverterUsingJsonObject(jsonValue);

		assertTrue(converterFactorySpy.getConverterCalled);
		assertSame(converterFactorySpy.jsonValue, jsonValue);
		assertSame(converter, converterFactorySpy.returnedConverter);
	}

	private JsonValue createJsonValue() {
		OrgJsonParser jsonParser = new OrgJsonParser();
		String json = "{\"name\":\"groupNameInData\", \"children\":[]}";
		JsonValue jsonValue = jsonParser.parseString(json);
		return jsonValue;
	}

	@Test
	public void testStartingOfConverterFactoryCanOnlyBeDoneByOneThreadAtATime() throws Exception {
		Method declaredMethod = JsonToClientDataConverterProvider.class
				.getDeclaredMethod("ensureConverterFactoryIsSet");
		assertTrue(Modifier.isSynchronized(declaredMethod.getModifiers()));
	}

	@Test
	public void testNonExceptionThrowingStartup() throws Exception {
		JsonToDataConverterModuleStarterSpy starter = startDataGroupModuleInitializerWithStarterSpy();
		JsonValue jsonValue = createJsonValue();
		JsonToClientDataConverterProvider.getConverterUsingJsonObject(jsonValue);
		assertTrue(starter.startWasCalled);
	}

	private JsonToDataConverterModuleStarterSpy startDataGroupModuleInitializerWithStarterSpy() {
		JsonToClientDataConverterModuleStarter starter = new JsonToDataConverterModuleStarterSpy();
		JsonToClientDataConverterProvider.setStarter(starter);
		return (JsonToDataConverterModuleStarterSpy) starter;
	}

	@Test
	public void testInitUsesDefaultLoggerModuleStarter() throws Exception {
		makeSureErrorIsThrownAsNoImplementationsExistInThisModule();
		JsonToClientDataConverterModuleStarter starter = JsonToClientDataConverterProvider.getStarter();
		assertStarterIsDataGroupModuleStarter(starter);
	}

	private void makeSureErrorIsThrownAsNoImplementationsExistInThisModule() {
		Exception caughtException = null;
		try {
			JsonToClientDataConverterProvider.getConverterUsingJsonObject(createJsonValue());
		} catch (Exception e) {
			caughtException = e;
		}
		assertTrue(caughtException instanceof ClientDataInitializationException);
		assertEquals(caughtException.getMessage(),
				"No implementations found for JsonToDataConverterFactory");
	}

	private void assertStarterIsDataGroupModuleStarter(JsonToClientDataConverterModuleStarter starter) {
		assertTrue(starter instanceof JsonToClientDataConverterModuleStarterImp);
	}
}
