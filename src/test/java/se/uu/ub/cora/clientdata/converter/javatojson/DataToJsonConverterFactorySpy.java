package se.uu.ub.cora.clientdata.converter.javatojson;

import java.util.ArrayList;
import java.util.List;

public class DataToJsonConverterFactorySpy implements DataToJsonConverterFactory {

	public int calledNumOfTimes = 0;
	public List<Convertible> dataElements = new ArrayList<>();

	@Override
	public DataToJsonConverter createForClientDataElement(Convertible clientDataElement) {
		calledNumOfTimes++;
		dataElements.add(clientDataElement);
		return new DataToJsonConverterSpy(clientDataElement);
	}

	@Override
	public DataToJsonConverter createForClientDataElementIncludingActionLinks(
			Convertible clientDataElement, boolean includeActionLinks) {
		// TODO Auto-generated method stub
		return null;
	}

}
