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
import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;

public class DataToJsonConverterFactoryImp implements DataToJsonConverterFactory {

	@Override
	public DataToJsonConverter createForClientDataElement(JsonBuilderFactory factory,
			ClientDataElement clientDataElement) {

		if (clientDataElement instanceof ClientDataGroup) {
			if (clientDataElement instanceof ClientDataRecordLink) {
				return DataRecordLinkToJsonConverter.usingJsonFactoryForClientDataLink(factory,
						(ClientDataRecordLink) clientDataElement);
			}
			if (clientDataElement instanceof ClientDataResourceLink) {
				return DataResourceLinkToJsonConverter.usingJsonFactoryForClientDataLink(factory,
						(ClientDataResourceLink) clientDataElement);
			}
			return DataGroupToJsonConverter.usingJsonFactoryForClientDataGroup(factory,
					(ClientDataGroup) clientDataElement);
		}
		if (clientDataElement instanceof ClientDataAtomic) {
			return DataAtomicToJsonConverter.usingJsonFactoryForClientDataAtomic(factory,
					(ClientDataAtomic) clientDataElement);
		}
		return DataAttributeToJsonConverter.usingJsonFactoryForClientDataAttribute(factory,
				(ClientDataAttribute) clientDataElement);
	}
}
