package ru.ecom.web.actions.entity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.IEntityFormService;
import ru.ecom.ejb.services.entityform.MapEntityForm;
import ru.ecom.web.util.EntityInjection;
import ru.nuzmsh.forms.validator.MapForm;

/**
 * Список всех значений
 */
public class ListAction extends AbstractEntityAction {

	private final static Logger LOG = Logger.getLogger(ListAction.class);

	private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
			HttpServletRequest aRequest, HttpServletResponse aResponse)
			throws Exception {
		
		IEntityForm form = castEntityForm(aForm, aMapping);
		IEntityFormService service = EntityInjection.find(aRequest)
				.getEntityFormService();
		
		boolean isMap = isMap(form);
		Collection list = isMap 
				? service.listAll(getFormClassname(aMapping)) 
				: service.listAll(form.getClass());

		aRequest.setAttribute("list"
				, isMap ? transormCollection(list, form.getClass()) : list);

		return aMapping.findForward("success");
	}

	public static Collection transormCollection(Collection aCol, Class aClass) throws Exception {
		ArrayList list = new ArrayList(aCol.size()) ; 
		for(Object obj : aCol) {
			Object dest = aClass.newInstance();
			BeanUtils.copyProperties(dest, obj);
			list.add(dest);
		}
		return list ;
	}
	
	private void dumpCollection(Collection aObject) {
		for (Object o : aObject) {
			dump(o);
		}
	}

	private void dump(Object aObject) {
		for (Method m : aObject.getClass().getMethods()) {
			if (CAN_DEBUG)
				LOG.debug("dump: m = " + m);
		}
	}


}
