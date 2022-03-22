package se.uu.ub.cora.clientdata.converter.javatojson;

import se.uu.ub.cora.clientdata.ClientDataElement;
import se.uu.ub.cora.json.builder.JsonBuilderFactory;
import se.uu.ub.cora.json.builder.JsonObjectBuilder;
import se.uu.ub.cora.json.builder.org.OrgJsonBuilderFactoryAdapter;

public class DataToJsonConverterSpy extends DataToJsonConverter {

	public Convertible clientDataElement;

	public DataToJsonConverterSpy(Convertible clientDataElement) {
		this.clientDataElement = clientDataElement;
	}

	@Override
	protected JsonObjectBuilder toJsonObjectBuilder() {
		JsonBuilderFactory jsonBuilderFactory = new OrgJsonBuilderFactoryAdapter();
		JsonObjectBuilder jsonObjectBuilder = jsonBuilderFactory.createObjectBuilder();

		jsonObjectBuilder.addKeyString("name",
				((ClientDataElement) clientDataElement).getNameInData());
		return jsonObjectBuilder;
	}

}
