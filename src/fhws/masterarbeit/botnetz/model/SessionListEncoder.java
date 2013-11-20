package fhws.masterarbeit.botnetz.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SessionListEncoder implements Encoder.Text<HashMap<String, Session>>
{
	@Override
	public void destroy() 
	{
	}//end method destroy

	@Override
	public void init(EndpointConfig epc) 
	{
	}//end method init

	@Override
	public String encode(HashMap<String, Session> sessionList) throws EncodeException 
	{
		Iterator<Entry<String, Session>> it = sessionList.entrySet().iterator();
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		int laufendeNr = 1;
		
		while (it.hasNext())
		{
			Map.Entry<String, Session> entry = (Map.Entry<String, Session>) it.next();
			jsonObj.put("nr", laufendeNr);
			jsonObj.put("id", entry.getKey());
			jsonArr.add(jsonObj);
			laufendeNr++;
		}//end while
		
		return jsonArr.toString();
	}//end method encode
}//end class SessionListEncoder