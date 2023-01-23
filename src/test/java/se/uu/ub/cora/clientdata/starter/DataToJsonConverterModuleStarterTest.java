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

package se.uu.ub.cora.clientdata.starter;

import static org.testng.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.converter.DataToJsonConverterFactoryCreator;
import se.uu.ub.cora.clientdata.converter.DataToJsonConverterFactoryCreatorSpy;

public class DataToJsonConverterModuleStarterTest {
	DataToJsonConverterModuleStarter starter;
	List<DataToJsonConverterFactoryCreator> converterFactoryCreatorImplementations;
	DataToJsonConverterFactoryCreator converterFactoryCreatorSpy;

	@BeforeMethod
	public void beforeMethod() {
		starter = new DataToJsonConverterModuleStarterImp();
		converterFactoryCreatorImplementations = new ArrayList<>();
		converterFactoryCreatorSpy = new DataToJsonConverterFactoryCreatorSpy();
	}

	@Test(expectedExceptions = DataInitializationException.class, expectedExceptionsMessageRegExp = ""
			+ "No implementations found for DataToJsonConverterFactoryCreator")
	public void testStartModuleThrowsErrorIfNoConverterFactoryImplementations() throws Exception {
		starter.startUsingConverterFactoryImplementations(converterFactoryCreatorImplementations);
	}

	@Test(expectedExceptions = DataInitializationException.class, expectedExceptionsMessageRegExp = ""
			+ "More than one implementation found for DataToJsonConverterFactoryCreator")
	public void testStartModuleThrowsErrorIfMoreThanOneConverterFactoryImplementations()
			throws Exception {
		converterFactoryCreatorImplementations.add(new DataToJsonConverterFactoryCreatorSpy());
		converterFactoryCreatorImplementations.add(new DataToJsonConverterFactoryCreatorSpy());
		starter.startUsingConverterFactoryImplementations(converterFactoryCreatorImplementations);
	}

	@Test
	public void testGetConverterFactory() throws Exception {
		converterFactoryCreatorImplementations.add(converterFactoryCreatorSpy);
		starter.startUsingConverterFactoryImplementations(converterFactoryCreatorImplementations);
		assertSame(starter.getDataToJsonConverterFactoryCreator(), converterFactoryCreatorSpy);
	}
}
