<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" pageEncoding="UTF-8" %>
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
   <meta content="text/javascript; charset=utf-8" />
	<link title='Поиск в МИАЦ' rel='search' type='application/opensearchdescription+xml' href='opensearch.jsp?tmp=6'/>


<%@ include file="/WEB-INF/tiles/libscache.jsp" %>
<!-- Дополнительное определение стиля -->
<tiles:insert attribute="style" ignore='true'/>

<!-- Дополнительное определение стиля END -->


 </head>

    <body>
    <div id="content">
    <tiles:insert attribute="body"/>
    </div>
    <msh:javascriptContextWrite/>
	<!-- Additional Javascript -->
    <tiles:insert attribute="javascript" ignore='true'/>
    <!-- Additional Javascript END -->
    </body>
</html:html>
