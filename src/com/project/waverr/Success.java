package com.project.waverr;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class Success extends GlobalActionBar implements OnClickListener{

	String s,name;
	TextView tv1,tv2,tv3,Dealvalue;
	Intent i;
	ProgressBar bar;
	Animation animblink;
	RandAlphaNum gen = new RandAlphaNum();
	GlobalClass global;
	String actid;
	Button okButton;
	AlertDialog dialog;
	String dtext;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		global = (GlobalClass) getApplication();
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.success);
		tv1=(TextView)findViewById(R.id.successtext1);
		tv2=(TextView)findViewById(R.id.successtext2);
		tv3=(TextView)findViewById(R.id.CodeGen);
		Dealvalue=(TextView)findViewById(R.id.dealvalue);
		okButton = (Button)findViewById(R.id.success_ok);
		okButton.setOnClickListener(this);
		okButton.setVisibility(View.INVISIBLE);
		Bundle b=getIntent().getExtras();
		String s=b.getString("deal");
		i = getIntent();
		if(b.getBoolean("Working")==true)
		{
			Dealvalue.setText(s);
			s=gen.randomString(4);
			dtext = i.getStringExtra("Share");
			Deal deal = global.getDeal();
			String dealid = deal.getID();
			okButton.setVisibility(View.VISIBLE);
			name = deal.getRestaurantID();
			actid = dealid+"-"+s;
			tv1.setText("Congratulations");
			tv2.setText("DEAL ACTIVATED");
			tv3.setText("CODE:"+actid);
			
			animblink=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
			tv2.startAnimation(animblink);
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Deal activated!");
			builder.setMessage("Congratulations! Your deal has been activated. Share the deal with your friends and rate us!");
			builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.setNeutralButton("Share", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent i=new Intent(android.content.Intent.ACTION_SEND);
					i.setType("text/plain");
					i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Waverr");
					i.putExtra(android.content.Intent.EXTRA_TEXT, "I just activated the deal: "+dtext+".\nAvail this deal and more, only on Waverr - India's First Live Deal Engine. Get the app from the Play Store - waverr.in/getwaverr");
					startActivity(Intent.createChooser(i,"Share via"));
				}
			});
			builder.setPositiveButton("Rate Us", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					try {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.project.waverr")));
					}
					catch (ActivityNotFoundException e) {
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.project.waverr"));
						startActivity(intent);
					}
				}
			});
			dialog = builder.create();
			
			bar = (ProgressBar) findViewById(R.id.progressBar);
			bar.setVisibility(View.VISIBLE);

			//new PostData().execute();
			String[] things = {
					"http://waverr.in/sendactivateddeals.php",
					"dealid", dealid,
					"restaurantname", deal.getRestaurantName(),
					"emailid", global.getPersonEmail(),
					"customername", global.getPersonName(),
					"actid", actid
			};
			
			new JSONObtainer() {
				protected void onPostExecute(JSONArray array) {
					Toast.makeText(getBaseContext(), "Activation successful", Toast.LENGTH_SHORT).show();
				}
			}.execute(things);
		}
		else if(b.getBoolean("Working")==false)
		{
			tv1.setText("Sorry");
			tv2.setText("Fake Q.R. Code/Retry");
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(dialog!=null && !dialog.isShowing())
			dialog.show();
	}
}
