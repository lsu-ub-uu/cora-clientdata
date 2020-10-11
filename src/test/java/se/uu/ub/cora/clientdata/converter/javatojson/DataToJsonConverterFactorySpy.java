package se.uu.ub.cora.clientdata.converter.javatojson;

import java.util.ArrayList;
import java.util.List;

import se.uu.ub.cora.clientdata.ClientDataElement;

public class DataToJsonConverterFactorySpy implements DataToJsonConverterFactory {

	public int calledNumOfTimes = 0;
	public List<ClientDataElement> dataElements = new ArrayList<>();

	@Override
	public DataToJsonConverter createForClientDataElement(ClientDataElement clientDataElement) {
		calledNumOfTimes++;
		dataElements.add(clientDataElement);
		return new DataToJsonConverterSpy(clientDataElement);
	}

	@Override
	public DataToJsonConverter createForClientDataElementIncludingActionLinks(ClientDataElement clientDataElement,
			boolean includeActionLinks) {
		// TODO Auto-generated method stub
		return null;
	}

}
