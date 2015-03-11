package com.project.waverr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class ChineseDeal extends GlobalActionBar {

	JSONObtainer obtainer;
	TextView DealType;
	String s;
	boolean login;
	ArrayList<Deal> deals = new ArrayList<Deal>();
	ArrayList<ImageButton> buttons = new ArrayList<>();
	ArrayList<TextView> texts = new ArrayList<>();
	
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
		Bundle b = getIntent().getExtras();
		s=b.getString("cuisine");
		login=b.getBoolean("login");
		DealType=(TextView)findViewById(R.id.DealType);
		DealType.setText(s+" Deals");
		DealType.setTextSize(25);
		DealType.setTextColor(Color.parseColor("#fe5335"));
		Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/script.ttf");
		DealType.setTypeface(typeface);
		
		final LinearLayout mLayout = (LinearLayout) findViewById(R.id.deallist);
		mLayout.setGravity(Gravity.CENTER);
		final DisplayMetrics display = mLayout.getResources().getDisplayMetrics();
		String[] url = {
				"http://waverr.in/getdealparameters.php",
				"cuisine", s
		};
		
		obtainer = new JSONObtainer() {
			@Override
			protected void onPostExecute(JSONArray array) {

				String things[] = {
						"ID",
						"Restaurant ID",
						"Restaurant Name",
						"Percentage Discount",
						"Amount Discount",
						"Freebie",
						"CanvasText",
						"Min Purchase Amount",
						"Deal Start Date",
						"Deal End Date",
						"Start Time",
						"End Time",
						"Cuisine",
				};

				try {
					if(array==null){
						LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
						TextView text = new TextView(getBaseContext());
						//text.setText(newDeal.getDetails());
						text.setText("No "+s+" Deals Currently.Please check back later.");
						int height = display.heightPixels; 
						text.setPadding(0, height/3,0, 0);
						text.setTextColor(Color.parseColor("#a9a9a9"));
						text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
						text.setGravity(Gravity.CENTER);
						texts.add(text);

						LinearLayout smallLayout=new LinearLayout(getBaseContext());
						smallLayout.setOrientation(LinearLayout.VERTICAL);
						smallLayout.setLayoutParams(params);
						smallLayout.setPadding(0, 0,0, 10);
						smallLayout.addView(text);
						mLayout.addView(smallLayout);
					}
					if(array!=null){
						Toast.makeText(getBaseContext(), "Got the deals", Toast.LENGTH_SHORT).show();
						for(int i=0; i<array.length(); i++) {
							JSONObject object = array.getJSONObject(i);

							Gson gson = new Gson();
							final Deal newDeal = new Deal();
							newDeal.setID(object.getInt(things[0]));
							newDeal.setRestaurantID(object.getString(things[1]));
							newDeal.setRestaurantName(object.getString(things[2]));
							newDeal.setPercentageDiscount(object.getInt(things[3]));
							newDeal.setAmountDiscount(object.getInt(things[4]));
							newDeal.setFreebie(object.getString(things[5]));
							newDeal.setCanvasText(object.getString(things[6]));
							newDeal.setMinimumAmount(object.getInt(things[7]));
							DateTime start = new DateTime();
							start.setDate(object.getString(things[8]));
							start.setTime(object.getString(things[10]));
							DateTime end = new DateTime();
							end.setDate(object.getString(things[9]));
							end.setTime(object.getString(things[11]));
							newDeal.setStartDateTime(start);
							newDeal.setEndDateTime(end);
							newDeal.setCuisine(object.getString(things[12]));
							deals.add(newDeal);

							LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
							ImageButton button = new ImageButton(getBaseContext());
							button.setLayoutParams(params);
							button.setImageResource(getResources().getIdentifier("soup"+(i+1), "drawable",getPackageName()));
							button.setScaleType(ScaleType.FIT_XY);
							button.setBackgroundColor(Color.TRANSPARENT);
							button.setAdjustViewBounds(true);

							final String deal = gson.toJson(newDeal);
							//Toast.makeText(getBaseContext(), deal, Toast.LENGTH_SHORT).show();

							button.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									//Toast.makeText(getBaseContext(), "Click!", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(getBaseContext(), com.project.waverr.DealPage.class);
									//Toast.makeText(getBaseContext(), deal, Toast.LENGTH_SHORT).show();
									intent.putExtra("deal", deal);
									intent.putExtra("login", login);
									global.setDeal(newDeal);
									startActivity(intent);
								}
							});
							buttons.add(button);

							TextView text = new TextView(getBaseContext());
							//text.setText(newDeal.getDetails());
							try {
								text.setText(object.getString(things[2]));
							}
							catch(JSONException e) {
								Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT).show();
							}
							text.setBackgroundResource(R.drawable.deal_details);
							text.setPadding(15,25, 15, 25);
							text.setTextSize(15);
							text.setTextColor(Color.WHITE);
							text.setLayoutParams(params);
							LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
							//FrameLayout smallLayout = new FrameLayout(getBaseContext());
							LinearLayout smallLayout=new LinearLayout(getBaseContext());
							smallLayout.setOrientation(LinearLayout.VERTICAL);
							smallLayout.setLayoutParams(params);
							smallLayout.setPadding(0, 0, 0, 10);
							LinearLayout smallLayout2=new LinearLayout(getBaseContext());
							smallLayout2.setOrientation(LinearLayout.VERTICAL);
							smallLayout2.setLayoutParams(params);
							smallLayout2.setPadding(0, 0, 0, -20);
							smallLayout2.addView(button);
							smallLayout.addView(smallLayout2);
							LinearLayout smallLayout3=new LinearLayout(getBaseContext());
							smallLayout3.setOrientation(LinearLayout.VERTICAL);
							smallLayout3.setLayoutParams(params2);
							smallLayout3.setPadding(24, 0, 24, 0);
							smallLayout3.addView(text);
							texts.add(text);
							smallLayout.addView(smallLayout3);
							mLayout.addView(smallLayout);
						}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//if (!mNavigationDrawerFragment.isDrawerOpen()) {
		// Only show items in the action bar relevant to this screen
		// if the drawer is not showing. Otherwise, let the drawer
		// decide what to show in the action bar.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.chinese_deal, menu);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		restoreActionBar();
		return true;
		//}
		//return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.refresh_deal) {
			finish();
			Intent intent = getIntent();
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			return true;
		}
		if (id == android.R.id.home) {
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
