<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" beginForm="e2_vocKdpForm" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/entitySaveGoView-e2_vocKdp.do" defaultField="name">
            <msh:hidden property="id" />
            <msh:hidden property="saveType" />
            <msh:panel>
                <msh:separator colSpan="8" label="Общие"/>
                <msh:row>
                </msh:row><msh:row>
                <msh:textField property="code"/>
                <msh:textField property="name"/>
            </msh:row>
            <msh:row>
                <msh:textField property="cost"/>
            </msh:row>
                <msh:row>
                    <msh:textArea property="medServicesList" size="100" />
                </msh:row>
            <msh:row>
                <msh:autoComplete property="speciality" vocName="vocE2FondV021" size="50"/>
            </msh:row>
                <msh:submitCancelButtonsRow colSpan="4" />
            </msh:panel>
        </msh:form>
        <msh:ifFormTypeIsView formName="e2_vocKdpForm">
        <ecom:webQuery nameFldSql="kuksgListSql" name="kuksgList" nativeSql="
        select c.id, vms.code||' '||vms.name
        , case when vms.finishDate is not null then 'color:red' else '' end as f3_color
        from VocDiagnosticVisitMedService c
        left join VocMedService vms on vms.id=c.medService_id
        where c.visit_id=${param.id}
        order by vms.code"/>
        <msh:section title='Услуги' >
            <msh:table deleteUrl="entityDelete-e2_vocKdpMedService.do" name="kuksgList" printToExcelButton="в excel" action="entityDelete-e2_vocKdpMedService.do" idField="1" >
                <msh:tableColumn property="2" columnName="Услуга"/>
            </msh:table>
        </msh:section>
            <msh:separator colSpan="8" label="Привязать койки"/>
            <msh:autoComplete label="Добавить услугу к КДП" size="100" property="medServiceAdd" vocName="vocMedServiceActual"/>
            <input type="button" onclick="addNewMedService()" value="Добавить услугу">
            <input type="textarea" name="tst" id="tst" cols="30" rows="10"></textarea>
        </msh:ifFormTypeIsView>
    </tiles:put>


    <tiles:put name="side" type="string">
        <msh:ifFormTypeIsView formName="e2_vocKdpForm">
            <msh:sideMenu>
                <msh:sideLink key="ALT+2" params="id" action="/entityEdit-e2_vocKdp" name="Изменить" roles="/Policy/E2/Edit" />
                <msh:sideLink params="id" action="/entityDelete-e2_vocKdp" name="Удалить" roles="/Policy/E2/View" />
            </msh:sideMenu>
        </msh:ifFormTypeIsView>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <msh:ifFormTypeIsView formName="e2_vocKdpForm">
            <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
            <script type="text/javascript">
                alert('НЕ используется!!!!');
                alert('НЕ используется!!!!');
                alert('НЕ используется!!!!');
                function addNewMedService() {
                    var msId = jQuery('#medServiceAdd').val();
                    if (msId) {
                        var url = 'entityParentSaveGoParentView-e2_vocKdpMedService.do';
                        jQuery.ajax({
                            url:url
                            ,data: {
                                id:0
                                ,visit:${param.id}
                                ,saveType:1
                                ,medService:msId
                            }
                        }).done (function() {alert("Добавлено!")});

                    } else {
                        alert("Выберите услугу");
                    }


                }


            </script>

        </msh:ifFormTypeIsView>
        <div id = 'testDiv'></div>
    </tiles:put>
</tiles:insert>