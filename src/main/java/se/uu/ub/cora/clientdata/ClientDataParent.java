/*
 * Copyright 2019, 2020 Uppsala University Library
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

import java.util.Collection;
import java.util.List;

/**
 * DataParent is a container for related DataElements, i.e. it has children.
 */
public interface ClientDataParent {

	/**
	 * hasChildren checks if this DataGroup has at least one child or not
	 * 
	 * @return A boolean, true if at least one child exists, else false
	 */
	boolean hasChildren();

	/**
	 * containsChildWithNameInData checks if this DataGroup has at least one child with the
	 * specified name or not.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the child
	 * @return A boolean, true if a child exists with the specified name, else false.
	 */
	boolean containsChildWithNameInData(String nameInData);

	/**
	 * addChild adds a {@link ClientDataChild} to this DataGroup
	 * 
	 * @param dataElement
	 *            to add to this DataGroup
	 */
	void addChild(ClientDataChild dataChild);

	/**
	 * addChildren is used to add the entered dataElements as children into the current dataGroup.
	 * If the entered collection of dataElements is empty should no children be added.
	 * 
	 * @param dataChildren
	 *            to add as children
	 */
	void addChildren(Collection<ClientDataChild> dataChildren);

	/**
	 * getChildren is used to get a List with all the children of this {@link ClientDataGroup}
	 * 
	 * @return A List of {@link ClientDataChild} with all the children of this datagroup.
	 */
	List<ClientDataChild> getChildren();

	/**
	 * getAllChildrenWithNameInData is used to get all children that matches the specified
	 * nameInData as DataElements.
	 * <p>
	 * An empty list SHOULD be returned if no child exists with the specified nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the children to get
	 * @return A List with all children that has the specified nameInData
	 */
	List<ClientDataChild> getAllChildrenWithNameInData(String nameInData);

	/**
	 * getAllChildrenWithNameInDataAndAttributes is used to get all children that matches the
	 * specified nameInData and the specified attributes, as DataElements.
	 * <p>
	 * An empty list SHOULD be returned if no child exists with the specified nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the children to get
	 * @param childAttributes
	 *            A Varargs with attributes the children must have to be returned
	 * @return A List with all children that has the specified nameInData
	 * @deprecated Use {@link #getAllChildrenMatchingFilter(ClientDataChildFilter)} instead.
	 */
	@Deprecated
	List<ClientDataChild> getAllChildrenWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... childAttributes);

	/**
	 * getFirstChildWithNameInData is used to get the first {@link ClientDataChild} that matches the
	 * specified nameInData.
	 * <p>
	 * A {@link ClientDataMissingException} SHOULD be thrown if no child exists with the specified
	 * nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the child to get
	 * @return The first {@link ClientDataChild} that matches the specified nameInData.
	 */
	ClientDataChild getFirstChildWithNameInData(String nameInData);

	/**
	 * getFirstAtomicValueWithNameInData is used to get the value of the first
	 * {@link ClientDataAtomic} that matches the specified nameInData.
	 * <p>
	 * A {@link ClientDataMissingException} SHOULD be thrown if no child exists with the specified
	 * nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the child to get
	 * @return String value of the atomic that matches the specified nameInData.
	 */
	String getFirstAtomicValueWithNameInData(String nameInData);

	/**
	 * getFirstDataAtomicWithNameInData returns the first DataAtomic child with the specified
	 * nameInData.
	 * <p>
	 * A {@link ClientDataMissingException} SHOULD be thrown if no child exists with the specified
	 * nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the child to get
	 * @return The first {@link ClientDataAtomic} that matches nameInData.
	 */
	ClientDataAtomic getFirstDataAtomicWithNameInData(String nameInData);

	/**
	 * getAllDataAtomicsWithNameInData is used to get a List of the all {@link ClientDataAtomic}
	 * that matches the specified nameInData.
	 * <p>
	 * If no matching elements are found SHOULD an empty list be returned.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the children to get
	 * @return A List of {@link ClientDataAtomic} that matches the specified nameInData.
	 */
	List<ClientDataAtomic> getAllDataAtomicsWithNameInData(String nameInData);

	/**
	 * getAllDataAtomicsWithNameInDataAndAttributes is used to get a Collection of the all
	 * {@link ClientDataAtomic} that matches the specified nameInData and the specified attributes.
	 * <p>
	 * If no matching elements are found SHOULD an empty collection be returned.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the children to get
	 * @param childAttributes
	 *            A Varargs with attributes the children must have to be returned
	 * @return A Collection of {@link ClientDataAtomic} that matches the specified nameInData and
	 *         the specified attributes.
	 */
	Collection<ClientDataAtomic> getAllDataAtomicsWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... childAttributes);

	/**
	 * getFirstGroupWithNameInData returns the first DataGroup child with the specified nameInData.
	 * <p>
	 * A {@link ClientDataMissingException} SHOULD be thrown if no child exists with the specified
	 * nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the child to get
	 */
	ClientDataGroup getFirstGroupWithNameInData(String nameInData);

	/**
	 * getAllGroupsWithNameInData is used to get a List of the all {@link ClientDataGroup} that
	 * matches the specified nameInData.
	 * <p>
	 * If no matching elements are found SHOULD an empty list be returned.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the children to get
	 * @return A List of {@link ClientDataGroup} that matches the specified nameInData.
	 */
	List<ClientDataGroup> getAllGroupsWithNameInData(String nameInData);

	/**
	 * getAllGroupsWithNameInData is used to get a List of the all {@link ClientDataGroup} that
	 * matches the specified nameInData and the specified attributes.
	 * <p>
	 * If no matching elements are found SHOULD an empty collection be returned.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the children to get
	 * @param childAttributes
	 *            A Varargs with attributes the children must have to be returned
	 * @return A Collection of {@link ClientDataGroup} that matches the specified nameInData and
	 *         attributes list.
	 */
	Collection<ClientDataGroup> getAllGroupsWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... childAttributes);

	/**
	 * removeFirstChildWithNameInData removes the first child in this DataGroup that has the
	 * specified nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the child to remove
	 * @return true if a child was removed, false otherwise
	 */
	boolean removeFirstChildWithNameInData(String nameInData);

	/**
	 * removeAllChildrenWithNameInData removes all children in this DataGroup that has the specified
	 * nameInData.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the children to remove
	 * @return true if any child has been removed, false otherwise
	 */
	boolean removeAllChildrenWithNameInData(String nameInData);

	/**
	 * removeAllChildrenWithNameInDataAndAttributes removes all children in this DataGroup that has
	 * the specified nameInData and the specified attributes.
	 * 
	 * @param nameInData
	 *            A String with the nameInData of the child to remove
	 * @param childAttributes
	 *            A Varargs with attributes the children must have to be returned
	 * @return true if any child has been removed, false otherwise
	 * @deprecated Use {@link #removeAllChildrenMatchingFilter(ClientDataChildFilter)} instead.
	 */
	@Deprecated
	boolean removeAllChildrenWithNameInDataAndAttributes(String nameInData,
			ClientDataAttribute... childAttributes);

	/**
	 * getAllChildrenMatchingFilter is used to get all children that matches the specified
	 * childFilter.
	 * </p>
	 * See, {@link ClientDataChildFilter#childMatches(ClientDataChild)} for exactly how a child is
	 * considered a match.
	 * <p>
	 * An empty list SHOULD be returned if no child exists with the specified nameInData.
	 * 
	 * @param childFilter
	 *            A DataChildFilter to filter the children to return
	 * @return A List with all children that matches the specified childFilter
	 */
	List<ClientDataChild> getAllChildrenMatchingFilter(ClientDataChildFilter childFilter);

	/**
	 * removeAllChildrenMatchingFilter removes all children in this DataGroup that matches the
	 * specified childFilter.
	 * </p>
	 * See, {@link ClientDataChildFilter#childMatches(ClientDataChild)} for exactly how a child is
	 * considered a match.
	 * 
	 * @param childFilter
	 *            A DataChildFilter to filter the children to remove
	 * @return true if any child has been removed, false otherwise
	 */
	boolean removeAllChildrenMatchingFilter(ClientDataChildFilter childFilter);

	/**
	 * containsChildOfTypeAndName checks if this DataParent has at least one child with the
	 * specified name class, nameInData and attributes.
	 * 
	 * @param <T>
	 *            Automatically set to the requested type of class
	 * @param type
	 *            A Class that the child must have to be found. Must be {@link DataChild} or a class
	 *            that extends it.
	 * @param name
	 *            A String with the nameInData of the child to find
	 * @return A boolean, true if a child exists with the specified name, else false.
	 */

	<T> boolean containsChildOfTypeAndName(Class<T> type, String name);

	/**
	 * getFirstChildOfTypeAndName is used to get the first {@link ClientDataChild} that matches the
	 * specified class, nameInData and attributes. The returned list is typed to the same class that
	 * is requested.
	 * <p>
	 * A {@link ClientDataMissingException} SHOULD be thrown if no child exists with the specified
	 * Class and nameInData.
	 * 
	 * @param <T>
	 *            Automatically set to the requested type of class
	 * @param type
	 *            A Class that the child must have to be returned. Must be {@link ClientDataChild}
	 *            or a class that extends it.
	 * @param name
	 *            A String with the nameInData of the child to get
	 * @return
	 */
	<T extends ClientDataChild> T getFirstChildOfTypeAndName(Class<T> type, String name);

	/**
	 * getChildrenOfTypeAndName is used to get a List of the all {@link ClientDataChild}s that
	 * matches the specified class, nameInData and attributes. The returned list is typed to the
	 * same class that is requested.
	 * <p>
	 * An empty list SHOULD be returned if no child exists with the specified Class and nameInData.
	 * 
	 * @param <T>
	 *            Automatically set to the requested type of class
	 * @param type
	 *            A Class that the children must have to be returned. Must be
	 *            {@link ClientDataChild} or a class that extends it.
	 * @param name
	 *            A String with the nameInData of the children to get
	 * @return
	 */
	<T extends ClientDataChild> List<T> getChildrenOfTypeAndName(Class<T> type, String name);

	/**
	 * removeFirstChildWithTypeAndName is used to remove the first {@link ClientDataChild}s that
	 * matches the specified class, nameInData and attributes.
	 * 
	 * @param <T>
	 *            Automatically set to the type of class to remove
	 * @param type
	 *            A Class that the child must have to be removed. Must be {@link ClientDataChild} or
	 *            a class that extends it.
	 * @param name
	 *            A String with the nameInData of the child to remove
	 * @return true if any child has been removed, false otherwise
	 */
	<T extends ClientDataChild> boolean removeFirstChildWithTypeAndName(Class<T> type, String name);

	/**
	 * removeChildrenWithTypeAndName is used to remove all {@link ClientDataChild}s that matches the
	 * specified class, nameInData and attributes.
	 * 
	 * @param <T>
	 *            Automatically set to the type of class to remove
	 * @param type
	 *            A Class that the children must have to be removed. Must be {@link ClientDataChild}
	 *            or a class that extends it.
	 * @param name
	 *            A String with the nameInData of the children to remove
	 * @return true if any child has been removed, false otherwise
	 */
	<T extends ClientDataChild> boolean removeChildrenWithTypeAndName(Class<T> type, String name);

}
