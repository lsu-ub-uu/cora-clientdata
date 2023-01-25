package se.uu.ub.cora.clientdata.converter;

import se.uu.ub.cora.clientdata.starter.JsonToClientDataConverterModuleStarter;

public class JsonToDataConverterModuleStarterSpy implements JsonToClientDataConverterModuleStarter {

	public boolean startWasCalled = false;

	@Override
	public void startUsingConverterFactoryImplementations(
			Iterable<JsonToClientDataConverterFactory> converterFactoryImplementations) {
		startWasCalled = true;
	}

	@Override
	public JsonToClientDataConverterFactory getJsonToDataConverterFactory() {
		return new JsonToDataConverterFactorySpy();
	}

}
