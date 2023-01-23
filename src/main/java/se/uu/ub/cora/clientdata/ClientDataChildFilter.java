/*
 * Copyright 2022 Uppsala University Library
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

/**
 * DataChildFilter is used to filter children when working with a {@link ClientDataParent} (
 * {@link ClientDataRecordGroup}, {@link ClientDataGroup}), to filter wich children are affected by the
 * different methods.
 * <p/>
 * DataChildFilters SHOULD be created using the
 * {@link ClientDataProvider#createDataChildFilterUsingNameInData(String)} method.
 * 
 * DataChildFilter SHOULD be considered non thread-safe.
 */
public interface ClientDataChildFilter {
	/**
	 * addAttributeUsingNameInDataAndPossibleValues add a new attribute to this filter with the
	 * specified nameInData and possibleValues
	 * 
	 * @param nameInData
	 *            Name of the attribute
	 * @param possibleValues
	 *            Set of values of an attribute that this filter should match.
	 */
	public void addAttributeUsingNameInDataAndPossibleValues(String nameInData,
			Set<String> possibleValues);

	/**
	 * childMatches returns true if the entered {@link ClientDataChild} matches this filter. The child is
	 * considered a match if, nameInData is the same, and any to the filter, added attributes exist
	 * with the same nameInData, and with the value present in the list of possible attribute
	 * values. If any extra attributes exists in the child that does not exist in the filter, is the
	 * child not a match.
	 * 
	 * @param child
	 *            {@link ClientDataChild} to be matched
	 * @return Returns a boolean whether the child matches with the configured filter
	 */
	public boolean childMatches(ClientDataChild child);

}
