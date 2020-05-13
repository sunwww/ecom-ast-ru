<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="preg_misbirthForm">
            <msh:sideMenu title="Выкидыш">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_misbirth" name="Изменить" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_misbirth" name="Удалить" roles="/Policy/Mis/Pregnancy/ChildBirth/Delete" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoView-preg_misbirth.do" defaultField="durationPregnancy">
            <msh:hidden property="id" />
            <msh:hidden property="medCase" />
            <msh:hidden property="saveType" />
            <msh:hidden property="isECO"  />
            <msh:hidden property="isRegisteredWithWomenConsultation"  />
            <msh:panel>
                <msh:row>
                    <msh:textField property="misbirthDate" label="Дата выкидыша"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="durationPregnancy" label="Срок беременности (нед.)"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="typeMisbirth" label="Тип выкидыша" vocName="vocTypeMisbirth" horizontalFill="true" size="130"/>
                 </msh:row>
                <msh:row>
                    <msh:textField property="numFetus" fieldColSpan="1" label="Кол-во плодов" />
                </msh:row>
                <msh:ifFormTypeIsNotView formName="preg_misbirthForm">
                    <msh:row>
                        <td class="label" title="Поиск по промежутку  (ecoGroup)" colspan="1"><label for="ecoGroupName" id="tecoGroupLabel">Выберите:</label></td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                            <input type="radio" name="ecoGroup" value="1"> ЭКО
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                            <input type="radio" name="ecoGroup" value="2"> Без ЭКО
                        </td>
                    </msh:row>
                    <msh:row>
                        <td class="label" title="Поиск по промежутку  (gkGroup)" colspan="1"><label for="gkGroupName" id="tgkGroupLabel">Выберите:</label></td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                            <input type="radio" name="gkGroup" value="1"> Состояла на учёте в ЖК
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                            <input type="radio" name="gkGroup" value="2"> НЕ состояла на учёте в ЖК
                        </td>
                    </msh:row>
                </msh:ifFormTypeIsNotView>
                <msh:ifFormTypeIsView formName="preg_misbirthForm">
                    <msh:row>
                        <msh:checkBox property="isECO" label="ЭКО?" fieldColSpan="1"/>
                    </msh:row>
                    <msh:row>
                        <msh:checkBox property="isRegisteredWithWomenConsultation" label="Учёт в ЖК?" fieldColSpan="1"/>
                    </msh:row>
                </msh:ifFormTypeIsView>
                <msh:ifFormTypeAreViewOrEdit formName="preg_misbirthForm">
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
                        <msh:label property="editDate" label="Дата редактирования"/>
                        <msh:label property="editTime" label="время"/>
                    </msh:row>
                    <msh:row>
                        <msh:label property="editUsername" label="пользователь"/>
                    </msh:row>
                </msh:ifFormTypeAreViewOrEdit>
                <msh:submitCancelButtonsRow colSpan="3"  functionSubmit="save();"/>
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="preg_misbirthForm" />
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/PregnancyService.js" >/**/</script>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            <msh:ifFormTypeAreViewOrEdit formName="preg_misbirthForm">
            <msh:ifFormTypeIsNotView formName="preg_misbirthForm">
                if ($('isECO').value=='true') document.getElementsByName("ecoGroup")[0].checked=true; else document.getElementsByName("ecoGroup")[1].checked=true;
                if ($('isRegisteredWithWomenConsultation').value=='true') document.getElementsByName("gkGroup")[0].checked=true; else document.getElementsByName("gkGroup")[1].checked=true;
            </msh:ifFormTypeIsNotView>
            </msh:ifFormTypeAreViewOrEdit>
            <msh:ifFormTypeIsCreate formName="preg_misbirthForm">
            //Если уже есть и мы переходим с СЛО, запустить просмотр существующего
            function loadExisting() {
                PregnancyService.getIfRobbsonClassOrMisbirthAlreadyExists($('medCase').value,false,{
                    callback: function(aResult) {
                        if (aResult!='')
                            window.location.href='entityParentEdit-preg_misbirth.do?id='+aResult;
                    }
                });
            }
            loadExisting();
            </msh:ifFormTypeIsCreate>
        //проверка на ЭКО и ЖК
        function save() {
            if (!document.getElementsByName("ecoGroup")[0].checked && !document.getElementsByName("ecoGroup")[1].checked) {
                alert("Необходимо установить, было ли проведено ЭКО!"); $('submitButton').disabled = false;
            }
            else if (!document.getElementsByName("gkGroup")[0].checked && !document.getElementsByName("gkGroup")[1].checked) {
                alert("Необходимо установить, состояла ли на учёте в женской консультации!"); $('submitButton').disabled = false;
            }
            else {
                $('isECO').value = document.getElementsByName("ecoGroup")[0].checked;
                $('isRegisteredWithWomenConsultation').value = document.getElementsByName("gkGroup")[0].checked;
                document.forms["mainForm"].submit();
            }
        }
        </script>
    </tiles:put>
</tiles:insert>