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

<%@ include file="/WEB-INF/tiles/libscache.jsp" %>
 </head>
    <body>
    <tiles:insert attribute="body"/>
    <msh:javascriptContextWrite/>
    <tiles:insert attribute="javascript" ignore='true'/>
    <iframe width=174 height=189 name="gToday:datetime::gfPop1:plugins_time.js"
            id="gToday:datetime::gfPop1:plugins_time.js"
            src="/skin/ext/cal/themes/DateTime/ipopeng.htm"
            scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"/>
    </iframe>
    <iframe width=174 height=189 name="gToday:normal::gfPop2:plugins.js"
            id="gToday:normal::gfPop2:plugins.js"
            src="/skin/ext/cal/themes/Normal/ipopeng.htm"
            scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
    </iframe>
    </body>
</html:html>
