module se.uu.ub.cora.clientdata {
	requires transitive se.uu.ub.cora.json;

	exports se.uu.ub.cora.clientdata;
	exports se.uu.ub.cora.clientdata.converter;

	uses se.uu.ub.cora.clientdata.ClientDataFactory;

	uses se.uu.ub.cora.clientdata.converter.JsonToDataConverterFactory;
	uses se.uu.ub.cora.clientdata.converter.DataToJsonConverterFactoryCreator;
}