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
   <title>ЭкоМ: МИС</title>
   <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
	<link title='Поиск в МИАЦ' rel='search' type='application/opensearchdescription+xml' href='opensearch.jsp?tmp=6'/>
	
<msh:ifInIdeMode>
   <script type='text/javascript' src='ru.ecom.gwt.idemode.Main/ru.ecom.gwt.idemode.Main.nocache.js'></script>
   <script type='text/javascript' src='./dwr/interface/IdeModeService-CA113b8ec45f6.js'></script>   
</msh:ifInIdeMode>

<%@ include file="/WEB-INF/tiles/libscache.jsp" %>
<!-- Дополнительное определение стиля -->
<tiles:insert attribute="style" ignore='true'/>
<!-- Дополнительное определение стиля END -->


 </head>

    <body>
    <div id='header'>
        <h1>ЭкоМ: МИС</h1>
        <a href="<%=request.getContextPath()%>">
            <img src='/customer/images/main/logo-75x50.jpg' width='75' height="50"
                 alt='На главное меню' title='Переход на главное меню'/>
        </a>
        <ul id='user'>

            <li><a href='ecom_releases.do'>Новости</a></li>
            <li class="separator">|</li>
            <li><a href='http://www.ecom-ast.ru/riams'>Помощь</a></li>
            <li class="separator">|</li>
            <li><ecom:loginName /></li>
            <li class="separator">|</li>
            <li><a href="javascript:window.location = 'ecom_relogin.do?next='+escape(window.location);">Войти под другим именем</a></li>
            <li class="separator">|</li>
            <li><msh:link action="/ecom_loginExit.do">Завершить работу</msh:link></li>

        </ul>
        <ul id='mainMenu'>
            <msh:sideLink params="" styleId="mainMenuLpu" action="/entityParentList-mis_lpu.do?id=-1" name="ЛПУ"
                          title="Список ЛПУ" roles="/Policy/MainMenu/MisLpu"/>

            <msh:sideLink params="" key="SHIFT+CTRL+0" styleId="mainMenuPatient" action="/mis_patientSearch.do" name="Персона"
                          title="Персоны" roles="/Policy/MainMenu/Patient"/>

            <msh:sideLink params="" styleId="mainMenuPoly" action="/js-smo_visit-findPolyAdmissions.do" name="Рабочий календарь"
                          title="Рабочий календарь" roles="/Policy/MainMenu/WorkCalendar"/>

            <msh:sideLink params="" styleId="mainMenuMedcard" action="/poly_medcards.do" name="Медицинские карты"
                                      title="Медицинские карты" roles="/Policy/MainMenu/MedCard"/>

            <msh:sideLink params="" styleId="mainMenuDisability" action="/dis_documents.do" name="Нетрудоспособность"
                          title="Талоны" roles="/Policy/MainMenu/Disability"/>

            <msh:sideLink params=""  styleId="mainMenuStacJournal" action="/stac_findSlsByStatCard.do" name="Журнал обращений"
                          roles="/Policy/MainMenu/AdmissionJournal" title="Журнал обращений" />

            <msh:sideLink params="" styleId="mainMenuJournals"
            			name="Журналы" roles="/Policy/MainMenu/Journals" title="Журналы" 
            			action="/riams_journals.do"/>
            <msh:sideLink params="" styleId="mainMenuDiet" 
            			  action="/entityParentList-diet_diet.do?id=0" name="Диетпитание"
                          roles="/Policy/MainMenu/Diet" title="Диетпитание"/>

            <msh:sideLink params="" styleId="mainMenuTemplate" action="/entityList-diary_template.do" name="Шаблоны"
                          title="Шаблоны" roles="/Policy/MainMenu/Template"/>

            <msh:sideLink params="" styleId="mainMenuReg" action="/exp_reg.do" name="Реестры"
                          roles="/Policy/MainMenu/ExpDocument" title="Реестры"/>
                          
            <msh:sideLink params="" styleId="mainMenuReport" action="../../birt/start.do" name="Отчеты"
                          title="Список отчетов" roles="/Policy/MainMenu/Report"/>
            <msh:sideLink params="" styleId="mainMenuContract" action="/contract_reports.do" name="Договоры"
                          title="Договоры" roles="/Policy/MainMenu/Contract,/Policy/Mis/Contract/MedContract/View"/>

			<msh:sideLink params="" styleId="mainMenuVoc" action="/js-ecom_vocEntity-list.do" name="Справочники"
                          roles="/Policy/MainMenu/Voc" title="Справочники"/>
			<msh:sideLink params="" styleId="mainMenuExpert" action="/js-expert_card-list.do" name="Экспертиза"
                          roles="/Policy/MainMenu/Expert" title="Экспертиза"/>
           
            <msh:sideLink params="" styleId="mainMenuConfig" action="/riams_config.do" name="Настройки"
                          roles="/Policy/MainMenu/Config" title="Настройки"/>
	
        </ul>

    </div>

    <div id='side'>
    	<div id='mainMenuPolicies' class='subMenu'>
    		<a href='entityParentList-secpolicy.do?id=1'>Политики</a>
    	</div>
    	<div id='mainMenuRoles' class='subMenu'>
    		<a href='entityList-secrole.do'>Роли</a>
    	</div>
    	<div id='mainMenuUsers' class='subMenu'>
    		<a href='entityList-secuser.do'>Пользователи</a>
    	</div>

    	<div id='mainMenuDocument' class='subMenu'>
    		<a href='entityList-exp_importdocument.do'>Справочники</a>
    	</div>
    	
        <tiles:insert attribute="side" ignore="true"/>
    </div>

    <div id="content">
        <tiles:insert attribute="title" ignore="true"/>
        <msh:errorMessage/>
        <msh:infoMessage/>
        <tiles:insert attribute="body"/>
    </div>
    <div id="hotkey">
    	<tiles:insert attribute="hotkey" ignore="true"/>
    </div>
    <div id="footer">
        <div id='copyright'>&copy; ООО КДЦ "ЭкоМ" (v. <%@ include file="/WEB-INF/buildnumber.txt" %> )
        </div>
    </div>
    <div id="divInstantMessage" class="instant_message">&nbsp;</div>
    
    <msh:javascriptContextWrite/>

	<msh:ifInRole roles="/Policy/Config/CustomizeMode/Edit">
		   <!-- FORM CUSTOMIZE AND IDE MODE -->
		   <div id='ideModeMainMenuClose' onclick="javascript:$('ideModeMainMenu').style.display=='none'?$('ideModeMainMenu').style.display='block':$('ideModeMainMenu').style.display='none'">Показать меню x</div>
           <ul id='ideModeMainMenu'>
           		<!-- NOTE: li already printed -->
				<msh:ideMode />
				
           		<li><msh:customizeFormModeLink roles="/Policy/Config/CustomizeMode/Edit" /></li>
           		<msh:ifInRole roles="/Policy/Config/IdeMode/Enabled">
           		<msh:ifNotInIdeMode>
				<li><a href='javascript:msh.idemode.goInIdeMode();'>Перейти в режим IDE</a>
				</li>
           		</msh:ifNotInIdeMode>
           		</msh:ifInRole>
				<!--  IDE MODE -->
				<msh:ifInIdeMode>
						<li><a id='ideModeHideIdeTags' href='javascript:msh.idemode.hideIdeTags();'>Скрыть тэги</a></li>
						<li><a href='javascript:msh.idemode.addGuids();'>Добавить GUIDs</a></li>
						<li><a href='javascript:msh.idemode.addNewForm();'>Добавить форму</a></li>
				</msh:ifInIdeMode>
           </ul>
           
    </msh:ifInRole>


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
