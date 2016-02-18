package org.osrspvp.core.cycle.impl;

import java.io.File;

import org.osrspvp.core.cycle.CycleEvent;
import org.osrspvp.core.cycle.CycleEventContainer;
import org.osrspvp.model.Client;
import org.osrspvp.model.player.PlayerHandler;
import org.osrspvp.util.Misc;

public class GlobalHelpEvent extends CycleEvent {

	private static File directory = new File("./Data/characters/");

	private static int getTotalCharacters() {
		File[] fileDirectory = directory.listFiles();
		if(fileDirectory == null) {
			return 0;
		}
		return fileDirectory.length;
	}

	@Override
	public void execute(CycleEventContainer container) {
		String msg[] = {
				"@cr6@@red@You can help the server by voting at @blu@::vote",
				"@cr6@@red@You can claim your vote reward by typing @blu@::claim",
				"@cr6@@red@Did you know you get more PVP points in the @blu@wilderness@bla@?",
				"@cr6@@red@Did you know that you can visit our forums at @blu@www.dragonicpk.com",
				"@cr6@@red@You can help the server by donating at @blu@::donate",
				"@cr6@@red@You can type @blu@::commands@red@ to find all working commands.",
				"@cr6@@red@Di you know? There are @blu@" + Misc.formatNumbers(getTotalCharacters())
						+ " @red@in-game characters!" };
		for (int i = 0; i < PlayerHandler.players.length; i++) {
			if (PlayerHandler.players[i] == null)
				continue;
			((Client) PlayerHandler.players[i]).sendMessage(msg[(int) (Math
					.random() * msg.length)]);
		}
	}

}
