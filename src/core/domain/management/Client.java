package core.domain.management;

import core.domain.game.PlayerInfo;
import core.net.ClientConnection;

public class Client {
	
	private long _id;
	private PlayerInfo _playerInfo;
	private Long _currentRoomId;
	private ClientConnection _clientSocket;
	private ClientState _clientState;
	
	public Client(long id, ClientConnection clientSocket) {
		_id = id;
		_playerInfo = null;
		_currentRoomId = null;
		_clientSocket = clientSocket;
		_clientSocket.start();
	}
	
	public PlayerInfo getPlayerInfo() {
		return _playerInfo;
	}
	
	public void setPlayerInfo(PlayerInfo playerInfo) {
		_playerInfo = playerInfo;
	}
	
	public ClientState getCurrentClientState() {
		return _clientState;
	}
	
	public long getId() {
		return _id;
	}
	
	public Long getCurrentRoomId() {
		return _currentRoomId;
	}
	
	public void setCurrentRoomId(Long id) {
		_currentRoomId = id;
	}
	
	public ClientConnection getClientSocket() {
		return _clientSocket;
	}
}
