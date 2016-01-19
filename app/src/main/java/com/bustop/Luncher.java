package com.bustop;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import JSON.pack.JSONfunctions;

public class Luncher extends Activity {
	TextView min,sec;
	long[] val;
	long time;
	Luncher me;
	String typed,bus_id;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.me = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bus);
		sec = (TextView) findViewById(R.id.sec);
		min = (TextView) findViewById(R.id.min);

		Intent intent = getIntent();
		typed = intent.getStringExtra("typed");
		bus_id = intent.getStringExtra("bus_id");


		AsyncTask asyncTask = new AsyncTask() {
			@Override
			protected Object doInBackground(Object[] params) {
				time = 305000;
				/*JSONObject jsonobj = JSONfunctions.getJSONfromURL("http://localhost/app/time_finder.php?bus_id=bus_id&location=typed");
				try {
					JSONObject resultObj = jsonobj.getJSONObject("response");
					time = resultObj.getLong("prediction");


					return "Done";
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				return null;
			}
			@Override
			protected void onPostExecute(Object result){
				CountDownTimer myCountDown = new CountDownTimer(2000+(time/1000)*1000, 1000) {
					public void onTick(long millisUntilFinished) {
						//sec.setText("00");
						runner();
					}

					public void onFinish() {

					}
					public void runner(){
						val = this.formateMe(me.time);
						if(val[0]!=0 || val[1]!=0) {

							if(val[0]<10){
								min.setText("0"+String.valueOf(val[0]));
							}else{min.setText(String.valueOf(val[0]));}
							if(val[1]<10){
								sec.setText("0"+String.valueOf(val[1]));
							}else{sec.setText(String.valueOf(val[1]));}

							time-=1000;
						}else {
							sec.setText("00");
							min.setText("00");
						}

					}
					public long[] formateMe(long milisec){
						long value = milisec/1000;
						long [] returnMe = new long[2];

						returnMe[0] = value/(2*30);
						returnMe[1] = (value%(2*30));

						return returnMe;
					}
				};

				myCountDown.start();
			}
		};

		asyncTask.execute("");

	}

}
