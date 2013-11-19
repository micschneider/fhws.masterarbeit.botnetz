package fhws.masterarbeit.botnetz.data;

import java.util.HashMap;

import javax.websocket.Session;

public class ClientList implements MyObservable
{
	private static HashMap<String, Session> sessionList = new HashMap<String, Session>();
	private static ClientList clientList = new ClientList();
	
	private ClientList()
	{
	}
	
	public static ClientList getClientList()
	{
		return clientList;
	}
	
	public HashMap<String, Session> getSessionList()
	{
		return sessionList;
	}
	
	public void addSession(Session session)
	{
		sessionList.put(session.getId(), session);
		notifyObservers(sessionList);
	}
	
	public void removeSession(Session session)
	{
		sessionList.remove(session.getId());
		notifyObservers(sessionList);
	}

	@Override
	public void notifyObservers(HashMap<String, Session> sessionList) 
	{
		for(MyObserver myObserver : myObservers)
		{
			myObserver.onUpdate(sessionList);
		}
	}

	@Override
	public void addObserver(MyObserver myObserver) 
	{
		myObservers.add(myObserver);
	}

	@Override
	public void removeObserver(MyObserver myObserver) 
	{
		myObservers.remove(myObserver);
	}
}
