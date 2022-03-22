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
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;

public class DataToJsonConverterFactoryImp implements DataToJsonConverterFactory {

	private boolean includeActionLinks = true;

	@Override
	public DataToJsonConverter createForClientDataElement(JsonBuilderFactory factory,
			Convertible convertible) {
		return createForClientDataElementIncludingActionLinks(factory, convertible,
				includeActionLinks);
	}

	@Override
	public DataToJsonConverter createForClientDataElementIncludingActionLinks(
			JsonBuilderFactory factory, Convertible convertible, boolean includeActionLinks) {

		if (convertible instanceof ClientDataGroup) {
			return handleClientDataGroup(factory, convertible, includeActionLinks);
		}
		if (convertible instanceof ClientDataAtomic dataAtomic) {
			return DataAtomicToJsonConverter.usingJsonFactoryForClientDataAtomic(factory,
					dataAtomic);
		}
		return DataAttributeToJsonConverter.usingJsonFactoryForClientDataAttribute(factory,
				(ClientDataAttribute) convertible);
	}

	private DataToJsonConverter handleClientDataGroup(JsonBuilderFactory factory,
			Convertible convertible, boolean includeActionLinks) {
		this.includeActionLinks = includeActionLinks;

		DataToJsonConverterFactory converterFactory = getConverterFactory();
		if (convertible instanceof ClientDataRecordLink) {
			return handleDataRecordLink(factory, convertible, converterFactory, includeActionLinks);
		}
		if (convertible instanceof ClientDataResourceLink resourceLink) {
			return DataResourceLinkToJsonConverter.usingJsonFactoryForClientDataLink(factory,
					resourceLink, converterFactory);
		}
		return DataGroupToJsonConverter.usingJsonFactoryAndConverterFactoryForClientDataGroup(
				factory, converterFactory, (ClientDataGroup) convertible);
	}

	private DataToJsonConverter handleDataRecordLink(JsonBuilderFactory factory,
			Convertible convertible, DataToJsonConverterFactory converterFactory,
			boolean includeActionLinks) {
		if (includeActionLinks) {
			return getDataRecordLinkToJsonConverter(factory, convertible, converterFactory);
		}
		return getDataRecordLinkToJsonConverterWithoutActionLinks(factory, convertible,
				converterFactory);
	}

	protected DataToJsonConverterFactory getConverterFactory() {
		DataToJsonConverterFactoryImp converterFactoryForChildren = new DataToJsonConverterFactoryImp();
		converterFactoryForChildren.setIncludeActionLinks(includeActionLinks);
		return converterFactoryForChildren;

	}

	protected DataToJsonConverter getDataRecordLinkToJsonConverter(JsonBuilderFactory factory,
			Convertible clientDataElement, DataToJsonConverterFactory factoryConverterForGroup) {
		return DataRecordLinkToJsonConverter.usingJsonFactoryForClientDataLink(factory,
				(ClientDataRecordLink) clientDataElement, factoryConverterForGroup);
	}

	protected DataToJsonConverter getDataRecordLinkToJsonConverterWithoutActionLinks(
			JsonBuilderFactory factory, Convertible clientDataElement,
			DataToJsonConverterFactory factoryConverterForGroup) {

		return DataRecordLinkToJsonWithoutActionLinkConverter.usingJsonFactoryForClientDataLink(
				factory, (ClientDataRecordLink) clientDataElement, factoryConverterForGroup);
	}

	public boolean getIncludeActionLinks() {
		return includeActionLinks;
	}

	public void setIncludeActionLinks(boolean includeActionLinks) {
		this.includeActionLinks = includeActionLinks;

	}
}
