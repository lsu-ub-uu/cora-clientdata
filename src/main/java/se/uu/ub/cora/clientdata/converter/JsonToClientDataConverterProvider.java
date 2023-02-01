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

import java.util.ServiceLoader;

import se.uu.ub.cora.clientdata.starter.JsonToClientDataConverterModuleStarter;
import se.uu.ub.cora.clientdata.starter.JsonToClientDataConverterModuleStarterImp;

public class JsonToClientDataConverterProvider {

	private static JsonToClientDataConverterFactory jsonToDataConverterFactory;
	private static JsonToClientDataConverterModuleStarter jsonToDataConverterModuleStarter = new JsonToClientDataConverterModuleStarterImp();

	private JsonToClientDataConverterProvider() {
		// not called
		throw new UnsupportedOperationException();
	}

	public static JsonToClientDataConverter getConverterUsingJsonObject(String json) {
		ensureConverterFactoryIsSet();
		return jsonToDataConverterFactory.factorUsingString(json);
	}

	private static synchronized void ensureConverterFactoryIsSet() {
		if (null == jsonToDataConverterFactory) {
			getConverterFactoryImpUsingModuleStarter();
		}
	}

	private static void getConverterFactoryImpUsingModuleStarter() {
		Iterable<JsonToClientDataConverterFactory> dataGroupFactoryImplementations = ServiceLoader
				.load(JsonToClientDataConverterFactory.class);
		jsonToDataConverterModuleStarter
				.startUsingConverterFactoryImplementations(dataGroupFactoryImplementations);
		jsonToDataConverterFactory = jsonToDataConverterModuleStarter
				.getJsonToDataConverterFactory();
	}

	/**
	 * Sets a JsonToDataConverterFactory that will be used to factor jsonToDataConverters for
	 * Classes. This possibility to set a JsonToDataConverterFactory is provided to enable testing
	 * of logging in other classes and is not intented to be used in production. The
	 * DataGroupFactory to use should be provided through an implementation of
	 * JsonToDataConverterFactory in a seperate java module.
	 * 
	 * @param jsonToDataConverterFactory
	 *            A JsonToDataConverterFactory to use to create jsonToDataConverters for testing
	 */
	public static void setJsonToDataConverterFactory(
			JsonToClientDataConverterFactory jsonToDataConverterFactory) {
		JsonToClientDataConverterProvider.jsonToDataConverterFactory = jsonToDataConverterFactory;

	}

	static JsonToClientDataConverterModuleStarter getStarter() {
		return jsonToDataConverterModuleStarter;
	}

	static void setStarter(JsonToClientDataConverterModuleStarter starter) {
		jsonToDataConverterModuleStarter = starter;
	}
}
