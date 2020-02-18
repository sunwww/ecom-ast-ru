<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_extDispPriceForm" />

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_extDispPrice.do" defaultField="dispType">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>

                <msh:row>
                   <msh:autoComplete property="dispType" vocName="vocE2FondV016" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="sex" vocName="vocSex" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="cost" label="Цена полного случая" />
                </msh:row>
                <msh:row>
                    <msh:textField property="ages" label="Возраста (через запятую)" />
                    <msh:textField property="ageGroup"  />
                </msh:row>
                <msh:row>
                    <msh:textField property="ageFrom" />
                    <msh:textField property="ageTo" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateFrom" />
                    <msh:textField property="dateTo" />
                </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
        <msh:separator colSpan="4" label="Необходимые услуги по возрасту"/>
        <msh:section>
            <ecom:webQuery name="medServices" nativeSql="
            select ms.id, ms.medservice||' '||coalesce(vms.name,'') as name, case when ms.isRequired='1' then true else false end as isReq
            from ExtDispPriceMedService ms
            left join vocmedservice vms on vms.code=ms.medservice and vms.finishDate is null
            where ms.price_id=${param.id}" />
            <msh:table
                    idField="1" name="medServices" action="javascript:void()"
                    deleteUrl="entityParentDeleteGoParentView-e2_extDispPriceMedService.do"
                    noDataMessage="Нет услуг">
                <msh:tableColumn columnName="ИД" property="1"/>
                <msh:tableColumn columnName="Услуга" property="2"/>
                <msh:tableColumn columnName="Обязательная" property="3"/>
            </msh:table>
        </msh:section>
    </tiles:put>

    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_extDispPriceForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_extDispPrice" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink key="ALT+2" params="id" action="/entityParentPrepareCreate-e2_extDispPriceMedService" name="Добавить услугу" roles="/Policy/E2/Create" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_extDispPriceForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">

             </script>

        </msh:ifFormTypeIsView>
    </tiles:put>
</tiles:insert>

