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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import se.uu.ub.cora.clientdata.ClientDataAttribute;
import se.uu.ub.cora.clientdata.ClientDataGroup;

public class TextConstructorTest {
	private TextConstructor textConstructor;
	private String textId = "someTextId";
	private String dataDivider = "someDataDivider";
	private String defaultSvText = "some default swedish text";
	private Map<String, String> alternativeTexts = new HashMap<>();
	private String alternativeEnText = "some alternative english text";

	@BeforeMethod
	public void beforeMethod() {
		textConstructor = new TextConstructor(dataDivider);
		alternativeTexts.put("en", alternativeEnText);
	}

	@Test
	public void testConstructText() throws Exception {
		ClientDataGroup createdTextGroup = textConstructor
				.constructTextUsingTextIdAndDefaultSvTextAndAlternativeTexts(textId, defaultSvText,
						alternativeTexts);
		assertEquals(createdTextGroup.getNameInData(), "text");
		// assertEquals(createdTextGroup.getChildren().size(), 2);
	}

	@Test
	public void testCorrectRecordInfo() throws Exception {
		ClientDataGroup createdTextGroup = textConstructor
				.constructTextUsingTextIdAndDefaultSvText(textId, defaultSvText);
		ClientDataGroup recordInfo = createdTextGroup.getFirstGroupWithNameInData("recordInfo");
		assertNotNull(recordInfo);
		assertEquals(recordInfo.getFirstAtomicValueWithNameInData("id"), textId);
		ClientDataGroup dataDividerGroup = recordInfo.getFirstGroupWithNameInData("dataDivider");
		assertNotNull(dataDividerGroup);
		assertEquals(dataDividerGroup.getFirstAtomicValueWithNameInData("linkedRecordType"),
				"system");
		assertEquals(dataDividerGroup.getFirstAtomicValueWithNameInData("linkedRecordId"),
				dataDivider);
		assertEquals(recordInfo.getChildren().size(), 2);
	}

	@Test
	public void testCorrectDefaultText() throws Exception {
		ClientDataGroup createdTextGroup = textConstructor
				.constructTextUsingTextIdAndDefaultSvText(textId, defaultSvText);

		assertDefaultSvTextIsCorrect(createdTextGroup);
		assertEquals(createdTextGroup.getChildren().size(), 2);
	}

	private void assertDefaultSvTextIsCorrect(ClientDataGroup createdTextGroup) {
		ClientDataGroup defaultTextPart = createdTextGroup.getFirstGroupWithNameInDataAndAttributes(
				"textPart", ClientDataAttribute.withNameInDataAndValue("type", "default"),
				ClientDataAttribute.withNameInDataAndValue("lang", "sv"));
		String svText = defaultTextPart.getFirstAtomicValueWithNameInData("text");
		assertEquals(svText, defaultSvText);
	}

	@Test
	public void testCorrectAlternativeText() throws Exception {
		ClientDataGroup createdTextGroup = textConstructor
				.constructTextUsingTextIdAndDefaultSvTextAndAlternativeTexts(textId, defaultSvText,
						alternativeTexts);

		assertDefaultSvTextIsCorrect(createdTextGroup);
		assertAlternativeEnTextIsCorrect(createdTextGroup);
		assertEquals(createdTextGroup.getChildren().size(), 3);
	}

	private void assertAlternativeEnTextIsCorrect(ClientDataGroup createdTextGroup) {
		ClientDataGroup alternativeEnTextPart = createdTextGroup
				.getFirstGroupWithNameInDataAndAttributes("textPart",
						ClientDataAttribute.withNameInDataAndValue("type", "alternative"),
						ClientDataAttribute.withNameInDataAndValue("lang", "en"));
		String enText = alternativeEnTextPart.getFirstAtomicValueWithNameInData("text");
		assertEquals(enText, alternativeEnText);
	}

	@Test
	public void testCorrectTwoAlternativeTexts() throws Exception {
		String spanishText = "spanish text";
		alternativeTexts.put("es", spanishText);
		ClientDataGroup createdTextGroup = textConstructor
				.constructTextUsingTextIdAndDefaultSvTextAndAlternativeTexts(textId, defaultSvText,
						alternativeTexts);

		assertDefaultSvTextIsCorrect(createdTextGroup);
		assertAlternativeEnTextIsCorrect(createdTextGroup);
		assertAlternativeEsTextIsCorrect(spanishText, createdTextGroup);
		assertEquals(createdTextGroup.getChildren().size(), 4);
	}

	private void assertAlternativeEsTextIsCorrect(String spanishText,
			ClientDataGroup createdTextGroup) {
		ClientDataGroup alternativeEsTextPart = createdTextGroup
				.getFirstGroupWithNameInDataAndAttributes("textPart",
						ClientDataAttribute.withNameInDataAndValue("type", "alternative"),
						ClientDataAttribute.withNameInDataAndValue("lang", "es"));
		String esText = alternativeEsTextPart.getFirstAtomicValueWithNameInData("text");
		assertEquals(esText, spanishText);
	}

}
