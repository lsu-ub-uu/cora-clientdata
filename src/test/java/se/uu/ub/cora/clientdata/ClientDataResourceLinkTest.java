/*
 * Copyright 2016, 2018 Uppsala University Library
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
 */package se.uu.ub.cora.clientdata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.Action;
import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;

public class ClientDataResourceLinkTest {
	private ClientDataResourceLink resourceLink;

	@BeforeMethod
	public void setUp() {
		resourceLink = ClientDataResourceLink.withNameInData("aNameInData");

		ClientDataAtomic streamId = ClientDataAtomic.withNameInDataAndValue("streamId", "aStreamId");
		resourceLink.addChild(streamId);
	}

	@Test
	public void testInit() {
		assertEquals(resourceLink.getChildren().size(), 1);
		assertTrue(resourceLink.containsChildWithNameInData("streamId"));
	}

	@Test
	public void testWithActionLinks() {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		resourceLink.addActionLink("read", actionLink);
		assertEquals(resourceLink.getActionLink("read"), actionLink);
		assertEquals(resourceLink.getActionLinks().get("read"), actionLink);
		assertNull(resourceLink.getActionLink("notAnAction"));
	}

	@Test
	public void testWithRepeatId() {
		resourceLink.setRepeatId("x2");
		assertEquals(resourceLink.getRepeatId(), "x2");
	}

	@Test
	public void testSetActionLinks() throws Exception {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		Map<String, ActionLink> actionLinks = new HashMap<>();
		actionLinks.put("read", actionLink);
		resourceLink.setActionLinks(actionLinks);
		assertEquals(resourceLink.getActionLink("read"), actionLink);
		assertEquals(resourceLink.getActionLinks().get("read"), actionLink);
		assertNull(resourceLink.getActionLink("notAnAction"));
	}
}
