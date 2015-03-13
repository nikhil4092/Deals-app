package com.project.waverr;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



public class Success extends Activity{

	String s,name;
	TextView tv1,tv2,tv3,Dealvalue;
	Intent i;
	ProgressBar bar;
	Animation animblink;
	RandAlphaNum gen = new RandAlphaNum();
	GlobalClass global;
	String actid;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		global = (GlobalClass) getApplication();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.success);
		tv1=(TextView)findViewById(R.id.successtext1);
		tv2=(TextView)findViewById(R.id.successtext2);
		tv3=(TextView)findViewById(R.id.CodeGen);
		Dealvalue=(TextView)findViewById(R.id.dealvalue);
		Bundle b=getIntent().getExtras();
		String s=b.getString("deal");
		if(b.getBoolean("Working")==true)
		{
			Dealvalue.setText(s);
			s=gen.randomString(4);
			Deal deal = global.getDeal();
			String dealid = deal.getID();
			name = deal.getRestaurantID();
			actid = dealid+"-"+s;
			tv1.setText("Congratulations");
			tv2.setText("DEAL ACTIVATED");
			tv3.setText("CODE:"+actid);
			
			animblink=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
			tv2.startAnimation(animblink);

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
					Toast.makeText(getBaseContext(), "Done", Toast.LENGTH_SHORT).show();
				}
			}.execute(things);
		}
		else if(b.getBoolean("Working")==false)
		{
			tv1.setText("Sorry");
			tv2.setText("Fake Q.R. Code/Retry");
		}
		bar = (ProgressBar) findViewById(R.id.progressBar);
		bar.setVisibility(View.VISIBLE);
	}
}
