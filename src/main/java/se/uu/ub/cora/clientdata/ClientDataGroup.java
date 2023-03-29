package se.uu.ub.cora.clientdata;

import java.util.List;

import se.uu.ub.cora.clientdata.converter.javatojson.Convertible;

public interface ClientDataGroup extends ClientDataElement, ClientData, Convertible {

	String getRepeatId();

	List<ClientDataElement> getChildren();

	void removeFirstChildWithNameInData(String linkedRepeatId);

	boolean containsChildWithNameInData(String linkedRepeatId);

	ClientDataElement getFirstChildWithNameInData(String linkedRepeatId);

}