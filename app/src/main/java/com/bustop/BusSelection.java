package com.bustop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import JSON.pack.JSONfunctions;
import row.pack.Bus;

import adapter.local.BusAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BusSelection extends Activity implements OnItemClickListener {
	ListView listView;
    ArrayAdapter<Bus> adapter;
    List<Bus> list = new ArrayList<Bus>();
	String typed;
	TextView did;
	JSONObject jsonobj;
	String[] bus_ids,route_ids,sources_destinations;
	int rt_id;


	public ArrayList<String> splitString(String ourString){
		String[] data = ourString.split(";");
		ArrayList<String> array = new ArrayList<String>();
		for(String s:data){
			array.add(s);
		}
		return array;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus_selection);
		did = (TextView) findViewById(R.id.didIt);

		Intent intent = getIntent();

		final ArrayList<String> selected = this.splitString(intent.getStringExtra("selected"));
		typed = intent.getStringExtra("typed");
		String tem = intent.getStringExtra("selected");
		rt_id = intent.getIntExtra("route_id",0);




		AsyncTask asyncTask = new AsyncTask() {
			@Override
			protected Object doInBackground(Object[] params) {

				/*jsonobj = JSONfunctions.getJSONfromURL("http://localhost/app/bus_finder.php?location=typed&selects=tem");
				try {
					resultObj = jsonobj.getJSONObject("response");
					bus_ids = new String[resultObj.length()];
					route_ids = new String[resultObj.length()];
					sources_destinations = new String[resultObj.length()];
					for(int i = 0;i<resultObj.length();i++){
						bus_ids[i] = resultObj.getString("bus_id");
						route_ids[i] = resultObj.getString("route_id");
						sources_destinations[i] = resultObj.getString("route_name");

					}
					return "Done";
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				return null;
			}
			@Override
			protected void onPreExecute(){
				bus_ids = new String[]{"123", "103", "312"};//null;
				route_ids = new String[]{"A1", "A2", "A3"};//null;
				sources_destinations = new String[]{"Galle-Colombo", "Kandy-Colombo", "Jaffna-Colombo"};//null;
				did.setText(typed);
			}
		};
		asyncTask.execute("");

		listView = (ListView) findViewById(R.id.bus_list);
        adapter = new BusAdapter(this,getBus(bus_ids,route_ids,sources_destinations));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((OnItemClickListener) this);

		
	}
	public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
		Intent intent = new Intent("com.bustop.Luncher");
		intent.putExtra("type",intent.getStringExtra("typed"));
		intent.putExtra("bus_id",list.get(position).toString());
		startActivity(intent);
       
	}
	 private List<Bus> getBus(String[] bus_ids,String[] route_ids,String[] sources_destinations) {
		 for(int i =0;i<bus_ids.length;i++){
			 list.add(new Bus(bus_ids[i],route_ids[i],sources_destinations[i]));
		 }
	        return list;
	    }
}
