package ru.ecom.web.actions.entity;

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

    public static IEntityForm castEntityForm(ActionForm aForm, ActionMapping aMapping) {
        StrutsFormUtil theStrutsFormUtil = new StrutsFormUtil();
        IEntityForm ret = theStrutsFormUtil.getEntityForm(aForm, aMapping);
        if (isMap(ret)) {
            MapForm form = (MapForm) ret;
            form.setStrutsFormName(aMapping.getName());
        }
        return ret;
    }

    public static boolean isMap(String aClassname) {
        return aClassname.startsWith("$$map$$");
    }

    public static boolean isMap(IEntityForm aObject) {
        return aObject instanceof MapForm;
    }

    protected String getFormClassname(ActionMapping aMapping) {
        return "$$map$$" + aMapping.getName();
    }
}