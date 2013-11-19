package fhws.masterarbeit.botnetz.data;

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
	{}

	@Override
	public void init(EndpointConfig epc) 
	{}

	@Override
	public String encode(HashMap<String, Session> sessionList) throws EncodeException 
	{
		Iterator<Entry<String, Session>> it = sessionList.entrySet().iterator();
		JSONArray jsonArr = new JSONArray();
		JSONObject jsonObj = new JSONObject();
		
		while (it.hasNext())
		{
			Map.Entry<String, Session> entry = (Map.Entry<String, Session>) it.next();
			jsonObj.put("id", entry.getKey());
			jsonArr.add(jsonObj);
		}
		
		return jsonArr.toString();
	}
}