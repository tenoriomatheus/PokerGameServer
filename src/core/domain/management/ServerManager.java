package core.domain.management;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import core.domain.messageHandler.EchoReciver;
import core.domain.messageHandler.UpdateHandler;
import core.net.ClientConnection;
import core.service.NumberGenerator;

public class ServerManager extends Thread {

	private java.net.ServerSocket _welcomeSocket;
	private Map<Long, Client> _listOfClients;
	private Map<Long, Room> _listOfRooms;
	private NumberGenerator _numGenClient;
	private NumberGenerator _numGenRoom;
	
	public ServerManager() throws IOException {
		_listOfClients = new HashMap<Long, Client>();
		_listOfRooms = new HashMap<Long, Room>();
		_numGenClient = new NumberGenerator();
		_numGenRoom = new NumberGenerator();
		
		_welcomeSocket = new java.net.ServerSocket(1095);
		this.start();
	}
	
	
	
	private void createClientHandlers(Client client) {
		client.addMessageHandler(new EchoReciver(client.getClientSocket()));
		client.addMessageHandler(new UpdateHandler(client.getClientSocket()));
	}
	
	@Override
	public void run() {
		Socket connectionSocket = null;
		ClientConnection t = null;
		Client c = null;
		
		while (true) {
			try {
				System.out.println("Listen at port 1095...");
				connectionSocket = _welcomeSocket.accept();
				
				System.out.println("Trying connect with the client...");
				t = new ClientConnection(connectionSocket);
				t.connect();
				
				System.out.println("Gen a unique client id...");
				long id = _numGenClient.genId();
				
				System.out.println("Creating a client instance...");
				//Criando um client
				c = new Client(id, t);
				//Add all handlers necessary to client
				createClientHandlers(c);
				//Add um client na lista de clientes
				_listOfClients.put(id, c);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
