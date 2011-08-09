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
   <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<link title='Поиск в МИАЦ' rel='search' type='application/opensearchdescription+xml' href='opensearch.jsp?tmp=6'/>
	

<!-- Дополнительное определение стиля -->
<tiles:insert attribute="style" ignore='true'/>
<!-- Дополнительное определение стиля END -->


 </head>

    <body>
        <tiles:insert attribute="title" ignore="true"/>
        <tiles:insert attribute="body"/>




    </body>

</html:html>
