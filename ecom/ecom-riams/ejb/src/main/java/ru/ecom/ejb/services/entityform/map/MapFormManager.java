package ru.ecom.ejb.services.entityform.map;

import ru.ecom.ejb.services.entityform.map.model.MapFormInfo;
import ru.ecom.ejb.services.entityform.map.model.MapFormsHolder;
import ru.ecom.ejb.util.injection.EjbEcomConfig;

import java.io.FileInputStream;
import java.io.InputStream;

public class MapFormManager {


	private static final MapFormManager INSTANCE = new MapFormManager() ;
	
	private MapFormManager() {}
	
	public static MapFormManager getInstance() {
		return INSTANCE ;
	}
	
	public boolean isClassChanged(String aFormClassName) {
		return theConfig.get("mapforms.file", null) != null;
	}
	
	public MapFormInfo getFormInfo(String aFormClassName) {
		String resource = theConfig.get("mapforms.file", "/META-INF/mapforms.xml");
		InputStream in = getClass().getResourceAsStream(resource); // Thread.currentThread().getContextClassLoader()
		try {
			if(in==null) {
				in = new FileInputStream(resource);
			}
			MapFormsHolder holder = new MapFormsHolder() ;
			holder.load(in);
			return holder.getFormInfo(aFormClassName);
		} catch (Exception e) {
			throw new IllegalArgumentException("Ошибка загрузки "+resource+": "+e.getMessage(),e);
		} finally {
			try { in.close() ;} catch (Exception e) {}
		}
	}
	
	private final EjbEcomConfig theConfig = EjbEcomConfig.getInstance();
	
}
