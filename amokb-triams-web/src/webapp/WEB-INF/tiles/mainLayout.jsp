<%@page import="java.util.Calendar"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%
Calendar cal = Calendar.getInstance() ;
String fon = "" ;
if (cal.get(Calendar.MONTH)==Calendar.MARCH) fon="1" ;
if (cal.get(Calendar.MONTH)==Calendar.APRIL) fon="1" ;
if (cal.get(Calendar.MONTH)==Calendar.MAY) fon="1" ;
if (cal.get(Calendar.MONTH)==Calendar.JUNE) fon="2" ;
if (cal.get(Calendar.MONTH)==Calendar.JULY) fon="2" ;
if (cal.get(Calendar.MONTH)==Calendar.AUGUST) fon="2" ;
if (cal.get(Calendar.MONTH)==Calendar.SEPTEMBER) fon="3" ;
if (cal.get(Calendar.MONTH)==Calendar.OCTOBER) fon="3" ;
if (cal.get(Calendar.MONTH)==Calendar.NOVEMBER) fon="3" ;
request.setAttribute("fon_id", fon) ;
%>
<html:html xhtml="true" locale="true">
 <head>
   <title>МедОС</title>
   <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
   <meta content="text/javascript; charset=utf-8" />
	<link title='Поиск в МИАЦ' rel='search' type='application/opensearchdescription+xml' href='opensearch.jsp?tmp=6'/>
	
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
<msh:javascriptSrc src='/skin/js/ac.js' />
<msh:javascriptSrc src='/skin/js/engine.js' />
<script type="text/javascript" src='js/Dialog.js' ></script>
<script type="text/javascript" src='js/FadeEffect.js' ></script>
<!-- Дополнительное определение стиля END -->
<style type="text/css">
	@import url("css/addition.css?tmp=<%=ru.nuzmsh.web.filter.caching.CacheUniqueUtil.getUniqueId()%>") all;
</style>
<!-- Дополнительное определение стиля -->
<tiles:insert attribute="style" ignore='true'/>

<style type="text/css">
body{
    background:url("img/fon_z${fon_id}.jpg") no-repeat fixed left top #033268 ;
}
	</style>

 </head>

    <body>
    <div id='header'>
        <h1>МедОС</h1>
        <a href="<%=request.getContextPath()%>">
            <img src='/customer/images/main/logo-75x50.jpg' width='75' height="50"
                 alt='На главное меню' title='Переход на главное меню'/>
        </a>
       <div id='titleHead'>
       <tiles:insert attribute="title" ignore="true"/>
       </div>
        <%--
        <ul id='user'>

            <li><a href='ecom_releases.do'>Новости</a></li>
            <msh:ifInRole roles="/Policy/Mis/CustomMessage/View">
            <li class="separator">|</li>
            <li><a href='javascript:void(0)' onclick='getDefinition("js-mis_customMessage-getSystemMessages.do?id=-1&short=Short")'>Сообщения</a></li>
            </msh:ifInRole>
            <li class="separator">|</li>
             <li>
             <% request.setAttribute("servletPath", request.getServletPath()); %>
             <msh:ifNotInRole roles="/Policy/Mis/Help/Edit">
             <a href='javascript:void(0)' onclick='getDefinition("mis_help.do?code="+escape("${servletPath}")+"&nextUrl="+escape(window.location))'>Помощь</a>
             </msh:ifNotInRole>
             <msh:ifInRole roles="/Policy/Mis/Help/Edit">
             <a href='javascript:void(0)' onclick='window.open("mis_help.do?code="+escape("${servletPath}")+"&nextUrl="+escape(window.location))'>Помощь</a>
             </msh:ifInRole>
             </li>
            <li class="separator">|</li>
            <li><ecom:loginName /></li>
            <li class="separator">|</li>
            <li><a href="javascript:window.location = 'ecom_relogin.do?next='+escape(window.location);">Войти под другим именем</a></li>
            <li class="separator">|</li>
            <li><msh:link action="/ecom_loginExit.do">Завершить работу</msh:link></li>

        </ul>
        
        <ul id='mainMenu'>
            <msh:sideLink params="" key="SHIFT+CTRL+0" styleId="mainMenuPatient" action="/mis_patientSearch.do" name="Персона"
                          title="Персоны" roles="/Policy/MainMenu/Patient"/>
        </ul>
         --%>
    </div>
    <div id='side'>    	
        <tiles:insert attribute="side" ignore="true"/>
    </div>

    <div id="content" >
         <msh:errorMessage/>
        <msh:infoMessage/>
        <msh:userMessage/>
        <tiles:insert attribute="body"/>

    </div>
    <div id="footer">
        <div id='copyright'>&copy; МедОС (v. <%@ include file="/WEB-INF/buildnumber.txt" %> )
        </div>
    </div>
    <div id="divInstantMessage" class="instant_message">&nbsp;</div>
    <%--
    <div id='scrollUp'  style="position: absolute; top:10px ;right: 10px">
    <button  style='padding: 30px;' onclick="">ВВЕРХ</button>
    </div>
    <div id='scrollDown' style="position: absolute; bottom:10px ;right: 10px;">
    <button style='padding: 30px;' onclick="window.scrollTo(document.body.scrollWidth,document.body.scrollHeight+20);this.parentNode.style.top=(document.body.scrollHeight-20)+'px'">ВНИЗ</button>
    </div> --%>
    <msh:javascriptContextWrite/>

	<msh:ifInRole roles="/Policy/Config/CustomizeMode/Edit">
		   <!-- FORM CUSTOMIZE AND IDE MODE -->
		   <div id='ideModeMainMenuClose' onclick="javascript:$('ideModeMainMenu').style.display=='none'?$('ideModeMainMenu').style.display='block':$('ideModeMainMenu').style.display='none'">Показать меню x</div>
           <ul id='ideModeMainMenu'>
				<msh:ideMode />
				
           		<li><msh:customizeFormModeLink roles="/Policy/Config/CustomizeMode/Edit" /></li>
           </ul>
           
    </msh:ifInRole>


	<!-- Additional Javascript -->
    <tiles:insert attribute="javascript" ignore='true'/>
    <!-- Additional Javascript END -->


    </body>

</html:html>
