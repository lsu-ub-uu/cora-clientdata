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
	}

	@Test
	public void testInitWithRepeatId() {
		clientDataAtomic.setRepeatId("x1");
		assertEquals(clientDataAtomic.getNameInData(), "nameInData");
		assertEquals(clientDataAtomic.getValue(), "value");
		assertEquals(clientDataAtomic.getRepeatId(), "x1");

	}
}
