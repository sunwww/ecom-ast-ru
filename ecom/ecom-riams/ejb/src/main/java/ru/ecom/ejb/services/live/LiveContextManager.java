package ru.ecom.ejb.services.live;

@Deprecated
public class LiveContextManager {

    private static final ThreadLocal<LiveContext> THREAD  = new ThreadLocal<>();
	
}
