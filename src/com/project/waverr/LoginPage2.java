package com.project.waverr;

import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.Image;

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
	//private FacebookFragment facebookFragment;

	GlobalClass global;
	ProgressDialog progressDialog;
	String gotoActivity;
	String dealString = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		global = (GlobalClass) getApplication();

		Intent intent = getIntent();
		if(intent.hasExtra("returnActivity")) {
			dealString = intent.getStringExtra("deal");
			gotoActivity = intent.getStringExtra("returnActivity");
		}
		else
			gotoActivity = "com.project.waverr.HOMETWO";
			
		findViewById(R.id.btn_sign_in).setOnClickListener(this);
		findViewById(R.id.nologin).setOnClickListener(this);

		mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this)
		.addApi(Plus.API)
		.addScope(Plus.SCOPE_PLUS_LOGIN)
		.build();

		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Logging you in...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
	}

	@Override
	public void onClick(View view) {
		//Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
		if (view.getId() == R.id.btn_sign_in) {
			if(!mGoogleApiClient.isConnecting()) {
				//Toast.makeText(this, "Processing stuff", Toast.LENGTH_SHORT).show();
				mSignInClicked = true;
				resolveSignInError();
			}
		}
		if(view.getId() == R.id.nologin) {
			Intent intent = new Intent("com.project.waverr.HOMETWO");
			global.setloginstatus("none");
			global.setLoggedIn(false);
			startActivity(intent);
		}
	}

	/* A helper method to resolve the current ConnectionResult error. */
	private void resolveSignInError() {
		//Toast.makeText(this, "Resolving sign-in error", Toast.LENGTH_SHORT).show();
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
						RC_SIGN_IN, null, 0, 0, 0);
				if(progressDialog!=null && !progressDialog.isShowing())
					progressDialog.show();
			} catch (SendIntentException e) {
				// The intent was canceled before it was sent.  Return to the default
				// state and attempt to connect to get an updated ConnectionResult.
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	public void onConnectionFailed(ConnectionResult result) {
		//Toast.makeText(this, "Connection failed!", Toast.LENGTH_SHORT).show();
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
		mSignInClicked = false;
		goAheadWithGoogle();
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
		//Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
			Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
			final String personName = currentPerson.getDisplayName();
			Image personPhoto = currentPerson.getImage();
			String personGooglePlusProfile = currentPerson.getUrl();
			final String personEmail = Plus.AccountApi.getAccountName(mGoogleApiClient);
			final String personBirthday = currentPerson.getBirthday();

			global.setPersonName(personName);
			global.setPersonPhoto(personPhoto);
			global.setPersonGooglePlusProfile(personGooglePlusProfile);
			global.setPersonEmail(personEmail);
			global.setloginstatus("google");
			global.setLoggedIn(true);

			JSONObtainer checker = new JSONObtainer() {
				@Override
				protected void onPostExecute(JSONArray array) {

					if(array==null) {
						//Toast.makeText(getBaseContext(), "New user... Adding to database", Toast.LENGTH_SHORT).show();
						new JSONObtainer().execute(new String[] {
								"http://waverr.in/adduser.php",
								"name", personName,
								"email", personEmail,
								"age", personBirthday
						});
					}
				}
			};
			checker.execute(new String[] {
					"http://waverr.in/checkuser.php",
					"email", personEmail
			});
			global.setClient(mGoogleApiClient);
			Intent intent = new Intent(gotoActivity);
			intent.putExtra("deal", dealString);
			if(progressDialog!=null && progressDialog.isShowing())
				progressDialog.dismiss();
			startActivity(intent);
		}
	}
}
