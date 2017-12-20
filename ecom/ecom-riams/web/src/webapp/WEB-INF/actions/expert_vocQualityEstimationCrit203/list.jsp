<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Редактирование связей Критерий-Диагнозы-Услуги по 203 приказу</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/listCrits203.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>

            </msh:panel>
        </msh:form>
        <script type='text/javascript'>
            function find() {
                var frm = document.forms[0];
                frm.target = '';
                frm.action = 'listCrits203.do';
            }
        </script>
        <ecom:webQuery name="critList" nativeSql="select crit.id,crit.name,crit.isgrownup,crit.ischild,translate(crit.medservicecodes,'''',''),list(idc.code) from vocqualityestimationcrit  crit
left join vocqualityestimationcrit_diagnosis crit_diag on crit_diag.vqecrit_id=crit.id
left join vocidc10 idc on idc.id=crit_diag.vocidc10_id
left join vocqualityestimationkind kind on crit.kind_id=kind.id
where kind.code='PR203'
group by crit.id order by crit.id
        "/>
        <msh:table hideTitle="false" styleRow="2" idField="1" name="critList" action="entityEdit-expert_vocQualityEstimationCrit203.do" >
            <msh:tableColumn columnName="#" property="sn" />
            <msh:tableColumn columnName="Критерий" property="2" />
            <msh:tableColumn columnName="Взрослые" property="3"/>
            <msh:tableColumn columnName="Дети" property="4"/>
            <msh:tableColumn columnName="Диагнозы" property="6"/>
            <msh:tableButton property="1" addParam="this" buttonFunction="deleteCrit" buttonName="Удалить" buttonShortName="Уд."/>
            <msh:tableButton property="1" addParam="this" buttonFunction="editCrit" buttonName="Редактировать" buttonShortName="Ред."/>
            <msh:tableColumn columnName="Услуги" property="5"/>
        </msh:table>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu title="Критерии">
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-expert_vocQualityEstimationCrit203" name="Добавить" title="Добавить" />
        </msh:sideMenu>
    </tiles:put>
    <script type="text/javascript" src="./dwr/interface/QualityEstimationService.js">/**/</script>
    <script type='text/javascript'>
        function deleteCrit(id) {
            QualityEstimationService.deleteCrit(
                id, {
                    callback: function (res) {
                        if (res==true) location.reload();
                        else alert("Невозможно удалить, т.к. по этому критерию уже были созданы экспертные карты!");
                    }
                }
            );
        }
        function editCrit(id) {
           window.location="entityEdit-expert_vocQualityEstimationCrit203.do?id="+id;
        }
    </script>
</tiles:insert>