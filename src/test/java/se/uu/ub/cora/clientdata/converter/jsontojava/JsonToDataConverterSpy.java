package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.ClientDataActionLinks;
import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.clientdata.ClientDataGroup;
import se.uu.ub.cora.json.parser.JsonObject;

public class JsonToDataConverterSpy implements JsonToDataConverter {

	public JsonObject jsonValue;
	public ClientDataElement returnedElement;

	public JsonToDataConverterSpy(JsonObject jsonValue) {
		this.jsonValue = jsonValue;
	}

	@Override
	public ClientDataElement toInstance() {
		if (jsonValue.containsKey("read")) {
			returnedElement = new ClientDataActionLinks();
		} else {
			String nameinData = jsonValue.getValueAsJsonString("name").getStringValue();
			returnedElement = ClientDataGroup.withNameInData(nameinData);
		}
		return returnedElement;
	}

}
