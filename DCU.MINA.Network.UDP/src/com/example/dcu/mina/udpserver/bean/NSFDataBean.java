package com.example.dcu.mina.udpserver.bean;

import org.json.JSONException;
import org.json.JSONObject;


public class NSFDataBean {

	String eventId = "";
	String sendDTime = "";
	char dataType;
	int dataLength = 0;
	JSONObject nsfJSONData = null;
	
	
	
	
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getSendDTime() {
		return sendDTime;
	}
	public void setSendDTime(String sendDTime) {
		this.sendDTime = sendDTime;
	}
	public char getDataType() {
		return dataType;
	}
	public void setDataType(char dataType) {
		this.dataType = dataType;
	}
	public int getDataLength() {
		return dataLength;
	}
	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}
	public JSONObject getNsfJSONData() {
		return nsfJSONData;
	}
	
	public void setNsfJSONData(JSONObject nsfData) {
		this.nsfJSONData = nsfData;
	}
	
	public void setNsfJSONData(String nsfJSONStrata){
		try {
			nsfJSONData = new JSONObject(nsfJSONStrata);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	} 
	
	
	@Override
	public String toString() {
		String data = "";
		if(dataType == 'J'){
			data = nsfJSONData.toString();
		}
		
		
		return "[R]["+eventId+"]["+sendDTime+"]["+dataType+"]["+dataLength+"]["+data+"]";
	}
}
