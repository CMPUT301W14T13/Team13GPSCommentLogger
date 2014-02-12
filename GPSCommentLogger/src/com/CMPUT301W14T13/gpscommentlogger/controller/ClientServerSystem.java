package com.CMPUT301W14T13.gpscommentlogger.controller;

/// @author kyomaru
/// This class handles the task of creating a client and attaching it to a server.
/// Since we have no server, we are simulating one on the client machine.
/// The connection is therefore simply reference passing.
/// No information is passed to the server regarding the client for authentication purposes,
/// so there is no "Client" class storing these details, because it would be empty
/// 

public class ClientServerSystem
{	
	private static final ClientServerSystem system = new ClientServerSystem();
	private ClientController client;
	private ServerController server;
	
	private ClientServerSystem()
	{
		client = new ClientController();
		server = new ServerController();
		client.setServer(server);
		server.setClient(client);
		client.init();
		server.init();
	}
	
	public ClientServerSystem getInstance()
	{
		return system;
	}
	
	public ClientController getClient()
	{
		return client;
	}
	
	public ServerController getServer()
	{
		return server;
	}
}
