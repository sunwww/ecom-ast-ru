package ru.ecom.web.tags;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.ecom.ejb.services.entityform.IParentEntityFormService;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.StrutsConfigUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;

/**
 *
 */
public class ParentEntityListAllTag extends AbstractGuidSimpleSupportTag {

    private final static Log LOG = LogFactory.getLog(ParentEntityListAllTag.class) ;
    private final static boolean CAN_TRACE = LOG.isTraceEnabled() ;


    /** Имя формы */
    public String getFormName() { return theFormName ; }
    public void setFormName(String aFormName) { theFormName = aFormName ; }

    /** Куда записывать */
    public String getAttribute() { return theAttribute ; }
    public void setAttribute(String aAttribute) { theAttribute = aAttribute ; }

    /** Куда записывать */
    private String theAttribute ;
    /** Имя формы */
    private String theFormName ;
    public void doTag() throws JspException, IOException {
    	printIdeStart();
    	
//        JspWriter out = getJspContext().getOut() ;
        PageContext ctx = (PageContext) getJspContext() ;
        HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
        try {
            IParentEntityFormService service = EntityInjection.find(request).getParentEntityFormService();
            long id = Long.parseLong(request.getParameter("id")) ;
            String classname = theStrutsConfigUtil.findClassNameByFormName(request, theFormName) ;
            Collection list ;
            if(AbstractEntityAction.isMap(classname)) {
                Class clazz = theStrutsConfigUtil.findClassByFormName(request, theFormName);
                list = service.listAll(classname, id) ;
                list = ru.ecom.web.actions.entity.ListAction.transormCollection(list, clazz) ;            
            } else {
                Class clazz = theStrutsConfigUtil.findClassByFormName(request, theFormName);
                list = service.listAll(clazz, id) ;
            }
            request.setAttribute(theAttribute, list);
        } catch (Exception e) {
        	showException(e);
//            LOG.error("Ошибка при получении значений", e) ;
//            e.printStackTrace() ;
//            throw new IllegalStateException("Ошибка при получении значений: "+e,e) ;
        }
    	printIdeEnd();
    }

    private final StrutsConfigUtil theStrutsConfigUtil = new StrutsConfigUtil();
}
