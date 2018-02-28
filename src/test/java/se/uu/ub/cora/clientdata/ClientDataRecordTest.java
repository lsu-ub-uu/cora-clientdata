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
	public void testActionLinks() {
		ClientDataActionLinks dataActionLinks = new ClientDataActionLinks();
		dataActionLinks.addActionLink("read", ActionLink.withAction(Action.READ));
		clientDataRecord.setActionLinks(dataActionLinks);
		Map<String, ActionLink> actionLinks = clientDataRecord.getActionLinks().getActionLinks();
		ActionLink actionLinkOut = actionLinks.get("read");
		assertEquals(actionLinkOut.getAction(), Action.READ);
	}

	@Test
	public void testActionLinksGet() {
		ClientDataActionLinks dataActionLinks = new ClientDataActionLinks();
		dataActionLinks.addActionLink("read", ActionLink.withAction(Action.READ));
		clientDataRecord.setActionLinks(dataActionLinks);
		ActionLink actionLinkOut = clientDataRecord.getActionLinks().getActionLink("read");
		assertEquals(actionLinkOut.getAction(), Action.READ);
	}

	@Test
	public void testSetActionLinks() throws Exception {
		ClientDataActionLinks dataActionLinks = new ClientDataActionLinks();

		ActionLink actionLink = ActionLink.withAction(Action.READ);
		Map<String, ActionLink> actionLinks = new HashMap<>();
		actionLinks.put("read", actionLink);
		dataActionLinks.setActionLinks(actionLinks);
		clientDataRecord.setActionLinks(dataActionLinks);

		ClientDataActionLinks readActionLinks = clientDataRecord.getActionLinks();
		assertEquals(readActionLinks.getActionLink("read"), actionLink);
		assertEquals(readActionLinks.getActionLinks().get("read"), actionLink);
		assertNull(readActionLinks.getActionLink("notAnAction"));
	}

}
