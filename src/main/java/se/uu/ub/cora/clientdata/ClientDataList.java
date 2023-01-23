/*
 * Copyright 2019 Uppsala University Library
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

import java.util.List;

/**
 * DataList is a List of {@link ClientData}, normally used to send multiple Data items in response to
 * requests from clients.
 * <p>
 * Implementations are expected to retain the order of the items in the list.
 * <p>
 * The list contains information about what part of the total data set it represents, this
 * information can be accessed through the methods {@link #getFromNo()} and {@link #getToNo()}
 * respectively. Sets of data can be limited in scope for a variety of reasons, such as filters,
 * requested data types, etc. The total number of {@link ClientData} the system is aware of can be
 * accessed through the method {@link #getTotalNumberOfTypeInStorage()}.
 * <p>
 * Numbering start at 1, so the first ten items in a returned list are 1-10.
 */
public interface ClientDataList extends ClientConvertible, ClientExternallyConvertible {

	/**
	 * getFromNo returns the start position of the included List of {@link ClientData} in relation to the
	 * total number of {@link ClientData} the system is aware of for the requested data.
	 * 
	 * @return String with the start position of included list.
	 */
	String getFromNo();

	/**
	 * getToNo method returns the final position of the included List of {@link ClientData} in relation to
	 * the total number of {@link ClientData} the system is aware of for the requested data.
	 * 
	 * @return String with the final position of included list.
	 */
	String getToNo();

	/**
	 * (totalNo) getTotalNumberOfTypeInStorage returns the total number of {@link ClientData} that the
	 * system is aware of for the requested set of data. Sets of data can be limited by for a
	 * variety of reasons, such as filters, requested data types, etc.
	 *
	 * @return A String that represents the total number of {@link ClientData}
	 */
	String getTotalNumberOfTypeInStorage();

	/**
	 * getContainDataOfType returns a String describing the type(s) of data that the list contains
	 * 
	 * @return A String describing the type(s) of {@link ClientData} included in this list
	 */
	String getContainDataOfType();

	/**
	 * getDataList returns the included list.
	 * 
	 * @return The included List of {@link ClientData}
	 */
	List<ClientData> getDataList();

	/**
	 * addData adds an element {@link ClientData} to the internal list of {@link ClientData}
	 * 
	 * @param data
	 *            A {@link ClientData} to be added to the internal list
	 */
	void addData(ClientData data);

	/**
	 * setFromNo sets the start position of the included List of {@link ClientData} in relation to the
	 * total number of {@link ClientData} the system is aware of for the requested data.
	 * 
	 * @param position
	 *            The start postion of the list
	 */
	void setFromNo(String position);

	/**
	 * setToNo sets the final position of the included List of {@link ClientData} in relation to the total
	 * number of {@link ClientData} the system is aware of for the requested data.
	 * 
	 * @param position
	 *            The final postion of the list
	 */
	void setToNo(String position);

	/**
	 * setTotalNo sets the total number of {@link ClientData} that the system is aware of for the
	 * requested set of data.
	 * 
	 * @param totalNumber
	 *            A String with the total number of {@link ClientData}
	 */
	void setTotalNo(String totalNumber);

}
