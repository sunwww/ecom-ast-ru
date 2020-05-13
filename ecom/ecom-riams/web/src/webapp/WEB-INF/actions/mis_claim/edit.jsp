<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">


    <tiles:put name='body' type='string'>
        <%
            String img = request.getParameter("img");
            String desc = request.getParameter("description");
            String hist = request.getParameter("hist");
            request.setAttribute("description",desc);
            request.setAttribute("img",img);
            request.setAttribute("hist",hist);
        %>
        <msh:form action="entitySaveGoView-mis_claim.do" defaultField="id">

            <msh:hidden property="id"/>
            <msh:ifNotInRole roles="/Policy/Mis/Claim/Operator">
                <msh:hidden property="workfunction"/>
            </msh:ifNotInRole>
            <msh:hidden property="saveType"/>
            <msh:panel colsWidth="20% 20% 15%">
                <input type='hidden' id='statusState'>
                <msh:ifFormTypeIsCreate formName="mis_claimForm">
                    <msh:ifInRole roles="/Policy/Mis/Claim/Operator">
                        <msh:row>
                            <msh:autoComplete property="workfunction" vocName="workFunction" label="Пользователь" fieldColSpan="3" size="50"/>
                        </msh:row>
                    </msh:ifInRole>
                    <msh:row>
                        <msh:textArea  property="description" label="Текст заявки" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="phone" label="Контактный телефон"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="address" size="20" label="Место исполнения заявки"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="vocClaimType" property="claimType" label="Тип заявки" fieldColSpan="3" horizontalFill="true" />
                    </msh:row>
                    <msh:row>
                        <msh:textField property="screenFileName" size="50"  label="Скриншот"/>
                    </msh:row>
                </msh:ifFormTypeIsCreate>

                <msh:ifFormTypeAreViewOrEdit formName="mis_claimForm">
                    <msh:row>
                        <msh:textArea  property="description" rows="5"  size="50" label="Текст заявки"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="phone" label="Контактный телефон" viewOnlyField="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:textField property="address" size="50"  label="Место исполнения заявки" viewOnlyField="true"/>
                    </msh:row>
                    <msh:row>
                        <msh:ifInRole roles="/Policy/Mis/Claim/Operator">
                            <msh:autoComplete vocName="vocClaimType" size="50"  property="claimType" label="Тип заявки" fieldColSpan="3" horizontalFill="true"/>
                        </msh:ifInRole>
                        <msh:ifNotInRole roles="/Policy/Mis/Claim/Operator">
                            <msh:autoComplete vocName="vocClaimType" size="50"  property="claimType" label="Тип заявки" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
                        </msh:ifNotInRole>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete vocName="workFunction" size="50"  property="workfunction" label="Создал" fieldColSpan="3" horizontalFill="true" viewOnlyField="true"/>
                    </msh:row>
                    <msh:separator label="Информация о создании заявки" colSpan="10"></msh:separator>
                    <msh:row>
                        <msh:label property="createDate" label="Дата создания"/>
                    </msh:row> <msh:row>
                    <msh:label property="createTime" label="Время создания"/>
                </msh:row> <msh:row>
                    <msh:label property="username" label="Пользователь" />
                </msh:row>
                    <msh:ifInRole roles="/Policy/Mis/Claim/Operator">
                        <msh:separator label="Информация о просмотре заявки оператором" colSpan="10"></msh:separator>
                        <msh:row>
                            <msh:label property="viewDate" label="Дата просмотра оператором"/>
                        </msh:row> <msh:row>
                        <msh:label property="viewTime" label="Время просмотра оператором"/>
                    </msh:row> <msh:row>
                        <msh:label property="viewUsername" label="Оператор" />
                    </msh:row>
                    </msh:ifInRole>
                    <msh:separator label="Информация об исполнении заявки" colSpan="10"></msh:separator>
                    <msh:row>
                        <msh:label property="startWorkDate" label="Дата начала исполнения"/>
                    </msh:row> <msh:row>
                    <msh:label property="startWorkTime" label="Время начала исполнения"/>
                </msh:row> <msh:row>
                    <msh:label property="startWorkUsername" label="Исполнитель" />
                </msh:row>
                    <msh:separator label="Информация о выполнении заявки" colSpan="10"></msh:separator>
                    <msh:row>
                        <msh:label property="finishDate" label="Дата исполнения"/>
                    </msh:row> <msh:row>
                    <msh:label property="finishTime" label="Время исполнения"/>
                </msh:row> <msh:row>
                    <msh:label property="finishUsername" label="Работник, выполнивший заявку" />
                    <msh:ifInRole roles="/Policy/Mis/Claim/Operator">
                        <msh:row>
                            <msh:textField property="creatorComment" size="50" />
                        </msh:row>
                    </msh:ifInRole>
                </msh:row>

                    <msh:separator label="Информация об отмене заявки" colSpan="10"></msh:separator>
                    <msh:row>
                        <msh:label property="cancelDate" label="Дата отмены"/>
                    </msh:row> <msh:row>
                    <msh:label property="cancelTime" label="Время отмены"/>
                </msh:row> <msh:row>
                    <msh:label property="cancelUsername" label="Пользователь, отменивший заявку" />
                </msh:row>
                    <msh:hidden property="screenFileName"/>
                    <msh:separator label="Прикреплённый скриншот" colSpan="10"></msh:separator>
                    <msh:submitCancelButtonsRow colSpan="4"/>
                </msh:ifFormTypeAreViewOrEdit>

                <msh:ifFormTypeIsCreate formName="mis_claimForm">
                    <msh:submitCancelButtonsRow colSpan="1"  />
                    <script type='text/javascript' src='./dwr/interface/ClaimService.js'></script>
                    <script type="text/javascript">
                        window.onload = function() {
                            ($('screenFileName')).setAttribute("readonly","false"); //чтобы при перезагрузке не терялся скрин
                            if ('${img}'!='') {
                                $('description').value='Описание: ' + '${description}' + '\nСтраница ошибки: ' + document.referrer +
                                    '\nСтраница перед ошибкой: ' + '${hist}';
                                $('screenFileName').value='${img}';
                                ($('screenFileName')).setAttribute("readonly","true");
                                if ($('description').value!=null && $('description').value!="") {
                                    ClaimService.getSoftType({
                                        callback: function (res) {
                                            if (res != null && res != '{}') {
                                                var Result = JSON.parse(res);
                                                if (typeof(Result.id) !=='undefined') $('claimType').value = Result.id;
                                                if (typeof(Result.name) !=='undefined') $('claimTypeName').value = Result.name;
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    </script>
                </msh:ifFormTypeIsCreate>
                <msh:ifFormTypeAreViewOrEdit formName="mis_claimForm">
                    <script type='text/javascript' src='./dwr/interface/ClaimService.js'></script>
                    <script type="text/javascript">
                        window.onload = function() {
                            if ($('screenFileName').value!=null && $('screenFileName').value!="") {
                                var image = document.getElementById("fileinfo") ;
                                image.src="/docmis"+$('screenFileName').value;
                                image.removeAttribute("hidden");
                            }
                        };
                        <msh:ifFormTypeIsNotView formName="mis_claimForm">
                        window.onload = function() {
                            ($('description')).setAttribute("readonly","false"); //не даёт сохранить - просто перезагружает страницу (при viewOnlyField=true)
                            if ($('screenFileName').value!=null && $('screenFileName').value!="") {
                                var image = document.getElementById("fileinfo") ;
                                image.src="/docmis"+$('screenFileName').value;
                                image.removeAttribute("hidden");
                        };
                        </msh:ifFormTypeIsNotView>
                    </script>
                </msh:ifFormTypeAreViewOrEdit>
            </msh:panel>
        </msh:form>
        <tags:mis_claimStart name="New" status="id"/>
        <img alt="Загрузка изображения" id="fileinfo" width="100%" hidden>
        <script type="text/javascript">
            function show(status) {
                $('statusState').value=status;
                showNewClaimStart();
            }
        </script>


    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:ifFormTypeAreViewOrEdit formName="mis_claimForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-mis_claim" name="Изменить" roles="/Policy/Mis/Claim/Edit" />
            </msh:sideMenu>
        </msh:ifFormTypeAreViewOrEdit>

    </tiles:put>

    <tiles:put name='title' type='string'>
        <ecom:titleTrail mainMenu="Patient" beginForm="mis_claimForm" />
    </tiles:put>
</tiles:insert>