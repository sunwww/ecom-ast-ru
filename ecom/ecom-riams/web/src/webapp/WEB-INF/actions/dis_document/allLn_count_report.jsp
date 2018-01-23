<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
    <tiles:put name="body" type="string">
        <msh:form guid="formHello" action="/allLn_count_report.do" defaultField="hello">
            <msh:panel guid="panel">
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
        <msh:section>
            <msh:sectionContent>
                <msh:table name="elnList" action="" idField="1">
                    <msh:tableColumn columnName="Отделение" property="1"/>
                    <msh:tableColumn columnName="Всего ЛН" property="2"/>
                    <msh:tableColumn columnName="Бумажных" property="3"/>
                    <msh:tableColumn columnName="%" property="4"/>
                    <msh:tableColumn columnName="Электронных" property="5"/>
                    <msh:tableColumn columnName="%" property="6"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%} else { %>
        <p>Укажите период!</p>
        <%}%>
    </tiles:put>
</tiles:insert>
