package com.logan19gp.ipmaps.serverAPI;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;

import com.logan19gp.ipmaps.util.DialogUtil;
import com.logan19gp.ipmaps.util.Utilities;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ServerRequestTask<BODY_CLASS, RESPONSE extends ResponseOrError<?>> extends
        AsyncTask<BODY_CLASS, Void, ResponseOrError<?>>
{
    Activity activity;
    public ServerRequestTask(Activity activity)
    {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute()
    {
    }

    protected void errorAction(ResponseOrError<?> error)
    {

    }

    @Override
    protected void onPostExecute(ResponseOrError<?> result)
    {
        try
        {
            if (result != null)
            {
                if (result.isValid())
                {
                    processSuccessResponse(result);
                }
                else
                {
                    if (result.isServerError())
                    {
                            {
                                result.getServerError().printStackTrace();

                                HashMap<String, ArrayList<ServerError.ValueRuleMessage>> errorMap = result.getServerError().getErrorMap();

                                if (errorMap != null)
                                {
                                    StringBuilder errorMsg = new StringBuilder();
                                    for (String key : errorMap.keySet())
                                    {
                                        ArrayList<ServerError.ValueRuleMessage> vrms = errorMap.get(key);
                                        if (vrms != null)
                                        {
                                            for (ServerError.ValueRuleMessage vrm : vrms)
                                            {
                                                errorMsg.append("" + vrm.message);
                                            }
                                        }
                                    }
                                    DialogUtil.showErrorDialog(activity, 0, "Error", errorMsg.toString());
                                }
                                else
                                {
                                    DialogUtil.showErrorDialog(activity, 0, "Error", result.getServerError().getSummary());
                                }
                            }
                    }
                    else
                    {
                        result.getError().printStackTrace();
                        if (activity != null)
                        {
                            String userFriendlyErrorMessage = "There is an issue with your request. Please try again.";

                            if (!Utilities.isNetworkAvailable(activity.getBaseContext()))
                            {
                                userFriendlyErrorMessage = "Please check your network connection.";
                            }

                            DialogUtil.showErrorDialog(activity, 0, "Warning", userFriendlyErrorMessage);
                        }
                    }

                    errorAction(result);

                }
            }
            else
            {
                if (activity != null)
                {
                    DialogUtil.showErrorDialog(activity, 0, "Warning", "Unknown system error");
                }

                errorAction(result);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            if (activity != null)
            {
                DialogUtil.showErrorDialog(activity, 0, "Warning", "Unknown system error.");
            }

            errorAction(result);
        }

    }

    @Override
    protected abstract ResponseOrError<?> doInBackground(BODY_CLASS... body);

    protected abstract void processSuccessResponse(ResponseOrError<?> successResponse);

}
