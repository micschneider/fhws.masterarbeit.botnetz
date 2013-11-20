package fhws.masterarbeit.botnetz.model;

import java.util.HashMap;

import javax.websocket.Session;

public interface MyObserver 
{
	public void onUpdate(HashMap<String, Session> sessionList);
}//end interface MyObserver
