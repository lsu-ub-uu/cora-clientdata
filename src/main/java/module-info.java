module se.uu.ub.cora.clientdata {
	requires transitive se.uu.ub.cora.json;

	exports se.uu.ub.cora.clientdata;
	exports se.uu.ub.cora.clientdata.constructor;
	exports se.uu.ub.cora.clientdata.converter;
	exports se.uu.ub.cora.clientdata.converter.javatojson;
	exports se.uu.ub.cora.clientdata.converter.jsontojava;
}