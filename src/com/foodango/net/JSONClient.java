package com.foodango.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import com.foodango.R;
import com.foodango.constant.ApplicationConstants;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

public class JSONClient extends AsyncTask<String, Void, String> {
	ProgressDialog progressDialog;
	GetJSONListener getJSONListener;
	Context mCurContext;
	static JSONObject mJsonObject;
	File cacheDir;
	boolean mIsMultipart = false;
	static String mUrl;
	static boolean mIsLogin = false;
	static HashMap mLoginValues;
	static int mCode;
	static List<NameValuePair> mNameValuePairs;
	static boolean multipartFlag = false;
	static boolean isPostRequest = false;

	// for login

	public JSONClient(Context context, GetJSONListener listener,
			JSONObject jsonObject, String url, boolean isLogin,
			HashMap loginValues, int code, boolean isPostRequest) {

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			ApplicationConstants.cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"Android/data/com.foodango/files");
		else
			ApplicationConstants.cacheDir = context.getCacheDir();

		if (!ApplicationConstants.cacheDir.exists()) {

			ApplicationConstants.cacheDir.mkdirs();
		}

		this.getJSONListener = listener;
		mJsonObject = jsonObject;
		mCurContext = context;
		mUrl = url;
		mIsLogin = isLogin;
		mLoginValues = loginValues;
		mCode = code;
		isPostRequest = isPostRequest;
		// System.out.println("***map***"+loginValues);

	}

	public JSONClient(Context context, GetJSONListener listener,
			JSONObject jsonObject, String url, boolean isLogin, int code) {

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			ApplicationConstants.cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"Android/data/com.foodango/files");
		else
			ApplicationConstants.cacheDir = context.getCacheDir();

		if (!ApplicationConstants.cacheDir.exists()) {
			ApplicationConstants.cacheDir.mkdirs();
		}

		this.getJSONListener = listener;
		mJsonObject = jsonObject;
		mCurContext = context;
		mUrl = url;
		mIsLogin = isLogin;

		mCode = code;

	}

	public JSONClient(Context context, GetJSONListener listener,
			List<NameValuePair> nameValuePairs, String url,
			boolean multipartFlag, int code) {

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED))
			ApplicationConstants.cacheDir = new File(
					android.os.Environment.getExternalStorageDirectory(),
					"Android/data/com.cognizant.appstore/files");
		else
			ApplicationConstants.cacheDir = context.getCacheDir();

		if (!ApplicationConstants.cacheDir.exists()) {

			ApplicationConstants.cacheDir.mkdirs();
		}

		this.getJSONListener = listener;
		mNameValuePairs = nameValuePairs;
		mCurContext = context;
		mUrl = url;

		mCode = code;
	}

	public static String convertStreamToString(InputStream is)
			throws IOException {
		/*
		 * To convert the InputStream to String we use the Reader.read(char[]
		 * buffer) method. We iterate until the Reader return -1 which means
		 * there's no more data to read. We use the StringWriter class to
		 * produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024 * 4];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	// get method
	public static String connect() {
		String result = null;
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpget = new HttpGet(mUrl);

		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i("Praeda", response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				result = convertStreamToString(instream);
				// now you have the string representation of the HTML request
				instream.close();
			}

		} catch (Exception e) {

		}
		return result;
	}

	public static String connect1() {
		// check already data exists are or not??

		InputStream instream = null;
		String result = null;

		HttpParams my_httpParams = new BasicHttpParams();
		HttpClient httpclient;

		HttpConnectionParams.setConnectionTimeout(my_httpParams, 35000);
		HttpConnectionParams.setSoTimeout(my_httpParams, 20000);
		httpclient = new DefaultHttpClient(my_httpParams);

		trustEveryone();

		// Prepare a request object

		HttpPost httpPostRequest = new HttpPost(mUrl);

		// Execute the request
		HttpResponse response;
		try {

			httpPostRequest.setHeader("Content-Type",
					"application/json; charset=utf-8");

			StringEntity se = null;
			if (mJsonObject != null) {
				se = new StringEntity(mJsonObject.toString());
				httpPostRequest.setEntity(se);
			}

			response = httpclient.execute(httpPostRequest);

			HttpEntity entity = response.getEntity();

			if (entity != null) {

				// A Simple JSON Response Read
				instream = entity.getContent();
				result = convertStreamToString(instream);

				// Closing the input stream will trigger connection release
				instream.close();
				// Log.i("result", "result: " + result);
				return result;

			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 11-16 07:02:36.706: W/System.err(1515):
			// java.net.SocketTimeoutException

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// add exception value

			e.printStackTrace();
		}

		return result;
	}

	@Override
	public void onPreExecute() {
		progressDialog = new ProgressDialog(mCurContext);
		Resources res = mCurContext.getResources();
		progressDialog.setMessage(res.getString(R.string.msg_loading));
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();

	}

	@Override
	protected String doInBackground(String... urls) {
		if (isPostRequest)
			return connect();
		else
			return connect1();
	}

	@Override
	protected void onPostExecute(String json) {
		progressDialog.dismiss();

		getJSONListener.onRemoteCallComplete(json, mCode);

	}

	// https connection code
	public static void trustEveryone() {
		try {
			HttpsURLConnection
					.setDefaultHostnameVerifier(new HostnameVerifier() {
						public boolean verify(String hostname,
								SSLSession session) {
							return true;
						}
					});
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new X509TrustManager[] { new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}
			} }, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(context
					.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}