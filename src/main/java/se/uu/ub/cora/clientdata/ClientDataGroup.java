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

/**
 * DataGroup is a container for related {@link ClientDataChild} elements.
 * </p>
 * DataGroup has a sibling class {@link ClientDataRecordGroup} that is intended to be used when handling
 * the top most dataGroup for a record, as it has extra utility methods to handle the info found in
 * recordInfo.
 * </p>
 * There are a few usecases when it is of lesser importance if the class beening handled is a
 * {@link ClientDataRecordGroup} or a {@link ClientDataGroup}, such as when converting to other formats. The
 * recomeded way to handle these usecases is to use the common parent class {@link ClientDataParent}.
 * </p>
 * {@link ClientDataProvider} has methods to turn a DataRecordGroup into a DataGroup and vice versa. See:
 * {@link ClientDataProvider#createRecordGroupFromDataGroup(ClientDataGroup)} and
 * {@link ClientDataProvider#createGroupFromRecordGroup(ClientDataRecordGroup)}
 * </p>
 */
public interface ClientDataGroup extends ClientDataParent, ClientDataChild, ClientData, ClientConvertible, ClientExternallyConvertible {

}
