package com.project.waverr;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;

@SuppressWarnings("deprecation")
public class LoginPage extends Activity implements OnClickListener,ConnectionCallbacks,OnConnectionFailedListener {

	/* Request code used to invoke sign in user interactions. */
	private static final int RC_SIGN_IN = 0;
	//private static final String TAG = null;
	private static String APP_ID = "1547265585557850";

	/* Client used to interact with Google APIs. */
	private GoogleApiClient mGoogleApiClient;
	private ConnectionResult mConnectionResult;

	/* A flag indicating that a PendingIntent is in progress and prevents
	 * us from starting further intents.
	 */
	private boolean mIntentInProgress;

	private Facebook facebook;
	//private AsyncFacebookRunner mAsyncRunner;
	String FILENAME = "AndroidSSO_data";
	private SharedPreferences mPrefs; 


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		findViewById(R.id.btn_sign_in).setOnClickListener((OnClickListener) this);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN)
		.build();

		facebook = new Facebook(APP_ID);
		//mAsyncRunner = 
		new AsyncFacebookRunner(facebook); 

		//View btnFbLogin = null;
		Button btnFbLogin = (Button) findViewById(R.id.login_button);
		btnFbLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				loginToFacebook();
			}
		});

		Button btnnologin = (Button) findViewById(R.id.nologin);
		btnnologin.setOnClickListener(this);
	}
	protected void onStart() {
		super.onStart();
		//mGoogleApiClient.connect();
	}

	protected void onStop() {
		super.onStop();

		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		mSignInClicked = false;
		Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
			Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
			String personName = currentPerson.getDisplayName();
			Image personPhoto = currentPerson.getImage();
			String personGooglePlusProfile = currentPerson.getUrl();
			String personEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);

			//Toast.makeText(this, personName+","+personEmail, Toast.LENGTH_SHORT).show();
			GlobalClass global = (GlobalClass) getApplicationContext();
			global.setPersonName(personName);
			global.setPersonPhoto(personPhoto);
			global.setPersonGooglePlusProfile(personGooglePlusProfile);
			global.setPersonEmail(personEmail);
		}
		Intent intent = new Intent(this, com.project.waverr.Home2.class);
		startActivity(intent);
	}

	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		mGoogleApiClient.connect();
	}


	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		if (!mIntentInProgress && result.hasResolution()) {
			try {
				mIntentInProgress = true;
				startIntentSenderForResult(result.getResolution().getIntentSender(),
						RC_SIGN_IN, null, 0, 0, 0);
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent.  Return to the default
				// state and attempt to connect to get an updated ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
			//mConnectionResult = result;
		}

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

	/* Track whether the sign-in button has been clicked so that we know to resolve
	 * all issues preventing sign-in without waiting.
	 */
	private boolean mSignInClicked;

	/* Store the connection result from onConnectionFailed callbacks so that we can
	 * resolve them when the user clicks sign-in.
	 */

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		if (mConnectionResult!=null && mConnectionResult.hasResolution()) {
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


	public void onClick(View view) {
		if (view.getId() == R.id.btn_sign_in
				&& !mGoogleApiClient.isConnecting()) {
			mGoogleApiClient.connect();
			mSignInClicked = true;
			resolveSignInError();		  

		}
		
		if(view.getId()==R.id.nologin){
			Intent intent = new Intent(this, com.project.waverr.Home2.class);
		    startActivity(intent);
		}



	}



	public void loginToFacebook() {
		mPrefs = getPreferences(MODE_PRIVATE);
		String access_token = mPrefs.getString("access_token", null);
		long expires = mPrefs.getLong("access_expires", 0);

		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}

		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}

		if (!facebook.isSessionValid()) {
			facebook.authorize(this,
					new String[] { "email", "publish_stream" },
					new DialogListener() {

				@Override
				public void onCancel() {
					// Function to handle cancel event
				}

				@Override
				public void onComplete(Bundle values) {
					// Function to handle complete event
					// Edit Preferences and update facebook acess_token
					SharedPreferences.Editor editor = mPrefs.edit();
					editor.putString("access_token",
							facebook.getAccessToken());
					editor.putLong("access_expires",
							facebook.getAccessExpires());
					editor.commit();
					Intent intent = new Intent(getBaseContext(), com.project.waverr.Home2.class);
					startActivity(intent);
				}


				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub

				}

			});
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

}


