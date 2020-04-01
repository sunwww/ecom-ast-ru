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
   <meta content="text/javascript; charset=utf-8" />


 		<script type="text/javascript" src="/ckeditor/ckeditor/ckeditor.js" ></script>
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
</head>

    <body>
    <div id='header'>
        <h1>МедОС</h1>
        <a href="<%=request.getContextPath()%>">
            <img src='/customer/images/main/logo-75x50.jpg' width='75' height="50"
                 alt='На главное меню' title='Переход на главное меню'/>
        </a>
        <ul id='user'>

            <li><a href='ecom_releases.do'>Новости</a></li>
            <msh:ifInRole roles="/Policy/Mis/CustomMessage/View">
            <li class="separator">|</li>
            <li><a href='javascript:void(0)' onclick='getDefinition("js-mis_customMessage-getSystemMessages.do?id=-1&short=Short")'>Сообщения</a></li>
            </msh:ifInRole>
             <msh:ifInRole roles="/Policy/Jaas/SecUser/ReplaceWorkFunction">
            <li class="separator">|</li>
            <li><a href='js-secuser-listWF.do'>Смена раб.функции</a></li>
           
            </msh:ifInRole>
             <li class="separator">|</li>
             <li><a href='mis_help.do?code=<%=request.getServletPath()%>'>Помощь</a></li>
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
            			name="Отчеты" roles="/Policy/MainMenu/Journals" title="Отчеты" 
            			action="/riams_journals.do"/>
<%--            <msh:sideLink params="" styleId="mainMenuDiet" 
            			  action="/entityParentList-diet_diet.do?id=0" name="Диетпитание"
                          roles="/Policy/MainMenu/Diet" title="Диетпитание"/>
 --%>
            <msh:sideLink params="" styleId="mainMenuTemplate" action="/entityList-diary_template.do" name="Шаблоны"
                          title="Шаблоны" roles="/Policy/MainMenu/Template"/>


                          
            <msh:sideLink params="" styleId="mainMenuContract" action="/contract_reports_finance.do" name="Договоры"
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
    	
     </div>

    <div id="content">
   <h2><a href='js-riams-instructions.do'>ИНСТРУКЦИИ ПО РАБОТЕ В МедОС</a></h2>
	 <msh:link action="${nextUrl}">Вернуться к работе)</msh:link>
        		<msh:form action="mis_helpsave.csp" defaultField="contextText" method="post">
			<msh:panel colsWidth="1%,99%">
				<msh:row>
					<msh:hidden property="code"/>
					<msh:hidden property="nextUrl"/>
					<msh:textArea property="contextText" rows="60" hideLabel="true"/>
				</msh:row>
			</msh:panel>
		</msh:form>
        	<script type='text/javascript'>
	CKEDITOR.replace('contextText',{
	 extraPlugins : 'autogrow',
	 autoGrow_minHeight : 500
	});
	</script>

    </div>
    <div id="footer">
        <div id='copyright'>&copy; МедОС (v. <%@ include file="/WEB-INF/buildnumber.txt" %> )
        </div>
    </div>
    <div id="divInstantMessage" class="instant_message">&nbsp;</div>

    </body>

</html:html>
