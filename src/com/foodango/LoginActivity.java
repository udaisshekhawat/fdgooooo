/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.foodango;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.LoggingBehavior;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.foodango.R;
import com.foodango.constant.APIConstants;
import com.foodango.constant.ApplicationConstants;
import com.foodango.model.LoginResponseSuccessResponse;
import com.foodango.net.GetJSONListener;
import com.foodango.net.JSONClient;
import com.google.gson.Gson;

public class LoginActivity extends Activity implements GetJSONListener {
	private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";

	EditText mLoginEditText;
	Button btn_login;
	Button btn_fblogin;
	String userName = null;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login_screen);

		mLoginEditText = (EditText) findViewById(R.id.txt_username);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_fblogin = (Button) findViewById(R.id.btn_fblogin);
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				login(userName);
				// check login is valid or not

				// TODO Auto-generated method stub
			//	Intent intent = new Intent(LoginActivity.this, QRActivity.class);
			//	startActivity(intent);

			}
		});

		userName = mLoginEditText.getText().toString();
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);

		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}
			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				session.openForRead(new Session.OpenRequest(this)
						.setCallback(statusCallback));
			}
		}

		updateView();
	}

	private void login(String userName) {
		if (userName != null) {
			JSONObject holder = new JSONObject();
			try {
				holder.put(ApplicationConstants.LOGIN_USER_VALUE, userName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JSONClient client = new JSONClient(this, this, holder,
					APIConstants.LOGIN_URL, true, null,
					ApplicationConstants.LOGIN_CODE, true);
			client.execute();
		} else {
			Toast.makeText(this, " PLEASE ENTER SOME VALUE ",
					Toast.LENGTH_SHORT);
		}
	}

	@Override
	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
	}

	private void updateView() {
		Session session = Session.getActiveSession();
		if (session.isOpened()) {
			// textInstructionsOrLink.setText(URL_PREFIX_FRIENDS +
			// session.getAccessToken());
			// buttonLoginLogout.setText(R.string.logout);
			// buttonLoginLogout.setOnClickListener(new OnClickListener() {
			// public void onClick(View view) { onClickLogout(); }
			// });
			System.out.println("*****login****");
		} else {
			btn_fblogin.setOnClickListener(new OnClickListener() {
				public void onClick(View view) {
					onClickLogin();
				}
			});
			System.out.println("*****login failed****");
		}
	}

	private void onClickLogin() {
		Session session = Session.getActiveSession();
		if (!session.isOpened() && !session.isClosed()) {
			session.openForRead(new Session.OpenRequest(this)
					.setCallback(statusCallback));
		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	private void onClickLogout() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			session.closeAndClearTokenInformation();
		}
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			updateView();
		}
	}

	@Override
	public void onRemoteCallComplete(String json, int code) {
		Log.d("LoginActivity"," ******* JSON Response ********* : "+json);
	//	try {
	//		Gson gson = new Gson();
	//		LoginResponseSuccessResponse response = gson.fromJson(json,
	//				LoginResponseSuccessResponse.class);
			

	//		if (code == ApplicationConstants.LOGIN_CODE) {

				Log.d("LoginActivity"," ******* JSON Response Success ********* ");
				
				Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
				startActivity(intent);

	//		} else {

	//			Log.d("LoginActivity"," ******* JSON Response Else ********* ");
	//		}
	//	} catch (Exception e) {
			// Util.toast("", "");
	//	}
	}
}
