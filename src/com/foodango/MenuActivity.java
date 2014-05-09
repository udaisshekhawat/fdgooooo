package com.foodango;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.foodango.R;
import com.foodango.adapter.CategoryAdapter;
import com.foodango.adapter.DishDisplayAdapter;
import com.foodango.constant.APIConstants;
import com.foodango.constant.ApplicationConstants;
import com.foodango.data.DishCategories;
import com.foodango.data.DishesDetails;
import com.foodango.data.GetDishCategoryList;
import com.foodango.data.GetDishes;
import com.foodango.net.GetJSONListener;
import com.foodango.net.JSONClient;
import com.foodango.net.NetworkMonitor;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MenuActivity extends FragmentActivity implements GetJSONListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	MenuActivity menuActivity;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	Resources res;
	public static LinkedList<DishesDetails> dishdetails = null;
	public  LinkedList<DishesDetails> dishcatdetails = null;
	public static LinkedList<DishCategories> dishcategories = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menuActivity = this;
		setContentView(R.layout.menu_screen);
		res = getResources();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		NetworkMonitor networkMonitor = NetworkMonitor.getNetworkMonitor();
		if (networkMonitor.isAcivated(menuActivity)) {
			JSONObject holder = new JSONObject();
			// try {
			// holder.put("RestID", "2");
			// } catch (JSONException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }f

			if (dishcategories == null && dishcategories == null) {
				System.out.println("*****url***"
						+ APIConstants.GET_DISH_CATEOGRY);
				JSONClient client = new JSONClient(menuActivity, menuActivity,
						holder, APIConstants.GET_DISH_CATEOGRY, false, null,
						ApplicationConstants.GET_DISH_CATEGORY, false);
				client.execute();
			} else {

				mSectionsPagerAdapter = new SectionsPagerAdapter(
						getSupportFragmentManager());

				// Set up the ViewPager with the sections adapter.
				mViewPager = (ViewPager) findViewById(R.id.pager);
				mViewPager.setAdapter(mSectionsPagerAdapter);
				mViewPager.setCurrentItem(0);
				mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			            @Override
			            public void onPageSelected(int position) {
			            	System.out.println("**hereis position**"+position);
			            	//here is the logic
			               
			            }
			        });

			}

		} else {
			// Util.toast(moreScreen, ApplicationConstants.NETWORK_NOT_FOUND);
			ApplicationConstants
					.toast(menuActivity, R.string.network_not_found);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {

		case R.id.home:
			finish();
			intent = new Intent(MenuActivity.this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		case R.id.menu:
			finish();
			intent = new Intent(MenuActivity.this, JavaActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		case R.id.myorder:
			finish();
			intent = new Intent(MenuActivity.this, AndroidActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		case R.id.callwaiter:
			finish();
			intent = new Intent(MenuActivity.this, MenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		case R.id.askforbill:
			finish();
			intent = new Intent(MenuActivity.this, MenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		case R.id.rewards:
			finish();
			intent = new Intent(MenuActivity.this, MenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.feedback:
			finish();
			intent = new Intent(MenuActivity.this, MenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		case R.id.logout:
			finish();
			intent = new Intent(MenuActivity.this, MenuActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
		
			Fragment fragment = new MySecondFragment(position);
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return dishcategories.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Gson gson = new Gson();

			final String CategoryName = dishcategories.get(position).cat_name;

			return CategoryName;

		}
	}

	@SuppressLint("ValidFragment")
	public class MySecondFragment extends Fragment {
		int position;

		public MySecondFragment(int position) {
			super();
			this.position = position;
			System.out.println("***getDetatis of Position***"+position);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// check for network

			View fragmentLayout = inflater.inflate(R.layout.listview,
					container, false);

			ListView listView1 = (ListView) fragmentLayout
					.findViewById(R.id.ListViewId);
			dishcatdetails=new LinkedList<DishesDetails>();
			String cat_id = dishcategories.get(position).cat_id;
					for (DishesDetails _dishdetails : dishdetails) {

						if (cat_id.equals(_dishdetails.cat_id)){
							dishcatdetails.add(_dishdetails);	
						}

					}
					

			//ApplicationConstants.addData();
			listView1.setCacheColorHint(0);
			listView1.setFastScrollEnabled(false);
			listView1.setAdapter(new DishDisplayAdapter(MenuActivity.this,
					dishcatdetails));
			listView1.setOnItemClickListener(listener);

			return fragmentLayout;
		}

		OnItemClickListener listener = new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// v=0 in=4 g=8
				View view1 = arg1.findViewById(R.id.menuLayout);
				View next = arg1.findViewById(R.id.nextArrow);
				View down = arg1.findViewById(R.id.downArrow);
				int v = next.getVisibility();
				if (v == 0) {
					next.setVisibility(View.GONE);
					down.setVisibility(View.VISIBLE);
					view1.setVisibility(View.VISIBLE);
				}
				if (v == 8) {
					next.setVisibility(View.VISIBLE);
					down.setVisibility(View.GONE);
					view1.setVisibility(View.GONE);
				}

			}

		};
	}

	@Override
	public void onRemoteCallComplete(String json, int code) {
		// TODO Auto-generated method stub
		try {

			Gson gson = new Gson();

			if (code == ApplicationConstants.GET_DISH_CATEGORY) {
				Log.d("Response"," json response : "+json);
				final GetDishCategoryList response = gson.fromJson(json,
						GetDishCategoryList.class);
				System.out.println("****response.record_count" + json);
				int record_count = response.record_count;
				System.out.println("****response.record_count"
						+ response.record_count);
				if (record_count != 0) {
					dishcategories = response.dishcategories;
					(new Thread(new Runnable() {

						@Override
						public void run() {

							Looper.prepare();
							System.out
									.println("****response.dishcategories.get(0).cat_id****"
											+ response.dishcategories.get(0).cat_id);
							getDishes();

							Looper.loop();
						}
					})).start();
				}
			} else if (code == ApplicationConstants.GET_DISH_DETAILS) {
				final GetDishes response = gson.fromJson(json, GetDishes.class);
				int record_count = response.record_count;
				if (record_count != 0) {
					dishdetails = response.dishdetails;
					mSectionsPagerAdapter = new SectionsPagerAdapter(
							getSupportFragmentManager());

					// Set up the ViewPager with the sections adapter.
					mViewPager = (ViewPager) findViewById(R.id.pager);
					mViewPager.setAdapter(mSectionsPagerAdapter);
					mViewPager.setCurrentItem(0);
					mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
				            @Override
				            public void onPageSelected(int position) {
				            	System.out.println("**hereis position**"+position);
				            	//here is the logic
				               
				            }
				        });
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			// Util.toast(menuActivity,
			// res.getString(R.string.error_in_connectivity));
		}
	}

	private void getDishes() {

		NetworkMonitor networkMonitor = NetworkMonitor.getNetworkMonitor();
		if (networkMonitor.isAcivated(menuActivity)) {
			JSONObject holder = new JSONObject();

			JSONClient client = new JSONClient(menuActivity, menuActivity,
					holder, APIConstants.GET_DISH_DETAILS, false,
					null, ApplicationConstants.GET_DISH_DETAILS, false);
			client.execute();

			// Util.toast(moreScreen, ApplicationConstants.NETWORK_NOT_FOUND);

		} else {
			ApplicationConstants
					.toast(menuActivity, R.string.network_not_found);
		}

	}

}
