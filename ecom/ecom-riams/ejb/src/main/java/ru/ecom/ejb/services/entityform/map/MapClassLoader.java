package ru.ecom.ejb.services.entityform.map;

import org.apache.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import ru.ecom.ejb.services.entityform.MapEntityForm;
import ru.ecom.ejb.services.entityform.map.model.MapFormInfo;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MapClassLoader extends ClassLoader {
	
	private static final Logger LOG = Logger.getLogger(MapClassLoader.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();

	private static final Map<String, Class> HASH = new HashMap<>() ;
	
	public MapClassLoader(ClassLoader aLoader) {
		this(aLoader, false);
	}
	
	public MapClassLoader(ClassLoader aLoader, boolean aTomcatMode) {
		theLoader = aLoader ;
		theTomcatMode = aTomcatMode ;
	}
	
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if (CAN_DEBUG)
			LOG.debug("findClass: name = " + name); 

		Class hashedClass = HASH.get(name);
		return hashedClass!=null ? hashedClass : super.findClass(name) ; 
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		//if(!loadUnique) theLoadUniqueName = true ;
		
		Class ret ;
		
		if (CAN_DEBUG)
			LOG.debug("loadClass: loading " + name+" ..."); 

		
		if(name.startsWith("$$")) {
			Class hashedClass = HASH.get(name);
			if(!theMapFormManager.isClassChanged(name) && hashedClass!=null) {
				if (CAN_DEBUG)
					LOG.debug("loadClass: loaded from hash = " + name); 
				ret = hashedClass ;
			} else {
				String classNameForLoader = name + "__"+UUID.randomUUID() ; 
				try {
					InputStream in = MapEntityForm.class
							.getResourceAsStream("/ru/ecom/ejb/services/entityform/MapEntityForm.class") ;
					ClassReader reader = new ClassReader(in);
					ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
					MapFormInfo formInfo = theMapFormManager.getFormInfo(removeUuidFromClassName(name)) ;
					MapClassVisitor visitor = new MapClassVisitor(writer
							, formInfo,  classNameForLoader, theTomcatMode);
					reader.accept(visitor, ClassReader.SKIP_DEBUG);
					byte[] buf = writer.toByteArray();
					ret = defineClass(
							 classNameForLoader
							, buf,
							0, buf.length);
					if (CAN_DEBUG)
						LOG.debug("loadClass: new class loaded " + ret); 
					HASH.put(name, ret);
				} catch (Exception e) {
					LOG.error("Error loading "+classNameForLoader+": "+e.getMessage(), e) ;
					throw new IllegalStateException(e);
				}
			}
		} else {
				ret = theLoader.loadClass(name);
		}
		if (CAN_DEBUG)
			LOG.debug("loadClass: returned class = " + ret); 

		return ret ;

	}

	
	private String removeUuidFromClassName(String aName) {
		int pos = aName.indexOf("__");
		if(pos>0) {
			return aName.substring(0,pos);
		} else {
			return aName ;
		}
	}
	@Override
	public InputStream getResourceAsStream(String name) {
		if (CAN_DEBUG)
			LOG.debug("getResourceAsStream: name = " + name); 

		return theLoader.getResourceAsStream(name);
	}


	private final MapFormManager theMapFormManager = MapFormManager.getInstance();
	private final ClassLoader theLoader ;
	private boolean theTomcatMode ;
}
