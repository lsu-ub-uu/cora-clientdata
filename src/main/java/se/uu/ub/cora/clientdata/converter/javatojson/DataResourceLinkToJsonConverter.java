/*
 * Copyright 2015, 2016, 2018 Uppsala University Library
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

import java.util.Map;

import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public final class DataResourceLinkToJsonConverter extends DataGroupToJsonConverter {

	private ClientDataResourceLink resourceLink;
	ActionLinksToJsonConverter actionLinkConverter;

	private DataResourceLinkToJsonConverter(JsonBuilderFactory jsonFactory,
			ClientDataResourceLink resourceLink,
			DataToJsonConverterFactory dataToJsonConverterFactory) {
		super(jsonFactory, resourceLink, dataToJsonConverterFactory);
		this.resourceLink = resourceLink;
	}

	public static DataResourceLinkToJsonConverter usingJsonFactoryForClientDataLink(
			JsonBuilderFactory jsonFactory, ClientDataResourceLink resourceLink,
			DataToJsonConverterFactory dataToJsonConverterFactory) {
		return new DataResourceLinkToJsonConverter(jsonFactory, resourceLink,
				dataToJsonConverterFactory);
	}

	@Override
	public JsonObjectBuilder toJsonObjectBuilder() {
		possiblyAddActionLinks();
		return super.toJsonObjectBuilder();
	}

	private void possiblyAddActionLinks() {
		if (hasActionLinks()) {
			addActionLinksToRecordLink();
		}
	}

	private boolean hasActionLinks() {
		return !resourceLink.getActionLinks().isEmpty();
	}

	private void addActionLinksToRecordLink() {
		Map<String, ActionLink> actionLinks = resourceLink.getActionLinks();
		actionLinkConverter = new ActionLinksToJsonConverter(jsonBuilderFactory, actionLinks,
				dataToJsonConverterFactory);
		JsonObjectBuilder actionLinksObject = actionLinkConverter.toJsonObjectBuilder();
		dataGroupJsonObjectBuilder.addKeyJsonObjectBuilder("actionLinks", actionLinksObject);
	}

}
