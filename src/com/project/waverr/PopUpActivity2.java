package com.project.waverr;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

public class PopUpActivity2 extends Activity {
	public void OnCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LayoutInflater inflater = (LayoutInflater) getBaseContext()
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		View popupview = inflater.inflate(R.layout.popup2, null);
		final PopupWindow popupwindow = new PopupWindow(popupview,
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		Button btnclose = (Button) popupview.findViewById(R.id.popupclose1);
		btnclose.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				popupwindow.dismiss();
			}

		});
		popupwindow.showAsDropDown(popupview);
	}
}
