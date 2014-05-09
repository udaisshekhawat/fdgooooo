package com.foodango.service.helper;

import com.foodango.service.ServiceException;

public interface ServerSideSessionManager
{
	void checkSession() throws ServiceException;
	
	void refreshSession() throws ServiceException;
}
