/*
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
import java.util.List;
import java.util.Optional;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataChild;
import se.uu.ub.cora.clientdata.ClientDataChildFilter;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.clientdata.ClientDataRecordGroup;

public class DataRecordGroupSpy implements ClientDataRecordGroup {

	@Override
	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsChildWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addChild(ClientDataChild dataElement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addChildren(Collection<ClientDataChild> dataElements) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<ClientDataChild> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientDataChild> getAllChildrenWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientDataChild> getAllChildrenWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... childAttributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDataChild getFirstChildWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFirstAtomicValueWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientDataAtomic> getAllDataAtomicsWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientDataGroup getFirstGroupWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientDataGroup> getAllGroupsWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ClientDataGroup> getAllGroupsWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... childAttributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeFirstChildWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAllChildrenWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAllChildrenWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... childAttributes) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClientDataAtomic getFirstDataAtomicWithNameInData(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	// @Override
	// public void setRepeatId(String repeatId) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public String getRepeatId() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	@Override
	public void addAttributeByIdWithValue(String nameInData, String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean hasAttributes() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClientDataAttribute getAttribute(String nameInData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ClientDataAttribute> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameInData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ClientDataAtomic> getAllDataAtomicsWithNameInDataAndAttributes(
			String childNameInData, ClientDataAttribute... childAttributes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ClientDataChild> getAllChildrenMatchingFilter(ClientDataChildFilter childFilter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAllChildrenMatchingFilter(ClientDataChildFilter childFilter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getDataDivider() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataDivider(String dataDivider) {
		// TODO Auto-generated method stub
	}

	@Override
	public <T> boolean containsChildOfTypeAndName(Class<T> type, String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends ClientDataChild> T getFirstChildOfTypeAndName(Class<T> type, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ClientDataChild> List<T> getChildrenOfTypeAndName(Class<T> type,
			String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends ClientDataChild> boolean removeFirstChildWithTypeAndName(Class<T> type,
			String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T extends ClientDataChild> boolean removeChildrenWithTypeAndName(Class<T> type,
			String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<String> getAttributeValue(String nameInData) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
