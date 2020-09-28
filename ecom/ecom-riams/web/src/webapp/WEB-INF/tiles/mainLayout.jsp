<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="java.util.Calendar"%>

<%
    String username = LoginInfo.find(request.getSession()).getUsername();
    request.setAttribute("username", username);
%>

<html:html xhtml="true" locale="true">
 <head>
   <title>МедОС</title>
   <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
   <meta content="text/javascript; charset=utf-8" />

<msh:ifInIdeMode>
   <script type='text/javascript' src='ru.ecom.gwt.idemode.Main/ru.ecom.gwt.idemode.Main.nocache.js'></script>
   <script type='text/javascript' src='./dwr/interface/IdeModeService-CA113b8ec45f6.js'></script>   
</msh:ifInIdeMode>

<%@ include file="/WEB-INF/tiles/libscache.jsp" %>
<!-- Дополнительное определение стиля -->
<tiles:insert attribute="style" ignore='true'/>
    <style type="text/css">
     #message {
         margin-left: 30%;
         font-size: 30px;
         margin-bottom: 10px;
     }
    .rightAlign {
        text-align:right;
        margin-right:80px
    }
    </style>
<!-- Дополнительное определение стиля END -->

     <script type="text/javascript">
         var ws_socketServerStorageName;

     </script>
     <msh:ifInRole roles="/Policy/WebSocket">
         <script type="text/javascript">
             ws_socketServerStorageName="ws_server_name";
             jQuery(document).ready(function(){connectWebSocket();});

         </script>
         <script type="text/javascript" src="./dwr/interface/VocService.js"></script>

     </msh:ifInRole>
 </head>
    <%
    //Date curDate = new Date() ;
    //SimpleDateFormat form = new SimpleDateFormat("MMdd") ;
    //request.setAttribute("curdate_MMdd", ""+form.format(curDate)) ;
    Calendar cal = Calendar.getInstance() ;
    int month = cal.get(Calendar.MONTH) ;
    int day = cal.get(Calendar.DAY_OF_MONTH) ;
    String path_curdate="default" ;
    if (month == Calendar.JANUARY) { 
    	if (day<10) {path_curdate="0101";}
    	else if (day==13) {path_curdate="0113";}
    } else if (month ==  Calendar.FEBRUARY) {
    	if (day==14) {path_curdate="0214";}
    	else if (day>20 && day<24) {path_curdate="0223";}
    } else if (month == Calendar.MARCH) {
    	if (day>5 && day<9) {path_curdate="0308";}
    } else if (month==Calendar.DECEMBER) {
        if (day>23) {path_curdate="ny";}
        else if (day>10) {path_curdate="1231";}
    }
    String style="background: url('/customer/images/top_images/"+path_curdate+".jpg') no-repeat left top ;" ;
    request.setAttribute("style_addition_body", style) ;
    %>
    <body>
    <div id='header' style="${style_addition_body}">
        <h1>МедОС</h1>
        <a href="<%=request.getContextPath()%>">
            <img src='/customer/images/main/logo-75x50.jpg' width='75' height="50"
                 alt='На главное меню' title='Переход на главное меню'/>
        </a>
        <ul id='user'>
        	<msh:ifInRole roles="/Policy/Mis/CustomMessage/PhoneAmokb">
            <li><a href='js-riams-phoneTest.do' target='_blank'>Телефонный справочник</a></li>
            </msh:ifInRole>
           <li class="separator">|</li>
           <msh:ifInRole roles="/Policy/Mis/Claim/View">
            <li><a href='js-mis_claim-my_claims.do'>Заявки в отдел ИТ</a></li>
            <li class="separator">|</li>
            </msh:ifInRole>
            <li><a href='ecom_releases.do'>Новости</a></li>
            <msh:ifInRole roles="/Policy/Mis/CustomMessage/View">
            <li class="separator">|</li>
            <li><a href='javascript:void(0)' onclick='getDefinition("js-mis_customMessage-getMessages.do?id=-1&short=Short")'>Сообщения</a></li>
            </msh:ifInRole>
             <msh:ifInRole roles="/Policy/Jaas/SecUser/ReplaceWorkFunction">
            <li class="separator">|</li>
            <li><a href='js-secuser-listWF.do'>Смена раб.функции</a></li>
           
            </msh:ifInRole>
             <li class="separator">|</li>
            <li> <a href='javascript:void(0)' onclick='getDefinition("js-riams-instructions.do?short=Short")'>Инструкции</a>
             </li>
             <li class="separator">|</li>
            <msh:ifInRole roles="/Policy/Config/MakeScreenshortWithout500">
            <li>
                <a href='javascript:void(0)' onclick='capture()'>Скриншот проблемы</a>
            </li>
            <li class="separator">|</li>
            </msh:ifInRole>
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
            <li id="current_username_li"><ecom:loginName /></li>
            <li class="separator">|</li>
            <li><a href='js-secuser-changePassword.do'>Смена пароля</a></li>
            <li class="separator">|</li>
            <li><a href="javascript:ws_exit();window.location = 'ecom_relogin.do?next='+escape(window.location);">Войти под др. именем</a></li>
            <li class="separator">|</li>
            <li><msh:link action="/ecom_loginExit.do">Завершить работу</msh:link></li>

        </ul>
        <ul id='mainMenu'>
            <msh:sideLink params="" styleId="mainMenuLpu" action="/entityParentList-mis_lpu.do?id=-1" name="ЛПУ"
                          title="Список ЛПУ" roles="/Policy/MainMenu/MisLpu"/>

            <msh:sideLink params="" styleId="mainMenuPatient" action="/mis_patientSearch.do" name="Персона"
                          title="Персоны" roles="/Policy/MainMenu/Patient"/>

            <msh:sideLink params="" styleId="mainMenuPoly" action="/js-smo_visit-findPolyAdmissions.do" name="Рабочий календарь"
                          title="Рабочий календарь" roles="/Policy/MainMenu/WorkCalendar"/>

            <msh:sideLink params="" styleId="mainMenuMedcard" action="/poly_medcards.do" name="Медицинские карты"
                                      title="Медицинские карты" roles="/Policy/MainMenu/MedCard"/>

            <msh:sideLink params="" styleId="mainMenuDisability" action="/dis_documents.do" name="Нетрудоспособность"
                          title="Талоны" roles="/Policy/MainMenu/Disability"/>

            <msh:sideLink params=""  styleId="mainMenuStacJournal" action="/stac_findSlsByStatCard.do" name="Журнал обращений"
                          roles="/Policy/MainMenu/AdmissionJournal" title="Журнал обращений" />
            <msh:sideLink params=""  styleId="mainMenuLaboratoryJournal" action="/pres_prescription_find.do" name="Лаборатория"
                          roles="/Policy/MainMenu/LaboratoryJournal" title="Лаборатория" />

            <msh:sideLink params="" isReport="true" styleId="mainMenuJournals"
            			name="Отчеты" roles="/Policy/MainMenu/Journals" title="Отчеты" 
            			action="/riams_journals.do"/>
           <msh:sideLink params="" styleId="mainMenuDiet" 
            			  action="/entityParentList-diet_diet.do?id=0" name="Диетпитание"
                          roles="/Policy/MainMenu/Diet" title="Диетпитание"/>

            <msh:sideLink params="" styleId="mainMenuTemplate" action="/entityList-diary_template.do" name="Шаблоны"
                          title="Шаблоны" roles="/Policy/MainMenu/Template"/>

            <msh:sideLink params="" styleId="mainMenuContract" action="/contract_find_by_number.do" name="Договоры"
                          title="Договоры" roles="/Policy/MainMenu/Contract,/Policy/Mis/Contract/MedContract/View"/>

			<msh:sideLink params="" styleId="mainMenuVoc" action="/js-ecom_vocEntity-list.do" name="Справочники"
                          roles="/Policy/MainMenu/Voc" title="Справочники"/>
			<msh:sideLink params="" styleId="mainMenuExpert" action="/js-expert_card-list.do" name="Экспертиза"
                          roles="/Policy/MainMenu/Expert" title="Экспертиза"/>
			<msh:sideLink params="" styleId="mainMenuExpert2" action="/entityList-e2_entryList.do" name="Реестры ФОМС"
                          roles="/Policy/MainMenu/Expert2" title="Реестры ФОМС"/>
            <msh:sideLink params="" styleId="mainMenuExpert2" action="/riams_edkc.do" name="ЕДКЦ"
                          roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet" title="ЕДКЦ"/>
            <msh:sideLink params="" styleId="mainMenuVoc" action="/pharm_admin" name="Аптека"
                          roles="/Policy/Mis/Pharmacy/Administration" title="Аптека"/>
            <msh:sideLink params="" styleId="mainMenuConfig" action="/riams_config.do" name="Настройки"
                          roles="/Policy/MainMenu/Config" title="Настройки"/>
            <msh:sideLink params="" styleId="mainMenuConfig" action="/http://keo.amokb.ru/keo?" name="НСИ"
                          roles="/Policy/MainMenu/Standards" title="Нормативно-справочная информация"/>

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
        <div id="message"></div>

        <tiles:insert attribute="title" ignore="true"/>
        <msh:errorMessage/>
        <msh:infoMessage/>
        <msh:userMessage/>
        <tiles:insert attribute="body"/>
    </div>
    <div id="hotkey">
    	<tiles:insert attribute="hotkey" ignore="true"/>
    </div>

    <div id="footer" class="rightAlign">

        <div id='gotoUpDown'><a class="gotoTop" href="#header">Вверх</a><a class="gotoBottom" href="#copyright">Вниз</a></div>
        <msh:ifInRole roles="/Policy/Config/CornerMessage">
        <tags:UnreadMessages name="UnreadMessages" />
        <div class="msgBox">
                <h3>Сообщения</h3>
                <table>
                    <tr id="clorRow" onclick="showUnreadMessages();">
                        <td><img src='/skin/images/msg/new.png' width='25' height="25"
                                 alt='Новые' title='Непрочитанные сообщения' style="margin-right:3px"/></td>
                        <td>новые:</td>
                        <td id="unreadMsg"></td>
                    </tr>
                    <tr onclick='getDefinition("js-mis_customMessage-getMessages.do?id=-1&short=Short")'>
                        <td><img src='/skin/images/msg/all.png' width='25' height="25"
                                 alt='Все' title='Все сообщения' style="margin-right:3px"/></td>
                        <td>все</td>
                        <td id="msgAllTd"></td>
                    </tr>
                </table>
        </div>
        </msh:ifInRole>
        <msh:ifInRole roles="/Policy/WebSocket/Queue">
           <div class='ws_workerDiv'>
                <p id='ws_windowWorkDiv' title="Нажмите для изменения номера окна" onclick="ws_setNewWindowNumber()"></p><hr/>
                <p id='ws_finishWorkDiv' title="Нажмите чтобы приступить к работе/прекратить работу в очереди">СТАТУС</p>
            </div>
            <div class="ws_ticketDiv">
                <p id='ws_ticketNumberDiv' title="Текущий талон" onclick="alert('Текущий талон = '+jQuery('#ws_ticketNumberDiv').html())" >---</p><hr/>
                <p id='ws_nextTicketDiv'   title="Нажмите для перехода к следующему талону" onclick='ws_setNewTicket()'>Перейти к следующему талону</p>
            </div>
        </msh:ifInRole>
        </div>

        <div id='copyright' style="float: right;">&copy; МедОС (v. <%@ include file="/WEB-INF/buildnumber.txt" %> )
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
						<li><a href='javascript:msh.idemode.addNewForm();'>Добавить форму</a></li>
				</msh:ifInIdeMode>
           </ul>
           
    </msh:ifInRole>




	<!-- Additional Javascript -->
    <tiles:insert attribute="javascript" ignore='true'/>
    <!-- Additional Javascript END -->
<msh:ifInRole roles="/Policy/Config/EmergencyMessage">
<script type="text/javascript">
theDefaultTimeOut = setTimeout(funcemergencymessage.func,12000) ;
</script>
</msh:ifInRole>
<msh:ifInRole roles="/Policy/Config/CornerMessage">
    <script type="text/javascript">
        getCountUnreadMessages();
    </script>
</msh:ifInRole>
<!-- ClaimService нужен для создания скриншота ошибки-->
<script type='text/javascript' src='./dwr/interface/ClaimService.js'></script>
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
