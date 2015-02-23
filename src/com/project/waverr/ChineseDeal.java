package com.project.waverr;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ChineseDeal extends GlobalActionBar{

	JSONObtainer obtainer;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chinese_deal);

		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Getting deals...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.show();

		final LinearLayout mLayout = (LinearLayout) findViewById(R.id.deallist);
		mLayout.setGravity(Gravity.CENTER);
		url = "http://waverr.in/getdealparameters.php";
		obtainer = new JSONObtainer() {
			@Override
			protected void onPostExecute(JSONArray array) {

				String things[] = {
						"Did",
						"Oid",
						"Detail",
						"Discount",
						"MinAmt",
						"StartDate",
						"EndDate",
						"StartTime",
						"EndTime",
						"imgurl",
						"Cuisine"
				};

				ArrayList<Deal> deals = new ArrayList<Deal>();
				ArrayList<ImageButton> buttons = new ArrayList<>();
				ArrayList<TextView> texts = new ArrayList<>();

				try {

					for(int i=0; i<array.length(); i++) {
						JSONObject object = array.getJSONObject(i);

						Gson gson = new Gson();
						Deal newDeal = new Deal();
						newDeal.setDid(object.getInt(things[0]));
						newDeal.setOid(object.getInt(things[1]));
						newDeal.setDetails(object.getString(things[2]));
						newDeal.setDiscount(object.getInt(things[3]));
						newDeal.setMinimumAmount(object.getInt(things[4]));
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						try {
							newDeal.setStartDate(format.parse(object.getString(things[5])));
							newDeal.setEndDate(format.parse(object.getString(things[6])));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						newDeal.setStartTime(Time.valueOf(object.getString(things[7])));
						newDeal.setEndTime(Time.valueOf(object.getString(things[8])));
						newDeal.setImageURL(object.getString(things[9]));
						newDeal.setCuisine(object.getString(things[9]));
						deals.add(newDeal);
						//Toast.makeText(getBaseContext(), "Got the object", Toast.LENGTH_SHORT).show();
						LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

						ImageButton button = new ImageButton(getBaseContext());
						button.setLayoutParams(params);
						button.setImageResource(R.drawable.soup5);
						button.setScaleType(ScaleType.FIT_XY);
						button.setBackgroundColor(Color.TRANSPARENT);
						button.setAdjustViewBounds(true);
						buttons.add(button);

						final String deal = gson.toJson(newDeal);

						button.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(getBaseContext(), com.project.waverr.DealPage.class);
								Toast.makeText(getBaseContext(), deal, Toast.LENGTH_SHORT).show();
								intent.putExtra("deal", deal);
								startActivity(intent);
							}
						});

						TextView text = new TextView(getBaseContext());
						//text.setText(newDeal.getDetails());
						text.setText("This is just some text for testing");
						text.setBackgroundColor(getResources().getColor(R.color.abc_search_url_text_normal));
						text.setLayoutParams(params);
						texts.add(text);

						LinearLayout smallLayout = new LinearLayout(getBaseContext());
						smallLayout.setOrientation(LinearLayout.VERTICAL);
						smallLayout.setLayoutParams(params);
						smallLayout.setPadding(4, 0, 4, 0);
						smallLayout.addView(button);
						smallLayout.addView(text);

						mLayout.addView(smallLayout);
					}
					progressDialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		obtainer.execute(url);
	}
}
