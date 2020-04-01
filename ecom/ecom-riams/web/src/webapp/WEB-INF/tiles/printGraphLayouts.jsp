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

<msh:ifInIdeMode>
   <script type='text/javascript' src='ru.ecom.gwt.idemode.Main/ru.ecom.gwt.idemode.Main.nocache.js'></script>
   <script type='text/javascript' src='./dwr/interface/IdeModeService-CA113b8ec45f6.js'></script>   
</msh:ifInIdeMode>

<!-- CSS  -->
<msh:stylesheet src='/skin/css/css/main/main.css'/>
<style type="text/css">
	@import url("/skin/css/css/main/main_fo<%=ru.nuzmsh.web.filter.caching.CacheUniqueUtil.getUniqueId()%>.css") all;
</style>
<!--[if IE]>
	<msh:stylesheet src='/skin/css/css/main/main_ie.css'/>
<![endif]-->

<script type="text/javascript">
try {
  switch(screen.width) {
      case 800: document.write("<msh:stylesheet src='/skin/css/css/main/main_800.css'/>") ; break ;
      case 1024: document.write("<msh:stylesheet src='/skin/css/css/main/main_1024.css'/>") ; break ;
  }
} catch (e) {
}
</script>

<msh:javascriptSrc src='/skin/js/engine.js' />
<msh:javascriptSrc src='./dwr/interface/VocService.js' />
<msh:javascriptSrc src='./dwr/interface/VocEditService.js' />
<msh:javascriptSrc src='./dwr/interface/RemoteMonitorService.js' />
<msh:javascriptSrc src='./dwr/interface/AddressService.js' />

<!-- Дополнительное определение стиля -->
<tiles:insert attribute="style" ignore='true'/>
<!-- Дополнительное определение стиля END -->


 </head>

    <body>

    <div id="content">
        <tiles:insert attribute="title" ignore="true"/>
        <msh:errorMessage/>
        <msh:infoMessage/>
        <tiles:insert attribute="body"/>
    </div>



    <msh:javascriptContextWrite/>



	<!-- Additional Javascript -->
    <tiles:insert attribute="javascript" ignore='true'/>
    <!-- Additional Javascript END -->

<iframe width=174 height=189 name="gToday:datetime::gfPop1:plugins_time.js" 
id="gToday:datetime::gfPop1:plugins_time.js" 
src="/skin/ext/cal/themes/DateTime/ipopeng.htm" 
scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<iframe width=174 height=189 name="gToday:normal::gfPop2:plugins.js" 
id="gToday:normal::gfPop2:plugins.js" 
src="/skin/ext/cal/themes/Normal/ipopeng.htm" 
scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
    </body>

</html:html>
