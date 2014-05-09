package com.foodango;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.foodango.testing.BackEndServer;

import android.app.Application;
import android.content.Context;

/**
 * BritishGasApplication class Store global state in here
 * 
 * @author andy
 * 
 */
public final class FoodangoApplication extends Application
{
    // Analytics Constants

    private static final String HTTPS_PROTOCOL = "https";

    private static final String HTTP_PROTOCOL = "http";

    private static final String TAG = "BritishGasApplication";

    private static boolean cachingEnabled = true;

    private static String customerCareNumber;
    
    // HTTPCLient
    private HttpClient httpClient;

    // server url
    private BackEndServer server = BackEndServer.PRODUCTION;

    // testing flag
    private boolean testing = false;
    private boolean isSelfSignedCertAllowed = false;

    // test data for unit testing
   // private Bundle testData;
    
    private static Context context;

    /**
     * @deprecated Pass Context in with calls instead of using static access
     * @return
     */
    @Deprecated
    // shouldn't access Context statically, context should be passed in from requesting instance
    public static Context getAppContext()
    {
        return context;
    }

    @Deprecated
    // shouldn't access Context statically, context should be passed in from requesting instance
    public static FoodangoApplication getInstance()
    {
        return (FoodangoApplication) context;
    }

    public static String getCustomerCareNumber()
    {
        return customerCareNumber;
    }

    public static boolean isCachingEnabled()
    {
        return cachingEnabled;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();

        // store context before we do anything else
        FoodangoApplication.context = this.getApplicationContext();

     //   String testing = getString(R.string.testing);
        String testing = "true";
        if (testing != null && Boolean.parseBoolean(testing))
        {
            this.testing = true;
        }
        
 /*       String selfSignedCertAllowed = getString(R.string.selfSignedCertAllowed);
        if (selfSignedCertAllowed != null && Boolean.parseBoolean(selfSignedCertAllowed)) {
            this.isSelfSignedCertAllowed = true;
        }*/

        // Create HTTPClient
        httpClient = createHttpClient();

    //    PropertiesHelper.setLoginStatus(LoginStatus.LOGGED_OUT);
        
        // trigger MOTD check
       // startService(new Intent(MessageOfTheDaySvc.INTENT));

    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();

        shutdownHttpClient();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();

        // Was causing NetworkOnMainThread Exception
        //shutdownHttpClient();
    }

    public void FoodangoApplication() {
		// TODO Auto-generated constructor stub
	}

    public HttpClient getHttpClient()
    {
        if (httpClient == null)
        {
            httpClient = createHttpClient();
        }
        return httpClient;
    }

    public void refreshHttpClient()
    {
        shutdownHttpClient();
        createHttpClient();
    }
    
    private HttpClient createHttpClient()
    {
        Context ctx = getApplicationContext();

        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
        HttpProtocolParams.setUseExpectContinue(params, true);

    //    String connectionTimeoutString = ctx.getString(R.string.connectionTimeout);
    //    int connectionTimeOut = Integer.parseInt(connectionTimeoutString);
        int connectionTimeOut = 120000;

    //    String soTimeoutString = ctx.getString(R.string.socketTimeout);
    //    int soTimeOut = Integer.parseInt(soTimeoutString);
        int soTimeOut = 120000;

        HttpConnectionParams.setConnectionTimeout(params, connectionTimeOut);
        HttpConnectionParams.setSoTimeout(params, soTimeOut);

        
        // Default Un Pinned Connection Manager
        SchemeRegistry schReg = new SchemeRegistry();
        // HTTP
        schReg.register(new Scheme(HTTP_PROTOCOL, PlainSocketFactory.getSocketFactory(), 80));

        // HTTPS - secure socket factory
        schReg.register(new Scheme(HTTPS_PROTOCOL, SSLSocketFactory.getSocketFactory(), 443));

        
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
        
        return new DefaultHttpClient(conMgr, params);
    }

    private void shutdownHttpClient()
    {
        if (httpClient != null && httpClient.getConnectionManager() != null)
        {
            httpClient.getConnectionManager().shutdown();
            httpClient = null;
        }
    }

  /*  public BackEndServer getBackEndServer()
    {
    	if (isTesting())
        {
            int backendServerIndex = PropertiesHelper.getIntegerProperty(Values.BACKEND_SERVER, server.ordinal());

            BackEndServer selectedServer = server;
            if (backendServerIndex != -1)
            {
                selectedServer = BackEndServer.values()[backendServerIndex];
            }

            this.server = selectedServer;
        }
        return server;
    }*/

    public void setBackEndServer(BackEndServer backEnd)
    {
        int index = BackEndServer.indexOf(backEnd);

   //     PropertiesHelper.setIntegerProperty(Values.BACKEND_SERVER, index);
    //    PropertiesHelper.setLongProperty(Values.MOTD_LAST_CHECKED, 0);//reset MOTD check when environment changes
        
        this.server = backEnd;
    }

    public static boolean isTesting()
    {
        return getInstance().testing;
    }
    
    public static boolean isSelfSignedCertPermitted() {
        return getInstance().isSelfSignedCertAllowed;
    }

    /*
    @Deprecated
    public static Bundle getTestData()
    {
        Bundle testData = null;
        if (context != null)
        {
            testData = BritishGasApplication.getInstance().testData;
        }
        return testData;
    }

    @Deprecated
    public static void setTestData(Bundle data)
    {
        if (context != null)
        {
            BritishGasApplication.getInstance().testData = data;
        }
    }

    @Deprecated
    public static boolean hasTestData()
    {
        return getTestData() != null;
    }
*/
}
