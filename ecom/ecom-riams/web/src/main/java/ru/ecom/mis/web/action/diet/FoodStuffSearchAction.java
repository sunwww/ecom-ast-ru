package ru.ecom.mis.web.action.diet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.service.diet.IFoodStuffService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
public class FoodStuffSearchAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        FoodStuffSearchForm form = (FoodStuffSearchForm) aForm;
        form.validate(aMapping, aRequest) ;
        IFoodStuffService service = Injection.find(aRequest).getService(IFoodStuffService.class);
//        IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
        aRequest.setAttribute("list"
                , service.findFoodStuff(form.getName()));
        return aMapping.findForward(SUCCESS);
    }
}




 

