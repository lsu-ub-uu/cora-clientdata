/*
 * Copyright 2015, 2020 Uppsala University Library
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
 * ClientData defines a unit of information at the level sent to and from a cora system. This is
 * normaly information about an cora-object handled by the system. ClientData is used as a container
 * of related information between a cora system and its clients.
 * <p>
 * If multiple instances of {@link ClientData} needs to be sent to a client at once can they be
 * added to a {@link ClientDataList}.
 */
public interface ClientData extends ClientConvertible {

}
