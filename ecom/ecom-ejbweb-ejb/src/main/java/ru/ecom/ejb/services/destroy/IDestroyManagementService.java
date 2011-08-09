package ru.ecom.ejb.services.destroy;

import org.jboss.annotation.ejb.Management;

@Management
public interface IDestroyManagementService {
	void destroy();
}
