package com.foodango.service.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.foodango.FoodangoApplication;
import com.foodango.constant.APIConstants;
import com.foodango.constant.ApplicationConstants;
import com.foodango.service.helper.ServerSideSessionManager;
import com.foodango.service.ServiceException;

import android.app.Application;
import android.os.Build;
import android.util.Log;

/**
 * 
 * @author andy
 * 
 */
public abstract class BaseWebService
{
	protected static final String OCTET_STREAM_CONTENT_TYPE = "application/octet-stream";

	/**
	 * To be Implemented by custom webservice to handle response stream
	 * 
	 * @author andy
	 * 
	 */
	public abstract class WebserviceParser
	{
		abstract void parse(InputStream inputStream) throws ServiceException;

		public void setContentTypeHeaders(Header[] contentTypes)
		{
			// override if interested;
		}
	}

	private ServerSideSessionManager sessionManager;

	// common parameters
	public final static String CLIENT_VERSION_PARAM = "clientVersion";
	public final static String CHANNEL_PARAM = "channel";
	public final static String DEVICE_VERSION_PARAM = "deviceVersion";
	public final static String IOS_VERSION_PARAM = "iOSVersion";
	public final static String CONTRACT_ACCOUNT_NUMBER_PARAM = "contractAccountNumber";
	public final static String ACCOUNT_NUMBER = "accountNumber";
	public final static String ACCOUNT_TYPE = "accountType";

	protected static final String JSON_RESPONSE_PARAM = "responseType";
	protected static final String RESPONSE_TYPE = "JSON";

	public static final String UTF_8 = "UTF-8";
	public static final String TEXT_HTML_CONTENT_TYPE = "text/html";
	public static final String TEXT_XML_CONTENT_TYPE = "text/xml";
	public static final String TEXT_PLAIN_CONTENT_TYPE = "text/plain";
	public static final String JSON_CONTENT_TYPE = "application/json";
	public static final String PDF_CONTENT_TYPE = "application/pdf";

	private static final String TAG = "BaseWebService";

	private static final String GENERAL_ERR_MSG = "We are currently unable to deal with your request. Please try later.";

	private HttpClient httpClient;

	private String serverUrl;

	private FoodangoApplication bgApp;

	public BaseWebService(Application app)
	{
		if (app == null)
		{
			throw new RuntimeException("App is Null");
		}

		if (app instanceof FoodangoApplication)
		{
			bgApp = (FoodangoApplication) app;
		}
		else
		{
			// HACK to Fix Unit Tests
			bgApp = FoodangoApplication.getInstance();
		}

		if (bgApp != null)
		{
			this.httpClient = bgApp.getHttpClient();
			this.serverUrl = APIConstants.SERVER_URL_TEST;
		}
		else
		{
			Log.e(TAG, "Missing Application Context!");
		}
	}

	/**
	 * 
	 * @param postRequest
	 * @param params
	 * @param parser
	 * @param testDataKey
	 * @throws ServiceException
	 */
	public void executeAndParseResponse(HttpRequestBase postRequest, List<NameValuePair> params,
			WebserviceParser parser, String testDataKey) throws ServiceException
	{
		executeAndParseResponse(postRequest, params, parser, testDataKey, new String[]
		{ JSON_CONTENT_TYPE }, true);
	}

	public void executeAndParseResponse(HttpRequestBase postRequest, List<NameValuePair> params,
			WebserviceParser parser, String testDataKey, boolean serviceRequired)
			throws ServiceException
	{
		executeAndParseResponse(postRequest, params, parser, testDataKey, new String[]
		{ JSON_CONTENT_TYPE }, serviceRequired);
	}

	/**
	 * Improved execute and parse architecture reduces duplicated code
	 * 
	 * @param post
	 * @param params
	 * @param parser
	 * @throws ServiceException
	 */
	public void executeAndParseResponse(HttpRequestBase request, List<NameValuePair> params,
			WebserviceParser parser, String testDataKey, String[] contentTypes,
			boolean sessionRequired) throws ServiceException
	{
		if (sessionRequired)
		{
		//	Log.d(TAG, "OAM Session Is Required");
		//	sessionManager.checkSession();
		}
		else
		{
		//	Log.d(TAG, "OAM Session Is NOT Required");
		}

		InputStream inStream = null;

		byte[] testData = null;

		// When Run by Unit Tests we have Mock Services to provide data
	/*	if (getBGApplication().hasMockServices())
		{
			Log.w(TAG, "HAS MOCK SERVICES");
			MockServiceRepository mocks = getBGApplication().getMockService();

			testData = mocks.executeMockService(request, params, testDataKey);

			Log.w(TAG, "TEST DATA FOR KEY: " + testDataKey + " : " + (testData != null));
		}*/

		// When running Normally Data is retrieved from HTTP Call
		if (testData == null)
		{
			HttpResponse response = null;
	//		try
	//		{
		//		Log.d(TAG, "Attempting First HTTP Request");
				response = executeRequest(request, params);
	//		}
	/*		catch (OAMSessionException u)
			{
				Log.d(TAG, "OAM SESSION EXCEPTION");
				try
				{
					Log.d(TAG, "REFRESH SESSION");
					sessionManager.refreshSession();

					Log.d(TAG, "Attempting Second HTTP Request");
					response = executeRequest(request, params);
				}
				catch (OAMSessionException ue)
				{
					Log.d(TAG, "OAM SESSION EXCEPTION - GIVING UP");
					handleUnauthorized();
				}
			}
*/
			if (contentTypes != null)
			{
				try
				{
					assertResponseContentType(response, contentTypes);
				}
				catch (IOException e)
				{
					throw new ServiceException(GENERAL_ERR_MSG, e);
				}
			}

			inStream = getStreamFromResponse(response);

			// allows parser to customise behaviour for certain content types,
			// see PDF download
			parser.setContentTypeHeaders(response.getHeaders(HTTP.CONTENT_TYPE));

			// Only log test data if we are testing 'live' services, ignore mock
			// data
		}
		else
		{
			inStream = new ByteArrayInputStream(testData);
		}

		// Parse result
		parser.parse(inStream);
		
		// Application Security: ID - 900878 - Unreleased Resource
		try {
			inStream.close();
		} catch (IOException e) {
		}

	}

	public static void addParam(List<NameValuePair> params, String name, String value)
	{
		params.add(new BasicNameValuePair(name, value));
	}

	public static void addMandatoryParam(List<NameValuePair> params, String name, String value)
			throws ServiceException
	{
		if (value != null && value.length() > 0)
		{
			addParam(params, name, value);
		}
		else
		{
		// Application Security: ID - 9300877 - Privacy Violation
		//	throw new ServiceException("Missing Mandatory Parameter: " + name);
			String exceptionMessage = "Service Exception: Missing Parameter";
				exceptionMessage = "Missing Mandatory Parameter: " + name;
			throw new ServiceException(exceptionMessage);
		}
	}

	protected URI createURI(String path)
	{
		URI serviceUri = null;

		StringBuffer buf = new StringBuffer();
		buf.append(getServerUrl());
		buf.append(path);
		try
		{
			serviceUri = new URI(buf.toString());

			Log.d(TAG, "URI: " + serviceUri);
		}
		catch (URISyntaxException e)
		{
			Log.e(TAG, "URI Exception: " + e.getMessage());
		}

		return serviceUri;
	}

	private void debugCall(HttpPost postRequest, UrlEncodedFormEntity encodedFormData)
	{
		try
		{
			System.out
					.println("POST ******************************************************************************************");
			System.out.println("  URI: " + postRequest.getURI());

			if (encodedFormData != null)
			{
				System.out.print("  BODY: ");
				encodedFormData.writeTo(System.out);
				System.out.println("\n");
			}
			System.out
					.println("***********************************************************************************************");

		}
		catch (IOException e)
		{
			Log.e(TAG, "Exception debugging service call", e);
		}
	}

	protected void assertResultContentTypeIsJSON(HttpResponse response) throws IOException
	{
		assertResponseContentType(response, new String[]
		{ JSON_CONTENT_TYPE });
	}

	protected void assertResponseContentType(HttpResponse response, String[] types)
			throws IOException
	{
		if (!checkResponseContentType(response, types))
		{
			Log.w(TAG, "== UNEXPECTED CONTENT TYPE - Expected: [" + Arrays.toString(types)
					+ "] ==");
			throw new IOException("Unexpected Content Type");
		}

		Log.d(TAG, "== Result Content-Type Accepted ==");
	}

	protected boolean checkResponseContentType(HttpResponse response, String[] contentTypes)
	{
		Header[] headers = response.getHeaders(HTTP.CONTENT_TYPE);

		for (String type : contentTypes)
		{
			for (Header header : headers)
			{
				if (header.getValue().contains(type))
				{
					return true;
				}
				else
				{
					Log.w(TAG, "== Content-Type [" + header.getValue() + "] NOT MATCHED ");
				}
			}
		}

		return false;
	}

	protected HttpResponse executeRequest(HttpRequestBase request, List<NameValuePair> params)
			throws ServiceException
	{
	/*	if (!Connectivity.isNetworkAvailable(getBGApplication()))
		{
			Log.d(TAG, "Unable to connect to the server");
			throw new ServiceException("Unable to connect to the server");
		}*/

		if (params != null && !params.isEmpty())
		{
			try
			{
				// HANDLE POST
				if (request instanceof HttpPost)
				{
					Log.d(TAG, "EXECUTE POST REQUEST");

					UrlEncodedFormEntity encodedFormData = null;
					encodedFormData = new UrlEncodedFormEntity(params, HTTP.UTF_8);
					encodedFormData.setChunked(false);

					((HttpPost) request).setEntity(encodedFormData);

				/*	if (BritishGasApplication.isTesting())
					{
						debugCall((HttpPost) request, encodedFormData);
					}*/
				}
				else if (request instanceof HttpGet)
				{
					Log.d(TAG, "EXECUTE GET REQUEST");

					String url = request.getURI().toString();

					// Get Params
					String paramString = URLEncodedUtils.format(params, HTTP.UTF_8);
					if (!url.endsWith("?"))
					{
						url += "?";
					}

					url += paramString;

					// replacing passed in request here
					request = new HttpGet(createURI(url));

					Log.d(TAG, "GET: " + url);

				}
				else if (request instanceof HttpHead)
				{
					Log.d(TAG, "EXECUTE HEAD REQUEST");
				}
			}
			catch (UnsupportedEncodingException e)
			{
				// this should never happen as Encoding is from HTTP.UTF_8
				// constant
				Log.e(TAG, "UnsupportedEncoding", e);
				throw new ServiceException("Unsupported Encoding in form params", e);
			}
		}

		HttpResponse response;
		try
		{
			response = executeWithRetry(request);
		}
		catch (InterruptedException e)
		{
			throw new ServiceException("Cancelled");
		}

	/*	if (BritishGasApplication.isTesting())
		{
			Header[] headers = response.getAllHeaders();
			for (Header header : headers)
			{
				Log.d(TAG, "Header :" + header.getName() + "-->" + header.getValue());
			}
		}*/

		checkResponse(response);

		return response;
	}

	public URI createGetURI(String uri, List<NameValuePair> params) throws ServiceException
	{
		String paramString = URLEncodedUtils.format(params, UTF_8);

		try
		{
			return new URI(uri + "?" + paramString);
		}
		catch (URISyntaxException e)
		{
			Log.e(TAG, "URISyntaxException", e);
			throw new ServiceException(GENERAL_ERR_MSG);
		}
	}

	/**
	 * Executes a head request...
	 * 
	 * @param headRequest
	 * @param params
	 * @return
	 * @throws ServiceException
	 * @throws InterruptedException
	 * @throws
	 */
	protected HttpResponse executeHeadRequest(HttpHead headRequest, List<NameValuePair> params)
			throws ServiceException, InterruptedException
	{

	/*	if (!Connectivity.isNetworkAvailable(getBGApplication()))
		{
			throw new ServiceException("Unable to connect to the server");
		}
*/
		String paramString = URLEncodedUtils.format(params, UTF_8);

		URI uri = headRequest.getURI();

		try
		{
			URI newUri = new URI(uri + "?" + paramString);
			headRequest.setURI(newUri);
		}
		catch (URISyntaxException e)
		{
			// Application Security: ID - 9300868 - System Information Leak - the stack trace should be printed for Test Build.
				e.printStackTrace();
		}

		HttpResponse response = executeWithRetry(headRequest);

	/*	if (BritishGasApplication.isTesting())
		{
			Header[] headers = response.getAllHeaders();
			for (Header header : headers)
			{
				Log.d(TAG, "Header Header ------ :" + header.getName() + "-->" + header.getValue());
			}
		}*/
		
		checkResponse(response);

		return response;
	}

	private void checkResponse(HttpResponse response) throws ServiceException
	{
		int statusCode = response.getStatusLine().getStatusCode();

		switch (statusCode)
		{
			case HttpStatus.SC_OK:
				Log.d(TAG, "HTTP Status Code OK");
				break;

			case HttpStatus.SC_UNAUTHORIZED:
				Log.w(TAG, "HTTP Status UNAUTHORIZED " + statusCode);
				// handleUnauthorized();
		//		throw new OAMSessionException();

			default:
				Log.w(TAG, "HTTP Status " + statusCode);
				Log.w(TAG, "Request didn't return Success: "
						+ response.getStatusLine().toString());

				throw new ServiceException(statusCode,
					//	bgApp.getString(R.string.base_webservice_generic_failure));
						"We cannot process your request right now, please try later");
		}
	}

/*	private void handleUnauthorized()
	{
		PropertiesHelper.setLoginStatus(LoginStatus.LOGGED_OUT);
	}*/

	private HttpResponse executeWithRetry(HttpRequestBase request) throws ServiceException,
			InterruptedException
	{
		int tries = 0;
	//	int maxTries = Integer.parseInt(bgApp.getString(R.string.maxRetries));
		int maxTries = 1;

		HttpResponse response = null;

		boolean finished = false;

		while (!finished)
		{
			tries++;

			try
			{
				Log.d(TAG, "Execute attempt: " + tries);
				response = httpClient.execute(request);

				finished = true;
			}
			catch (ClientProtocolException e)
			{
				throw new ServiceException(e);
			}
			catch (IOException e)
			{
				Log.w(TAG, "Request failed; " + e.getMessage());

				if (Thread.interrupted())
				{
					throw new InterruptedException();
				}

				if (tries >= maxTries)
				{
					Log.d(TAG, "Numer of Attempts Exceeded, throwing ServiceException");
					throw new ServiceException("Unable to connect to the server");
				}
			}
		}

		return response;
	}

	protected InputStream getStreamFromResponse(HttpResponse response) throws ServiceException
	{
		InputStream instream = null;

		HttpEntity entity = response.getEntity();

		if (entity != null)
		{
			try
			{
				instream = entity.getContent();

			}
			catch (IllegalStateException e)
			{
				throw new ServiceException("Illegal state getting stream from response", e);
			}
			catch (IOException e)
			{
				throw new ServiceException("IOException getting webservice reply content", e);
			}
		}

		return instream;
	}

	private String getServerUrl()
	{
		return this.serverUrl;
	}

	protected String getClientVersion()
	{
		String clientVersion = "";
	/*	if (bgApp != null)
		{
			clientVersion = bgApp.getString(R.string.clientVersion);
		}*/
		return clientVersion;
	}

	protected String getChannelName()
	{
		String channelName = "";
	/*	if (bgApp != null)
		{
			channelName = bgApp.getString(R.string.channelName);
		}*/
		return channelName;
	}

	protected String getDeviceVersion()
	{
		// 40 chars
		return Build.DEVICE;
	}

	protected String getOSVersion()
	{
		return Build.VERSION.RELEASE;
	}

/*	protected BritishGasApplication getBGApplication()
	{
		return bgApp;
	}*/

	protected static boolean parseBoolean(String booleanString)
	{
		return (booleanString != null && booleanString.equalsIgnoreCase("TRUE"));
	}

	protected static double parseDouble(String doubleString)
	{
		double result = 0d;
		if (doubleString != null && doubleString.length() > 0)
		{
			try
			{
				result = Double.parseDouble(doubleString);
			}
			catch (NumberFormatException e)
			{
				Log.e(TAG, "Number format exception parsing double: " + doubleString);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param is
	 * @return String
	 * @throws ServiceException
	 */
	protected static JSONObject convertStreamToJSONObject(InputStream is) throws JSONException
	{
		StringBuilder sb = new StringBuilder();
		if (is != null)
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));

			String line = null;
			try
			{
				while ((line = reader.readLine()) != null)
				{
					sb.append(line + "\n");
				}
			}
			catch (IOException e)
			{
				Log.e(TAG, "IOException",e);
			}
			finally
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					Log.e(TAG, "IOException2",e);
				}
			}

		}

		return new JSONObject(sb.toString());

	}

	protected static String getStringFromJSONWithDefault(JSONObject json, String key,
			String defaultValue)
	{
		String result = defaultValue;

		if (json.has(key))
		{
			try
			{
				result = json.getString(key);
			}
			catch (JSONException e)
			{
				Log.e(TAG, "JSON Exception reading string ]" + key + "[", e);
			}
		}

		return result;
	}

	protected static Date getDateFromJSONWithDefault(JSONObject json, String key, String pattern)
	{
		String dateString = getStringFromJSONWithDefault(json, key, null);

		Date date = null;

		if (dateString != null)
		{

			SimpleDateFormat f = new SimpleDateFormat(pattern, Locale.ENGLISH);

			try
			{
				date = f.parse(dateString);
			}
			catch (ParseException e)
			{
				Log.e(TAG, "Failed to parse date: " + dateString + " : pattern " + pattern);
			}

		}
		else
		{
			Log.w(TAG, "Returning null date for: " + dateString + "  pattern: " + pattern);
		}

		return date;
	}

	protected static double getDoubleFromJSONWithDefault(JSONObject json, String key,
			double defaultValue)
	{
		double result = defaultValue;

		if (json.has(key))
		{
			try
			{
				result = json.getDouble(key);
			}
			catch (JSONException e)
			{
				Log.e(TAG, "JSON Exception reading double ]" + key + "[", e);
			}
		}

		return result;
	}

	protected static int getIntegerFromJSONWithDefault(JSONObject json, String key, int defaultValue)
	{
		int result = defaultValue;

		if (json.has(key))
		{
			try
			{
				String value = json.getString(key);
				if (value != null && value.length() > 0)
				{
					result = json.getInt(key);
				}
			}
			catch (JSONException e)
			{
				Log.e(TAG, "JSON Exception reading integer ]" + key + "[", e);
			}
		}

		return result;
	}

	protected static boolean getBooleanFromJSONWithDefault(JSONObject json, String key,
			boolean defaultValue)
	{
		boolean result = defaultValue;

		if (json.has(key))
		{
			try
			{
				String value = json.getString(key);
				if (value != null)
				{
					result = value.equalsIgnoreCase("ON") || value.equalsIgnoreCase("TRUE")
							|| value.equalsIgnoreCase("YES");
				}
			}
			catch (JSONException e)
			{
				Log.e(TAG, "JSON Exception reading boolean ]" + key + "[", e);
			}
		}

		return result;
	}
	
	protected static String commaSeparatedStringFromArray(String[] values)
	{
		StringBuffer sb = new StringBuffer();
		if (values != null)
		{
			for (int i = 0; i < values.length; i++)
			{
				sb.append(values[i]);
				if (i < values.length - 1)
				{
					sb.append(",");
				}
			}
		}

		return sb.toString();
	}

	protected static void handleInterrupted() throws ServiceException
	{
		Thread.yield();
		if (Thread.interrupted())
		{
			throw new ServiceException("Cancelled");
		}
	}

	protected static JSONObject populateJSONObjectFromStream(InputStream inputStream)
			throws ServiceException
	{
		try
		{
			JSONObject jsonObject = convertStreamToJSONObject(inputStream);

			return jsonObject;
		}
		catch (JSONException e)
		{
			Log.e(TAG, "JSON problem", e);
			throw new ServiceException(GENERAL_ERR_MSG, e);
		}
		finally
		{
			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					Log.e(TAG, "Exception closing input stream");
				}
			}
		}
	}

}
