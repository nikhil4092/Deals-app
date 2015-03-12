package com.project.waverr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;



public class Success extends Activity{

	String s,name;
	TextView tv1,tv2,tv3,Dealvalue;
	Intent i;
	ProgressBar bar;
	Animation animblink;
	RandAlphaNum gen = new RandAlphaNum();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			name="SIZ";
			tv1.setText("Congratulations");
			tv2.setText("DEAL ACTIVATED");
			tv3.setText("CODE:WAV"+name+"MLR-"+s);
			animblink=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
			tv2.startAnimation(animblink);

			new PostData().execute();
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
