/*
 * Copyright 2018 Uppsala University Library
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
package se.uu.ub.cora.clientdata.constructor;

import java.util.Map;
import java.util.Map.Entry;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataGroup;

public class TextConstructor extends DataConstructor {

	public TextConstructor(String dataDivider) {
		this.dataDivider = dataDivider;
	}

	public ClientDataGroup constructTextUsingTextIdAndDefaultSvText(String textId,
			String defaultSvText) {
		ClientDataGroup textGroup = ClientDataGroup.withNameInData("text");
		textGroup.addChild(createRecordInfoGroupForId(textId));
		textGroup.addChild(createDefaultSvTextPartForText(defaultSvText));
		return textGroup;
	}

	private ClientDataGroup createDefaultSvTextPartForText(String defaultSvText) {
		return createTextPartUsingTypeAndLangAndText("default", "sv", defaultSvText);
	}

	private ClientDataGroup createTextPartUsingTypeAndLangAndText(String type, String lang,
			String text) {
		ClientDataGroup textPart = ClientDataGroup.withNameInData("textPart");
		textPart.addAttributeByIdWithValue("type", type);
		textPart.addAttributeByIdWithValue("lang", lang);
		textPart.addChild(createAtomicForText(text));
		return textPart;
	}

	private ClientDataAtomic createAtomicForText(String text) {
		return ClientDataAtomic.withNameInDataAndValue("text", text);
	}

	public ClientDataGroup constructTextUsingTextIdAndDefaultSvTextAndAlternativeTexts(
			String textId, String defaultSvText, Map<String, String> alternativeTexts) {
		ClientDataGroup textGroup = constructTextUsingTextIdAndDefaultSvText(textId, defaultSvText);
		addAlternativeTextsToTextGroup(alternativeTexts, textGroup);
		return textGroup;
	}

	private void addAlternativeTextsToTextGroup(Map<String, String> alternativeTexts,
			ClientDataGroup textGroup) {
		for (Entry<String, String> alternativeText : alternativeTexts.entrySet()) {
			textGroup.addChild(createAlternativeTextPartForText(alternativeText));
		}
	}

	private ClientDataGroup createAlternativeTextPartForText(
			Entry<String, String> alternativeText) {
		return createTextPartUsingTypeAndLangAndText("alternative", alternativeText.getKey(),
				alternativeText.getValue());
	}

}
