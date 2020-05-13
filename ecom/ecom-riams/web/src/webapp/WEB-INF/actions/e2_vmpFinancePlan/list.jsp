<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Expert2">Финансовый план</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить" >
            <msh:sideLink key="ALT+2" action="/entityPrepareCreate-e2_vmpFinancePlan" name="Сформировать новое" roles="/Policy/E2/Create" />
        </msh:sideMenu>
        <tags:expertvoc_menu currentAction="main"/>
    </tiles:put>

    <tiles:put name='body' type='string'>


        <%
            String dtype= request.getParameter("type");
            String formType = "e2_nothinToDo";
            if (dtype==null || dtype.equals("") ) {dtype="HospitalFinancePlan";}
            switch (dtype) {
                case "HospitalFinancePlan":
                    formType="e2_vmpFinancePlan";
                    break;
                case "PolyclinicFinancePlan":
                    formType="e2_polFinancePlan";
                    break;
                case "VmpFinancePlan":
                    formType="e2_vmpFinancePlan";
                    break;
            }
            request.setAttribute("formName",formType);
            request.setAttribute("dtype",dtype);
        String month = request.getParameter("month");
        String year = request.getParameter("year");
        String startDateSql  = " and to_char(fp.startDate,'MM')='01' and to_char(fp.finishDate,'MM')='12'";
        request.setAttribute("startDateSql",startDateSql);
        String[][] filters = {{"department","fp.department_id"}
                            ,{"profile","fp.profile_id"}
                            ,{"ksg","fp.ksg_id"}
                            ,{"bedSubType","fp.bedsubtype_id"}
                            ,{"method","fp.method_id"}
                            ,{"vidSluch","fp.vidSluch_id"}
                            ,{"type","fp.dtype"}
                    };
        request.setAttribute("filterJson","['department','profile','ksg','bedSubType','year','month','reestr','method','vidSluch','type']");

        StringBuilder sqlAppend = new StringBuilder();
        for (String[] filter: filters) {
            String par = request.getParameter(filter[0]);
            if (par!=null&&!par.equals("")) {
                sqlAppend.append(" and ").append(filter[1]).append("='").append(par).append("'");
            }
        }
        request.setAttribute("sqlAppend",sqlAppend.toString());


            if (year==null||year.equals("")) { //Список планов по годам.
            %>
        <ecom:webQuery name="entryList" nativeSql="select to_char(fp.startDate,'yyyy') as year,'&type=${dtype}&year='||to_char(fp.startDate,'yyyy') as url
             from financePlan fp
              where fp.dtype='${dtype}' ${startDateSql}
              group by to_char(fp.startDate,'yyyy')
              order by to_char(fp.startDate,'yyyy')"/>
        <msh:section title='Планы по годам'>
            <msh:table  name="entryList" action="e2_vmpFinancePlan.do" idField="2" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="Год" property="1" />
            </msh:table>
        </msh:section>
        <%
        } else {
            String isReestr = request.getParameter("reestr");
            if (isReestr!=null &&isReestr.equals("1")) {    //План на год
                String selectDateSql ;
                if (month==null||month.equals("")) { // Список по месяцу
                    selectDateSql="to_char(fp.startDate,'yyyy')";
                } else { //Список планов за год
                    selectDateSql="to_char(fp.startDate,'MM.yyyy')";
                    startDateSql =" and '"+month+"'=to_char(fp.startDate,'MM.yyyy') and '"+month+"'=to_char(fp.finishDate,'MM.yyyy')";
                    request.setAttribute("startDateSql",startDateSql);
                }
                request.setAttribute("selectDateSql",selectDateSql);

        %>

        <msh:form action="e2_vmpFinancePlan" defaultField="ksg">
        <msh:panel>
            <msh:row><td>
            <input type="hidden" value="${param.reestr}" id="reestr" name="reestr">
            <input type="hidden" value="${param.year}" id="year" name="year">
            <input type="hidden" value="${param.month}" id="month" name="month">
            <input type="hidden" value="${param.type}" id="type" name="month">
            <input type="hidden" value="" id="method" name="method">
            </td></msh:row>
            <msh:row>
                <msh:autoComplete property="department" vocName="lpu" label="Отделение" size="50"/>
            </msh:row><msh:row>
                <msh:autoComplete property="ksg" vocName="vocKsg" label="КСГ" size="50"/>
            </msh:row><msh:row>
                <msh:autoComplete property="profile" vocName="vocE2MedHelpProfile" label="Профиль мед. помощи" size="50"/>
            </msh:row><msh:row>
                <msh:autoComplete property="bedSubType" vocName="vocBedSubType" label="Тип коек" size="50"/>
            </msh:row><msh:row>
            <msh:autoComplete property="vidSluch" vocName="vocE2VidSluch" label="Вид случая" size="50"/>
        </msh:row><msh:row><td>
                     <input type="button" value="Применить фильтр" onclick="applyFilters()">
                </td>
            </msh:row>
        </msh:panel>
        </msh:form>
        <ecom:webQuery name="entryList" nativeSql="select fp.id
            ,${selectDateSql} as date
            ,ksg.code||' '||ksg.name as f5_ksg
            , mhp.profilek||' '||mhp.name as profile
            ,ml.name as f4_department
            ,fp.count as f6
            ,fp.cost as f7
            ,vbt.name as f8
            ,vs.name as f9_vidSluch
             from financePlan fp
             left join vocksg ksg on ksg.id=fp.ksg_id
             left join VocE2MedHelpProfile mhp on mhp.id=fp.profile_id
             left join mislpu ml on ml.id=fp.department_id
             left join vocbedsubtype vbt on vbt.id=fp.bedsubtype_id
             left join voce2vidSluch vs on vs.id=fp.vidsluch_id
              where fp.dtype='${dtype}' ${startDateSql} ${sqlAppend}
              order by fp.startDate, cast(ksg.code as int), ml.name, vbt.name "/>
        <msh:section title='Результат поиска по ${param.year} ${param.month} году'>
            <msh:table  name="entryList" action="entityView-${formName}.do" idField="1" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="Период" property="2" />
                <msh:tableColumn columnName="Вид случая" property="9" />
                <msh:tableColumn columnName="КСГ" property="3" />
                <msh:tableColumn columnName="Профиль" property="4" />
                <msh:tableColumn columnName="Отделение" property="5" />
                <msh:tableColumn columnName="Кол-во случаев" property="6" isCalcAmount="true" />
                <msh:tableColumn columnName="Цена" property="7" isCalcAmount="true" />
            </msh:table>
        </msh:section>


        <%

            } else {
                //Список по месяцам
        %>
        <msh:panel>
            <msh:row>
                <msh:textField label="Копировать с " property="copyStartDate" />
                <msh:textField label="Копировать по" property="copyFinishDate"/><td>
                <input type="button" onclick="splitFinancePlan()" value="Раскидать по месяцам">
                <input type="button" onclick="fillAggregateTable()" value="Сформировать сведения о выполнении плана. ТЕСТ">
            </td>
            </msh:row>
        </msh:panel>
        <ecom:webQuery name="entryList" nativeSql="select to_char(fp.startDate,'MM.yyyy') as period,'&type=${dtype}&reestr=1&year=${param.year}&month='||to_char(fp.startDate,'MM.yyyy') as url
             from financePlan fp
              where fp.dtype='${dtype}' and to_char(fp.startDate,'MM')= to_char(fp.finishDate,'MM') and to_char(fp.startDate,'yyyy')='${param.year}'
              group by to_char(fp.startDate,'MM.yyyy')
              order by to_char(fp.startDate,'MM.yyyy')"/>

        <msh:section title='Финансовый план за ${param.year} ${param.month} '><input type="button" value="Просмор плана" onclick="addHref('reestr',1)">
            <msh:table  name="entryList" action="e2_vmpFinancePlan.do" idField="2" disableKeySupport="true" styleRow="6">
                <msh:tableColumn columnName="Период" property="1"  />
            </msh:table>
        </msh:section>
        <%
            }
            }
            //Список месяцов, в которых есть планы
            %>

    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/Expert2Service.js"></script>
        <script type="text/javascript">
            try {
                new dateutil.DateField($('copyStartDate'));
                new dateutil.DateField($('copyFinishDate'));
            } catch (e) {}

            function addHref(name, value) {
                window.location.search+="&"+name+"="+value;
            }
            function applyFilters() {
                var json = ${filterJson};
                var href = '';
                for (var i=0;i<json.length;i++) {
                    var obj = json[i];
                    if ($(obj).value) {href+="&"+obj+"="+$(obj).value;}
                }
                window.location.search=href;
            }

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
                Expert2Service.splitFinancePlan('${dtype}','${param.year}', {
                    callback: function(){alert('MISSION IS POSSIBLE');}
                });
            }

            function fillAggregateTable() {
                Expert2Service.fillAggregateTable('${dtype}', $('copyStartDate').value, $('copyFinishDate').value, null, {
                    callback: function(ret) {
                        ret = JSON.parse(ret);
                        if (ret.status=="ok") {
                            showToastMessage("Формирование чего-то там успешно завершено. Сформированино "+ret.count+" записей",null,false);
                        } else {
                            alert("Что-то пошло не так, обратитесь к программситу"+ret.status);
                        }
                    }
                });
                showToastMessage("Формирование запущено, дождитесь сообщения об успешном завершении",null,false);

            }
        </script>
            </tiles:put>
</tiles:insert>