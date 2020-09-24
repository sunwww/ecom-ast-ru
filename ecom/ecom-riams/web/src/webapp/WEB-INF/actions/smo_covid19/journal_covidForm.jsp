<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
    String nul = request.getParameter("nul") ;
    if (nul==null) {

%>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск госпитализаций, в которых нет <u><i>формы оценки тяжести COVID-19</i></u></msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeDate = ActionUtil.updateParameter("typeDate","typeDate","2", request) ;
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
            String date="",dateEnd="", dateTo="";
            if (typeDate!=null) {
                if (typeDate.equals("2")) dateTo = "sls.dateFinish";
                else if (typeDate.equals("1")) dateTo = "sls.datestart";
                date = request.getParameter("dateBegin");
            }
            if (date!=null && !date.equals("")) {
                dateEnd = request.getParameter("dateEnd");

                if (dateEnd == null || dateEnd.equals("")) {
                    dateEnd = date;
                }
            }
            request.setAttribute("dateTo",dateTo) ;
            request.setAttribute("dateBegin",date) ;
            request.setAttribute("dateEnd", dateEnd) ;
            if (request.getParameter("short")==null) {
        %>
        <msh:form action="/journal_covidForm.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocCovidLpu"/>
                </msh:row>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeDate" value="1">  поступления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeDate" value="2">  выписки
                    </td>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {

        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="journal_covidForm" nativeSql="
                select dep.name, count (distinct sls.id) as total
				 ,sum(case when (select id from covidmark where medcase_id=sls.id) is not null then 1 else 0 end) as cntCard
	             ,round(100*cast(sum(case when c.id is not null then 1 else 0 end) as numeric)/cast(count (distinct sls.id)  as numeric),2) as per
                ,sum(case when c.id is null then 1 else 0 end) as cntNotCard
                ,round(100*cast(sum(case when c.id is null then 1 else 0 end) as numeric)/cast(count (distinct sls.id)  as numeric),2) as perNot
                ,'&depId='||coalesce(dep.id,0)||'&depname='||coalesce(dep.name,'')

                from medCase sls
                left join bedfund as bf on bf.id=(select bedfund_id from medcase where dtype='DepartmentMedCase' and parent_id=sls.id limit 1)
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join Patient pat on sls.patient_id = pat.id
                left join CovidMark c on sls.id=c.medcase_id
                left join mislpu dep on dep.id=sls.department_id
                left join vocsost vs on vs.id=c.sost_id
                where sls.DTYPE='HospitalMedCase'
                and ${dateTo} between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and vbt.code='14'
           		${department}
                group by dep.id,dep.name
                order by dep.name
" />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_covidForm"  noDataMessage="Нет данных"
                           action="journal_covidForm.do?&short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"
                           idField="7" cellFunction="true">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="1" columnName="Отделение" addParam="&nul=nul"/>
                    <msh:tableColumn property="2" isCalcAmount="true" columnName="Всего пациентов" addParam="&type=total"/>
                    <msh:tableColumn property="3" isCalcAmount="true" columnName="Форм создано" addParam="&type=create"/>
                    <msh:tableColumn property="4" isCalcAmount="true" columnName="% создано" addParam="&nul=nul"/>
                    <msh:tableColumn property="5" isCalcAmount="true" columnName="Форм не создано" addParam="&type=notCreate"/>
                    <msh:tableColumn property="6" isCalcAmount="true" columnName="% не создано" addParam="&nul=nul"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    }}
        else {
            String type = request.getParameter("type");
            String sqlAdd="";
            if (type!=null) {
                if ("create".equals(type))
                    sqlAdd = " and c.id is not null";
                else if ("notCreate".equals(type))
                    sqlAdd = " and c.id is null";
                request.setAttribute("sqlAdd",sqlAdd);
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd} в отделении ${depname}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="journal_covidFormPat" nativeSql="
                select distinct sls.id,dep.name as depname, pat.patientinfo as info, vs.name as vsname
                from medCase sls
                left join bedfund as bf on bf.id=(select bedfund_id from medcase where dtype='DepartmentMedCase' and parent_id=sls.id limit 1)
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join Patient pat on sls.patient_id = pat.id
                left join CovidMark c on sls.id=c.medcase_id
                left join mislpu dep on dep.id=sls.department_id
                left join vocsost vs on vs.id=c.sost_id
                where sls.DTYPE='HospitalMedCase'
                and ${dateTo} between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and vbt.code='14'
                ${sqlAdd}
                and dep.id=${param.depId}
                order by dep.name" />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_covidFormPat"  noDataMessage="Нет данных"
                           action="entityParentView-stac_ssl.do" idField="1">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                    <msh:tableColumn property="4" columnName="Степень тяжести COVID-19"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%} }%>
        <script type='text/javascript'>
            checkFieldUpdate('typeDate','${typeDate}',1) ;
            function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].'+aField) ;
                var aMax=chk.length ;
                if ((+aValue)==0 || (+aValue)>(+aMax)) {
                    chk[+aDefaultValue-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
        </script>
    </tiles:put>
</tiles:insert>
<%
    }
%>