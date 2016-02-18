package org.osrspvp.model;

public class DialogueHandler {

	private Client c;

	public DialogueHandler(Client client) {
		this.c = client;
	}

	/**
	 * Handles all talking
	 *
	 * @param dialogue
	 *            The dialogue you want to use
	 * @param npcId
	 *            The npc id that the chat will focus on during the chat
	 */
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch (dialogue) {
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		}
	}

	/*
	 * Information Box
	 */

	public void sendStartInfo(String text, String text1, String text2,
			String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}

	public void lineText(String text) {
		c.getPA().sendFrame126(text, 357);
		c.getPA().sendFrame164(356);
	}

	public void lineText2(String text, String text2) {
		c.getPA().sendFrame126(text, 360);
		c.getPA().sendFrame126(text2, 361);
		c.getPA().sendFrame164(359);
	}

	public void lineText3(String text, String text2, String text3) {
		c.getPA().sendFrame126(text, 364);
		c.getPA().sendFrame126(text2, 365);
		c.getPA().sendFrame126(text3, 366);
		c.getPA().sendFrame164(363);
	}

	public void lineText4(String text, String text2, String text3, String text4) {
		c.getPA().sendFrame126(text, 369);
		c.getPA().sendFrame126(text2, 370);
		c.getPA().sendFrame126(text3, 371);
		c.getPA().sendFrame126(text4, 372);
		c.getPA().sendFrame164(368);
	}

	public void sendItemChat1(String header, String one, int item, int zoom) {
		c.getPA().sendFrame246(4883, zoom, item);
		c.getPA().sendFrame126(header, 4884);
		c.getPA().sendFrame126(one, 4885);
		c.getPA().sendFrame164(4882);
	}

	public void sendItemChat2(String header, String one, String two, int item,
			int zoom) {
		c.getPA().sendFrame246(4888, zoom, item);
		c.getPA().sendFrame126(header, 4889);
		c.getPA().sendFrame126(one, 4890);
		c.getPA().sendFrame126(two, 4891);
		c.getPA().sendFrame164(4887);
	}

	public void sendItemChat3(String header, String one, String two,
			String three, int item, int zoom) {
		c.getPA().sendFrame246(4894, zoom, item);
		c.getPA().sendFrame126(header, 4895);
		c.getPA().sendFrame126(one, 4896);
		c.getPA().sendFrame126(two, 4897);
		c.getPA().sendFrame126(three, 4898);
		c.getPA().sendFrame164(4893);
	}

	public void sendItemChat4(String header, String one, String two,
			String three, String four, int item, int zoom) {
		c.getPA().sendFrame246(4901, zoom, item);
		c.getPA().sendFrame126(header, 4902);
		c.getPA().sendFrame126(one, 4903);
		c.getPA().sendFrame126(two, 4904);
		c.getPA().sendFrame126(three, 4905);
		c.getPA().sendFrame126(four, 4906);
		c.getPA().sendFrame164(4900);
	}

	/*
	 * Options
	 */

	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}

	public void sendOption3(String text, String text2, String text3) {
		c.getPA().sendFrame126(text, 2471);
		c.getPA().sendFrame126(text2, 2472);
		c.getPA().sendFrame126(text3, 2473);
		c.getPA().sendFrame164(2469);
	}

	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}

	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */

	private void sendStatement(String s) { // 1 line click here to continue chat
		// box interface
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}

	/*
	 * Npc Chatting
	 */

	private void sendNpcChat4(String s, String s1, String s2, String s3,
			int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}

	/*
	 * Player Chating Back
	 */
}
