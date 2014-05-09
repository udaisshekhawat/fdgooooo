package com.foodango.service;

public class ServiceException extends Exception
{
    private static final long serialVersionUID = 7858989409561545715L;

    private int errorCode = 0;
    
    public ServiceException()
    {
    }

    public ServiceException( int errorCode, String detailMessage)
    {
        super(detailMessage);
        this.errorCode = errorCode;
    }

    public ServiceException(Throwable throwable)
    {
        super(throwable);
    }

    public ServiceException(int errorCode, String detailMessage, Throwable throwable)
    {
        super(detailMessage, throwable);
        
        this.errorCode = errorCode;
    }
    
    public ServiceException(String detailMessage, Throwable throwable)
    {
        this( 0, detailMessage, throwable);
    }
    
    public ServiceException(String detailMessage)
    {
        this( 0, detailMessage, null);
    }

    public int getErrorCode()
    {
        return errorCode;
    }
}
