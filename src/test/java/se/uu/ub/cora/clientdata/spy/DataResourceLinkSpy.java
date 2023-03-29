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
import java.util.Optional;

import se.uu.ub.cora.clientdata.ClientAction;
import se.uu.ub.cora.clientdata.ClientActionLink;
import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataResourceLink;
import se.uu.ub.cora.testutils.mcr.MethodCallRecorder;

public class DataResourceLinkSpy implements ClientDataResourceLink {
	public MethodCallRecorder MCR = new MethodCallRecorder();

	@Override
	public boolean hasReadAction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRepeatId(String repeatId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getRepeatId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNameInData() {
		// TODO Auto-generated method stub
		return null;
	}

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
	public void setStreamId(String streamId) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getStreamId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFileName(String filename) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFileSize(String fileSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFileSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMimeType(String mimeType) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMimeType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addActionLink(ClientActionLink actionLink) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ClientActionLink> getActionLink(ClientAction action) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Optional<String> getAttributeValue(String nameInData) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

}
