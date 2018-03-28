package ru.ecom.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;


@ApplicationPath("/api")
public class ApiApplication extends Application {

    @Override
    public Set<Class<?>> getClasses(){
        Set<Class<?>> set = new HashSet<Class<?>>();
        set.add(ApiRecordResource.class);
        set.add(ApiLoginResource.class);
        return set;
    }
}
