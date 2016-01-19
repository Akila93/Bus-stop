package db_connector;

import org.json.JSONException;
import org.json.JSONObject;

import JSON.pack.JSONfunctions;

public class JSON_db_connector {
	private JSONObject jsonobj = null;
	
	public void pull(SendUrl sendUrl) throws JSONException{
		this.jsonobj = JSONfunctions.getJSONfromURL(sendUrl.get_url());
		this.jsonobj = this.jsonobj.getJSONObject("resultArray");
	}
	public String[] get_variable_array(String variable) throws JSONException{
		String[] temp = new String[this.jsonobj.length()];
		for(int i = 0;i<jsonobj.length();i++){
			temp[i] = this.jsonobj.getString(variable);
		}
		return temp;
	}
}
