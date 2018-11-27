package com.worldline.android.headyio.application;

import static com.android.volley.VolleyLog.TAG;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Request.Priority;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.worldline.android.headyio.R;
import com.worldline.android.headyio.commons.database.SQLiteDatabaseManager;
import com.worldline.android.headyio.commons.network.ResponseHandler;
import com.worldline.android.headyio.commons.network.VolleyConnectionRequest;
import com.worldline.android.headyio.utility.ProductsSingleton;
import es.dmoral.toasty.Toasty;
import java.util.Calendar;
import java.util.HashMap;
import org.json.JSONObject;


public class MyApplication extends MultiDexApplication
{
	private static RequestQueue mRequestQueue;
	private static MyApplication myApplication;
	@Override
	public void onCreate()
	{
		super.onCreate();
		myApplication=this;
		SQLiteDatabaseManager.getInstance(this).createResponseTable();
	}

	public static RequestQueue getRequestQueue()
	{
		if (mRequestQueue == null)
		{
			mRequestQueue = Volley.newRequestQueue(myApplication);
		}

		return mRequestQueue;
	}


	public static <T> void addToRequestQueue(Request<T> req, String tag)
	{
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		//RequestFuture<JSONObject> future = RequestFuture.newFuture();
		RetryPolicy policy = new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		req.setRetryPolicy(policy);
		getRequestQueue().add(req);

	}


	public static void getDataFromServer()
	{
		try
		{


				final VolleyConnectionRequest volleyConnectionRequest = new VolleyConnectionRequest(myApplication, myApplication.getResources().getString(R.string.server_url), Method.GET, 0,null, false, new HashMap<String, String>(), new HashMap<String, String>(), Priority.HIGH, new ResponseHandler()
				{

					@Override
					public void onPreExecute()
					{

					}

					@Override
					public void onSuccessfulResponse(JSONObject response, int rowid)
					{

						try
						{
							if (null!=response)
							{
								saveDataToDB(response.toString());
							}
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}

					@Override
					public void onFailureResponse(Exception e)
					{
						e.printStackTrace();
					}
				});
				volleyConnectionRequest.sendRequest();

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void saveDataToDB(String response)
	{
		Cursor cursor= SQLiteDatabaseManager.getInstance(myApplication).search("select "+SQLiteDatabaseManager.TIMESTAMP+" from "+ SQLiteDatabaseManager.RESPONSE_TABLE+";");
		Long currentTimestamp=Calendar.getInstance().getTimeInMillis();
		if(cursor.moveToFirst())
		{

			if(Long.parseLong(cursor.getString(cursor.getColumnIndex(SQLiteDatabaseManager.TIMESTAMP)))< currentTimestamp)
			{
				Gson gson = new Gson();
				ContentValues contentValues=new ContentValues();
				contentValues.put(SQLiteDatabaseManager.TIMESTAMP,currentTimestamp);
				contentValues.put(SQLiteDatabaseManager.RESPONSE, response);
				SQLiteDatabaseManager.getInstance(myApplication).insert(SQLiteDatabaseManager.RESPONSE_TABLE,contentValues);
			}

		}
		else
		{
			ContentValues contentValues=new ContentValues();
			contentValues.put(SQLiteDatabaseManager.TIMESTAMP,currentTimestamp);
			contentValues.put(SQLiteDatabaseManager.RESPONSE, response);
			SQLiteDatabaseManager.getInstance(myApplication).insert(SQLiteDatabaseManager.RESPONSE_TABLE,contentValues);
		}


		cursor.close();

	}
}
