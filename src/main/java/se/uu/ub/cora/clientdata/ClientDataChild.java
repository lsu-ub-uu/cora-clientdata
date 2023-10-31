/*
 * Copyright 2015, 2019, 2022 Uppsala University Library
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

import se.uu.ub.cora.clientdata.ability.ClientDataCharacteristic;
import se.uu.ub.cora.clientdata.ability.ClientDataPart;

/**
 * DataChild defines elements that can be added as children to a DataGroup.
 */
public interface ClientDataChild extends ClientDataPart, ClientDataCharacteristic {

	/**
	 * hasRepeatId return where there is or there is not a repeatId.
	 * 
	 * @return A boolean saying wheter repeat exists or not.
	 */
	boolean hasRepeatId();

	/**
	 * setRepeatId sets the DataElements repeatId, if the repeatId has been set since before should
	 * the value be updated.
	 * 
	 * @param repeatId
	 *            A String with the repeatId
	 */
	void setRepeatId(String repeatId);

	/**
	 * getRepeatId returns the repeatId.
	 * 
	 * @return A String with the value of the repeatId
	 */
	String getRepeatId();
}
