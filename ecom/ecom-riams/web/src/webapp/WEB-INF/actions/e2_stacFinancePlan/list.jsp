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
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_stacFinancePlan" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
<msh:textField label="Копировать с " property="copyStartDate"/>
<msh:textField label="Копировать по" property="copyFinishDate"/>
        <%
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String startDateSql = " and to_char(fp.startDate,'MM')='01' and to_char(fp.finishDate,'MM')='12'";
        request.setAttribute("startDateSql",startDateSql);
            if (year==null||year.equals("")) { //Список планов по годам.
            %>
        <ecom:webQuery name="entryList" nativeSql="select to_char(fp.startDate,'yyyy') as year,'&year='||to_char(fp.startDate,'yyyy') as url
             from financePlan fp
              where fp.dtype='HospitalFinancePlan' ${startDateSql}
              group by to_char(fp.startDate,'yyyy')
              order by to_char(fp.startDate,'yyyy')"/>
        <msh:section title='Результат поиска'>
            <msh:table  name="entryList" action="entityList-e2_stacFinancePlan.do" idField="2" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="Период" property="1" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
            </msh:table>
        </msh:section>
        <%
        } else if (month==null||month.equals("")) {
            String isReestr = request.getParameter("reestr");
            if (isReestr!=null&&!isReestr.equals("")) {    //План на год
        %>
        <ecom:webQuery name="entryList" nativeSql="select fp.id
            ,to_char(fp.startDate,'yyyy') as date
            ,ksg.code||' '||ksg.name as f5_ksg
            , mhp.code||' '||mhp.name as profile
            ,ml.name as f4_department
            ,fp.count as f6
            ,fp.cost as f7
            ,vbt.name as f8
             from financePlan fp
             left join vocksg ksg on ksg.id=fp.ksg_id
             left join VocE2MedHelpProfile mhp on mhp.id=fp.profile_id
             left join mislpu ml on ml.id=fp.department_id
             left join vocbedsubtype vbt on vbt.id=fp.bedsubtype_id
              where fp.dtype='HospitalFinancePlan' ${startDateSql}
              order by fp.startDate, cast(ksg.code as int), ml.name, vbt.name "/>
        <input type="button" onclick="splitFinancePlan()" value="Раскидать по месяцам">
        <msh:section title='Результат поиска'>
            <msh:table  name="entryList" action="entityView-e2_stacFinancePlan.do" idField="1" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="Период" property="2" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                <msh:tableColumn columnName="КСГ" property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                <msh:tableColumn columnName="Профиль" property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                <msh:tableColumn columnName="Отделение" property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                <msh:tableColumn columnName="тип коек" property="8" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                <msh:tableColumn columnName="Кол-во случаев" property="6" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                <msh:tableColumn columnName="Цена" property="7" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
            </msh:table>
        </msh:section>
        <%

            } else {
                //Список по месяцам
        %>

        <ecom:webQuery name="entryList" nativeSql="select to_char(fp.startDate,'MM.yyyy') as period,'&year=${param.year}&month='||to_char(fp.startDate,'MM.yyyy') as url
             from financePlan fp
              where fp.dtype='HospitalFinancePlan' and to_char(fp.startDate,'MM')= to_char(fp.finishDate,'MM') and to_char(fp.startDate,'yyyy')='${param.year}'
              group by to_char(fp.startDate,'MM.yyyy')
              order by to_char(fp.startDate,'MM.yyyy')"/>
        <input type="button" onclick="splitFinancePlan()" value="Раскидать по месяцам">
        <msh:section title='Финансовый план за ${param.year} год помесячно'>
            <msh:table  name="entryList" action="entityList-e2_stacFinancePlan.do" idField="2" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="Период" property="1" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />

            </msh:table>
        </msh:section>
        <%
            }
            //Список месяцов, в которых есть планы
            %>


        <% } else {
            startDateSql =" and '"+month+"'=to_char(fp.startDate,'MM.yyyy')";
            request.setAttribute("startDateSql",startDateSql);

        %>
        <input type="button" onclick="copyPlanNextMonth()" value="Копировать на месяца"/>
        <msh:hideException>
            <ecom:webQuery name="entryList" nativeSql="select fp.id
            ,to_char(fp.startDate,'MM.yyyy') as date
            ,ksg.code||' '||ksg.name as ksg
            , mhp.code||' '||mhp.name as profile
            ,ml.name as department

            ,fp.count
            ,fp.cost
            ,vbt.name as f8_bedType
             from financePlan fp
             left join vocksg ksg on ksg.id=fp.ksg_id
             left join VocE2MedHelpProfile mhp on mhp.id=fp.profile_id
             left join mislpu ml on ml.id=fp.department_id
             left join vocbedsubtype vbt on vbt.id=fp.bedsubtype_id
              where fp.dtype='HospitalFinancePlan' ${startDateSql}
              order by fp.startDate, cast(ksg.code as int), ml.name, vbt.name  "/>
            <msh:section title='Результат поиска'>
                <msh:table  name="entryList" action="entityView-e2_stacFinancePlan.do" idField="1" disableKeySupport="true" styleRow="6">
                    <msh:tableColumn columnName="Период" property="2" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="КСГ" property="3" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Профиль" property="4" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Отделение" property="5" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="тип коек" property="8" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Кол-во случаев" property="6" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                    <msh:tableColumn columnName="Цена" property="7" guid="5b05897f-5dfd-4aee-ada9-d04244ef20c6" />
                </msh:table>
            </msh:section>
        </msh:hideException>
        <% }%>
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