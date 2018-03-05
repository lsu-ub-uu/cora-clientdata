package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.Action;
import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientDataActionLinks;
import se.uu.ub.cora.clientdata.ClientDataAtomic;
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
		} else if (jsonValue.containsKey("requestMethod")) {
			returnedElement = ActionLink.withAction(Action.READ);
		} else if (jsonValue.containsKey("name") && jsonValue.containsKey("value")) {
			returnedElement = ClientDataAtomic.withNameInDataAndValue("atomicNameInData",
					"atomicValue");
		} else {
			String nameInData = jsonValue.getValueAsJsonString("name").getStringValue();
			returnedElement = ClientDataGroup.withNameInData(nameInData);
		}
		return returnedElement;
	}
}
