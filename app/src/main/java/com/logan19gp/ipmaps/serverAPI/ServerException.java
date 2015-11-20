package com.logan19gp.ipmaps.serverAPI;

/**
 * Created by george on 11/19/15.
 */
public class ServerException extends Exception
{

    private static final long serialVersionUID = 1L;

    public ServerException(String message)
    {
        super(message);
    }

    public ServerException(String message, Throwable exception)
    {
        super(message, exception);
    }

}
