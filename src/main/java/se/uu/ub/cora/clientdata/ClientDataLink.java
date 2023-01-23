/*
 * Copyright 2016 Uppsala University Library
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
 * DataLink contains information linking the {@link ClientDataRecord} this link is a part of to another
 * entity in the system.
 */
public interface ClientDataLink extends ClientDataChild {

	/**
	 * addAction adds an Action to this DataLink. The actions added represents the actions the
	 * current user can perform on the resource this link points to.
	 * <p>
	 * At this moment only READ action are implemented for DataLinks.
	 */
	void addAction(ClientAction action);

	/**
	 * hasReadAction returns true if the current user is allowed to read the resource this link
	 * points to. This is also known as: link has read action.
	 * <p>
	 * Note! The action information is not always present. If no action information is present
	 * SHOULD hasReadAction return false.
	 * 
	 * @return a boolean true if this link has read as one of its actions else false
	 */
	boolean hasReadAction();

}
