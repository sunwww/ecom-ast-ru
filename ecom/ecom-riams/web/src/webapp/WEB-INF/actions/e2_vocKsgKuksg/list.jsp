<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Реестры омс</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" >
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vocKsgPlan" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
        <%
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String startDateSql = " and to_char(fp.startDate,'MM')='01' and to_char(fp.finishDate,'MM')='12'";
        request.setAttribute("startDateSql",startDateSql);
            %>
        <ecom:webQuery name="entryList" nativeSql="select ksg.id
            ,ksg.code||' '||ksg.name
            ,''||ksg.kz
            , ksg.profile
            ,vbst.name
            ,ksg.isoperation
            ,ksg.longKsg
            ,ksg.isFullPayment
             from VocKsg ksg
             left join vocBedSubType vbst on vbst.id=ksg.bedsubtype_id
              order by vbst.id, cast(ksg.code as int)"/>
        <msh:section title='Результат поиска'>
            <msh:table  name="entryList" action="entityView-e2_vocKsg.do" idField="1" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="КСГ" property="2" />
                <msh:tableColumn columnName="KZ" property="3" />
                <msh:tableColumn columnName="Профиль" property="4" />
                <msh:tableColumn columnName="Тип коек" property="5" />
                <msh:tableColumn columnName="Операционное КСГ" property="8" />
                <msh:tableColumn columnName="Сверхдлительное КСГ" property="6" />
                <msh:tableColumn columnName="ПРизнак полной оплаты" property="7" />
            </msh:table>
        </msh:section>

    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <script type="text/javascript">
            try {
                new dateutil.DateField($('copyStartDate'));
                new dateutil.DateField($('copyFinishDate'));
            } catch (e) {}
            function copyPlanNextMonth() {
                var month='${param.month}';
                if (!$('copyStartDate').value) {alert('Выберите период на который копировать'); return;}
                if (!$('copyFinishDate').value) {$('copyFinishDate').value=$('copyStartDate').value;}

                var startCopyMonth=$('copyStartDate').value.substring(3,10);
                var finishCopyMonth=$('copyFinishDate').value.substring(3,10);
                Expert2Service.copyFinancePlanNextMonth(month, startCopyMonth,finishCopyMonth, {
                    callback: function() {alert ("Сделано!");}
                });
            }

            function splitFinancePlan() {
                Expert2Service.splitFinancePlan('HospitalFinancePlan','${param.year}', {
                    callback: function(){alert('MISSION IS POSSIBLE');}
                });
            }
        </script>
            </tiles:put>
</tiles:insert>