<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск дневников по тексту</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/journal_searchEmptyCovid.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            String date = request.getParameter("dateBegin") ;
            if (date!=null && !date.equals(""))  {
                String dateEnd = request.getParameter("dateEnd") ;

                if (dateEnd==null||dateEnd.equals("")) {
                    dateEnd = date;
                }
                request.setAttribute("dateBegin",date) ;
                request.setAttribute("dateEnd", dateEnd) ;
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="journal_emptyCovid" nativeSql="
                select distinct  sls.id,dep.name, pat.patientinfo
                from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join bedfund as bf on bf.id=m.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join Patient pat on m.patient_id = pat.id
                left join Covid19 c on sls.id=c.medcase_id
                left join mislpu dep on dep.id=sls.department_id
                where m.DTYPE='DepartmentMedCase'
                and sls.dateStart between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and vbt.code='14'
                and c.id is null
                order by dep.name" />
                <msh:table name="journal_emptyCovid"  noDataMessage="Нет данных"
                           action="entityParentView-stac_ssl.do" idField="1">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    } %>
    </tiles:put>
</tiles:insert>