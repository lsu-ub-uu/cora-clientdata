/*
 * Copyright 2015, 2018, 2022 Uppsala University Library
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
import static org.testng.Assert.assertTrue;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientDataAtomicTest {
	private ClientDataAtomic clientDataAtomic;

	@BeforeMethod
	public void setUp() {
		clientDataAtomic = ClientDataAtomic.withNameInDataAndValue("nameInData", "value");

	}

	@Test
	public void testInit() {
		assertEquals(clientDataAtomic.getNameInData(), "nameInData");
		assertEquals(clientDataAtomic.getValue(), "value");
		assertTrue(clientDataAtomic.getAttributes().isEmpty());
	}

	@Test
	public void testInitWithRepeatId() {
		clientDataAtomic.setRepeatId("x1");
		assertEquals(clientDataAtomic.getNameInData(), "nameInData");
		assertEquals(clientDataAtomic.getValue(), "value");
		assertEquals(clientDataAtomic.getRepeatId(), "x1");
	}

	@Test
	public void testAddAttributes() {
		clientDataAtomic.addAttributeByIdWithValue("someAttributeName", "value");
		clientDataAtomic.addAttributeByIdWithValue("someOtherAttributeName", "otherValue");
		Map<String, String> attributes = clientDataAtomic.getAttributes();

		assertEquals(attributes.get("someAttributeName"), "value");
		assertEquals(attributes.get("someOtherAttributeName"), "otherValue");
	}

	@Test
	public void testAddAttributesSameNameInDataOverwrites() {
		clientDataAtomic.addAttributeByIdWithValue("someAttributeName", "value");

		Map<String, String> attributes = clientDataAtomic.getAttributes();
		assertEquals(attributes.get("someAttributeName"), "value");

		clientDataAtomic.addAttributeByIdWithValue("someAttributeName", "otherValue");

		attributes = clientDataAtomic.getAttributes();
		assertEquals(attributes.get("someAttributeName"), "otherValue");
	}
}
