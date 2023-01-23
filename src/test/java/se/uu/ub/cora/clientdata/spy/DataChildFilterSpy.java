package se.uu.ub.cora.clientdata.spy;

import java.util.Set;

import se.uu.ub.cora.clientdata.ClientDataChild;
import se.uu.ub.cora.clientdata.ClientDataChildFilter;

public class DataChildFilterSpy implements ClientDataChildFilter {

	@Override
	public void addAttributeUsingNameInDataAndPossibleValues(String nameInData,
			Set<String> possibleValues) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean childMatches(ClientDataChild child) {
		// TODO Auto-generated method stub
		return false;
	}

}
