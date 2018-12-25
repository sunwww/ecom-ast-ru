<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="preg_misbirthForm" guid="0908a638-fd02-4b94-978b-18ab86829e08">
            <msh:sideMenu title="Выкидыш" guid="bc6ceef3-4709-47d9-ba37-d68540cffc61">
                <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_misbirth" name="Изменить" roles="/Policy/Mis/Pregnancy/ChildBirth/Edit" guid="a8d1a1fa-aa31-408a-b1f6-6b9ba1ff18e8" />
                <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_misbirth" name="Удалить" roles="/Policy/Mis/Pregnancy/ChildBirth/Delete" guid="91460b8b-80a7-46b3-bc95-a53cd320f687" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entityParentSaveGoView-preg_misbirth.do" defaultField="durationPregnancy" guid="93666922-7bed-42a7-be5e-b2d52e41d39b">
            <msh:hidden property="id" guid="2821496c-bc8e-4cbe-ba14-ac9a7f019ead" />
            <msh:hidden property="medCase" guid="2104232f-62fa-4f0b-84de-7ec4b5f306b3" />
            <msh:hidden property="saveType" guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
            <msh:hidden property="isECO"  guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
            <msh:hidden property="isRegisteredWithWomenConsultation"  guid="3ec5c007-f4b1-443c-83b0-b6d93f55c6f2" />
            <msh:panel guid="0a4989f1-a793-45e4-905f-4ac4f46d7815">
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
                    <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                        <td class="label" title="Поиск по промежутку  (ecoGroup)" colspan="1"><label for="ecoGroupName" id="tecoGroupLabel">Выберите:</label></td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                            <input type="radio" name="ecoGroup" value="1"> ЭКО
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                            <input type="radio" name="ecoGroup" value="2"> Без ЭКО
                        </td>
                    </msh:row>
                    <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
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
                        <msh:checkBox property="isECO" label="ЭКО?" guid="bfc88e8a-d54c-48f9-87e9-6740779e3287" fieldColSpan="1"/>
                    </msh:row>
                    <msh:row>
                        <msh:checkBox property="isRegisteredWithWomenConsultation" label="Учёт в ЖК?" guid="bfc88e8a-d54c-48f9-87e9-6740779e3287" fieldColSpan="1"/>
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
                <msh:submitCancelButtonsRow colSpan="3"  guid="bd5bf27d-bcd4-4779-9b5d-1de22f1ddc68" functionSubmit="save();"/>
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" beginForm="preg_misbirthForm" guid="d16befe8-59da-47d9-9c54-ee0d13e97be2" />
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