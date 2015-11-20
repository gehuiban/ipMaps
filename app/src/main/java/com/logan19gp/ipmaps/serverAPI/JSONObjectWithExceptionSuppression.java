package com.logan19gp.ipmaps.serverAPI;

import org.json.JSONObject;

import java.io.Serializable;

public abstract class JSONObjectWithExceptionSuppression extends JSONObject implements Serializable
{

    public abstract void initializeJSONMapWithCurrentAttributes();

    @Override
    public JSONObject put(String name, boolean value)
    {
        try
        {
            return super.put(name, value);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JSONObject put(String name, double value)
    {
        try
        {
            return super.put(name, value);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JSONObject put(String name, int value)
    {
        try
        {
            return super.put(name, value);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    };

    @Override
    public JSONObject put(String name, long value)
    {
        try
        {
            return super.put(name, value);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public JSONObject put(String name, Object value)
    {
        try
        {
            if (value != null)
            {
                return super.put(name, value);
            }
            else
            {
                return this;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    };

    @Override
    public JSONObject putOpt(String name, Object value)
    {
        try
        {
            if (value != null)
            {
                return super.putOpt(name, value);
            }
            else
            {
                return this;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
