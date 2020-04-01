<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<html:html xhtml="true" locale="true">
 <head>
   <title>МедОС</title>
   <meta http-equiv="content-type" content="text/html; charset=utf-8"/>


<%@ include file="/WEB-INF/tiles/libscache.jsp" %>
<!-- Дополнительное определение стиля -->
<tiles:insert attribute="style" ignore='true'/>
<!-- Дополнительное определение стиля END -->


 </head>

    <body>

    <div id="shortContent">
    	<div id="shortContentClose" onclick="hideMessage();">Свернуть</div>
        <tiles:insert attribute="title" ignore="true"/>
        <msh:errorMessage/>
        <msh:infoMessage/>
        <tiles:insert attribute="body"/>
    	<div id="shortContentClose" onclick="hideMessage();">Свернуть</div>
    </div>
    </body>

</html:html>
