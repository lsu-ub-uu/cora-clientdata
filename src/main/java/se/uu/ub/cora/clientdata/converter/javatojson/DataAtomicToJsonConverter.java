/*
 * Copyright 2015, 2018 Uppsala University Library
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

package se.uu.ub.cora.clientdata.converter.javatojson;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public final class DataAtomicToJsonConverter extends DataToJsonConverter {

	private ClientDataAtomic clientDataAtomic;
	private JsonBuilderFactory factory;

	public static DataToJsonConverter usingJsonFactoryForClientDataAtomic(
			JsonBuilderFactory factory, ClientDataAtomic dataAtomic) {
		return new DataAtomicToJsonConverter(factory, dataAtomic);
	}

	private DataAtomicToJsonConverter(JsonBuilderFactory factory, ClientDataAtomic dataAtomic) {
		this.factory = factory;
		this.clientDataAtomic = dataAtomic;
	}

	@Override
	public JsonObjectBuilder toJsonObjectBuilder() {
		JsonObjectBuilder jsonObjectBuilder = factory.createObjectBuilder();

		jsonObjectBuilder.addKeyString("name", clientDataAtomic.getNameInData());
		jsonObjectBuilder.addKeyString("value", clientDataAtomic.getValue());
		possiblyAddRepeatId(jsonObjectBuilder);
		return jsonObjectBuilder;
	}

	private void possiblyAddRepeatId(JsonObjectBuilder jsonObjectBuilder) {
		if (hasNonEmptyRepeatId()) {
			jsonObjectBuilder.addKeyString("repeatId", clientDataAtomic.getRepeatId());
		}
	}

	private boolean hasNonEmptyRepeatId() {
		return clientDataAtomic.getRepeatId() != null && !"".equals(clientDataAtomic.getRepeatId());
	}

	public JsonBuilderFactory onlyForTestGetJsonBuilderFactory() {
		return factory;
	}
}
