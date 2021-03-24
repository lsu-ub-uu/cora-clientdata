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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClientDataGroup implements ClientDataElement, ClientData {

	private final String nameInData;
	private Map<String, String> attributes = new HashMap<>();
	private List<ClientDataElement> children = new ArrayList<>();
	private String repeatId;

	private Predicate<ClientDataElement> isDataGroup = dataElement -> dataElement instanceof ClientDataGroup;
	private Predicate<ClientDataElement> isDataAtomic = dataElement -> dataElement instanceof ClientDataAtomic;

	protected ClientDataGroup(String nameInData) {
		this.nameInData = nameInData;
	}

	public static ClientDataGroup withNameInData(String nameInData) {
		return new ClientDataGroup(nameInData);
	}

	public static ClientDataGroup asLinkWithNameInDataAndTypeAndId(String nameInData, String type,
			String id) {
		ClientDataGroup dataGroup = new ClientDataGroup(nameInData);
		dataGroup.addChild(ClientDataAtomic.withNameInDataAndValue("linkedRecordType", type));
		dataGroup.addChild(ClientDataAtomic.withNameInDataAndValue("linkedRecordId", id));
		return dataGroup;
	}

	@Override
	public String getNameInData() {
		return nameInData;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void addAttributeByIdWithValue(String nameInData, String value) {
		attributes.put(nameInData, value);
	}

	public void addChild(ClientDataElement clientDataElement) {
		children.add(clientDataElement);
	}

	public List<ClientDataElement> getChildren() {
		return children;
	}

	public void setRepeatId(String repeatId) {
		this.repeatId = repeatId;
	}

	public String getRepeatId() {
		return repeatId;
	}

	public boolean containsChildWithNameInData(String nameInData) {
		for (ClientDataElement clientDataElement : getChildren()) {
			if (clientDataElement.getNameInData().equals(nameInData)) {
				return true;
			}
		}
		return false;
	}

	public ClientDataElement getFirstChildWithNameInData(String nameInData) {
		for (ClientDataElement clientDataElement : getChildren()) {
			if (clientDataElement.getNameInData().equals(nameInData)) {
				return clientDataElement;
			}
		}
		throw new DataMissingException("Requested child " + nameInData + " does not exist");
	}

	public void removeFirstChildWithNameInData(String childNameInData) {
		boolean childRemoved = tryToRemoveChild(childNameInData);
		if (!childRemoved) {
			throw new DataMissingException(
					"Element not found for childNameInData:" + childNameInData);
		}
	}

	private boolean tryToRemoveChild(String childNameInData) {
		for (ClientDataElement dataElement : getChildren()) {
			if (dataElement.getNameInData().equals(childNameInData)) {
				getChildren().remove(dataElement);
				return true;
			}
		}
		return false;
	}

	public ClientDataGroup getFirstGroupWithNameInData(String childNameInData) {
		Optional<ClientDataGroup> findFirst = getGroupChildrenWithNameInDataStream(childNameInData)
				.findFirst();
		if (findFirst.isPresent()) {
			return findFirst.get();
		}
		throw new DataMissingException("Group not found for childNameInData:" + childNameInData);
	}

	private Stream<ClientDataGroup> getGroupChildrenWithNameInDataStream(String childNameInData) {
		return getGroupChildrenStream().filter(filterByNameInData(childNameInData))
				.map(ClientDataGroup.class::cast);
	}

	private Stream<ClientDataElement> getGroupChildrenStream() {
		return getChildrenStream().filter(isDataGroup);
	}

	private Stream<ClientDataElement> getChildrenStream() {
		return children.stream();
	}

	private Predicate<ClientDataElement> filterByNameInData(String childNameInData) {
		return dataElement -> dataElementsNameInDataIs(dataElement, childNameInData);
	}

	private boolean dataElementsNameInDataIs(ClientDataElement dataElement,
			String childNameInData) {
		return dataElement.getNameInData().equals(childNameInData);
	}

	public List<ClientDataGroup> getAllGroupsWithNameInData(String childNameInData) {
		return getGroupChildrenWithNameInDataStream(childNameInData).collect(Collectors.toList());
	}

	public String getFirstAtomicValueWithNameInData(String childNameInData) {
		Optional<ClientDataAtomic> optionalFirst = getAtomicChildrenWithNameInData(childNameInData)
				.findFirst();
		return possiblyReturnAtomicChildWithNameInData(childNameInData, optionalFirst);
	}

	private Stream<ClientDataAtomic> getAtomicChildrenWithNameInData(String childNameInData) {
		return getAtomicChildrenStream().filter(filterByNameInData(childNameInData))
				.map(ClientDataAtomic.class::cast);
	}

	private Stream<ClientDataElement> getAtomicChildrenStream() {
		return getChildrenStream().filter(isDataAtomic);
	}

	private String possiblyReturnAtomicChildWithNameInData(String childNameInData,
			Optional<ClientDataAtomic> optionalFirst) {
		if (optionalFirst.isPresent()) {
			return optionalFirst.get().getValue();
		}
		throw new DataMissingException(
				"Atomic value not found for childNameInData:" + childNameInData);
	}

	public List<ClientDataAtomic> getAllDataAtomicsWithNameInData(String childNameInData) {
		return getAtomicChildrenWithNameInData(childNameInData).collect(Collectors.toList());
	}

	public List<ClientDataElement> getAllChildrenWithNameInData(String childNameInData) {
		return getChildrenWithNameInDataStream(childNameInData).collect(Collectors.toList());
	}

	private Stream<ClientDataElement> getChildrenWithNameInDataStream(String childNameInData) {
		return getChildrenStream().filter(filterByNameInData(childNameInData))
				.map(ClientDataElement.class::cast);
	}

	public Collection<ClientDataGroup> getAllGroupsWithNameInDataAndAttributes(
			String childNameInData, ClientDataAttribute... childAttributes) {
		return getGroupChildrenWithNameInDataAndAttributes(childNameInData, childAttributes)
				.collect(Collectors.toList());
	}

	private Stream<ClientDataGroup> getGroupChildrenWithNameInDataAndAttributes(
			String childNameInData, ClientDataAttribute... childAttributes) {
		return getGroupChildrenWithNameInDataStream(childNameInData)
				.filter(filterByAttributes(childAttributes));
	}

	private Predicate<ClientDataGroup> filterByAttributes(ClientDataAttribute... childAttributes) {
		return dataElement -> dataElementsHasAttributes(dataElement, childAttributes);
	}

	private boolean dataElementsHasAttributes(ClientDataGroup dataElement,
			ClientDataAttribute[] childAttributes) {
		Map<String, String> attributesFromElement = dataElement.getAttributes();
		if (diffrentNumberOfAttributesInRequestedAndExisting(childAttributes,
				attributesFromElement)) {
			return false;
		}
		return allRequestedAttributesMatchExistingAttributes(childAttributes,
				attributesFromElement);
	}

	private boolean diffrentNumberOfAttributesInRequestedAndExisting(
			ClientDataAttribute[] childAttributes, Map<String, String> attributesFromElement) {
		return childAttributes.length != attributesFromElement.size();
	}

	private boolean allRequestedAttributesMatchExistingAttributes(
			ClientDataAttribute[] childAttributes, Map<String, String> attributesFromElement) {
		for (ClientDataAttribute dataAttribute : childAttributes) {
			if (attributesDoesNotMatch(attributesFromElement, dataAttribute)) {
				return false;
			}
		}
		return true;
	}

	private boolean attributesDoesNotMatch(Map<String, String> attributesFromElement,
			ClientDataAttribute dataAttribute) {
		return requestedAttributeDoesNotExists(attributesFromElement, dataAttribute)
				|| requestedAttributeHasDifferentValueAsExisting(attributesFromElement,
						dataAttribute);
	}

	private boolean requestedAttributeDoesNotExists(Map<String, String> attributesFromElement,
			ClientDataElement dataAttribute) {
		return !attributesFromElement.containsKey(dataAttribute.getNameInData());
	}

	private boolean requestedAttributeHasDifferentValueAsExisting(
			Map<String, String> attributesFromElement, ClientDataAttribute dataAttribute) {
		return !attributesFromElement.get(dataAttribute.getNameInData())
				.equals(dataAttribute.getValue());
	}

	public ClientDataGroup getFirstGroupWithNameInDataAndAttributes(String childNameInData,
			ClientDataAttribute... childDataAttributes) {
		Collection<ClientDataGroup> allGroupsWithNameInDataAndAttributes = getAllGroupsWithNameInDataAndAttributes(
				childNameInData, childDataAttributes);
		throwErrorIfNoGroupsFound(childNameInData, allGroupsWithNameInDataAndAttributes,
				childDataAttributes);
		return allGroupsWithNameInDataAndAttributes.iterator().next();
	}

	private void throwErrorIfNoGroupsFound(String childNameInData,
			Collection<ClientDataGroup> allGroupsWithNameInDataAndAttributes,
			ClientDataAttribute... childDataAttributes) {
		if (allGroupsWithNameInDataAndAttributes.isEmpty()) {
			StringJoiner attributesError = createErrorMessagesForAttributes(childDataAttributes);
			throw new DataMissingException("Group not found for childNameInData: " + childNameInData
					+ " with attributes: " + attributesError);
		}
	}

	private StringJoiner createErrorMessagesForAttributes(
			ClientDataAttribute... childDataAttributes) {
		StringJoiner attributesError = new StringJoiner(", ");
		for (ClientDataAttribute clientDataAttribute : childDataAttributes) {
			attributesError.add(
					clientDataAttribute.getNameInData() + ":" + clientDataAttribute.getValue());
		}
		return attributesError;
	}

}
