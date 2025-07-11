/*
 * Copyright 2015, 2025 Uppsala University Library
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

package se.uu.ub.cora.clientdata.converter;

import se.uu.ub.cora.clientdata.ClientConvertible;

/**
 * DataToJsonConverterFactory is a factory that creates new instances of
 * {@link ClientDataToJsonConverter} for provided {@link ClientConvertible}s
 * <p>
 * By default SHOULD implementations generate converters that creates json without action links. To
 * get converters to generate action links call the method
 * {@link #switchToActionLinkGeneratingModeUsingBaseUrl(String)} with the base url to use in the
 * links.
 */

public interface ClientDataToJsonConverterFactory {

	/**
	 * factorUsingConvertible creates a {@link ClientDataToJsonConverter} for the provided
	 * {@link ClientConvertible}
	 * 
	 * @param convertible
	 *            A {@link ClientConvertible} to create a converter for
	 * 
	 * @return returns a {@link ClientDataToJsonConverter} capable of converting the
	 *         {@link ClientConvertible} to a json String.
	 */
	ClientDataToJsonConverter factorUsingConvertible(ClientConvertible convertible);

	/**
	 * factorUsingBaseUrlAndConvertible creates a {@link ClientDataToJsonConverter} for the provided
	 * {@link ClientConvertible} using the provided baseUrl.
	 * 
	 * @param baseUrl
	 * @param convertible
	 * @return
	 */
	ClientDataToJsonConverter factorUsingBaseUrlAndConvertible(String baseUrl,
			ClientConvertible convertible);

}
