package ircquest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

public class IRCQuest {
	BufferedReader reader;
	BufferedWriter writer;
	Socket socket;

	public void start() {
		Server server = new Server("irc.esper.net", 5555, new String[] { "#ircquest" }, "IRCQuestBot");
		try {
			server.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
