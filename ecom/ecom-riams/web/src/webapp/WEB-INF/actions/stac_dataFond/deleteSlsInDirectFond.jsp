<%@page import="ru.ecom.ejb.services.util.ConvertSql"%>
<%@page import="ru.ecom.mis.web.action.synclpufond.HospitalDirectFondImportFromDirAction"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

  <tiles:put name="body" type="string">
  <% 
      String dir = HospitalDirectFondImportFromDirAction.deleteSlsInDirectFond(request,ConvertSql.parseLong(request.getParameter("id"))) ;
    %>
    Госпитализация от направления откреплена
    	  
  </tiles:put>
  <tiles:put name="title" type="string">
  
  </tiles:put>
  <tiles:put name="side" type="string">
    	  
  </tiles:put>
</tiles:insert>

