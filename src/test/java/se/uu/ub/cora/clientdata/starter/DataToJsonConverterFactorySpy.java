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

import se.uu.ub.cora.clientdata.ClientConvertible;
import se.uu.ub.cora.clientdata.converter.ClientDataToJsonConverter;
import se.uu.ub.cora.clientdata.converter.ClientDataToJsonConverterFactory;

public class DataToJsonConverterFactorySpy implements ClientDataToJsonConverterFactory {

	public boolean getConverterCalled = false;
	public ClientConvertible convertible;
	public DataToJsonConverterSpy dataToJsonConverterSpy;

	@Override
	public ClientDataToJsonConverter factorUsingConvertible(ClientConvertible convertible) {
		this.convertible = convertible;
		getConverterCalled = true;
		dataToJsonConverterSpy = new DataToJsonConverterSpy();
		return dataToJsonConverterSpy;
	}

	@Override
	public ClientDataToJsonConverter factorUsingBaseUrlAndConvertible(String baseUrl,
			ClientConvertible convertible) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDataToJsonConverter factorUsingBaseUrlAndRecordUrlAndConvertible(String baseUrl, String recordUrl,
			ClientConvertible convertible) {
		// TODO Auto-generated method stub
		return null;
	}

}
