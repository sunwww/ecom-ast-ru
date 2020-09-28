<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Отчёт по неидентифицированным госпитализациям</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
            String date = (String)request.getParameter("dateBegin") ;
            String dateEnd = (String)request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
            request.setAttribute("dateBegin", date) ;
            request.setAttribute("dateEnd", dateEnd) ;
             if (request.getParameter("short")==null) {
            %>
        <msh:form action="/journal_noIdentSls.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            if (date!=null && !date.equals("")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="journal_noIdentList" nativeSql="
                select '&depId='||coalesce(dep.id,0)||'&depName='||coalesce(dep.name,''),dep.name as depname
                ,count(sls.id) as c
                from medcase sls
                left join mislpu dep on dep.id=sls.department_id
                left join patient pat on pat.id=sls.patient_id
                where sls.dtype='HospitalMedCase' and (sls.isidentified=false or sls.isidentified is null) and sls.id is not null
                and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy') 
                and to_date('${dateEnd}','dd.mm.yyyy')
                ${department}
                group by dep.id
                order by count(sls.id) desc" />
                <msh:table printToExcelButton="сохранить в excel" name="journal_noIdentList"  noDataMessage="Нет данных" cellFunction="true"
                           action="journal_noIdentSls.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}" idField="1">
                    <msh:tableColumn property="sn" columnName="#" addParam=""/>
                    <msh:tableColumn property="2" columnName="Отделение" addParam=""/>
                    <msh:tableColumn property="3" columnName="Кол-во неидентифицированных" addParam="" isCalcAmount="true"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    } }
            else {
                    %>
        <msh:section>
            <msh:sectionTitle>
                <ecom:webQuery isReportBase="true" name="journal_noIdentList" nativeSql="
                select sls.id as slsId,to_char(sls.dateStart,'dd.mm.yyyy') as dateStart
                ,pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio
                from medcase sls
                left join mislpu dep on dep.id=sls.department_id
                left join patient pat on pat.id=sls.patient_id
                where sls.dtype='HospitalMedCase' and (sls.isidentified=false or sls.isidentified is null) and sls.id is not null
                and sls.dateStart between to_date('${param.dateBegin}','dd.mm.yyyy')
                and to_date('${param.dateEnd}','dd.mm.yyyy') and dep.id=${param.depId}
                order by sls.dateStart" />
                <form action="journal_noIdentList.do" method="post" target="_blank">
                    Результаты поиска за период с ${param.dateBegin} по ${param.dateEnd} по отделению ${param.depName}.
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="сохранить в excel" name="journal_noIdentList" openNewWindow="true"
                           action="entityParentView-stac_ssl.do" idField="1" noDataMessage="Нет данных">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="2" columnName="Дата госпитализации"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
        }%>
    </tiles:put>
</tiles:insert>