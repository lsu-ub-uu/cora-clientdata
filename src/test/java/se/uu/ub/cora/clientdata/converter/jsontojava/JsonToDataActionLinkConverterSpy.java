package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.Action;
import se.uu.ub.cora.clientdata.ActionLink;
import se.uu.ub.cora.clientdata.ClientData;
import se.uu.ub.cora.json.parser.JsonObject;

public class JsonToDataActionLinkConverterSpy implements JsonToDataActionLinkConverter {

	public JsonObject jsonValue;
	public ClientData returnedElement;

	public JsonToDataActionLinkConverterSpy(JsonObject jsonValue) {
		this.jsonValue = jsonValue;
	}

	@Override
	public ClientData toInstance() {
		returnedElement = ActionLink.withAction(Action.READ);
		return returnedElement;
	}
}
