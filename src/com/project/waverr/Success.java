package com.project.waverr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;



public class Success extends Activity{
	
	String s;
	TextView tv1,tv2,tv3;
	Intent i;
	Animation animblink;
	RandAlphaNum gen = new RandAlphaNum();
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.success);
        tv1=(TextView)findViewById(R.id.successtext1);
        tv2=(TextView)findViewById(R.id.successtext2);
        tv3=(TextView)findViewById(R.id.CodeGen);
        
        Bundle b=getIntent().getExtras();
        if(b.getBoolean("Working")==true)
        {
        s=gen.randomString(4);
        tv1.setText("Congratulations");
        tv2.setText("DEAL ACTIVATED");
        tv3.setText("CODE:WAVERR"+s);
        animblink=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink);
        tv2.startAnimation(animblink);
        }
        else if(b.getBoolean("Working")==false)
        {
            tv1.setText("Sorry");
            tv2.setText("Fake Q.R. Code/Retry");
        }
}
}
