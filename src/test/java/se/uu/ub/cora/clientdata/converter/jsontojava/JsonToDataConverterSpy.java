package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.*;
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
		}else if (jsonValue.containsKey("requestMethod")) {
			returnedElement = ActionLink.withAction(Action.READ);
		} else {
			String nameInData = jsonValue.getValueAsJsonString("name").getStringValue();
			returnedElement = ClientDataGroup.withNameInData(nameInData);
		}
		return returnedElement;
	}

}
