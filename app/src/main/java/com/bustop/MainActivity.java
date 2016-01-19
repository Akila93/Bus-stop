package com.bustop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Thread str_thread = new Thread(){
			
			public void run(){
				try{
					sleep(3000);
				}catch(Exception e){
					
				}finally{
					Intent st_main = new Intent("com.bustop.RouteSelection");
					startActivity(st_main);					
				}			
			}
		};
		str_thread.start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}
}
