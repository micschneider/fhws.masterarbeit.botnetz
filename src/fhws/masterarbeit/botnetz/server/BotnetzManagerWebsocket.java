package fhws.masterarbeit.botnetz.server;

import java.io.IOException;
import java.util.HashMap;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fhws.masterarbeit.botnetz.controller.ManagerController;
import fhws.masterarbeit.botnetz.model.SessionListEncoder;

@ServerEndpoint(value = "/botnetzServer",
				encoders = {SessionListEncoder.class})
public class BotnetzManagerWebsocket
{
	private Session session;
	private ManagerController controller;
	
	@OnOpen
	public void onOpen(Session session)
	{
		this.session = session;
		this.controller = ManagerController.getController();
		this.controller.sessionAdded(this.session);
	}//end method onOpen
	
	@OnMessage
	public void onMessage(String message)
	{
		this.controller.handleTextMessage(message);
	}//end method onMessage
	
	@OnClose
	public void onClose()
	{
		this.controller.sessionRemoved(this.session);
		//this.session.close();
	}//end method onClose
	
	@OnError
	public void onError(Throwable throwable)
	{
		this.controller.handleError(throwable);
	}//end method onError
	
	public static void sendWorkerSessionList(Session session, HashMap<String, Session> sessionList) throws IOException, EncodeException
	{
		session.getBasicRemote().sendObject(sessionList);
	}//end method sendWorkerSessionList
	
	public static void closeSession(Session session) throws IOException
	{
		session.close();
	}//end method closeSession
}//end class BotnetzServerWebsocket
