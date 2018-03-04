package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataRecordLink;
import se.uu.ub.cora.json.parser.JsonObject;

public class JsonToDataRecordLinkConverter extends JsonToDataGroupConverter {

	// private JsonObject jsonObject;

	public JsonToDataRecordLinkConverter(JsonObject jsonObject) {
		super(jsonObject);
		// this.jsonObject = jsonObject;
	}

	public static JsonToDataRecordLinkConverter forJsonObject(JsonObject jsonObject) {
		return new JsonToDataRecordLinkConverter(jsonObject);
	}

	@Override
	public ClientDataElement toInstance() {
		// validateOnlyCorrectKeysAtTopLevel();
		String nameInData = getNameInDataFromJsonObject();
		ClientDataRecordLink dataRecordLink = ClientDataRecordLink.withNameInData(nameInData);
		// addRepeatIdToGroup();
		// if (hasAttributes()) {
		// addAttributesToGroup();
		// }
		// addChildrenToGroup();
		return dataRecordLink;
	}

}
