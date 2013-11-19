package fhws.masterarbeit.botnetz.data;

import java.util.HashMap;

import javax.websocket.Session;

public class SessionMonitor implements MyObservable
{
	private static HashMap<String, Session> sessionList = new HashMap<String, Session>();
	private static SessionMonitor sessionMonitor = new SessionMonitor();
	
	private SessionMonitor()
	{
	}//end constructor
	
	public static SessionMonitor getSessionMonitor()
	{
		return sessionMonitor;
	}//end method getSessionMonitor
	
	public HashMap<String, Session> getSessionList()
	{
		return sessionList;
	}//end method getSessionList
	
	public void addSession(Session session)
	{
		sessionList.put(session.getId(), session);
		notifyObservers(sessionList);
	}//end method addSession
	
	public void removeSession(Session session)
	{
		sessionList.remove(session.getId());
		notifyObservers(sessionList);
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
		notifyObservers(sessionList);
	}//end method addObserver

	@Override
	public void removeObserver(MyObserver myObserver) 
	{
		myObservers.remove(myObserver);
	}//end method removeObserver
}//end class SessionMonitor
