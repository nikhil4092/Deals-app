package com.project.waverr;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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

		final ProgressDialog progressDialog = new ProgressDialog(ChineseDeal.this);
		progressDialog.setMessage("Getting deals...");
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(true);
		progressDialog.show();
		Bundle b=getIntent().getExtras();
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
						boolean network=isNetworkAvailable();
						if(network==false)
						{
							text.setText("Please check your internet connection and try again.");
						}
						//text.setText(newDeal.getDetails());
						else
						{
							text.setText("No "+s+" Deals Currently.Please check back later.");
						}
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
					for(int i=0; i<array.length(); i++) {
						JSONObject object = array.getJSONObject(i);

						Gson gson = new Gson();
						Deal newDeal = new Deal();
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
						//Toast.makeText(getBaseContext(), "Got the object", Toast.LENGTH_SHORT).show();
						LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						ImageButton button = new ImageButton(getBaseContext());
						button.setLayoutParams(params);
						button.setImageResource(getResources().getIdentifier("soup"+(i+1), "drawable",getPackageName()));
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
								intent.putExtra("login", login);
								startActivity(intent);
							}
						});
						/*String things[] = {
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
						};*/
						
						//text.setText(newDeal.getDetails());
						String dtext="";
						if(object.getString(things[6]).compareTo("")!=0&&object.getString(things[3])!=null)
						{
							dtext=object.getString(things[6]);
						}
						if(object.getString(things[5]).compareTo("")!=0&&object.getString(things[5])!=null)
						{
							dtext="Get "+object.getString(things[5])+" free on purchase of "+object.getString(things[7]);
						}
						if(object.getString(things[4]).compareTo("0")!=0&&object.getString(things[4])!=null)
						{
							dtext="Get Rs."+object.getString(things[4])+" off on a Minimum purchase of Rs."+object.getString(things[7]);
						}
						if(object.getString(things[3]).compareTo("0")!=0&&object.getString(things[3])!=null)
						{
							dtext="Get "+object.getString(things[3])+"% off on a Minimum purchase of Rs."+object.getString(things[7]);
						}
						
						TextView text = new TextView(getBaseContext());
						text.setText(object.getString(things[2])+"\n"+dtext+"\nDeal is valid till "+end.getDateTime());
						text.setBackgroundResource(R.drawable.deal_details);
						text.setPadding(15,25, 15, 25);
						text.setTextSize(15);
						text.setTextColor(Color.WHITE);
						text.setLayoutParams(params);
						texts.add(text);
						LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
						//FrameLayout smallLayout = new FrameLayout(getBaseContext());
						LinearLayout smallLayout=new LinearLayout(getBaseContext());
						smallLayout.setOrientation(LinearLayout.VERTICAL);
						smallLayout.setLayoutParams(params);
						smallLayout.setPadding(0, 0,0, 10);
						
						LinearLayout smallLayout2=new LinearLayout(getBaseContext());
						smallLayout2.setOrientation(LinearLayout.VERTICAL);
						smallLayout2.setLayoutParams(params);
						smallLayout2.setPadding(0, 0,0, -20);
						smallLayout.addView(smallLayout2);
						smallLayout2.addView(button);
						LinearLayout smallLayout3=new LinearLayout(getBaseContext());
						smallLayout3.setOrientation(LinearLayout.VERTICAL);
						smallLayout3.setLayoutParams(params2);
						smallLayout3.setPadding(24, 0,24, 0);
						smallLayout.addView(smallLayout3);
						smallLayout3.addView(text);

						mLayout.addView(smallLayout);
					}
					}
					//pullToRefreshView.onRefreshComplete();
					progressDialog.dismiss();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		};
		obtainer.execute(url);
		
		/*pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
		    @Override
		    public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
		        // Do work to refresh the list here.
		        obtainer.execute(url);
		    }
		});*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.chinese_deal, menu);
			
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
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
}
