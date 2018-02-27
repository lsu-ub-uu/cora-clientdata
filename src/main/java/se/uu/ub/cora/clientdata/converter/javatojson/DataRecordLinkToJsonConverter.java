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

import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;

import java.util.Map;

public final class DataRecordLinkToJsonConverter extends DataGroupToJsonConverter {

	private static final String LINKED_REPEAT_ID = "linkedRepeatId";
	private ClientDataRecordLink recordLink;

	private DataRecordLinkToJsonConverter(JsonBuilderFactory jsonFactory,
			ClientDataRecordLink recordLink) {
		super(jsonFactory, recordLink);
		this.recordLink = recordLink;
	}

	public static DataRecordLinkToJsonConverter usingJsonFactoryForClientDataLink(
			JsonBuilderFactory jsonFactory, ClientDataRecordLink dataLink) {
		return new DataRecordLinkToJsonConverter(jsonFactory, dataLink);
	}

	@Override
	public JsonObjectBuilder toJsonObjectBuilder() {
		removeEmptyLinkedRepeatId();
		possiblyAddActionLinks();
		return super.toJsonObjectBuilder();
	}

	private void removeEmptyLinkedRepeatId() {
		if (hasEmptyLinkedRepeatId()) {
			clientDataGroup.removeFirstChildWithNameInData(LINKED_REPEAT_ID);
		}
	}

	private boolean hasEmptyLinkedRepeatId() {
		return clientDataGroup.containsChildWithNameInData(LINKED_REPEAT_ID)
				&& ((ClientDataAtomic) clientDataGroup.getFirstChildWithNameInData(LINKED_REPEAT_ID))
						.getValue().equals("");
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
		ActionLinksToJsonConverter actionLinkConverter = new ActionLinksToJsonConverter(
				jsonBuilderFactory, actionLinks);
		JsonObjectBuilder actionLinksObject = actionLinkConverter.toJsonObjectBuilder();
		dataGroupJsonObjectBuilder.addKeyJsonObjectBuilder("actionLinks", actionLinksObject);
	}

}
