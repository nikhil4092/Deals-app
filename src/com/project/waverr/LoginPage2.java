package com.project.waverr;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;

import com.facebook.Session;

public class LoginPage2 extends Activity implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener{

	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */
	private ConnectionResult mConnectionResult;

	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;

	/* A flag indicating that a PendingIntent is in progress and prevents
	 * us from starting further intents.
	 */
	private boolean mIntentInProgress;

	GlobalClass global;
	/*private final int SPLASH_DURATION = 3000;
	private Boolean allCoolWithGoogle = false;*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);

		/*//LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.splash);

		new CountDownTimer(SPLASH_DURATION, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				if(allCoolWithGoogle==true)
					goAheadWithGoogle();
				else {
					showLoginPage();
				}
			}
		}.start();*/

		findViewById(R.id.btn_sign_in).setOnClickListener(this);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_PROFILE)
		.build();

		global = (GlobalClass) getApplication();
	}

	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.btn_sign_in
				&& !mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
						RC_SIGN_IN, null, 0, 0, 0);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent.  Return to the default
				// state and attempt to connect to get an updated ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	public void onConnectionFailed(ConnectionResult result) {
		if (!mIntentInProgress) {
			// Store the ConnectionResult so that we can use it later when the user clicks
			// 'sign-in'.
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		mSignInClicked = false;
		goAheadWithGoogle();
		//allCoolWithGoogle = true;
	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
	}

	@Override
	protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
		if (requestCode == RC_SIGN_IN) {
			if (responseCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
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
		}
		global.setClient(mGoogleApiClient);
		Intent intent = new Intent(this, com.project.waverr.Home2.class);
		startActivity(intent);
	}
	
	/*private void showLoginPage() {
		setContentView(R.layout.login_page);
		findViewById(R.id.btn_sign_in).setOnClickListener(this);
	}*/
}
