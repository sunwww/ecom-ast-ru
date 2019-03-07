<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocMedHelpProfile.do" defaultField="code" guid="05d29ef5-3f3c-43b5-bc22-e5d5494c5762">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
               <msh:row>
                   <msh:textField property="name" size="100"/>
                   <msh:textField property="code" size="100"/>
                   </msh:row><msh:row>
                   <msh:textField property="startDate"/>
                   <msh:textField property="finishDate"/>
            </msh:row><msh:row>
                   <msh:textField label="ProfilK" property="profileK" size="100"/>
                   <msh:checkBox property="noActuality"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete vocName="vocE2FondV015" property="medSpec" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete label="Профиль койки V020" vocName="vocE2FondV020" property="profileBed" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete label="Услуга по умолчанию для стационара" vocName="vocMedServiceActual" property="defaultStacMedService" size="100"/>
                </msh:row>

                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="4" />
            </msh:panel>
        </msh:form>
        <ecom:webQuery name="polyclinicCoefficientList" nativeSql="select coef.id, coef.startDate,coef.finishDate,coef.value, vest.code||' '||coalesce(vest.name,'')  as entryType
             ,coef.isdiagnosticSpo as isKdo, coef.isConsultation as f7_isCons
             ,case when coef.finishDate<current_date then 'color:red' else '' end as f8_styleRow
             from VocCoefficient coef
             left join VocE2EntrySubType vest on vest.id=coef.entryType_id
             where coef.dtype='VocE2PolyclinicCoefficient' and coef.profile_id=${param.id}"/>
        <msh:table idField="1" name="polyclinicCoefficientList" action="entityParentEdit-e2_polyclinicCoefficient.do" noDataMessage="Нет коэфцициентов" styleRow="8">
            <msh:tableColumn columnName="Тип случая" property="5"/>
            <msh:tableColumn columnName="КДО" property="6"/>
            <msh:tableColumn columnName="Консультативное" property="7"/>
            <msh:tableColumn columnName="Тариф" property="4"/>
            <msh:tableColumn columnName="Дата начала действия" property="2"/>
            <msh:tableColumn columnName="Дата окончания действия" property="3"/>
        </msh:table>

        <ecom:webQuery name="bedTypes" nativeSql="select mhbt.id, vbt.omccode||' '||vbt.name as typeName, vbst.code||' '||vbst.name as subTypeName from  E2MedHelpProfileBedType mhbt
        left join vocbedtype vbt on vbt.id=mhbt.bedtype_id
        left join vocbedsubtype vbst on vbst.id=mhbt.subtype_id
        where mhbt.profile_id=${param.id} "/>
        <msh:table idField="1" name="bedTypes" action="jabascript:void()" noDataMessage="Нет соответствий с профилем коек" deleteUrl="entityParentDeleteGoParentView-e2_medHelpBedType.do">
            <msh:tableColumn columnName="ИД" property="1"/>
            <msh:tableColumn columnName="Профиль коек" property="2"/>
            <msh:tableColumn columnName="Тип коек" property="3"/>
        </msh:table>
        <msh:separator colSpan="8" label="Привязать койки"/>
        <msh:autoComplete label="Профиль коек" property="newBedType" vocName="vocBedTypeWithCode"  size="100"/>
        <msh:autoComplete label="Тип коек" property="newBedSubType" vocName="vocBedSubType"  size="100"/>
        <input type="button" onclick="attachNewBedType()" value="Привязать койку" >
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocMedHelpProfileForm" guid="fbc3d5c0-2bf8-4584-a23f-1e2389d03646" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocMedHelpProfileForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
            function attachNewBedType () {
                if (jQuery('#newBedType').val()>0) {
                    Expert2Service.addMedHelpProfileBedType(jQuery('#id').val(),jQuery('#newBedType').val(), jQuery('#newBedSubType').val(), {
                        callback: function () {
                                window.location.reload();
                        }
                    });
                }
                    }
            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocMedHelpProfileForm" guid="22417d8b-beb9-42c6-aa27-14f794d73b32">
            <msh:sideMenu guid="32ef99d6-ea77-41c6-93bb-aeffa8ce9d55">
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocMedHelpProfile" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-e2_polyclinicCoefficient" name="Добавить поправочные коэффициент(пол-ка)" roles="/Policy/E2/Edit" />
            </msh:sideMenu>
            <tags:expertvoc_menu currentAction="main"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

