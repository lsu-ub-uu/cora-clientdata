/*
 * Copyright 2015, 2018, 2022 Uppsala University Library
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

import java.util.HashMap;
import java.util.Map;

import se.uu.ub.cora.clientdata.converter.javatojson.Convertible;

public final class ClientDataAtomic implements ClientDataElement, Convertible {

	private String nameInData;
	private String value;
	private String repeatId;
	private Map<String, String> attributes = new HashMap<>();

	public static ClientDataAtomic withNameInDataAndValue(String nameInData, String value) {
		return new ClientDataAtomic(nameInData, value);
	}

	private ClientDataAtomic(String nameInData, String value) {
		this.nameInData = nameInData;
		this.value = value;

	}

	@Override
	public String getNameInData() {

		return nameInData;
	}

	public String getValue() {
		return value;
	}

	public String getRepeatId() {
		return repeatId;
	}

	public void setRepeatId(String repeatId) {
		this.repeatId = repeatId;
	}

	public void addAttributeByIdWithValue(String attributeName, String attributeValue) {
		attributes.put(attributeName, attributeValue);
	}

	@Override
	public Map<String, String> getAttributes() {
		return attributes;
	}
}
