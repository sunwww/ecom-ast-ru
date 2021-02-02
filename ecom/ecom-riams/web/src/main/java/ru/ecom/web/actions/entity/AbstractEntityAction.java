package ru.ecom.web.actions.entity;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.web.util.StrutsFormUtil;
import ru.nuzmsh.forms.validator.MapForm;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * 
 */
public abstract class AbstractEntityAction extends BaseAction {

	private static final Logger LOG = Logger
			.getLogger(AbstractEntityAction.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();

    public static IEntityForm castEntityForm(ActionForm aForm, ActionMapping aMapping) {
    	StrutsFormUtil theStrutsFormUtil = new StrutsFormUtil();
        IEntityForm ret =theStrutsFormUtil.getEntityForm(aForm, aMapping);
        if(isMap(ret)) {

        	MapForm form = (MapForm) ret ;
        	form.setStrutsFormName(aMapping.getName());

        	if (CAN_DEBUG) LOG.debug("castEntityForm: name = " + form.getStrutsFormName());
        }
        return ret ;
    }

	protected String getFormClassname(ActionMapping aMapping) {
		return "$$map$$" + aMapping.getName();
	}

	public static boolean isMap(String aClassname) {
		return aClassname.startsWith("$$map$$");
	}
	public static boolean isMap(IEntityForm aObject) {
		boolean isCast = aObject instanceof MapForm ;
		if (CAN_DEBUG)
			LOG.debug("isMap: aObject.getClass() = " + aObject.getClass()
					+ " isCast = " + isCast);

		return isCast;
	}
}