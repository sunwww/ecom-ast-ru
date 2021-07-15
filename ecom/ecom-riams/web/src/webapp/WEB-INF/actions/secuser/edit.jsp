<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="java.util.Collection" %>
<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <msh:title title="Пользователь"/>
    </tiles:put>
    <tiles:put name="body" type="string">
        <div id="journalDiv" title="История входов в систему">
            <div id="journalDataDiv"></div>
        </div>
        <msh:form action="entitySaveGoView-secuser.do" defaultField="login">
            <msh:hidden property="id"/>
            <msh:hidden property="saveType"/>
            <msh:hidden property="enabledForCopy"/>
            <msh:ifFormTypeIsCreate formName="secuserForm">
                <msh:hidden property="workFunction"/>
            </msh:ifFormTypeIsCreate>
            <msh:panel>
                <msh:row>
                    <msh:ifFormTypeIsCreate formName="secuserForm">
                        <msh:textField property="login" label="Пользователь"/>

                    </msh:ifFormTypeIsCreate>
                    <msh:ifFormTypeAreViewOrEdit formName="secuserForm">
                        <msh:textField property="login" label="Пользователь" viewOnlyField="true"/>

                    </msh:ifFormTypeAreViewOrEdit>
                    <msh:ifFormTypeIsView formName="secuserForm">
                        <msh:checkBox property="disable" label="Заблокирован"/>
                    </msh:ifFormTypeIsView>
                    <msh:ifFormTypeIsNotView formName="secuserForm">
                        <msh:ifInRole roles="/Policy/Jaas/SecUser/EditDisable">
                            <msh:checkBox property="disable" label="Заблокирован"/>
                        </msh:ifInRole>
                    </msh:ifFormTypeIsNotView>
                </msh:row>
                <msh:row>
                    <msh:ifInRole roles="/Policy/Jaas/SecUser/EditSystem">
                        <msh:checkBox property="isSystems" label="Системный"/>
                    </msh:ifInRole>
                    <msh:ifNotInRole roles="/Policy/Jaas/SecUser/EditSystem">
                        <msh:hidden property="isSystems"/>
                    </msh:ifNotInRole>
                </msh:row>
                <msh:row>
                    <msh:textField property="fullname" label="Полное имя" size="50" horizontalFill="true"
                                   fieldColSpan="3"/>
                </msh:row>
                <msh:ifFormTypeIsCreate formName="secuserForm">
                    <msh:row>
                        <msh:autoComplete property="userCopy" vocName="secUser" label="Копировать роли у пользователя"
                                          fieldColSpan="2" labelColSpan="2" horizontalFill="true"/>
                    </msh:row>
                </msh:ifFormTypeIsCreate>
                <msh:row>
                    <msh:textField property="password" label="Пароль" size="20" passwordEnabled="true"
                                   fieldColSpan="1"/>
                    <msh:checkBox property="isRemoteUser" label="Удаленный пол-ль"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="changePasswordAtLogin" label="Сменить пароль при входе в систему"/>
                </msh:row>
                <msh:row>
                    <msh:textArea property="comment" label="Комментарий" horizontalFill="true" fieldColSpan="3"/>
                </msh:row>
                <msh:row>
                    <msh:separator label="Дополнительная информация" colSpan="4"/>
                </msh:row>
                <msh:row>
                    <msh:label property="createDate" label="Дата создания"/>
                    <msh:label property="createTime" label="время"/>
                </msh:row>
                <msh:row>
                    <msh:label property="createUsername" label="пользователь"/>
                </msh:row>
                <msh:row>
                    <msh:label property="passwordChangedDate" label="Дата изменения пароля"/>

                </msh:row>
                <msh:row>
                    <msh:label property="editDate" label="Дата редактирования"/>
                    <msh:label property="editTime" label="время"/>
                </msh:row>
                <msh:row>
                    <msh:label property="editUsername" label="пользователь"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4"/>
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="secuserForm">
            <msh:ifInRole roles="/Policy/Jaas/SecRole/View">
                <msh:section>
                    <msh:sectionTitle>Роли пользователя</msh:sectionTitle>
                    <msh:sectionContent>
                        <ecom:webQuery name="roles"
                                       nativeSql="select sr.id,sr.name,sr.comment from SecUser_SecRole ss left join secrole sr on sr.id=ss.roles_id where ss.secUser_id=${param.id}"/>
                        <msh:table name="roles" action="roleView.do" idField="1" noDataMessage="Нет ролей">
                            <msh:tableColumn columnName="Название" property="2"/>
                            <msh:tableColumn columnName="Комментарий" property="3"/>
                        </msh:table>
                    </msh:sectionContent>
                </msh:section>
            </msh:ifInRole>
        </msh:ifFormTypeIsView>
        <msh:ifFormTypeIsView formName="secuserForm">
            <msh:ifInRole roles="/Policy/Mis/Worker/WorkFunction/View">
                <ecom:webQuery name="workFunctionAll" nativeSql="select wf.id as wfid,vwf.name as vwfname,su.login as sulogin,ml.name as mlname,wf.code
  		,gwf.groupName as groupName
  		, case when wf.isadministrator='1' then 'Да' else '' end as f7_isadm
  		,case when wf.archival='1' then 'color: red'
  		 else case when su.id=${param.id} then 'background-color:#FFFFA0;'  end end as f8_styleRow
  		from WorkFunction wf
  		left join workfunction gwf on gwf.id=wf.group_id
  		left join Worker w on w.id=wf.worker_id
  		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
  		left join MisLpu ml on ml.id=w.lpu_id
  		left join SecUser su on su.id=wf.secUser_id
  		left join Patient pat on pat.id=w.person_id
        where pat.id=(select w.person_id from SecUser su
				 left join WorkFunction wf on su.id=wf.secUSer_id
				 left join Worker w on wf.worker_id=w.id
				 where su.id=${param.id}) "/>
            </msh:ifInRole>
        </msh:ifFormTypeIsView>
        <%
            if (request.getAttribute("workFunction") != null) {
                Collection list = (Collection) request.getAttribute("workFunction");
                if (list.size() > 1) {
                    out.println("<div class=\"errorMessage\" id='errorSecuser'>");
                    out.println("<a href=\"#\" class=\"errorMessageClose\" title=\"Убрать сообщение об ошибке\" onclick='$(\"errorSecuser\").style.display=\"none\"'>Убрать</a>");
                    out.println("ОШИБКА : Пользователь не должен иметь более одной рабочей фукции!!!");
                    out.println("</div>");
                    out.println("");
                }
            }
        %>
        <msh:ifFormTypeIsView formName="secuserForm">

            <msh:ifInRole roles="/Policy/Mis/Worker/WorkFunction/View">
                <msh:section>
                    <msh:sectionTitle>Список всех рабочих функций</msh:sectionTitle>
                    <msh:sectionContent>
                        <msh:table name="workFunctionAll" action="entityView-work_personalWorkFunction.do" styleRow="8"
                                   idField="1">
                            <msh:tableColumn columnName="ИД" identificator="false" property="1"/>
                            <msh:tableColumn property="5" columnName="Код специалиста"/>
                            <msh:tableColumn property="2" columnName="Функция"/>
                            <msh:tableColumn property="4" columnName="Подразделение"/>
                            <msh:tableColumn property="6" columnName="Групповая раб. функция"/>
                            <msh:tableColumn property="3" columnName="Вход в систему"/>
                            <msh:tableColumn columnName="Начальник (зав.) отделения" property="7"/>
                            <msh:tableButton buttonFunction="makeZav" property="1" buttonShortName="Сделать завед."
                                             role="/Policy/Mis/Worker/WorkFunction/Create"/>
                        </msh:table>
                    </msh:sectionContent>
                </msh:section>
            </msh:ifInRole>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu>
            <msh:sideLink key="ALT+2" params="id" action="/entityEdit-secuser" name="Редактировать"
                          roles="/Policy/Jaas/SecUser/Edit"/>
            <msh:sideLink key="ALT+3" params="id" action="/userRoleEdit" name="Изменить роли пользователя"
                          roles="/Policy/Jaas/SecUser/EditRoles"/>
            <msh:sideLink action="/javascript:setDefaultPassword('1')" name="Установить пароль по умолчанию"
                          roles="/Policy/Jaas/SecUser/Edit"/>
            <msh:sideLink action="/javascript:setDefaultPassword('Covid-19')" name="Установить пароль Covid-19"
                          roles="/Policy/Jaas/SecUser/Edit"/>
            <msh:sideLink action="/javascript:getUserLoginJounal()" name="Просмотреть журнал входов"
                          roles="/Policy/Jaas/SecUser/Edit"/>
            <!-- Для быстрого добавления-->
            <msh:sideLink action="/javascript:showAddUserToHosp(${param.id})"
                          name="Добавить в отделение/настроить пароль" roles="/Policy/Jaas/SecUser/Edit"/>
            <tags:AddUserToHosp name="AddUserToHosp" alreadyUser="1"/>
            <msh:sideLink action="/javascript:goToPatient()" name="⇧К персоне" styleId="listall"
                          roles="/Policy/Jaas/SecUser/View"/>
        </msh:sideMenu>
        <msh:sideMenu title="Списки пользователей">
            <msh:sideLink action="/entityList-secuser.do?list=all" name="Все пользователи" styleId="listall"
                          roles="/Policy/Jaas/SecUser/View"/>
            <msh:sideLink action="/entityList-secuser.do?list=disable" name="Заблокированные" styleId="listdisable"
                          roles="/Policy/Jaas/SecUser/View"/>
            <msh:sideLink action="/entityList-secuser.do" name="Активные" title="Активные" styleId="listactive"
                          roles="/Policy/Jaas/SecUser/View"/>
        </msh:sideMenu>
        <msh:sideMenu title="Безопасность">
            <msh:sideLink styleId="selected" action="/entityList-secuser.do" roles="/Policy/Jaas/SecUser/View"
                          name="Пользователи"/>
            <msh:sideLink styleId="roles" action="/entityList-secrole.do" roles="/Policy/Jaas/SecRole/View"
                          name="Роли"/>
            <msh:sideLink styleId="policies" action="/entityParentList-secpolicy.do?id=1"
                          roles="/Policy/Jaas/SecPolicy/View" name="Политики"/>
            <msh:sideLink action="/serviceExport.do" roles="/Policy/Jaas/Activation" name="Активация"
                          title="Активация"/>
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            Element.addClassName($('mainMenuUsers'), "selected");
        </script>
        <msh:ifFormTypeAreViewOrEdit formName="secuserForm">

            <script type='text/javascript' src='./dwr/interface/RolePoliciesService.js'></script>
            <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>

            <script type="text/javascript">

                <msh:ifInRole roles="/Policy/Mis/Worker/WorkCalendar/View">

                function makeZav(aWfId) {
                    WorkCalendarService.makeZav(aWfId, {
                        callback: function () {
                            window.location.reload();
                        }
                    });
                }

                </msh:ifInRole>

                function goToPatient() {
                    RolePoliciesService.getPatientBySecUser(${param.id}, {
                        callback: function (patientId) {
                            if (patientId) {
                                window.location.href = "entityView-mis_patient.do?id=" + patientId;
                            }
                        }
                    });
                }

                function getUserLoginJounal() {
                    var limit = confirm("Отобразить все входы?") ? 0 : 50;
                    RolePoliciesService.getUserLoginJounal($('login').value, limit, {
                        callback: function (ret) {
                            var html = "<table border='1'><tr><th>Дата входа</th><th>Адрес клиента</th><th>Сервер</th><th></tr>";
                            ret = JSON.parse(ret);
                            for (var i = 0; i < ret.length; i++) {
                                var el = ret[i];
                                html += "<tr><td>" + el.authdate + "</td><td>" + el.remoteaddress + "</td><td>" + el.servername + "</td></tr>";
                            }
                            jQuery('#journalDataDiv').html(html);
                            var journalDiv = jQuery('#journalDiv');
                            journalDiv.dialog({
                                modal: true,
                                width: '50%',
                                buttons: [
                                    {
                                        text: "Закрыть",
                                        click: function () {
                                            jQuery(this).dialog("close");
                                        }
                                    }
                                ]
                            });
                            journalDiv.dialog("open")
                        }
                    });
                }

                function setDefaultPassword(password) {
                    RolePoliciesService.defaultPassword(
                        $('login').value, password ? password : null, {
                            callback: function () {
                                window.location.reload();
                            }
                        });
                }
            </script>
        </msh:ifFormTypeAreViewOrEdit>
        <msh:ifFormTypeIsCreate formName="secuserForm">
            <script type='text/javascript' src='./dwr/interface/WorkCalendarService.js'></script>
            <script type="text/javascript">
                if ((+'${param.workFunction}' > 0) && ($('workFunction'))) {
                    $('workFunction').value = '${param.workFunction}';

                    WorkCalendarService.getInfoByWorkFunction(
                        $('workFunction').value, {
                            callback: function (aResult) {
                                if (aResult != null) {
                                    var s = aResult.split("#");
                                    $('login').value = s[0];
                                    $('fullname').value = s[1];
                                    $('password').value = s[2];
                                    $('comment').value = s[3];
                                }
                            }
                            , errorHandler: function (aMessage) {
                                alert(aMessage);
                            }
                        });
                }
            </script>
        </msh:ifFormTypeIsCreate>
    </tiles:put>
</tiles:insert>

