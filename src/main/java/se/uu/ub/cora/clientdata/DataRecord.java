/*
 * Copyright 2020 Uppsala University Library
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

import java.util.Set;

public interface DataRecord {

	ClientDataGroup getClientDataGroup();

	/**
	 * Returns a set contaning the read permissions set in the DataRecord
	 * 
	 * @return A Set<String> containing read permissions
	 */
	Set<String> getReadPermissions();

	/**
	 * Returns a set contaning the write permissions set in the DataRecord
	 * 
	 * @return A Set<String> containing write permissions
	 */
	Set<String> getWritePermissions();

}
