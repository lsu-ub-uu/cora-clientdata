package se.uu.ub.cora.clientdata.converter.jsontojava;

import se.uu.ub.cora.clientdata.ClientDataAtomic;
import se.uu.ub.cora.clientdata.ClientDataGroupImp;
import se.uu.ub.cora.clientdata.converter.javatojson.Convertible;
import se.uu.ub.cora.json.parser.JsonObject;

public class JsonToDataConverterSpy implements JsonToDataConverter {

	public JsonObject jsonValue;
	public Convertible returnedElement;

	public JsonToDataConverterSpy(JsonObject jsonValue) {
		this.jsonValue = jsonValue;
	}

	@Override
	public Convertible toInstance() {
		if (jsonValue.containsKey("name") && jsonValue.containsKey("value")) {
			returnedElement = ClientDataAtomic.withNameInDataAndValue("atomicNameInData",
					"atomicValue");
		} else {
			String nameInData = jsonValue.getValueAsJsonString("name").getStringValue();
			returnedElement = ClientDataGroupImp.withNameInData(nameInData);
		}
		return returnedElement;
	}
}
