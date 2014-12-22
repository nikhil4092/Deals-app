package com.project.waverr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

public class Splash extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(2000);					
				} catch (InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent openStartingPoint = new Intent("com.project.waverr.HOMETWO");
					startActivity(openStartingPoint);
				}
			}
		};
		timer.start();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		finish();
		super.onPause();
	}	
}