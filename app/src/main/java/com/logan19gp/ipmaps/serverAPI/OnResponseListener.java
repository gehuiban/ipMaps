package com.logan19gp.ipmaps.serverAPI;

/**
 * Created by george on 11/19/2015.
 */
public interface OnResponseListener
{
    void onResponseReceived(String ipAddress, IpResponse ipResponse);
}
