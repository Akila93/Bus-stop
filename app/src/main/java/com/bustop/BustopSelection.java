package com.bustop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import row.pack.Bustop;
import row.pack.Bustop;

import adapter.local.MyAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class BustopSelection extends Activity implements OnItemClickListener{

	Button btnSearch;
	ListView listView;
    ArrayAdapter<Bustop> adapter;
    List<Bustop> list = new ArrayList<Bustop>();

    private String getSelected(){
        String delim = ";";
        String returnMe = "";
        Iterator<Bustop> iterator = list.iterator();
        Bustop tem;
        if (iterator.hasNext()){
            tem = iterator.next();
            if(tem.isSelected()){
                returnMe+=tem.getName();
            }
        }
        while (iterator.hasNext()){
            tem = iterator.next();
            if(tem.isSelected()){
                returnMe+=delim;
                returnMe+=tem.getName();
            }
        }
        return returnMe;
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_2);

        final Intent intent = getIntent();

        btnSearch = (Button)findViewById(R.id.done);
        listView = (ListView) findViewById(R.id.my_list);
        String tem = intent.getStringExtra("bustops");
        getModel(tem);
        /*adapter = new MyAdapter(this,getModel(intent));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);*/

        btnSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent busSelection = new Intent("com.bustop.BusSelection");
                busSelection.putExtra("type",intent.getStringExtra("typed"));
                busSelection.putExtra("selected",getSelected());
                busSelection.putExtra("route_id",intent.getIntExtra("route_id",0));
                busSelection.putExtra("bustops",intent.getStringExtra("bustops"));
				startActivity(busSelection);
			}
		});
	}
    @Override
    public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3) {
                TextView label = (TextView) v.getTag(R.id.label);
                CheckBox checkbox = (CheckBox) v.getTag(R.id.check);
                Toast.makeText(v.getContext(), label.getText().toString()+" "+isCheckedOrNot(checkbox), Toast.LENGTH_LONG).show();
                
    }
    
    private String isCheckedOrNot(CheckBox checkbox) {
        if(checkbox.isChecked())
        return "is checked";
        else
        return "is not checked";
    }
    
    private List<Bustop> getModel(String tem) {
        tem = tem.substring(1);
        String[] bus_stops = tem.split(";");

        for(String s:bus_stops){
            list.add(new Bustop(s));
        }
    	

        return list;
    }
    
}
