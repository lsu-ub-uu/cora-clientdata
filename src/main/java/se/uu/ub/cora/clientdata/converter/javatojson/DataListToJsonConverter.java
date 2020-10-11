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

import se.uu.ub.cora.clientdata.ClientData;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataList;
import se.uu.ub.cora.clientdata.ClientDataRecord;
import se.uu.ub.cora.json.builder.JsonArrayBuilder;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public final class DataListToJsonConverter {

	private JsonBuilderFactory jsonBuilderFactory;
	private ClientDataList clientRecordList;
	private JsonObjectBuilder recordListJsonObjectBuilder;
	private DataToJsonConverterFactory dataToJsonFactory;

	private DataListToJsonConverter(JsonBuilderFactory jsonFactory, ClientDataList clientRecordList,
			DataToJsonConverterFactory dataToJsonFactory) {
		this.jsonBuilderFactory = jsonFactory;
		this.clientRecordList = clientRecordList;
		this.dataToJsonFactory = dataToJsonFactory;
		recordListJsonObjectBuilder = jsonFactory.createObjectBuilder();
	}

	public static DataListToJsonConverter usingJsonFactoryForClientDataList(
			JsonBuilderFactory jsonFactory, ClientDataList clientRecordList,
			DataToJsonConverterFactory dataToJsonFactory) {
		return new DataListToJsonConverter(jsonFactory, clientRecordList, dataToJsonFactory);
	}

	public String toJson() {
		return toJsonObjectBuilder().toJsonFormattedString();
	}

	JsonObjectBuilder toJsonObjectBuilder() {

		recordListJsonObjectBuilder.addKeyString("totalNo", clientRecordList.getTotalNo());
		recordListJsonObjectBuilder.addKeyString("fromNo", clientRecordList.getFromNo());
		recordListJsonObjectBuilder.addKeyString("toNo", clientRecordList.getToNo());
		recordListJsonObjectBuilder.addKeyString("containDataOfType",
				clientRecordList.getContainDataOfType());

		JsonArrayBuilder recordsJsonBuilder = jsonBuilderFactory.createArrayBuilder();

		for (ClientData clientData : clientRecordList.getDataList()) {
			convertClientToJsonBuilder(recordsJsonBuilder, clientData);
		}

		recordListJsonObjectBuilder.addKeyJsonArrayBuilder("data", recordsJsonBuilder);

		JsonObjectBuilder rootWrappingJsonObjectBuilder = jsonBuilderFactory.createObjectBuilder();
		rootWrappingJsonObjectBuilder.addKeyJsonObjectBuilder("dataList",
				recordListJsonObjectBuilder);
		return rootWrappingJsonObjectBuilder;
	}

	private void convertClientToJsonBuilder(JsonArrayBuilder recordsJsonBuilder,
			ClientData clientData) {
		if (clientData instanceof ClientDataRecord) {
			convertClientRecordToJsonBuilder(recordsJsonBuilder, clientData);
		} else {
			convertClientGroupToJsonBuilder(recordsJsonBuilder, clientData);
		}
	}

	private void convertClientRecordToJsonBuilder(JsonArrayBuilder recordsJsonBuilder,
			ClientData clientData) {
		DataRecordToJsonConverter converter = DataRecordToJsonConverter
				.usingJsonFactoryForClientDataRecord(jsonBuilderFactory,
						(ClientDataRecord) clientData, dataToJsonFactory);
		recordsJsonBuilder.addJsonObjectBuilder(converter.toJsonObjectBuilder());
	}

	private void convertClientGroupToJsonBuilder(JsonArrayBuilder recordsJsonBuilder,
			ClientData clientData) {
		DataToJsonConverter converter = dataToJsonFactory
				.createForClientDataElement((ClientDataGroup) clientData);

		recordsJsonBuilder.addJsonObjectBuilder(converter.toJsonObjectBuilder());
	}

}
