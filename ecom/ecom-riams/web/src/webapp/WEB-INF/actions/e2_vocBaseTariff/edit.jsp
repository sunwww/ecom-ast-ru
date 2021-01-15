<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocBaseTariff.do" defaultField="code">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
                <msh:row>
                    <msh:autoComplete property="type" vocName="vocE2BaseTariffType" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete  property="vidSluch" vocName="vocE2VidSluch" size="100"/>
                </msh:row>
               <msh:row>
                   <msh:textField property="startDate"/>
                   <msh:textField property="finishDate"/>
                   </msh:row><msh:row>
                   <msh:textField label="Значение тарифа" property="value"/>
            </msh:row><msh:row>
                   <msh:autoComplete label="Тип коек" property="stacType" vocName="vocBedSubType" size="100"/>
            </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
    </tiles:put>
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocBaseTariffForm" />
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocBaseTariffForm">

        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:ifFormTypeAreViewOrEdit formName="e2_vocBaseTariffForm">
        <msh:ifFormTypeIsView formName="e2_vocBaseTariffForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocBaseTariff" name="Изменить" roles="/Policy/E2/Edit" />
            </msh:sideMenu>
            <tags:expertvoc_menu currentAction="main"/>
        </msh:ifFormTypeIsView>
            <msh:ifFormTypeIsNotView formName="e2_vocBaseTariffForm">
                <msh:sideLink action="/javascript:createNewOne()" name="Новая цена" roles="/Policy/E2/Create" />
            </msh:ifFormTypeIsNotView>

        </msh:ifFormTypeAreViewOrEdit>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function createNewOne() {
                let date = prompt("Введите дату начала действия",getCurrentDate());
                let cost = prompt("Введите новую цену",$('value').value);
                if (date && cost) {
                    let url = "entitySaveGoView-e2_vocBaseTariff.do";
                    let newEntity = getFormDataAsJson(jQuery('#mainForm'));
                    newEntity.saveType=1;
                    newEntity.startDate=date;
                    newEntity.value=cost;

                    jQuery.ajax({ //создаем сущность
                        type: "POST"
                        ,url:url
                        ,data: newEntity
                    }).done (function(htm) {
                        alert('Добавлено!');
                        $('finishDate').value=changeDate(date,-1);
                    }).fail( function (err) {
                        console.log("ERROR "+err);
                        alert('Ошибка '+err);
                    });
                } else {
                    alert("нет даты или цены, отменяем");
                }
            }
            function changeDate(l, days) {
                    l = l.substr(6, 4) + '-' + l.substr(3, 2) + '-' + l.substr(0, 2);
                    let dt = new Date();
                    dt.setTime(Date.parse(l));
                    dt.setDate(dt.getDate() + days);
                    var newTextDay = dt.getDate() < 10 ? '0' + dt.getDate() : dt.getDate();
                    var newTextMonth = dt.getMonth() + 1;
                    newTextMonth = newTextMonth < 10 ? '0' + newTextMonth : newTextMonth;
                    var newTextYear = dt.getFullYear();
                    return newTextDay + '.' + newTextMonth + '.' + newTextYear;
            }

        </script>

</tiles:put>
</tiles:insert>

