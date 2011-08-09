package test.map;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;

import ru.ecom.ejb.services.entityform.map.model.MapFormInfo;
import ru.ecom.ejb.services.entityform.map.model.MapFormsHolder;
import ru.ecom.ejb.services.entityform.map.model.MapPropertyInfo;
import ru.ecom.ejb.services.entityform.map.model.forclass.EntityFormPersistanceAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.ParentAnnotation;
import ru.ecom.ejb.services.entityform.map.model.forclass.WebTrailAnnotation;

public class MarshalTest {

	public static void main(String[] args) throws Exception {
		MapFormInfo info = new MapFormInfo() ;
		info.setName("hello") ;
		info.setSecurityPrefix("/Policy/Privilege/Hello");

		MapFormInfo info2 = new MapFormInfo() ;
		info2.setName("hello2") ;
		info2.setSecurityPrefix("/Policy/Privilege/Hello");
		
		EntityFormPersistanceAnnotation efpa = new EntityFormPersistanceAnnotation() ;
		efpa.setClazz("java.lang.String");
		
		info.setEntityFormPersistance(efpa) ;

		ParentAnnotation pa = new ParentAnnotation() ;
		pa.setParentForm("java.lang.String");
		pa.setProperty("parentProperty");
		
		info.setParent(pa);
		//forms.add(info);

		WebTrailAnnotation wta = new WebTrailAnnotation() ;
		wta.setComment("Комментариz");
		wta.setView("hello.do");
		wta.setNameProperties(new String[]{"hello1","hello2"});
		info.setWebTrail(wta);
		
		MapPropertyInfo prop = new MapPropertyInfo() ;
		prop.setName("hell");
		prop.setPersist(true);
		//info.getProperties().add(prop);

		MapPropertyInfo prop2 = new MapPropertyInfo() ;
		prop2.setName("hell");
		//info.getProperties().add(prop2);
		
		MapFormsHolder container = new MapFormsHolder() ;
		container.putFormInfo(info) ;
		container.putFormInfo(info2) ;

		container.save(System.out);
		
		ByteArrayOutputStream out = new ByteArrayOutputStream(10000) ;
		container.save(out);
		out.close() ;
		
		byte[] buf = out.toByteArray() ;
		ByteArrayInputStream in = new ByteArrayInputStream(buf) ;
		container.load(in);
		in.close() ;
		
		container.save(System.out);
//		JAXBContext jaxbContext = JAXBContext.newInstance("test.map");
//		
//		Marshaller marshaller = jaxbContext.createMarshaller();
//		
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
//				   new Boolean(true));
		
		//marshaller.marshal(forms,
		//		   new FileOutputStream("jaxbOutput.xml"));
	}
}
