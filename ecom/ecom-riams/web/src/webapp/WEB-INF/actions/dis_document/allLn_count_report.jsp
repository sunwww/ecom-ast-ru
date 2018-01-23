<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="body" type="string">
        <msh:form guid="formHello" action="/allLn_count_report.do" defaultField="hello">
            <msh:panel guid="panel">
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <td>
                        Период
                    </td>
                    <msh:textField property="dateBegin" label="c"/>
                    <msh:textField property="dateEnd" label="по"/>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            String beginDate = request.getParameter("dateBegin");
            if (beginDate!=null && !beginDate.equals("")) {
                String finishDate = request.getParameter("dateEnd");
                if (finishDate==null || finishDate.equals("")) {
                    finishDate=beginDate ;
                }
                request.setAttribute("dateStart", beginDate);
                request.setAttribute("dateFinish", finishDate);
        %>

        <msh:section>
        <ecom:webQuery name = "elnList" nativeSql="
select
lpu.name,
count(dd.id) as lns,
count(dd.id) - count(edd.id) as ln,
((count(dd.id) - count(edd.id))*100)/count(dd.id) as procetln,
count(edd.id) as eln,
 100 - ((count(dd.id) - count(edd.id))*100)/count(dd.id) as procenteln
from disabilitydocument dd
left join disabilityrecord dr on dr.disabilitydocument_id = dd.id
left join workfunction wf on dr.workfunction_id = wf.id
left join worker w on w.id = wf.worker_id
left join mislpu lpu on lpu.id = w.lpu_id
left join electronicdisabilitydocumentnumber edd on edd.disabilitydocument_id = dd.id
where dd.issuedate between to_date('${dateStart}','dd.mm.yyyy') and to_date('${dateFinish}','dd.mm.yyyy')
and dd.hospitalizednumber is not null and dd.anotherlpu_id is null
group by lpu.name
order by lpu.name
" />


            <msh:sectionTitle>Период с ${dateStart} по ${dateFinish}
                <form action="print-report_categoryForeignNationals.do" method="post" target="_blank">
                    <input type='hidden' name="s" id="s" value="PrintService">
                    <input type='hidden' name="m" id="m" value="printManyNativeQuery">
                    <input type="submit" value="Печать">
                </form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="elnList" action="allLn_count_report" idField="1">
                    <msh:tableColumn columnName="№" identificator="false" property="sn" />
                    <msh:tableColumn columnName="Отделение" property="1"/>
                    <msh:tableColumn columnName="Всего ЛН" property="2" isCalcAmount="true"/>
                    <msh:tableColumn columnName="Бумажных" property="3" isCalcAmount="true"/>
                    <msh:tableColumn columnName="%" property="4"/>
                    <msh:tableColumn columnName="Электронных" property="5" isCalcAmount="true"/>
                    <msh:tableColumn columnName="%" property="6"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%} else { %>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <%}%>
    </tiles:put>
</tiles:insert>
