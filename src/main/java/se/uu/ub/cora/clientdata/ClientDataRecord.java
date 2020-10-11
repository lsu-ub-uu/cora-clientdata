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

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class ClientDataRecord implements DataRecord, ClientData {
	private ClientDataGroup clientDataGroup;
	private Map<String, ActionLink> actionLinks = new LinkedHashMap<>();
	private Set<String> readPermissions = new LinkedHashSet<>();
	private Set<String> writePermissions = new LinkedHashSet<>();

	private ClientDataRecord(ClientDataGroup clientDataGroup) {
		this.clientDataGroup = clientDataGroup;
	}

	public static ClientDataRecord withClientDataGroup(ClientDataGroup clientDataGroup) {
		return new ClientDataRecord(clientDataGroup);
	}

	@Override
	public ClientDataGroup getClientDataGroup() {
		return clientDataGroup;
	}

	public void addActionLink(String key, ActionLink actionLink) {
		actionLinks.put(key, actionLink);
	}

	public ActionLink getActionLink(String key) {
		return actionLinks.get(key);
	}

	@Override
	public Map<String, ActionLink> getActionLinks() {
		return actionLinks;
	}

	public void setActionLinks(Map<String, ActionLink> actionLinks) {
		this.actionLinks = actionLinks;
	}

	public void addReadPermission(String readPermission) {
		readPermissions.add(readPermission);
	}

	@Override
	public Set<String> getReadPermissions() {
		return readPermissions;
	}

	public void addWritePermission(String writePermission) {
		writePermissions.add(writePermission);

	}

	@Override
	public Set<String> getWritePermissions() {
		return writePermissions;
	}

}
