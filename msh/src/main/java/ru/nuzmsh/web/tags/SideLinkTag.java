package ru.nuzmsh.web.tags;

import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.helper.JavaScriptContext;
import ru.nuzmsh.web.tags.helper.RolesHelper;
import ru.nuzmsh.web.xhtml.Xa;
import ru.nuzmsh.web.xhtml.Xli;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;

/**
 * @jsp.tag name="sideLink"
 *          display-name="sideLink"
 *          body-content="empty"
 *          description="Side Link"
 */
public class SideLinkTag extends AbstractGuidSupportTag {
 

    /**
     * Политики безопасности, разделенный ;
     * @jsp.attribute   description="Политики безопасности, разделенный ;"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getRoles() { return theRoles ; }
    public void setRoles(String aRoles) { theRoles = aRoles ; }


    /**
     * Идентификатор в CSS
     *
     * @jsp.attribute description="Идентификатор в CSS"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getStyleId() {
        if (theStyleId == null) {
            return getKey();
        } else {
            return theStyleId;
        }
    }

    public void setStyleId(String aStyleId) {
        theStyleId = aStyleId;
    }

    /**
     * Клавиша быстрого доступа
     * Например: Ctrl+1 или Alt+F4 или F4
     * @jsp.attribute description="Клавиша быстрого доступа"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getKey() {
        return theKey != null ? theKey.replace('+', '_').toUpperCase() : null;
    }

    public void setKey(String aKey) {
        theKey = aKey;
    }

    /**
     * Параметры
     *
     * @jsp.attribute description="Есть ли такие параметры"
     * required="false"
     * rtexprvalue="true"
     */
    public String getParams() {
        return theParams!=null ? theParams : "";
    }

    public void setParams(String aExists) {
        theParams = aExists;
    }
    
    /** Это отчет 
    * @jsp.attribute   description = "Ссылка на отчетную базу"
     *                     required = "false"
     *                  rtexprvalue = "true"
     * */
	public boolean getIsReport() {
		return theIsReport;
	}

	public void setIsReport(boolean aIsReport) {
		theIsReport = aIsReport;
	}

	/** Это отчет */
	private boolean theIsReport;

    /**
     * Действие
     * @jsp.attribute description=" Действие"
     *                  required="true"
     *                  rtexprvalue="true"
     */
    public String getAction() {
        return theAction;
    }

    public void setAction(String aAction) {
        theAction = aAction;
    }

    /**
     * название
     *
     * @jsp.attribute description="Название"
     *                required="true"
     *                rtexprvalue="true"
     */
    public String getName() {
        return theName;
    }

    public void setName(String aName) {
        theName = aName;
    }

    /**
     *Подсказка
     * @jsp.attribute description="Подсказка"
     *                  required="false"
     *                  rtexprvalue="true"
     */
    public String getTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append(theTitle== null ? getName() : theTitle) ;
        if(theKey!=null) {
            sb.append(" ") ;
            sb.append(theKey) ;
        }
        return  sb.toString() ;
    }

    public void setTitle(String aTitle) {
        theTitle = aTitle;
    }

    /**
     * Подтверждение при переходе по ссылке
     * @jsp.attribute   description = "Подтверждение при переходе по ссылке"
     *                     required = "false"
     *                  rtexprvalue = "true"
     */
    public String getConfirm() { return theConfirm ; }
    public void setConfirm(String aConfirm) { theConfirm = aConfirm ; }


    public int doStartTag() throws JspException {
    	printIdeStart("SideLink") ;
    	
        if(StringUtil.isNullOrEmpty(theRoles) || RolesHelper.checkRoles(theRoles, pageContext)) {
        	SideMenuTag.removeMustHidden(pageContext) ;
        	
            StringTokenizer st = new StringTokenizer(getParams(), ", ");
            String action = getAction().substring(1) ;
            StringBuilder sb = new StringBuilder() ;
            boolean isJavascript= false ;
            String actionJavascript = "" ;
            if (!action.contains("javascript")) {
                String addSb = (String) pageContext.getSession().getAttribute(theIsReport ? "LOGININFO_URL_REPORT_BASE" : "LOGININFO_URL_MAIN_BASE") ;
                if (!action.contains("http")) sb.append(addSb!=null&&!addSb.equals("null") ? addSb : "") ;
            	sb.append(action) ;
                if(action.contains(".do")) {
	                if(action.indexOf('?')>=0) {
	                    sb.append("&amp;") ;
	                } else {
	                	sb.append("?");
	                }
	            } else {
	                sb.append(".do?") ;
	            }
            } else {
            	isJavascript = true ;
            	sb.append("javascript:void(0)") ;
            	actionJavascript = action.substring(11) ;
            }
            boolean canPrint = true;
            while (st.hasMoreTokens()) {
                String param = st.nextToken();
                String value = getParamValue(param);
                if (value == null) {
                    canPrint = false;
                    break;
                } else {
                    if(value.equals("0")) {
                        canPrint = false ;
                        break ;
                    }
                    sb.append(param);
                    sb.append('=');
                    try {
                        sb.append(URLEncoder.encode(value, "utf-8"));
                    } catch (UnsupportedEncodingException e) {
                        sb.append(value);
                        e.printStackTrace();
                    }
                    sb.append("&amp;");
                }
            }

            if (canPrint) {
            	String name = getName() ;
            	if (name.toUpperCase().trim().equals("ИЗМЕНИТЬ")||name.toUpperCase().trim().equals("РЕДАКТИРОВАТЬ")) {
                	name = "<img src='/skin/images/main/edit.png' alt='Изменить запись' title='Изменить запись' height='14' width='14'/> "+name ;
                } else {
	                if (name.toUpperCase().trim().equals("УДАЛИТЬ")) {
	                	name="<img src='/skin/images/main/delete.png' alt='Удалить' title='Удалить' height='14' width='14'/> "+name ;
	                } 
                }
                Xa a = theKey!=null ? new Xa(sb.toString(), name+"<sup class='accessKey'>"+theKey+"</sup>")
                        : new Xa(sb.toString(), getName()) ;

                if(getStyleId()!=null) {
                    a.setID(getStyleId()) ;
                }
                String title = getTitle() ;
                
                
                a.setTitle(getTitle()) ;
                if (isSelected()) {
                    a.setClass("selected");
                }

                StringBuilder onClickSb = new StringBuilder();
                if (isJavascript) {
                	if(!StringUtil.isNullOrEmpty(theConfirm)) {
                        onClickSb.append("if (confirm(\"")
                                .append(theConfirm).append("\")) { ") ;
                        onClickSb.append(actionJavascript) ;
                        onClickSb.append("}") ;
                    } else {
                    	onClickSb.append(actionJavascript) ;
                    }
                	
                } else {
                	onClickSb.append("return ") ;
                    if(!StringUtil.isNullOrEmpty(theConfirm)) {
                        onClickSb.append(" confirm(\"")
                                .append(theConfirm).append("\") && ") ;
                    }
                	onClickSb.append(" msh.util.FormData.getInstance().isChangedForLink() ;") ;
                }
                
                a.setOnClick(onClickSb.toString());
                
                Xli li = new Xli(a);
                
                if(theKey!=null) {
                    printKeyAccessKey();
                    if("ALT+DEL".equals(theKey.toUpperCase())) {
                        li.setClass("delete") ;
                    }
                }
                try {
                    JspWriter out = pageContext.getOut();
                    out.println(li);
                } catch (IOException ioe) {
                    throw new JspException(ioe) ;
                }

            }
        } else {
            JspWriter out = pageContext.getOut();
            try {
                out.print("<!--");
                out.print("Нет прав: ");
                out.print(theRoles);
                out.println("-->");
            } catch (IOException e) {
                throw new JspException(e) ;
            }

        }

        return EVAL_BODY_INCLUDE;
    }


    
    @Override
	public int doEndTag() throws JspException {
    	printIdeEnd();
		return super.doEndTag();
	}
	private void printKeyAccessKey() {
        String key = getKey();
        String id = theStyleId == null ? key : theStyleId;
        JavaScriptContext.getContext(pageContext, this)
                .println(
                 "     accesskeyutil.registerKey($('" + id + "'), accesskeyutil." + key + ") ;");
    }

    private boolean isSelected() {
        try {
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            return request.getRequestURI().indexOf(getAction()) != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getParamValue(String aName) {
        Object param = pageContext.getRequest().getAttribute(aName);
        if (param == null) {
            param = pageContext.getRequest().getParameter(aName);
            if (param == null) {
                HttpSession session = pageContext.getSession();
                if (session != null) {
                    param = session.getAttribute(aName);
                }
            }
        }
        String str;
        if (param != null) {
            if (param instanceof String) {
                str = (String) param;
            } else {
                str = param.toString();
            }
        } else {
            str = null;
        }
        return str;

    }

    
    /** Подтверждение при переходе по ссылке */
    private String theConfirm  ;
    /** Подсказка */
    private String theTitle;
    
    /**
     * Есть ли такой параметр
     */
    private String theParams;
    /**
     * название
     */
    private String theName;
    /**  Действие */
    private String theAction;
    /** Клавиша быстрого доступа */
    private String theKey;
    /** Идентификатор в CSS */
    private String theStyleId;

    /** Политики безопасности, разделенный ; */
    private String theRoles ;

}
