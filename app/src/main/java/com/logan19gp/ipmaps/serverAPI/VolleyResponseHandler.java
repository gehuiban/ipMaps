package com.logan19gp.ipmaps.serverAPI;

import com.android.volley.VolleyError;

public abstract class VolleyResponseHandler<RESPONSE_CLASS>
{
    // abstract public void onResponse(JSONObject response);
    abstract public void preExecution();

    abstract public void postExecution();

    abstract public void onResponse(RESPONSE_CLASS obj);

    abstract public void onErrorResponse(VolleyError error);
}
