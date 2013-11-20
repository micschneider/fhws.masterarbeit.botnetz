package fhws.masterarbeit.botnetz.model;

import java.util.HashMap;

import javax.websocket.Session;

public class SessionMonitor implements MyObservable
{
	private static HashMap<String, Session> workerSessionList = new HashMap<String, Session>();
	private static HashMap<String, Session> managerSessionList = new HashMap<String, Session>();
	private static SessionMonitor sessionMonitor = new SessionMonitor();
	
	private SessionMonitor()
	{
	}//end constructor
	
	public static SessionMonitor getSessionMonitor()
	{
		return sessionMonitor;
	}//end method getSessionMonitor
	
	public HashMap<String, Session> getWorkerSessionList()
	{
		return workerSessionList;
	}//end method getWorkerSessionList
	
	public HashMap<String, Session> getManagerSessionList()
	{
		return managerSessionList;
	}//end method getManagerSessionList
	
	public void addWorkerSession(Session session)
	{
		workerSessionList.put(session.getId(), session);
		notifyObservers(workerSessionList);
	}//end method addSession
	
	public void removeWorkerSession(Session session)
	{
		workerSessionList.remove(session.getId());
		notifyObservers(workerSessionList);
	}//end method removeSession
	
	public void addManagerSession(Session session)
	{
		managerSessionList.put(session.getId(), session);
	}//end method addSession
	
	public void removeManagerSession(Session session)
	{
		managerSessionList.remove(session.getId());
	}//end method removeSession

	@Override
	public void notifyObservers(HashMap<String, Session> sessionList) 
	{
		for(MyObserver myObserver : myObservers)
		{
			myObserver.onUpdate(sessionList);
		}//end for each
	}//end method notifyObservers

	@Override
	public void addObserver(MyObserver myObserver) 
	{
		myObservers.add(myObserver);
		notifyObservers(workerSessionList);
	}//end method addObserver

	@Override
	public void removeObserver(MyObserver myObserver) 
	{
		myObservers.remove(myObserver);
	}//end method removeObserver
}//end class SessionMonitor
