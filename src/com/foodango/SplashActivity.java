package com.foodango;


import com.foodango.R;
import com.foodango.constant.ApplicationConstants;
import com.foodango.net.NetworkMonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {
	private Handler mHandler = new Handler();
	private static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = this;
		setContentView(R.layout.splash_screen);
		mHandler.postDelayed(mCountdownTask, 2000);

	}

	private Runnable mCountdownTask = new Runnable() {
		public void run() {

			mHandler.removeCallbacks(this);
			runOnUiThread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					NetworkMonitor networkmonitor = NetworkMonitor
							.getNetworkMonitor();
					//if (networkmonitor.isAcivated(context)) {
						finish();
						Intent mainscreen = new Intent(context,
								LoginActivity.class);
						startActivity(mainscreen);

				/*	} else {

						// prepare the alert box
						AlertDialog.Builder alertbox = new AlertDialog.Builder(
								context);

						// set the message to display
						alertbox.setMessage(ApplicationConstants.NETWORK_NOT_FOUND);

						// add a neutral button to the alert box and assign a
						// click listener
						alertbox.setNeutralButton("Ok",
								new DialogInterface.OnClickListener() {

									// click listener on the alert box
									public void onClick(DialogInterface arg0,
											int arg1) {
										// the button was clicked
										// Toast.makeText(getApplicationContext(),
										// "OK button clicked",
										// Toast.LENGTH_LONG).show();
									}
								});

						// show it
						alertbox.show();

					}*/

				}
			});

		}
	};
}
