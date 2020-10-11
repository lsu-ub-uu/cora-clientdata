/*
 * Copyright 2015, 2018, 2020 Uppsala University Library
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
import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

public class DataRecordLinkToJsonConverter extends DataGroupToJsonConverter {

	private static final String LINKED_REPEAT_ID = "linkedRepeatId";
	private ClientDataRecordLink recordLink;
	ActionLinksToJsonConverter actionLinkConverter;

	protected DataRecordLinkToJsonConverter(JsonBuilderFactory jsonFactory,
			ClientDataRecordLink recordLink,
			DataToJsonConverterFactory dataToJsonConverterFactory) {
		super(jsonFactory, recordLink, dataToJsonConverterFactory);
		this.recordLink = recordLink;
	}

	public static DataRecordLinkToJsonConverter usingJsonFactoryForClientDataLink(
			JsonBuilderFactory jsonFactory, ClientDataRecordLink dataLink,
			DataToJsonConverterFactory dataToJsonConverterFactory) {
		return new DataRecordLinkToJsonConverter(jsonFactory, dataLink, dataToJsonConverterFactory);
	}

	@Override
	public JsonObjectBuilder toJsonObjectBuilder() {
		handleLinkSpecifics();
		return super.toJsonObjectBuilder();
	}

	protected void handleLinkSpecifics() {
		removeEmptyLinkedRepeatId();
		possiblyAddActionLinks();
	}

	protected void removeEmptyLinkedRepeatId() {
		if (hasEmptyLinkedRepeatId()) {
			clientDataGroup.removeFirstChildWithNameInData(LINKED_REPEAT_ID);
		}
	}

	private boolean hasEmptyLinkedRepeatId() {
		return clientDataGroup.containsChildWithNameInData(LINKED_REPEAT_ID) && "".equals(
				((ClientDataAtomic) clientDataGroup.getFirstChildWithNameInData(LINKED_REPEAT_ID))
						.getValue());
	}

	private void possiblyAddActionLinks() {
		if (hasActionLinks()) {
			addActionLinksToRecordLink();
		}
	}

	private boolean hasActionLinks() {
		return !recordLink.getActionLinks().isEmpty();
	}

	private void addActionLinksToRecordLink() {
		Map<String, ActionLink> actionLinks = recordLink.getActionLinks();
		actionLinkConverter = new ActionLinksToJsonConverter(getJsonBuilderFactory(), actionLinks,
				dataToJsonConverterFactory);
		JsonObjectBuilder actionLinksObject = actionLinkConverter.toJsonObjectBuilder();
		dataGroupJsonObjectBuilder.addKeyJsonObjectBuilder("actionLinks", actionLinksObject);
	}

	ClientDataElement getClientDataRecordLink() {
		// needed for test
		return recordLink;
	}

}
