<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocFondV020.do" defaultField="code">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
               <msh:row>
                   <msh:textField property="code" size="100"/>
                </msh:row><msh:row>
                   <msh:textField property="name" size="100"/>
            </msh:row><msh:row>
                   <msh:textField property="startDate" size="10"/>
                   <msh:textField property="finishDate" size="10"/>
            </msh:row>
                <msh:row>
                    <msh:autoComplete label="Услуга по умолчанию для стационара" vocName="vocMedServiceActual" property="defaultStacMedService" size="100"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="e2_vocFondV020Form">
            <ecom:webQuery name="linkList" nativeSql="select link.id, pr.code||' '||pr.name
             , link.startDate as f3, link.finishDate as f4
             , vbst.code||' '||vbst.name as f5_subType
             from E2MedHelpProfileBedType link
             left join vocbedsubtype vbst on vbst.id=link.subtype_id
             left join VocE2MedHelpProfile pr on pr.id=link.profile_id
             where link.bedProfile_id=${param.id}"/>
            <msh:table deleteUrl="entityParentDeleteGoParentView-e2_medHelpBedType.do" idField="1" name="linkList" action="entityParentEdit-e2_medHelpBedType.do" noDataMessage="Нет соответствий">
                <msh:tableColumn columnName="Профиль мед. помощи" property="2"/>
                <msh:tableColumn columnName="Тип коек" property="5"/>
                <msh:tableColumn columnName="Действует с" property="3"/>
                <msh:tableColumn columnName="Действует по" property="4"/>
            </msh:table>
            <msh:separator colSpan="8" label="Привязать профиль мед. помощи"/>
            <msh:autoComplete label="Профиль коек" property="newMedHelpProfile" vocName="vocE2MedHelpProfile"  size="100"/>
            <msh:autoComplete label="Тип коек" property="newBedSubType" vocName="vocBedSubType"  size="100"/>
            <input type="button" onclick="createProfileByBedType()" value="Привязать койку" >


        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocFondV020Form" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV020Form">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">

                function createProfileByBedType() {
                    var url = 'entityParentSaveGoParentView-e2_medHelpBedType.do';
                    jQuery.ajax({
                        url:url
                        ,data: {
                            id:0 //При создании всегда ноль
                            ,saveType:1
                            ,bedProfile:${param.id} //Если есть родитель
                            ,profile:$('newMedHelpProfile').value  //1 значит создать, 2 - изменить существующее
                            ,subType:$('newBedSubType').value
                            ,startDate:"01.01.2019" //Все остальные поля заполняем как  на форме
                        }
                    }).done (function(ret) {console.log(ret); alert("Добавлено!")});
                }
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV020Form">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocFondV020" name="Изменить" roles="/Policy/E2/Edit" />

            </msh:sideMenu>
            <tags:expertvoc_menu currentAction="main"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

