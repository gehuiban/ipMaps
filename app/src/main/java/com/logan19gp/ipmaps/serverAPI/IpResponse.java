package com.logan19gp.ipmaps.serverAPI;

/**
 * Created by george on 11/19/2015.
 */
public class IpResponse
{
//    "statusCode" : "ERROR",
//            "statusMessage" : "Invalid IP Address.",
//    "statusCode" : "OK",
//            "statusMessage" : "",
//            "ipAddress" : "74.125.45.100",
//            "countryCode" : "US",
//            "countryName" : "UNITED STATES",
//            "regionName" : "CALIFORNIA",
//            "cityName" : "MOUNTAIN VIEW",
//            "zipCode" : "94043",
//            "latitude" : "37.3956",
//            "longitude" : "-122.076",
//            "timeZone" : "-08:00"
    private String statusCode;
    private String statusMessage;
    private String ipAddress;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private String zipCode;
    private String latitude;
    private String longitude;
    private String timeZone;

    public String getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(String statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getStatusMessage()
    {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage)
    {
        this.statusMessage = statusMessage;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getCountryCode()
    {
        return countryCode;
    }

    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    public String getTimeZone()
    {
        return timeZone;
    }

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }
}
