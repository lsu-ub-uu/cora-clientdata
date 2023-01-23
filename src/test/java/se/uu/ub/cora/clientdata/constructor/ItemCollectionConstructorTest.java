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

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.RecordIdentifier;

public class ItemCollectionConstructorTest {
	private String dataDivider = "someDataDivider";
	private String id = "someId";
	private String nameInData = "someNameInData";
	private List<RecordIdentifier> collectionItems;

	private ItemCollectionConstructor itemCollectionConstructor;
	ClientDataGroup itemCollection;

	@BeforeMethod
	public void beforeMethod() {
		collectionItems = new ArrayList<>();
		itemCollectionConstructor = ItemCollectionConstructor.withDataDivider(dataDivider);
		itemCollection = itemCollectionConstructor
				.constructUsingIdAndNameInDataAndCollectionItems(id, nameInData, collectionItems);
	}

	@Test
	public void testNameInData() throws Exception {
		assertEquals(itemCollection.getNameInData(), "metadata");
		assertEquals(itemCollection.getAttributes().get("type"), "itemCollection");
	}

	@Test
	public void testNameInDataAsPartOfMetadata() throws Exception {
		assertEquals(itemCollection.getFirstAtomicValueWithNameInData("nameInData"),
				"someNameInData");
	}

	@Test
	public void testCorrectRecordInfo() throws Exception {
		ClientDataGroup recordInfo = itemCollection.getFirstGroupWithNameInData("recordInfo");
		DataConstructorTestHelper.assertCorrectRecordInfoUsingRecordInfoAndDataDividerAndId(
				recordInfo, dataDivider, id);
	}

	@Test
	public void testCollectionItemReferences() throws Exception {
		String recordType = "someCollectionItemType";
		String recordId = "someCollectionItemId";
		collectionItems.add(RecordIdentifier.usingTypeAndId(recordType, recordId));

		itemCollection = itemCollectionConstructor
				.constructUsingIdAndNameInDataAndCollectionItems(id, nameInData, collectionItems);

		ClientDataGroup itemRefs = itemCollection
				.getFirstGroupWithNameInData("collectionItemReferences");

		assertEquals(itemRefs.getAllChildrenWithNameInData("ref").size(), 1);

		ClientDataGroup itemReference = itemRefs.getFirstGroupWithNameInData("ref");
		DataConstructorTestHelper
				.assertCorrectDataLinkUsingNameInDataAndRecordTypeAndRecordIdAndRepeatId(
						itemReference, "ref", recordType, recordId, "0");
	}

	@Test
	public void testCollectionItemReferencesWithTwoItems() throws Exception {
		String recordType = "someCollectionItemType";
		String recordId = "someCollectionItemId";
		collectionItems.add(RecordIdentifier.usingTypeAndId(recordType, recordId));
		collectionItems.add(RecordIdentifier.usingTypeAndId(recordType + "1", recordId + "1"));

		itemCollection = itemCollectionConstructor
				.constructUsingIdAndNameInDataAndCollectionItems(id, nameInData, collectionItems);

		ClientDataGroup itemRefs = itemCollection
				.getFirstGroupWithNameInData("collectionItemReferences");

		List<ClientDataGroup> itemRefsList = itemRefs.getAllGroupsWithNameInData("ref");
		assertEquals(itemRefsList.size(), 2);

		DataConstructorTestHelper
				.assertCorrectDataLinkUsingNameInDataAndRecordTypeAndRecordIdAndRepeatId(
						itemRefsList.get(0), "ref", recordType, recordId, "0");
		DataConstructorTestHelper
				.assertCorrectDataLinkUsingNameInDataAndRecordTypeAndRecordIdAndRepeatId(
						itemRefsList.get(1), "ref", recordType + "1", recordId + "1", "1");
	}

}
