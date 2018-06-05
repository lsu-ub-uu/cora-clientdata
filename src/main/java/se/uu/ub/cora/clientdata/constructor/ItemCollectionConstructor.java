/*
 * Copyright 2018 Uppsala University Library
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
package se.uu.ub.cora.clientdata.constructor;

import java.util.List;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.RecordIdentifier;

public class ItemCollectionConstructor extends DataConstructor {

	public static ItemCollectionConstructor withDataDivider(String dataDivider) {
		return new ItemCollectionConstructor(dataDivider);
	}

	private ItemCollectionConstructor(String dataDivider) {
		this.dataDivider = dataDivider;
	}

	public ClientDataGroup constructUsingIdAndNameInDataAndCollectionItems(String id,
			String nameInData, List<RecordIdentifier> collectionItems) {
		ClientDataGroup itemCollection = ClientDataGroup.withNameInData("metadata");
		itemCollection.addAttributeByIdWithValue("type", "itemCollection");

		itemCollection.addChild(ClientDataAtomic.withNameInDataAndValue("nameInData", nameInData));
		itemCollection.addChild(createRecordInfoGroupForId(id));

		ClientDataGroup itemRefs = createCollectionItemReferences(collectionItems);
		itemCollection.addChild(itemRefs);
		return itemCollection;
	}

	protected ClientDataGroup createCollectionItemReferences(
			List<RecordIdentifier> collectionItems) {
		ClientDataGroup itemRefs = ClientDataGroup.withNameInData("collectionItemReferences");
		createReferenceForAllCollectionItems(collectionItems, itemRefs);
		return itemRefs;
	}

	protected void createReferenceForAllCollectionItems(List<RecordIdentifier> collectionItems,
			ClientDataGroup itemRefs) {
		int repeatId = 0;
		for (RecordIdentifier recordIdentifier : collectionItems) {
			createReferenceForOneCollectionItem(itemRefs, repeatId, recordIdentifier);
			repeatId++;
		}
	}

	protected void createReferenceForOneCollectionItem(ClientDataGroup itemRefs, int repeatId,
			RecordIdentifier recordIdentifier) {
		ClientDataGroup ref = createDataLinkUsingNameInDataAndRecordTypeAndRecordIdAndRepeatId(
				"ref", recordIdentifier.type, recordIdentifier.id, String.valueOf(repeatId));
		itemRefs.addChild(ref);
	}

}
