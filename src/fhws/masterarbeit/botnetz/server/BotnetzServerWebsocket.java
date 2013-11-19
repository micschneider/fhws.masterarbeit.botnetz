package fhws.masterarbeit.botnetz.server;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fhws.masterarbeit.botnetz.data.ClientList;
import fhws.masterarbeit.botnetz.data.MyObserver;
import fhws.masterarbeit.botnetz.data.SessionListEncoder;

@ServerEndpoint(value = "/botnetzServer",
				encoders = {SessionListEncoder.class})
public class BotnetzServerWebsocket implements MyObserver
{
	private Session session;
	private ClientList clientList;
	
	@OnOpen
	public void open(Session session)
	{
		this.session = session;
		clientList = ClientList.getClientList();
		clientList.addObserver(this);
	}
	
	@OnMessage
	public void onMessage(String message)
	{
		
	}
	
	@OnClose
	public void close()
	{
		clientList.removeObserver(this);
	}

	@Override
	public void onUpdate(HashMap<String, Session> sessionList)
	{
		try 
		{
			this.session.getBasicRemote().sendObject(sessionList);
		}
		catch (EncodeException e) 
		{
			System.out.println("EncodeException: " + e.getStackTrace());
		} 
		catch (IOException ioe) 
		{
			System.out.println("IOException: " + ioe.getStackTrace());
		}
	}
}
