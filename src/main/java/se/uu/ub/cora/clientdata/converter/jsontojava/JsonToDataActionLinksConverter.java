package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.json.parser.JsonObject;

public class JsonToDataActionLinksConverter implements JsonToDataConverter {

	public JsonToDataActionLinksConverter(JsonObject jsonObject) {
		// TODO Auto-generated constructor stub
	}

	static JsonToDataActionLinksConverter forJsonObject(JsonObject jsonObject) {
		return new JsonToDataActionLinksConverter(jsonObject);
	}

	@Override
	public ClientDataElement toInstance() {
		// TODO Auto-generated method stub
		return null;
	}

}
