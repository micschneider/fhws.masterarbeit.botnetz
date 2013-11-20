package fhws.masterarbeit.botnetz.server;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fhws.masterarbeit.botnetz.controller.WorkerController;

@ServerEndpoint("/botnetz")
public class BotnetzWorkerWebsocket
{
	private Session session;
	private static WorkerController controller;
	
	@OnOpen
	public void onOpen(Session session)
	{
		this.session = session;
		controller = WorkerController.getController();
		controller.sessionAdded(session);
	}//end method onOpen
	
	@OnMessage
	public void onMessage(String message)
	{
		controller.handleTextMessage(message);
	}//end method onMessage
	
	@OnError
	public void onError(Throwable throwable)
	{
		controller.handleError(throwable);
	}//end method onError
	
	@OnClose
	public void onClose()
	{
		controller.sessionRemoved(this.session);
	}//end method onClose
	
	public static void sendJavaScript(Session session, String code)
	{
		try 
		{
			session.getBasicRemote().sendText(code);
		}//end try
		catch (IOException e) 
		{
			controller.handleError(e);
		}//end catch IOException
	}//end method sendJavaScript

	public static void closeSession(Session session) throws IOException 
	{
		session.close();
	}//end method closeSession
}//end class BotnetzClientWebsocket
