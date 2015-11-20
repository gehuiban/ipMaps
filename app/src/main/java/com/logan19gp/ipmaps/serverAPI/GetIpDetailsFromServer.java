package com.logan19gp.ipmaps.serverAPI;

import android.app.Activity;
import com.android.volley.Request;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by george on 11/19/2015.
 */
public class GetIpDetailsFromServer
{

    public static void getIpFromServer(Activity activity, final String ipAddress, final OnResponseListener onResponseListener)
    {
        final String url = "http://api.ipinfodb.com/v3/ip-city/?key=26eaf994287e166ed697300537353c43b69cf451cfea2159385c59d6e821ed4d&ip=" +
                ipAddress + "&format=json";
        try
        {
            final StringBuilder bodyBuilder = new StringBuilder();
            new SimpleServerRequestTask(activity)
            {
                @Override
                protected void processSuccessResponse(ResponseOrError<?> successResponse)
                {
                    if (successResponse != null)
                    {
                        onResponseListener.onResponseReceived(ipAddress, (IpResponse) successResponse.getResponse());
                    }
                    return;
                }

                @Override
                protected ResponseOrError<?> doInBackground()
                {
                    ServerAPIClient client = new ServerAPIClient();
                    Map<String, String> serverHeaderOverrides = new HashMap<String, String>();
                    client.setJsonStreamFilter(new ServerAPIClient.JsonStreamFilter(){
                        @Override
                        public String applyFilterSuccessData(String original)
                        {
                            String retString = original.replaceAll("\"cash_ball\":", "\"mega_ball\":");
                            return retString;
                        }
                    });
                    ResponseOrError<IpResponse> responseOrError = client.addRequest_synchronous_custom(Request.Method.GET,
                            url, bodyBuilder.toString(), IpResponse.class, serverHeaderOverrides, url);
                    if (responseOrError.isValid())
                    {
                        return responseOrError;
                    }
                    else
                    {
                        return new ResponseOrError<Object>(null, null);
                    }
                }
            }.execute();
            return;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
