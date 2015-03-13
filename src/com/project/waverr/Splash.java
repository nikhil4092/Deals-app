package com.project.waverr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;

public class Splash extends Activity implements ConnectionCallbacks, OnConnectionFailedListener {

	private GoogleApiClient mGoogleApiClient;
	private GlobalClass global;
	private Boolean timerRunning;
	private int SPLASH_TIME = 2000;
	private Boolean googleConnected = false;
	private TextView loading;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		loading = (TextView)findViewById(R.id.loading);
		loading.setVisibility(View.INVISIBLE);
		
		timerRunning = true;
		
		new CountDownTimer(SPLASH_TIME, 1000) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				//Toast.makeText(getBaseContext(), "Timer done", Toast.LENGTH_SHORT).show();
				goAhead();
			}
		}.start();
		
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_PROFILE)
		.build();

		global = (GlobalClass) getApplication();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		// TODO Auto-generated method stub
		finish();
	}
	
	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		if(!timerRunning)
			goAheadWithGoogle();
		else
			googleConnected = true;
	}
	
	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();
	}
	
	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		if(!timerRunning)
			goToLoginPage();
	}
	
	private void goAheadWithGoogle() {
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
			Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
			String personName = currentPerson.getDisplayName();
			Image personPhoto = currentPerson.getImage();
			String personGooglePlusProfile = currentPerson.getUrl();
			String personEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);

			//Toast.makeText(this, personName+","+personEmail, Toast.LENGTH_SHORT).show();

			global.setPersonName(personName);
			global.setPersonPhoto(personPhoto);
			global.setPersonGooglePlusProfile(personGooglePlusProfile);
			global.setPersonEmail(personEmail);
			global.setloginstatus("google");
			global.setLoggedIn(true);
		}
		global.setClient(mGoogleApiClient);
		Intent intent = new Intent(this, com.project.waverr.Home2.class);
		startActivity(intent);
	}
	
	private void goToLoginPage() {
		Intent intent = new Intent("com.project.waverr.LOGINPAGE");
		startActivity(intent);
	}
	
	private void goAhead() {
		timerRunning = false;
		if(!mGoogleApiClient.isConnecting()) {
			if(googleConnected==true)
				goAheadWithGoogle();
			else
				goToLoginPage();
		}
		else {
			Animation anim = new AlphaAnimation(0.0f, 1.0f);
			anim.setDuration(1000);
			anim.setRepeatMode(Animation.REVERSE);
			anim.setRepeatCount(Animation.INFINITE);
			loading.setVisibility(View.VISIBLE);
			loading.startAnimation(anim);
		}
	}
}