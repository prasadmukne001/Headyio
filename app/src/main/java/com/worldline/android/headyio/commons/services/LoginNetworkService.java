package com.worldline.android.headyio.commons.services;

import android.content.Context;
import android.os.AsyncTask;
import com.worldline.android.headyio.commons.view.ProgressBarHandler;


/**
 * Created by prasad.mukne on 02/05/2018.
 */

public class LoginNetworkService extends AsyncTask<String, String, String>
{

	private Context context;
	private ProgressBarHandler mProgressBarHandler;
	private ApplicationService applicationService;

	public LoginNetworkService(Context context, ApplicationService applicationService)
	{
		this.context = context;
		this.applicationService = applicationService;
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		mProgressBarHandler = new ProgressBarHandler(context);
		mProgressBarHandler.show();
		applicationService.onStart();
	}

	@Override
	protected String doInBackground(String... strings)
	{
		try
		{
			Thread.sleep(2000);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String s)
	{
		super.onPostExecute(s);
		mProgressBarHandler.hide();

		applicationService.onStop(s);
	}
}
