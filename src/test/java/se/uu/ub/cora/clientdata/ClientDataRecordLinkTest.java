package se.uu.ub.cora.clientdata;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ClientDataRecordLinkTest {
	private ClientDataRecordLink recordLink;

	@BeforeMethod
	public void setUp() {
		recordLink = ClientDataRecordLink.withNameInData("aNameInData");
		ClientDataAtomic linkedRecordType = ClientDataAtomic
				.withNameInDataAndValue("linkedRecordType", "aLinkedRecordType");
		recordLink.addChild(linkedRecordType);

		ClientDataAtomic linkedRecordId = ClientDataAtomic.withNameInDataAndValue("linkedRecordId",
				"aLinkedRecordId");
		recordLink.addChild(linkedRecordId);
	}

	@Test
	public void testInit() {
		assertEquals(recordLink.getChildren().size(), 2);
		assertNotNull(recordLink.getFirstChildWithNameInData("linkedRecordType"));
		assertTrue(recordLink.containsChildWithNameInData("linkedRecordId"));
	}

	@Test
	public void testInitWithNameInDataAndTypeAndId() throws Exception {
		String nameInData = "someNameInData";
		String linkedType = "someLinkedRecordType";
		String linkedId = "someLinkedRecordId";
		ClientDataRecordLink clientDataRecordLink = ClientDataRecordLink
				.withNameInDataAndTypeAndId(nameInData, linkedType, linkedId);
		assertEquals(clientDataRecordLink.getNameInData(), "someNameInData");
		assertEquals(clientDataRecordLink.getFirstAtomicValueWithNameInData("linkedRecordType"),
				"someLinkedRecordType");
		assertEquals(clientDataRecordLink.getFirstAtomicValueWithNameInData("linkedRecordId"),
				"someLinkedRecordId");
	}

	@Test
	public void testWithActionLinks() {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		recordLink.addActionLink("read", actionLink);
		assertEquals(recordLink.getActionLink("read"), actionLink);
		assertEquals(recordLink.getActionLinks().get("read"), actionLink);
		assertNull(recordLink.getActionLink("notAnAction"));
	}

	@Test
	public void testWithRepeatId() {
		recordLink.setRepeatId("x2");
		assertEquals(recordLink.getRepeatId(), "x2");
	}

	@Test
	public void testSetActionLinks() throws Exception {
		ActionLink actionLink = ActionLink.withAction(Action.READ);
		Map<String, ActionLink> actionLinks = new HashMap<>();
		actionLinks.put("read", actionLink);
		recordLink.setActionLinks(actionLinks);
		assertEquals(recordLink.getActionLink("read"), actionLink);
		assertEquals(recordLink.getActionLinks().get("read"), actionLink);
		assertNull(recordLink.getActionLink("notAnAction"));
	}
}
