package com.bustop;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import JSON.pack.JSONfunctions;
import row.pack.Route;

import adapter.local.RouteAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RouteSelection extends Activity implements OnItemClickListener {
	ListView listView;
    ArrayAdapter<Route> adapter;
    List<Route> list = new ArrayList<Route>();
	Button sBtn;
	String[] sources_destinations,bus_stops;
	private RouteSelection selection;
	EditText location_text;
	String loc;
	Integer route_ids[];
	JSONObject jsonobj;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.route_selection);
		sBtn = (Button) findViewById(R.id.find);
		listView = (ListView) findViewById(R.id.route_list);
		location_text = (EditText)findViewById(R.id.loc_text);
		this.selection = this;

		loc = location_text.getText().toString();




		sBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AsyncTask asyncTask = new AsyncTask() {
					private String findMe(String bustops){
						bustops = bustops.substring(1);
						String[] bus_stops = bustops.split(";");
						int count = 0;

						for(int i = 0 ;i < bus_stops.length;i++){
							for(int j = 0 ; j < ((bus_stops[i].length()>loc.length())?loc.length():bus_stops[i].length()) ; j++){
								Character a = loc.charAt(j);
								Character b = bus_stops[i].charAt(j);

								if(a.equals(b)){
									if(j>count){
										count = j;
										bustops = bus_stops[i];
									}

								}
							}
						}
						return bustops;
					}
					@Override
					protected Object doInBackground(Object[] params) {

						DefaultHttpClient httpclient = new DefaultHttpClient();
						HttpPost httppost = new HttpPost("http://www.smart-bus-stop.netau.net/app/search_route.php");

						List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

						nameValuePairs.add(new BasicNameValuePair("location", loc));


						try {
							JSONObject j = new JSONObject();
							j.put("location", loc);
							UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);
							httppost.setEntity(urlEncodedFormEntity);
						} catch (Exception e) {
							e.printStackTrace();
							return "puhaa";
						}

						//httppost.setHeader("Content-type", "Bustop/json");

						InputStream inputStream = null;
						String result = null;
						try {
							HttpResponse response = httpclient.execute(httppost);
							HttpEntity entity = response.getEntity();

							inputStream = entity.getContent();
							InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
							// json is UTF-8 by default
							BufferedReader reader = new BufferedReader(inputStreamReader);
							StringBuilder sb = new StringBuilder();

							String line = null;
							while ((line = reader.readLine()) != null)
							{
								sb.append(line);
							}
							result = sb.toString();
						} catch (Exception e) {
							// Oops
							return "yoo";
						}
						finally {
							try{if(inputStream != null)inputStream.close();}catch(Exception squish){}
						}
						//String url = "http://www.smart-bus-stop.netau.net/app/search_route.php?location="+loc;
						//JSONObject jsonobj = JSONfunctions.getJSONfromURL(url);
						try {
							result = result_parser(result);
							JSONObject resultObj = (JSONObject) new JSONTokener(result).nextValue();
							String bus_stop_name = resultObj.getString("bus_stop_name");
							JSONArray j;
							try{
								j =resultObj.optJSONArray("records");
								if(j == null)throw new Exception();
								//JSONObject js = (JSONObject) resultObj.get("records");

								}catch (Exception e){return (bus_stop_name+"-error");}
							//result = this.split_result(result);
							//String[] res = this.split_result(result);
							//JSONObject resultObj1;

							//JSONArray sources_destinationsJsonArray = resultObj.getJSONArray("route_name");
							//JSONArray bus_stopsJsonArray = resultObj.getJSONArray("bustops");
							ArrayList<Integer> routes = new ArrayList<Integer>();
							ArrayList<String> sour_dest = new ArrayList<String>();
							ArrayList<String> bus_st = new ArrayList<String>();

							JSONObject resultObj1;
							try{
								resultObj1 = j.optJSONObject(0);
							}catch (Exception e){return (bus_stop_name+"-error");}
							for(int i = 1;resultObj1!=null;i++){
								try{
									routes.add(resultObj1.optInt("route_id"));
									sour_dest.add(resultObj1.optString("route_name"));
									//JSONArray jsonArray= resultObj1.optJSONArray("bustops");
									bus_st.add(resultObj1.optString("bustops"));
								/*route_ids[i] = resultObj1.getInt("route_id");
								sources_destinations[i] = resultObj1.getString("route_name");
								bus_stops[i] = resultObj1.getString("bustops");*/
									resultObj1 = j.optJSONObject(i);
								}catch (Exception e){return (bus_stop_name+"-error");}

							}
							route_ids = new Integer[routes.size()];
							sources_destinations = new String[sour_dest.size()];
							bus_stops = new String[bus_st.size()];
							for (int i = 0; i<route_ids.length;i++){
								route_ids[i] = routes.get(i);
								sources_destinations[i] = sour_dest.get(i);
								bus_stops[i] = bus_st.get(i);

							}
							//if(bus_stops[0].contains(";"))
							//return findMe(bus_stops[0]);*/
							return bus_stop_name;
							//return "Kaluthara";
							//return result;
							//return result.substring(29);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return null;
						}

						//return null;
					}
					private String split_result(String result){
						int st = result.indexOf('[')+1;
						int en = result.lastIndexOf(']');
						result = result.substring(st,en);
						String[] str = result.split("},");
						for(int i = 0; i< str.length-1;i++){
							str[i] = str[i]+"}";
						}
						//return str;
						return result;
					}
					private String result_parser(String result){
						int st = result.indexOf("{");
						int ed = result.lastIndexOf("}]}")+3;
						result = result.substring(st,ed);
						return result;
					}
					@Override
					public void onPreExecute(){
						route_ids = new Integer[]{2, 3, 4};//null;
						sources_destinations = new String[]{"Galle-Colombo", "Kandy-Colombo", "Jaffna-Colombo"};//null;
						bus_stops = new String[]{";Galle;Ambalangoda;Balapitiya;Ahungalla;Benthota;Aluthgama;Kaluthara;Panadura;Moratuwa;Dehiwala;Wallawaththa",";Galle;Ambalangoda;Balapitiya;Ahungalla;Benthota;Aluthgama;Kaluthara;Panadura;Moratuwa;Dehiwala;Wallawaththa",";Galle;Ambalangoda;Balapitiya;Ahungalla;Benthota;Aluthgama;Kaluthara;Panadura;Moratuwa;Dehiwala;Wallawaththa"};
						loc = location_text.getText().toString();

					}
					@Override
					public void onPostExecute(Object result){
						if(result==null)return;

						location_text.setText((CharSequence) result);
						adapter = new RouteAdapter(selection,getRoute(route_ids,sources_destinations));
						listView.setAdapter(adapter);
						listView.setOnItemClickListener((OnItemClickListener) selection);
					}
				};

				asyncTask.execute("");


			}
		});




	}
	private String findMe(String bustops){
		bustops = bustops.substring(1);
		String[] bus_stops = bustops.split(";");
		int count = 0;

		for(int i = 0 ;i < bus_stops.length;i++){
			for(int j = 0 ; j < ((bus_stops[i].length()>loc.length())?loc.length():bus_stops[i].length()) ; j++){
				Character a = loc.charAt(j);
				Character b = bus_stops[i].charAt(j);

				if(a.equals(b)){
					if(j>count){
						count = j;
						bustops = bus_stops[i];
					}

				}
			}
		}
		return bustops;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Route route = list.get(position);
		Intent st_main = new Intent("com.bustop.BustopSelection");
		st_main.putExtra("route_name",route.getName());
		st_main.putExtra("route_id",route.getRoute_id());
		st_main.putExtra("bustops",bus_stops[position]);
		loc = location_text.getText().toString();
		st_main.putExtra("typed",loc);
		//if(route.getName()!=null && (Integer)route.getRoute_id()!=null){location_text.setText(position+" :good :"+route.getName());}
		startActivity(st_main);

	}
	 private List<Route> getRoute(Integer[] s1,String[] s2) {
		 for(int i = 0;i<s1.length;i++){
			 list.add(new Route(s1[i],s2[i]));
		 }
	        
	        
	        return list;
	 }
}
