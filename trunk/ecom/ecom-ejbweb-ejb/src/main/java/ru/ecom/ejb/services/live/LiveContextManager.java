package ru.ecom.ejb.services.live;


public class LiveContextManager {

    private final static ThreadLocal<LiveContext> THREAD  = new ThreadLocal<LiveContext>();
	
}
