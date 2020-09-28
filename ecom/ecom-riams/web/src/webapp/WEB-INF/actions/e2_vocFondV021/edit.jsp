<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocFondV021.do" defaultField="code">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
               <msh:row>
                   <msh:textField property="code" size="100"/>
                </msh:row><msh:row>
                   <msh:textField property="name" size="100"/>
            </msh:row>
                <msh:row>
                    <msh:autoComplete property="defaultMedService" vocName="vocMedServiceActual" size="50"/>
                </msh:row><msh:row>
                    <msh:autoComplete property="repeatMedService" vocName="vocMedServiceActual" size="50"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="policProfile" vocName="vocE2MedHelpProfile" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="stacProfile" vocName="vocE2MedHelpProfile" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:checkBox property="isPodushevoy"/>
                    <msh:checkBox property="isNoActual"/>
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>

        </msh:form>
        <msh:separator colSpan="8" label="Добавить простую услугу"/>
        <msh:autoComplete label="Услуга" size="100" property="medServiceAdd" vocName="medService"/>
        <msh:checkBox label="Выбрана по умолчанию" property="booleanAdd" />
        <input type="button" onclick="createSimpleService()" value="Добавить услугу">
        <msh:ifFormTypeIsView formName="e2_vocFondV021Form">
            <msh:separator colSpan="8" label="Простые услуги"/>
            <ecom:webQuery name="simpeServiceList" nativeSql="select link.id, ms.code||' '||ms.name as name, link.weight as f3_weight
            ,link.isDefault as f4_isDefault
            from MedServiceComplexLink link
             left join medservice ms on ms.id=link.innerMedService_id where link.speciality_id=${param.id} order by link.weight"/>
            <msh:table idField="1" name="simpeServiceList" action="entityEdit-mis_medServiceBySpeciality.do" noDataMessage="Нет простых услуг">
                <msh:tableColumn columnName="Услуга" property="2"/>
                <msh:tableColumn columnName="Вес" property="3"/>
                <msh:tableColumn columnName="По умолчанию" property="4"/>
            </msh:table>

        </msh:ifFormTypeIsView>

    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocFondV021Form" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV021Form">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                function createSimpleService() {
                    var serviceId = +$('medServiceAdd').value;
                    if (serviceId===0) {
                        alert('Укажите простую услугу!');
                        return;
                    }
                    let url = 'entitySaveGoView-mis_medServiceBySpeciality.do';
                    let medServiceLink = {
                        saveType:1
                        ,speciality:${param.id}
                        ,countInnerMedService:1
                        ,innerMedService:serviceId
                        ,isDefault:$('booleanAdd').checked

                    };

                    jQuery.ajax({ //создаем сущность
                        type: "POST"
                        ,url:url
                        ,data: medServiceLink
                    }).done (function(htm) {
                        alert('Добавлено!');
                        console.log(htm);
                    }).fail( function (err) {
                        console.log("ERROR "+err);
                        alert('ERROR '+err);
                    });
                }

            </script>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocFondV021Form">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocFondV021" name="Изменить" roles="/Policy/E2/Edit" />

            </msh:sideMenu>
            <tags:expertvoc_menu currentAction="main"/>
        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

