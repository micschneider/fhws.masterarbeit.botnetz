package fhws.masterarbeit.botnetz.server;

import java.io.IOException;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import fhws.masterarbeit.botnetz.data.ClientList;

@ServerEndpoint("/botnetz")
public class BotnetzClientWebsocket 
{
	private Session session;
	private ClientList clientList;
	
	@OnOpen
	public void open(Session session, EndpointConfig epc) throws IOException
	{
		this.session = session;
		this.clientList = ClientList.getClientList();
		this.clientList.addSession(this.session);
	}
	
	@OnMessage
	public void onMessage(String message) throws IOException
	{
		//session.getBasicRemote().sendText("alert('" + message + "')");
	}
	
	@OnError
	public void handleError(Throwable t)
	{
		System.out.println("Exception: " + t.getStackTrace() + t.getCause());
	}
	
	@OnClose
	public void onClose() throws IOException
	{
		clientList.removeSession(this.session);
	}
}
