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

package se.uu.ub.cora.clientdata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientDataGroupTest {
	private ClientDataGroup clientDataGroup;
	private Collection<ClientDataGroup> groupsFound;

	@BeforeMethod
	public void setUp() {
		clientDataGroup = ClientDataGroup.withNameInData("nameInData");
	}

	@Test
	public void testGroupIsClientData() {
		assertTrue(clientDataGroup instanceof ClientData);
	}

	@Test
	public void testInit() {
		assertEquals(clientDataGroup.getNameInData(), "nameInData",
				"NameInData shold be the one set in the constructor");

		assertNotNull(clientDataGroup.getAttributes(),
				"Attributes should not be null for a new DataGroup");

		clientDataGroup.addAttributeByIdWithValue("nameInData", "Value");

		assertEquals(clientDataGroup.getAttributes().get("nameInData"), "Value",
				"Attribute with nameInData nameInData should have value Value");

		assertNotNull(clientDataGroup.getChildren(),
				"Children should not be null for a new DataGroup");

		ClientDataElement clientDataElement = ClientDataGroup.withNameInData("nameInData2");
		clientDataGroup.addChild(clientDataElement);
		assertEquals(clientDataGroup.getChildren().stream().findAny().get(), clientDataElement,
				"Child should be the same as the one we added");

	}

	@Test
	public void testGroupAsLink() {
		ClientDataGroup dataGroup = ClientDataGroup.asLinkWithNameInDataAndTypeAndId("nameInData",
				"someType", "someId");
		assertEquals(dataGroup.getNameInData(), "nameInData");
		assertEquals(dataGroup.getFirstAtomicValueWithNameInData("linkedRecordType"), "someType");
		assertEquals(dataGroup.getFirstAtomicValueWithNameInData("linkedRecordId"), "someId");
	}

	@Test
	public void testInitWithRepeatId() {
		clientDataGroup.setRepeatId("x1");
		assertEquals(clientDataGroup.getRepeatId(), "x1");
	}

	@Test
	public void testContainsChildWithNameInData() {
		ClientDataElement clientDataElement = ClientDataGroup.withNameInData("nameInData2");
		clientDataGroup.addChild(clientDataElement);
		assertTrue(clientDataGroup.containsChildWithNameInData("nameInData2"));
	}

	@Test
	public void testContainsChildWithNameInDataNotFound() {
		ClientDataElement clientDataElement = ClientDataGroup.withNameInData("nameInData2");
		clientDataGroup.addChild(clientDataElement);
		assertFalse(clientDataGroup.containsChildWithNameInData("nameInData_NOT_FOUND"));
	}

	@Test
	public void testGetFirstChildWithNameInData() {
		ClientDataElement clientDataElement = ClientDataGroup.withNameInData("nameInData2");
		clientDataGroup.addChild(clientDataElement);
		assertNotNull(clientDataGroup.getFirstChildWithNameInData("nameInData2"));
	}

	@Test(expectedExceptions = DataMissingException.class)
	public void testGetFirstChildWithNameInDataNotFound() {
		clientDataGroup.getFirstChildWithNameInData("nameInData_NOT_FOUND");
	}

	@Test
	public void testRemoveChild() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement child = ClientDataAtomic.withNameInDataAndValue("childId", "child value");
		dataGroup.addChild(child);
		dataGroup.removeFirstChildWithNameInData("childId");
		assertFalse(dataGroup.containsChildWithNameInData("childId"));
	}

	@Test(expectedExceptions = DataMissingException.class)
	public void testRemoveChildNotFound() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement child = ClientDataAtomic.withNameInDataAndValue("childId", "child value");
		dataGroup.addChild(child);
		dataGroup.removeFirstChildWithNameInData("childId_NOTFOUND");
	}

	@Test
	public void testGetFirstGroupWithNameInData() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);

		ClientDataGroup foundGroupChild = dataGroup.getFirstGroupWithNameInData("childNameInData");

		assertEquals(foundGroupChild, groupChild);
	}

	@Test(expectedExceptions = DataMissingException.class)
	public void testGetFirstGroupWithNameInDataGroupNotFound() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);

		dataGroup.getFirstGroupWithNameInData("childNameInData");
	}

	@Test
	public void testGetAllGroupsWithNameInDataOneGroup() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);

		List<ClientDataGroup> groupsFound = dataGroup.getAllGroupsWithNameInData("childNameInData");
		assertNumberOfGroupsFoundIs(groupsFound, 1);
		assertEquals(groupsFound.get(0), groupChild);
	}

	private void assertNumberOfGroupsFoundIs(Collection<ClientDataGroup> groupsFound,
			int numberOfGroups) {
		assertEquals(groupsFound.size(), numberOfGroups);
	}

	@Test
	public void testGetAllGroupsWithNameInDataOTwoGroups() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);
		ClientDataGroup groupChild2 = ClientDataGroup.withNameInData("NOTChildNameInData");
		dataGroup.addChild(groupChild2);
		ClientDataGroup groupChild3 = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild3);

		List<ClientDataGroup> groupsFound = dataGroup.getAllGroupsWithNameInData("childNameInData");
		assertNumberOfGroupsFoundIs(groupsFound, 2);
		assertEquals(groupsFound.get(0), groupChild);
		assertEquals(groupsFound.get(1), groupChild3);
	}

	@Test
	public void testGetFirstAtomicValueWithNameInData() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);

		String value = dataGroup.getFirstAtomicValueWithNameInData("childNameInData");
		assertEquals(value, "child value");
	}

	@Test(expectedExceptions = DataMissingException.class)
	public void testGetFirstAtomicValueWithNameInDataValueNotFound() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);
		dataGroup.getFirstAtomicValueWithNameInData("childNameInData");
	}

	@Test
	public void testGetFirstAtomicValueWithNameInDataThreeValuesSecondChildMatches() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChildWrongNameInData = ClientDataAtomic
				.withNameInDataAndValue("NOTChildNameInData", "not child value");
		dataGroup.addChild(atomicChildWrongNameInData);
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);
		ClientDataElement atomicChild2 = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"some other child value");
		dataGroup.addChild(atomicChild2);

		String value = dataGroup.getFirstAtomicValueWithNameInData("childNameInData");
		assertEquals(value, "child value");
	}

	@Test
	public void testGetAllDataAtomicsNonFound() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic
				.withNameInDataAndValue("NOTchildNameInData", "child value");
		dataGroup.addChild(atomicChild);

		List<ClientDataAtomic> atomicsFound = dataGroup
				.getAllDataAtomicsWithNameInData("childNameInData");
		assertEquals(atomicsFound.size(), 0);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInDataOneAtomic() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);

		List<ClientDataAtomic> atomicsFound = dataGroup
				.getAllDataAtomicsWithNameInData("childNameInData");
		assertEquals(atomicsFound.size(), 1);
		assertEquals(atomicsFound.get(0), atomicChild);
	}

	@Test
	public void testGetAllDataAtomicsWithNameInDataTwoAtomics() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataAtomic atomicChild = createAtomicDataWithRepeatId("childNameInData",
				"child value", "1");
		dataGroup.addChild(atomicChild);
		ClientDataAtomic atomicChild2 = createAtomicDataWithRepeatId("childNameInData",
				"child value", "0");
		dataGroup.addChild(atomicChild2);
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);

		List<ClientDataAtomic> atomicsFound = dataGroup
				.getAllDataAtomicsWithNameInData("childNameInData");
		assertEquals(atomicsFound.size(), 2);
		assertEquals(atomicsFound.get(0), atomicChild);
		assertEquals(atomicsFound.get(1), atomicChild2);
	}

	private ClientDataAtomic createAtomicDataWithRepeatId(String childNameInData, String childValue,
			String repeatId) {
		ClientDataAtomic atomicChild = ClientDataAtomic.withNameInDataAndValue(childNameInData,
				childValue);
		atomicChild.setRepeatId(repeatId);
		return atomicChild;
	}

	@Test
	public void testGetAllChildrenWithNameInDataNoChildFound() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);

		List<ClientDataElement> childrenFound = dataGroup
				.getAllChildrenWithNameInData("NOThildNameInData");
		assertEquals(childrenFound.size(), 0);
	}

	@Test
	public void testGetAllChildrenWithNameInDataOneGroup() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);

		List<ClientDataElement> childrenFound = dataGroup
				.getAllChildrenWithNameInData("childNameInData");
		assertEquals(childrenFound.size(), 1);
		assertEquals(childrenFound.get(0), groupChild);
	}

	@Test
	public void testGetAllChildrenWithNameInDataOneDataAtomic() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);

		List<ClientDataElement> childrenFound = dataGroup
				.getAllChildrenWithNameInData("childNameInData");
		assertEquals(childrenFound.size(), 1);
		assertEquals(childrenFound.get(0), atomicChild);
	}

	@Test
	public void testGetAllChildrenWithNameInDataOneGroupAndOneDataAtomic() {
		ClientDataGroup dataGroup = ClientDataGroup.withNameInData("nameInData");
		ClientDataElement atomicChild = ClientDataAtomic.withNameInDataAndValue("childNameInData",
				"child value");
		dataGroup.addChild(atomicChild);
		ClientDataGroup groupChild = ClientDataGroup.withNameInData("childNameInData");
		dataGroup.addChild(groupChild);

		List<ClientDataElement> childrenFound = dataGroup
				.getAllChildrenWithNameInData("childNameInData");
		assertEquals(childrenFound.size(), 2);
		assertEquals(childrenFound.get(0), atomicChild);
		assertEquals(childrenFound.get(1), groupChild);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributesOneMatch() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();

		groupsFound = clientDataGroup.getAllGroupsWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertNumberOfGroupsFoundIs(1);
		assertGroupsFoundAre(child3);
	}

	private ClientDataGroup createTestGroupForAttributesReturnChildGroupWithAttribute() {
		addAndReturnDataGroupChildWithNameInData("groupId2");
		addAndReturnDataGroupChildWithNameInData("groupId3");
		addAndReturnDataGroupChildWithNameInData("groupId2");
		createDataAtomicWithNameInData("groupId2");
		ClientDataGroup child3 = addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));
		return child3;
	}

	private ClientDataGroup addAndReturnDataGroupChildWithNameInData(String nameInData) {
		ClientDataGroup child = ClientDataGroup.withNameInData(nameInData);
		clientDataGroup.addChild(child);
		return child;
	}

	private ClientDataAtomic createDataAtomicWithNameInData(String nameInData) {
		ClientDataAtomic child = ClientDataAtomic.withNameInDataAndValue(nameInData, "someValue");
		return child;
	}

	private ClientDataGroup addAndReturnDataGroupChildWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... attributes) {
		ClientDataGroup child = ClientDataGroup.withNameInData(nameInData);
		clientDataGroup.addChild(child);
		for (ClientDataAttribute attribute : attributes) {
			child.addAttributeByIdWithValue(attribute.getNameInData(), attribute.getValue());
		}
		return child;
	}

	private void assertNumberOfGroupsFoundIs(int numberOfGroups) {
		assertEquals(groupsFound.size(), numberOfGroups);
	}

	private void assertGroupsFoundAre(ClientDataGroup... groups) {
		int i = 0;
		for (ClientDataGroup groupFound : groupsFound) {
			assertEquals(groupFound, groups[i]);
			i++;
		}
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributesTwoMatches() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();
		ClientDataGroup child4 = addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		groupsFound = clientDataGroup.getAllGroupsWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertNumberOfGroupsFoundIs(2);
		assertGroupsFoundAre(child3, child4);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributesOneWrongAttributeValueTwoMatches() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();
		ClientDataGroup child4 = addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value2"));

		groupsFound = clientDataGroup.getAllGroupsWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertNumberOfGroupsFoundIs(2);
		assertGroupsFoundAre(child3, child4);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndAttributesOneWrongAttributeNameTwoMatches() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();
		ClientDataGroup child4 = addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value1"));

		groupsFound = clientDataGroup.getAllGroupsWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertNumberOfGroupsFoundIs(2);
		assertGroupsFoundAre(child3, child4);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndTwoAttributesNoMatches() {
		createTestGroupForAttributesReturnChildGroupWithAttribute();
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value2"));

		groupsFound = clientDataGroup.getAllGroupsWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value1"));

		assertNumberOfGroupsFoundIs(0);
	}

	@Test
	public void testGetAllGroupsWithNameInDataAndTwoAttributesOneMatches() {
		createTestGroupForAttributesReturnChildGroupWithAttribute();
		ClientDataGroup child4 = addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value2"));
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData3", "value2"));

		groupsFound = clientDataGroup.getAllGroupsWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value2"));

		assertNumberOfGroupsFoundIs(1);
		assertGroupsFoundAre(child4);
	}

	@Test
	public void testGetFirstGroupsWithNameInDataAndAttributesOneMatch() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();

		ClientDataGroup found = clientDataGroup.getFirstGroupWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertEquals(found, child3);
	}

	@Test
	public void testGetFirstGroupWithNameInDataAndAttributesTwoMatches() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		ClientDataGroup found = clientDataGroup.getFirstGroupWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertEquals(found, child3);
	}

	@Test
	public void testGetFirstGroupWithNameInDataAndAttributesOneWrongAttributeValueTwoMatches() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value2"));

		ClientDataGroup found = clientDataGroup.getFirstGroupWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertEquals(found, child3);
	}

	@Test
	public void testGetFirstGroupWithNameInDataAndAttributesOneWrongAttributeNameTwoMatches() {
		ClientDataGroup child3 = createTestGroupForAttributesReturnChildGroupWithAttribute();
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value1"));

		ClientDataGroup found = clientDataGroup.getFirstGroupWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"));

		assertEquals(found, child3);
	}

	@Test(expectedExceptions = DataMissingException.class, expectedExceptionsMessageRegExp = ""
			+ "Group not found for childNameInData: groupId2 "
			+ "with attributes: nameInData:value1, nameInData2:value1")
	public void testGetFirstGroupWithNameInDataAndTwoAttributesNoMatches() {
		createTestGroupForAttributesReturnChildGroupWithAttribute();
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value2"));

		clientDataGroup.getFirstGroupWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value1"));
	}

	@Test
	public void testGetFirstGroupWithNameInDataAndTwoAttributesOneMatches() {
		createTestGroupForAttributesReturnChildGroupWithAttribute();
		ClientDataGroup child4 = addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value2"));
		addAndReturnDataGroupChildWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData3", "value2"));

		ClientDataGroup found = clientDataGroup.getFirstGroupWithNameInDataAndAttributes("groupId2",
				ClientDataAttribute.withNameInDataAndValue("nameInData", "value1"),
				ClientDataAttribute.withNameInDataAndValue("nameInData2", "value2"));

		assertEquals(found, child4);
	}

	@Test
	public void testGetAllChildrenWithNameInDataAndAttributesDataAtomicChild() {
		// DataGroup childGroup = createChildGroupWithNameInDataAndRepatIdAndAttributes(
		// "someChildNameInData", "0");
		// defaultDataGroup.addChild(childGroup);

		// CoraDataAtomic coraDataAtomic =
		// CoraDataAtomic.withNameInDataAndValue("someChildNameInData",
		// "someValue");
		//
		// defaultDataGroup.addChild(coraDataAtomic);
		//
		// List<DataElement> children = clientDataGroup
		// .getAllChildrenWithNameInDataAndAttributes("someChildNameInData");
		//
		// assertEquals(children.size(), 1);
		// assertSame(children.get(0), coraDataAtomic);

	}
}
