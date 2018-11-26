package com.worldline.android.headyio.commons.network;

import org.json.JSONObject;

/**
 * Created by prasad.mukne on 2/16/2017.
 */
public abstract class ResponseHandler
{
    public abstract void onPreExecute();

    public abstract void onSuccessfulResponse(JSONObject response, int rowId);

    public abstract void onFailureResponse(Exception e);

}
