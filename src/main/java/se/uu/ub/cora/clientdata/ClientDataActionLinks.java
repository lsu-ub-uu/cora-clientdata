package se.uu.ub.cora.clientdata;

import java.util.Map;
import java.util.TreeMap;

public class ClientDataActionLinks implements ClientDataElement {

	private Map<String, ActionLink> actionLinks = new TreeMap<>();

	@Override
	public String getNameInData() {
		return null;
	}

	public void addActionLink(String key, ActionLink actionLink) {
		actionLinks.put(key, actionLink);
	}

	public Map<String, ActionLink> getActionLinks() {
		return actionLinks;
	}

	public ActionLink getActionLink(String key) {
		return actionLinks.get(key);
	}

	public void setActionLinks(Map<String, ActionLink> actionLinks) {
		this.actionLinks = actionLinks;
	}
}
