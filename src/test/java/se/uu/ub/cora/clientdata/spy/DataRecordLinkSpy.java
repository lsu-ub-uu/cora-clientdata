/*
 * Copyright 2019 Uppsala University Library
 * Copyright 2022 Olov McKie
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
package se.uu.ub.cora.clientdata.spy;

import java.util.Collection;

import se.uu.ub.cora.clientdata.ClientAction;
import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;

public class DataRecordLinkSpy implements ClientDataRecordLink {

	@Override
	public void addAction(ClientAction action) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getNameInData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRepeatId() {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public String getFirstAtomicValueWithNameInData(String nameInData) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public DataGroup getFirstGroupWithNameInData(String childNameInData) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void addChild(DataChild dataElement) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public List<DataChild> getChildren() {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean containsChildWithNameInData(String nameInData) {
	// // TODO Auto-generated method stub
	// return false;
	// }

	@Override
	public void setRepeatId(String repeatId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addAttributeByIdWithValue(String id, String value) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public DataChild getFirstChildWithNameInData(String nameInData) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public List<DataGroup> getAllGroupsWithNameInData(String nameInData) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public ClientDataAttribute getAttribute(String attributeId) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public List<DataAtomic> getAllDataAtomicsWithNameInData(String childNameInData) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean removeFirstChildWithNameInData(String childNameInData) {
	// return true;
	// }
	//
	// @Override
	// public Collection<DataGroup> getAllGroupsWithNameInDataAndAttributes(String childNameInData,
	// DataAttribute... childAttributes) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public Collection<ClientDataAttribute> getAttributes() {
		return null;
	}

	// @Override
	// public boolean removeAllChildrenWithNameInData(String childNameInData) {
	// return true;
	// }
	//
	// @Override
	// public DataAtomic getFirstDataAtomicWithNameInData(String childNameInData) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public void addChildren(Collection<DataChild> dataElements) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public List<DataChild> getAllChildrenWithNameInData(String nameInData) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public boolean hasChildren() {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean removeAllChildrenWithNameInDataAndAttributes(String childNameInData,
	// DataAttribute... childAttributes) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public List<DataChild> getAllChildrenWithNameInDataAndAttributes(String nameInData,
	// DataAttribute... childAttributes) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public boolean hasReadAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLinkedRecordId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLinkedRecordType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasAttributes() {
		// TODO Auto-generated method stub
		return false;
	}

}
