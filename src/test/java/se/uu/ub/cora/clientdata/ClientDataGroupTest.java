/*
 * Copyright 2015 Uppsala University Library
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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientData;
import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.DataMissingException;

public class ClientDataGroupTest {
	private ClientDataGroup clientDataGroup;

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

}
