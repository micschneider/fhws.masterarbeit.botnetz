package fhws.masterarbeit.botnetz.server;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fhws.masterarbeit.botnetz.data.SessionMonitor;
import fhws.masterarbeit.botnetz.data.MyObserver;
import fhws.masterarbeit.botnetz.data.SessionListEncoder;

@ServerEndpoint(value = "/botnetzServer",
				encoders = {SessionListEncoder.class})
public class BotnetzServerWebsocket implements MyObserver
{
	private Session session;
	private SessionMonitor sessionMonitor;
	
	@OnOpen
	public void onOpen(Session session)
	{
		this.session = session;
		sessionMonitor = SessionMonitor.getSessionMonitor();
		sessionMonitor.addObserver(this);
	}//end method onOpen
	
	@OnMessage
	public void onMessage(String message)
	{	
	}//end method onMessage
	
	@OnClose
	public void onClose()
	{
		sessionMonitor.removeObserver(this);
		try 
		{
			session.close();
		}//end try 
		catch (IOException e) 
		{
			System.out.println("IOException" + e.getStackTrace());
		}//end catch IOException
	}//end method onClose

	@Override
	public void onUpdate(HashMap<String, Session> sessionList)
	{
		try 
		{
			this.session.getBasicRemote().sendObject(sessionList);
		}//end try
		catch (EncodeException e) 
		{
			System.out.println("EncodeException: " + e.getStackTrace());
		}//end catch EncodeException
		catch (IOException ioe) 
		{
			System.out.println("IOException: " + ioe.getStackTrace());
		}//end catch IOException
	}//end method onUpdate
}//end class BotnetzServerWebsocket
