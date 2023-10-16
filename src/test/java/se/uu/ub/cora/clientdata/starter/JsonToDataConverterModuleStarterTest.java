/*
 * Copyright 2019 Uppsala University Library
 * Copyright 2023 Olov McKie
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

package se.uu.ub.cora.clientdata.starter;

import static org.testng.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.converter.JsonToClientDataConverterFactory;
import se.uu.ub.cora.clientdata.converter.JsonToClientDataConverterFactorySpy;

public class JsonToDataConverterModuleStarterTest {
	JsonToClientDataConverterModuleStarter starter;
	List<JsonToClientDataConverterFactory> converterFactoryImplementations;
	JsonToClientDataConverterFactorySpy converterFactorySpy;

	@BeforeMethod
	public void beforeMethod() {
		starter = new JsonToClientDataConverterModuleStarterImp();
		converterFactoryImplementations = new ArrayList<>();
		converterFactorySpy = new JsonToClientDataConverterFactorySpy();

	}

	@Test(expectedExceptions = ClientDataInitializationException.class, expectedExceptionsMessageRegExp = ""
			+ "No implementations found for JsonToClientDataConverterFactory")
	public void testStartModuleThrowsErrorIfNoConverterFactoryImplementations() throws Exception {
		starter.startUsingConverterFactoryImplementations(converterFactoryImplementations);
	}

	@Test(expectedExceptions = ClientDataInitializationException.class, expectedExceptionsMessageRegExp = ""
			+ "More than one implementation found for JsonToClientDataConverterFactory")
	public void testStartModuleThrowsErrorIfMoreThanOneConverterFactoryImplementations()
			throws Exception {
		converterFactoryImplementations.add(new JsonToClientDataConverterFactorySpy());
		converterFactoryImplementations.add(new JsonToClientDataConverterFactorySpy());
		starter.startUsingConverterFactoryImplementations(converterFactoryImplementations);
	}

	@Test
	public void testGetConverterFactory() throws Exception {
		converterFactoryImplementations.add(converterFactorySpy);
		starter.startUsingConverterFactoryImplementations(converterFactoryImplementations);
		assertSame(starter.getJsonToDataConverterFactory(), converterFactorySpy);
	}
}
