package ircquest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Server {

	private String server;
	private int port;
	private String[] channels;
	private String nickname;

	public boolean running = false;

	public Server(String server, int port, String[] channels, String nickname) {
		this.server = server;
		this.port = port;
		this.channels = channels;
		this.nickname = nickname;
	}

	public void connect() throws IOException {
		running = true;

		Socket socket = new Socket(server, port);
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));   
		// Log on to the server.
		writer.write("NICK " + nickname + "\r\n");
		writer.write("USER " + "IRCQuest" + " 8 * : Bot\r\n");
		writer.flush();
		boolean joinChannel = false;

		String lineRead;
		while (running) {
			lineRead = reader.readLine();
			if (lineRead != null) {
				if (lineRead.startsWith("PING")) {
					writer.write("PONG " + lineRead.substring(5) + "\r\n");
					if (!joinChannel) {
						for (String channel : channels) {
							writer.write("JOIN " + channel + "\r\n");
							writer.flush();
						}
						joinChannel = true;
					}
				} else if (lineRead.contains("INVITE")) {
					String[] split = lineRead.split(":");
					writer.write("JOIN " + split[2] + "\r\n");
				} else {
					System.out.println(lineRead);
				}
				writer.flush();
			}
			
//			lineRead = console.readLine();
//			
//			if(lineRead != "" && lineRead != null){
//				writer.write("PRIVMSG " + "#ircquest :" + lineRead +"\r\n");
//				writer.flush();
//			}
		}
		socket.close();
	}

}