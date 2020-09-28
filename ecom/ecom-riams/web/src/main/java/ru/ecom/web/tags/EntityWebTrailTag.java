package ru.ecom.web.tags;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import ru.ecom.ejb.services.entityform.*;
import ru.ecom.ejb.services.entityform.map.MapClassLoader;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.web.actions.entity.AbstractEntityAction;
import ru.ecom.web.login.LoginInfo;
import ru.ecom.web.util.EntityInjection;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.commons.formpersistence.annotation.Parent;
import ru.nuzmsh.forms.validator.BaseValidatorForm;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.AbstractGuidSimpleSupportTag;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.util.DemoModeUtil;
import ru.nuzmsh.web.util.IdeTagHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Collection;

/**
 *
 */
public class EntityWebTrailTag extends AbstractGuidSimpleSupportTag {

	private static final Logger LOG = Logger.getLogger(EntityWebTrailTag.class);
	private static final boolean CAN_DEBUG = LOG.isDebugEnabled();
	
    /** Раздел основного меню */
    public String getMainMenu() { return theMainMenu ; }
    public void setMainMenu(String aMainMenu) { theMainMenu = aMainMenu ; }


    /** Начальная форма */
    public String getBeginForm() { return theBeginForm ; }
    public void setBeginForm(String aBeginForm) { theBeginForm = aBeginForm ; }

    /** Название страницы */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    /** Название страницы */
    private String theTitle ;

    /**
     * <ecom:titleTrail beginForm='linecolumnForm' />
     *
     * @throws JspException
     * @throws IOException
     */
    public void doTag() throws JspException, IOException {
        try {
            if(StringUtil.isNullOrEmpty(theBeginForm)) throw new IllegalStateException("Не указан параметр beginForm") ;
            JspWriter out = getJspContext().getOut() ;
            PageContext ctx = (PageContext) getJspContext() ;
            HttpServletRequest request = (HttpServletRequest) ctx.getRequest() ;
            IParentEntityFormService service = EntityInjection.find(request).getParentEntityFormService();
            IWebQueryService wservice = Injection.find(request).getService(IWebQueryService.class);
            
            out.write("<div class='titleTrail'>\n") ;
            
            // IDE
            IdeTagHelper.getInstance().printMarker(this, getJspContext());

            long id = !StringUtil.isNullOrEmpty(request.getParameter("id")) ? Long.parseLong(request.getParameter("id")) : 0 ;

//            printLi(out, getEntityName(service, LineColumnForm.class, id, "name"));

//            String id = request.getParameter("id") ;
            ActionForm form = (ActionForm) request.getAttribute(theBeginForm) ;

            boolean createState = false ;
            if(form instanceof BaseValidatorForm) {
                BaseValidatorForm baseForm = (BaseValidatorForm) form ;
                if(baseForm.isTypeCreate()) {
                    createState = true ;
                }
            }

            ModuleConfig moduleConfig = (ModuleConfig) request.getAttribute("org.apache.struts.action.MODULE") ;
            FormBeanConfig formBeanConfig =  moduleConfig.findFormBeanConfig(theBeginForm) ;
            if(formBeanConfig==null) throw new IllegalStateException("Не найдена конфигурация для формы "+theBeginForm) ;
            String type = formBeanConfig.getType() ;
            StringBuilder sb = new StringBuilder();
            StringBuilder sbTitle = new StringBuilder();

            //WebMapClassLoaderHelper loader = new WebMapClassLoaderHelper() ;
            //loader.setMapClassLoader(request);
            MapClassLoader loader = new MapClassLoader(Thread.currentThread().getContextClassLoader()) ; 
            JavaScriptContext js = JavaScriptContext.getContext(getJspContext(), this);
            
            String username = LoginInfo.find(request.getSession(true)).getUsername() ;
            print(service, sb, sbTitle, type, id, true, createState, false, loader, request, js,username,wservice) ;
            //loader.unsetMapClassLoader();
            
            out.println(sb) ;

            if(!StringUtil.isNullOrEmpty(theTitle)) {
                out.write(" ") ;
                out.println(theTitle) ;
            }

            out.write("</div>\n") ;

//            Enumeration en = request.getAttributeNames() ;
//            out.println("<pre>") ;
//            while (en.hasMoreElements()) {
//                Object key = en.nextElement();
//                out.print(key) ;
//                out.print("=") ;
//                out.println(request.getAttribute((String) key)) ;
//
//            }
//            out.println("</pre>") ;
            js.println("if ($('mainMenu"+theMainMenu+"')==null) msh.log.error('Ошибка: Нет основного меню mainMenu"+theMainMenu+"') ;") ;
            js.println("else Element.addClassName($('mainMenu"+theMainMenu+"'), 'selected');");
            js.println("document.title = '"+trimTo(sbTitle.toString(),200)+"' ;") ;
        } catch (Exception e) {
        	showException(e);
        }
    }

    private void print(IParentEntityFormService aService, StringBuilder aSb, StringBuilder aSbTitle, String aClazzName
            , Object aId, boolean aFirst, boolean aCreateState, boolean aSecond, ClassLoader aLoader
            , HttpServletRequest aRequest,JavaScriptContext js,String aUsername,IWebQueryService aWebService) throws NoSuchMethodException, IllegalAccessException, EntityFormException, ParseException, InvocationTargetException, IOException, ClassNotFoundException, InstantiationException {

    	if (CAN_DEBUG) {
    		LOG.debug("print() [ aSb = " + aSb+", \n\taSbTitle="+aSbTitle+", \n\taClassName="+aClazzName+", \n\taId="+aId
    				+", \n\taFirst="+aFirst+", \n\taCreateState ="+aCreateState+", \n\taSecond="+aSecond);
    	}
			 

        Class aClazz = aLoader.loadClass(aClazzName);
        IEntityForm form  = null;
        try {
        	form = aService.loadBySubclass(aClazz, aId) ;
        	if (form!=null) {
        		aClazz= form.getClass();
        		aClazzName = aClazz.getName() ;
        	}
        } catch (Exception e) {
        	//LOG.debug("             error subclass") ;
        	//LOG.debug(e) ;
        }
        LOG.debug("   print: aClazz = " + aClazz);
        String[] propertyNames = WebTrailUtil.getPropertiesName(aClazz) ;
        String[] listStyle = WebTrailUtil.getListStyle(aClazz);
        
        WebTrail webTrail = (WebTrail) aClazz.getAnnotation(WebTrail.class) ;
        String comment = webTrail.comment() ;
        
        if(aFirst) {
            js.println("if($('mainFormLegend')) {");
            js.println(" $('mainFormLegend').innerHTML = '"+comment+"' ;") ;
            js.println("}");
            
        	//aRequest.setAttribute("mainFormComment", comment);
        }
        
        LOG.debug("   print: comment = " + comment);
        StringBuilder sb = new StringBuilder();
        StringBuilder sbTitle = new StringBuilder();
        String entityName = aFirst && aCreateState ? "Создание" : getEntityName(aService, aClazzName, aId, propertyNames, aLoader) ;
        String sbStyle = (aFirst && aCreateState||listStyle==null) ? "" : getStyle(aId, listStyle,aWebService) ;
        LOG.debug("    print: entityName = " + entityName);
        

        printLi(sb, sbTitle, entityName , comment, aId, webTrail.view(),webTrail.shortView(), aFirst, aSecond,sbStyle);
        
        aSb.insert(0, sb) ;
        aSbTitle.insert(0, sbTitle) ;
        Parent parent = (Parent) aClazz.getAnnotation(Parent.class) ;
        
			LOG.debug("    print: parentAnnotation = " + parent);
       // int level = 1 ;
        if (!aCreateState && webTrail.journal()) {
        	
        	 aService.saveView(ConvertSql.parseLong(aId), aUsername, entityName,aClazz.getSimpleName(), aFirst?1:2);
        }
       

        if(parent!=null) {
            Object parentId ;
            if(aFirst && aCreateState) {
                parentId = aId ;
            } else {
            	if (CAN_DEBUG) LOG.debug("    print: Loading parentId from form "+aClazzName+"--"+form); 
                if (form==null) {
//                	IEntityForm form  ;
	                if(AbstractEntityAction.isMap(aClazzName)) {
	                	
	                	form = aService.load(aClazzName, aId)  ;
	                    form = copyToMapAndConvert(form, aClazzName, aLoader);
	                } else {
	                	form = aService.load(aClazz, aId) ; 
	                }
                }
                if (CAN_DEBUG) LOG.debug("    print: form = " + form); 
                if (CAN_DEBUG) LOG.debug("    print: converted form = " + form); 
                parentId = form!=null?ParentUtil.getParentIdValue(form):null ;
            }
            Class parentClass = ParentUtil.getParentFormClass(aClazz) ;
            if (CAN_DEBUG)	LOG.debug("    print: parentId = " + parentId); 
            if (CAN_DEBUG)	LOG.debug("    print: parentClass = " + parentClass);
            if (CAN_DEBUG)  LOG.debug("    print: webTrail.list() = " + webTrail.list()); 

            if(parentId != null && !parentId.toString().equals("0")) {
            	// печать списка
                if(!"".equals(webTrail.list())||!"".equals(webTrail.shortList())) {
                    printList(aSb, webTrail.listComment(), parentId, webTrail.list(),webTrail.shortList());
                }

                print(aService, aSb, aSbTitle, parentClass.getName(), parentId, false, aCreateState, aFirst, aLoader, aRequest, js,aUsername,aWebService); // FIXME MapClassLoader
            }
        }
    }

    private void printList(StringBuilder aSb, String aComment, Object aId, String aListAction,String aListShortAction) {
    	StringBuilder sb = new StringBuilder() ;
    	
    	sb.append("<span><a") ;
    	sb.append(" href='") ;
    	sb.append(aListAction) ;
    	if (aListAction.indexOf('?')<0) {sb.append("?id=") ;} else {sb.append("&id=");}
    	sb.append(aId);
    	sb.append("'");
        sb.append("'>");
        sb.append(aComment) ;
        sb.append("</a>") ;
        if (aListShortAction!=null && !aListShortAction.equals("")) {
        	sb.append("<a class='a_instance_message' href='javascript:void(0)' onclick='getDefinition(\"").append(aListShortAction);
            //aSb.append(" class='a_instance_message' onmouseover='getDefinition(\"").append(aDefaultShortAction);
        	if (aListShortAction.indexOf('?')<0) {sb.append("?id=") ;} else {sb.append("&id=");}
        	sb.append(aId);
        	sb.append("\", event); return false ;' ");
            //sb.append("onmouseout='hideMessage()'") ;
        	sb.append(" ondblclick='javascript:goToPage(") ;
        	sb.append("\"").append(aListShortAction).append("\",\"").append(aId).append("\"") ;
        	sb.append(")'><img src='/skin/images/main/view1.png' alt='Просмотр записи' title='Просмотр записи' height='16' width='16'/></a>");
        }
        //sb.append("<a href='") ;
        //sb.append(aListAction) ;
        //sb.append("?id=") ;
        //sb.append(aId);
    	sb.append("</span> » ") ;
    	aSb.insert(0,sb);
    }
    private IEntityForm copyToMapAndConvert(IEntityForm aSourceForm, String aDestClassName, ClassLoader aLoader) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException  {
    	if(AbstractEntityAction.isMap(aSourceForm)) {
        	Class destClass = aLoader.loadClass(aDestClassName);
        	IEntityForm destForm = (IEntityForm) destClass.newInstance();
        	BeanUtils.copyProperties(destForm, aSourceForm);
        	return destForm ;
    	} else {
    		return aSourceForm;
    	}
    }
    private String getStyle(Object aId,String[] aListStyle,IWebQueryService aWebService) {
    	StringBuilder sb = new StringBuilder() ;
    	if (aListStyle!=null && aListStyle.length>0) {
    		StringBuilder st = new StringBuilder() ;
    		for (String s: aListStyle) {
    			Collection<WebQueryResult> list = aWebService.executeNativeSql(s.replace(":id", ""+aId)) ;
    			if (!list.isEmpty()) {
    				Object obj=list.iterator().next().get1() ;
    				if (obj!=null)st.append(obj).append(";") ;
    			}
    		}
    		if (st.length()>0) sb.append(st);
    	}
    	return sb.toString() ;
    }
    private void printLi(StringBuilder aSb, StringBuilder aSbTitle, String aValue, String aComment, Object aId, String aDefaultViewAction
    		, String aDefaultShortAction
            , boolean aFirst, boolean aSecond, String aSbStyle) {
        aSb.append("<span");
        if (aSbStyle!=null && !aSbStyle.equals("")) aSb.append(" style ='").append(aSbStyle).append("'") ;
        aSb.append(">") ;
        final boolean firstIsLink = ! aFirst || !StringUtil.isNullOrEmpty(theTitle) ;
        if(firstIsLink) {
        	//TODO делаем
            aSb.append("<a ") ;
            if (aSbStyle!=null && !aSbStyle.equals("")) {
            	aSb.append("style ='").append(aSbStyle).append(";text-decoration:underline;'") ;
            } 
            aSb.append(" href='") ;
            aSb.append(aDefaultViewAction) ;
            if (aDefaultViewAction.indexOf('?')<0) {aSb.append("?id=") ;} else{aSb.append("&id=");}
            aSb.append(aId);
            aSb.append("'");
            aSb.append(" title='") ;
            aSb.append(aValue) ;
            aSb.append("'") ;
            if(aSecond) {
                aSb.append(" id='ALT_1'") ;
                JavaScriptContext js = JavaScriptContext.getContext(getJspContext(), this);
                js.println("accesskeyutil.registerKey($('ALT_1'), accesskeyutil.ALT_1) ;");
            }
            aSb.append(">");
        }
        if(!StringUtil.isNullOrEmpty(aComment)) {
            aSbTitle.append(trimTo(aComment,20)) ;
            aSbTitle.append(" : ") ;

            aSb.append(aComment) ;
            aSb.append(" : ") ;
        }
        aSbTitle.append(trimTo(aValue,20)) ;

        if(aFirst) {
            aSb.append(aValue) ;
        } else {
            aSb.append(trimTo(aValue,60)) ;
        }
        if(aSecond) {
            aSb.append("<sup>ALT+1</sup>") ;
        }
        if(firstIsLink) aSb.append("</a>") ;
        if (firstIsLink&&aDefaultShortAction!=null && !aDefaultShortAction.equals("")) {
        	aSb.append("<a class='a_instance_message' href='javascript:void(0)' onclick='getDefinition(\"").append(aDefaultShortAction);
            //aSb.append(" class='a_instance_message' onmouseover='getDefinition(\"").append(aDefaultShortAction);
        	if (aDefaultShortAction.indexOf('?')<0) {aSb.append("?id=") ;} else{aSb.append("&id=");}
            //aSb.append("?id=") ;
            aSb.append(aId);
            aSb.append("\", event); return false ;' ");
            //aSb.append("onmouseout='hideMessage()'") ;
            aSb.append(" ondblclick='javascript:goToPage(") ;
            aSb.append("\"").append(aDefaultViewAction).append("\",\"").append(aId).append("\"") ;
        	aSb.append(")'><img src='/skin/images/main/view1.png' alt='Просмотр записи' title='Просмотр записи' height='16' width='16'/></a>");
        } 
       aSb.append("</span>") ;
        if(firstIsLink) {
            aSb.append(" » ") ;
            aSbTitle.append(" » ") ;
        }


    }

    private static String trimTo(String aStr, int aLength) {
        return !StringUtil.isNullOrEmpty(aStr) && aStr.length()>aLength ? aStr.substring(0,aLength)+'…' : aStr ;
    }

//    private String getEntityNameNotUSED(IParentEntityFormService service, Class aClass, Object aId, String aPropertyName) throws EntityFormException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException {
////        System.out.println("aClass = " + aClass);
//        IEntityForm entityForm = service.load(aClass, aId) ;
//        Object value = PropertyUtil.getPropertyValue(entityForm, aPropertyName) ;
//        PageContext ctx = (PageContext) getJspContext() ;
//        if(DemoModeUtil.isInDemoMode((HttpServletRequest) ctx.getRequest())) {
//        	value = DemoModeUtil.secureValue(value);
//        }
//        return value!=null ? (String)PropertyUtil.convertValue(value.getClass(), String.class, value) : "" ;
//    }

    @SuppressWarnings("unchecked")
	private String getEntityName(IParentEntityFormService service, String aClassName, Object aId, String[] aPropertyNames, ClassLoader aLoader) throws EntityFormException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, ParseException, ClassNotFoundException, InstantiationException {
//      System.out.println("aClass = " + aClass);
			LOG.debug("getEntityName: aClassName = " + aClassName);

      Class formClass = aLoader.loadClass(aClassName);
      IEntityForm entityForm = (IEntityForm) formClass.newInstance();
      IEntityForm loadedForm = service.load(aClassName, aId) ;
      
       
      BeanUtils.copyProperties(entityForm, loadedForm);
      
      
      StringBuilder sb = new StringBuilder() ;
      for(String prop : aPropertyNames) {
          Object value = PropertyUtil.getPropertyValue(entityForm, prop) ;
        PageContext ctx = (PageContext) getJspContext() ;
        if(DemoModeUtil.isInDemoMode((HttpServletRequest) ctx.getRequest())) {
        	value = DemoModeUtil.secureValue(value);
        }
          if(value!=null) {
        	  sb.append(PropertyUtil.convertValue(value.getClass(), String.class, value) ) ;
        	  sb.append(' ') ;
          }
      }
      return sb.toString() ;
  }
    /** Раздел основного меню */
    private String theMainMenu ;
    /** Начальная форма */
    private String theBeginForm ;

}
