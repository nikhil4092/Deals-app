package com.project.waverr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class ChineseDeal extends Activity implements OnClickListener{

	ImageButton ib1,ib2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chinese_deal);
		
		ib1=(ImageButton)findViewById(R.id.chinesedeal1);
		ib2=(ImageButton)findViewById(R.id.chinesedeal2);
		ib1.setOnClickListener(this);
		ib2.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View arg0) {
		Intent c1;
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
		case R.id.chinesedeal1:
			c1 = new Intent("com.project.waverr.DEALPAGE");
			startActivity(c1);
			break;
		case R.id.chinesedeal2:break;
		}
	}

	
}
