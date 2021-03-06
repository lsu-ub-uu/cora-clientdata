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
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientDataRecordTest {
	private ClientDataRecord clientDataRecord;

	@BeforeMethod
	public void beforeMethod() {
		ClientDataGroup clientDataGroup = ClientDataGroup.withNameInData("nameInData");
		clientDataRecord = ClientDataRecord.withClientDataGroup(clientDataGroup);
	}

	@Test
	public void testRecordIsClientData() {
		assertTrue(clientDataRecord instanceof ClientData);
	}

	@Test
	public void testWithNameInData() {
		String nameInData = clientDataRecord.getClientDataGroup().getNameInData();
		assertEquals(nameInData, "nameInData");
	}

	@Test
	public void testWithActionLinks() {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		clientDataRecord.addActionLink("read", actionLink);
		assertEquals(clientDataRecord.getActionLink("read"), actionLink);
		assertEquals(clientDataRecord.getActionLinks().get("read"), actionLink);
		assertNull(clientDataRecord.getActionLink("notAnAction"));
	}

	@Test
	public void testSetActionLinks() throws Exception {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		Map<String, ActionLink> actionLinks = new HashMap<>();
		actionLinks.put("read", actionLink);
		clientDataRecord.setActionLinks(actionLinks);
		assertEquals(clientDataRecord.getActionLink("read"), actionLink);
		assertEquals(clientDataRecord.getActionLinks().get("read"), actionLink);
		assertNull(clientDataRecord.getActionLink("notAnAction"));
	}

	@Test
	public void testGetReadPermissions() {
		clientDataRecord.addReadPermission("rating");
		clientDataRecord.addReadPermission("value");
		Set<String> readPermissions = clientDataRecord.getReadPermissions();
		assertTrue(readPermissions.contains("rating"));
		assertTrue(readPermissions.contains("value"));
	}

	@Test
	public void testGetWritePermissions() {
		clientDataRecord.addWritePermission("title");
		clientDataRecord.addWritePermission("author");
		Set<String> writePermissions = clientDataRecord.getWritePermissions();
		assertTrue(writePermissions.contains("title"));
		assertTrue(writePermissions.contains("author"));
	}

}
