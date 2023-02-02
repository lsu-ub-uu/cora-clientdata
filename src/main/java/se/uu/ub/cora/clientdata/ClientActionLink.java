/*
 * Copyright 2023 Uppsala University Library
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

/**
 * ClientActionLink defines a possible action that can be taken for a record or a search
 *
 */
public interface ClientActionLink {

	ClientAction getAction();

	void setURL(String url);

	String getURL();

	void setRequestMethod(String requestMethod);

	String getRequestMethod();

	void setAccept(String accept);

	String getAccept();

	void setContentType(String contentType);

	String getContentType();

	void setBody(ClientDataGroup body);

	ClientDataGroup getBody();

}