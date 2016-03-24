package core.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Observable;

public class ClientConnection extends Observable implements Runnable {
	private Socket _socket;
	private BufferedReader _inputFromClient;
	private DataOutputStream _outputToClient;
	
	public static final String DISCONNECT_MESSAGE = "";
	
	public ClientConnection(Socket connectionSocket) {
		_socket = connectionSocket;
	}
	
	public void connect() throws IOException {
		_inputFromClient = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		_outputToClient = new DataOutputStream(_socket.getOutputStream());
	}
	
	public void disconnect() throws IOException {
		_socket.close();
	}
	
	public void write(String message) throws IOException {
		_outputToClient.writeBytes(message);
	}
	
	private void listen() throws IOException {
		String clientMessage = null;
		
		System.out.println("Listen from client...");
		clientMessage = _inputFromClient.readLine();
			
		if (clientMessage == null)
			throw new IOException("Read line null!");
		
		System.out.println("Message from client: " + clientMessage + "\n");
		notifyObservers(clientMessage);
	}
	
	@Override
	public void run() {
		boolean exit = false;
		while (!exit) {
			try {
				listen();
			} catch (IOException e) {
				e.printStackTrace();
				exit = true;
			}
		}
		notifyObservers(DISCONNECT_MESSAGE);
	}
}