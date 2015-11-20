package com.logan19gp.ipmaps.serverAPI;

import com.android.volley.VolleyError;

public class ResponseOrError<T>
{
    public T response;
    public VolleyError error;

    public ResponseOrError(T response, VolleyError error)
    {
        this.response = response;
        this.error = error;
    }

    public boolean isValid()
    {
        return error == null;
    }

    @Override
    public String toString()
    {
        return "Response:" + response + ":Error:" + error;
    }

    public T getResponse()
    {
        return response;
    }

    public boolean isServerError()
    {
        return error instanceof ServerError;
    }

    public ServerError getServerError()
    {
        return (ServerError) error;
    }

    public VolleyError getError()
    {
        return error;
    }
}
