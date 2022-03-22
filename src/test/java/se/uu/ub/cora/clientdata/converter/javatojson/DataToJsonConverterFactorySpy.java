package se.uu.ub.cora.clientdata.converter.javatojson;

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.json.builder.JsonBuilderFactory;

public class DataToJsonConverterFactorySpy implements DataToJsonConverterFactory {

	public int calledNumOfTimes = 0;
	public List<Convertible> dataElements = new ArrayList<>();

	@Override
	public DataToJsonConverter createForClientDataElement(JsonBuilderFactory factory,
			Convertible clientDataElement) {
		calledNumOfTimes++;
		dataElements.add(clientDataElement);
		return new DataToJsonConverterSpy(clientDataElement);
	}

	@Override
	public DataToJsonConverter createForClientDataElementIncludingActionLinks(
			JsonBuilderFactory factory, Convertible clientDataElement, boolean includeActionLinks) {
		// TODO Auto-generated method stub
		return null;
	}

}
