package ru.ecom.expomc.web.actions.filltime;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.struts.BaseAction;

/**
 * Свойство заполнения по формату
 */
public class FillTimePropertyByFormatAction extends BaseAction {

	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		
		String idParam = aRequest.getParameter("id");
		
		StringTokenizer st = new StringTokenizer(idParam, ", ");
		String id = st.hasMoreTokens() ? st.nextToken() : null ;
		String property = st.hasMoreTokens() ? st.nextToken() : null ;
		if(!StringUtil.isNullOrEmpty(id) &&  !StringUtil.isNullOrEmpty(property)) {
			// создание нового
			aRequest.setAttribute("exp_fillTimePropertyForm.property", property);
			return new ActionForward("/entityParentPrepareCreate-exp_fillTimeProperty.do?id="+id, false) ;
		} else if(!StringUtil.isNullOrEmpty(id)){
			// редактирование существующего
			return new ActionForward("entityParentEdit-exp_fillTimeProperty.do?id="+id, true) ;
		} else {
			throw new IllegalStateException("Ошибка в параметрах [id="+id+", property="+property+"]") ;
		}
	}
}
