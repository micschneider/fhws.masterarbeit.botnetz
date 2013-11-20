package fhws.masterarbeit.botnetz.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.websocket.Session;

public interface MyObservable 
{
	public ArrayList<MyObserver> myObservers = new ArrayList<MyObserver>();
	public void notifyObservers(HashMap<String, Session> sessionList);
	public void addObserver(MyObserver myObserver);
	public void removeObserver(MyObserver myObserver);
}//end interface MyObservable
