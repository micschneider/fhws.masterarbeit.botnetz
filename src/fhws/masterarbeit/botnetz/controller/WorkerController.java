package fhws.masterarbeit.botnetz.controller;

import javax.websocket.Session;

import fhws.masterarbeit.botnetz.model.ConsoleWriter;
import fhws.masterarbeit.botnetz.model.SessionMonitor;
import fhws.masterarbeit.botnetz.server.BotnetzWorkerWebsocket;

public class WorkerController 
{
	private static WorkerController controller = new WorkerController();
	private SessionMonitor sessionMonitor;
	private ConsoleWriter consoleWriter;
	
	private WorkerController()
	{
		this.sessionMonitor = SessionMonitor.getSessionMonitor();
		this.consoleWriter = ConsoleWriter.getConsoleWriter();
	}//end constructor
	
	public static WorkerController getController()
	{
		return controller;
	}//end method getController

	public void sessionAdded(Session session) 
	{
		this.sessionMonitor.addWorkerSession(session);
	}//end method sessionAdded

	public void sessionRemoved(Session session) 
	{
		this.sessionMonitor.removeWorkerSession(session);
				
		try 
		{
			BotnetzWorkerWebsocket.closeSession(session);
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
		consoleWriter.writeMessageToConsole(message);
	}//end method handleTextMessage
}//end class WorkerController

