package test.map;

import java.lang.reflect.Method;

import ru.ecom.ejb.services.entityform.MapEntityForm;
import ru.ecom.ejb.services.entityform.map.MapClassLoader;
import ru.ecom.ejb.services.entityform.map.MapFormManager;
import ru.ecom.ejb.services.entityform.map.model.MapFormInfo;
import ru.nuzmsh.commons.formpersistence.annotation.EntityFormSecurityPrefix;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.MapForm;

public class MapTest {

	public static void main(String[] args) throws Exception {
		while(true) {
		MapFormManager manager = MapFormManager.getInstance();
		MapFormInfo info = manager.getFormInfo("$$map$$exp_sequenceInfoForm");
		System.out.println(info.getSecurityPrefix());
		
		Thread.currentThread().setContextClassLoader(new MapClassLoader(Thread.currentThread().getContextClassLoader())) ;
		Class cl = Thread.currentThread().getContextClassLoader().loadClass("$$map$$exp_sequenceInfoForm");
		System.out.println(cl);
		System.out.println("ann = "+cl.getAnnotation(EntityFormSecurityPrefix.class));
		System.out.println("pers = "+cl.getAnnotation(EntityFormPersistance.class));
		
		Method getNameMethod = cl.getMethod("getName");
		System.out.println(getNameMethod) ;
		MapForm form = (MapForm) cl.newInstance();
		form.setValue("name", "hello from asm");
		String value = (String) getNameMethod.invoke(form) ;
		System.out.println("value = "+value);
		}
	}
}
