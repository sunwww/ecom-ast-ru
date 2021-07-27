<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Просмотр данных по вакцинированным пациентам</msh:title>
    </tiles:put>

    <tiles:put name="side" type="string">

    </tiles:put>
    <%
        String typeView = ActionUtil.updateParameter("Form039Action", "typeView", "3", request);
    %>
    <tiles:put name="body" type="string">
        <msh:form action="/covid19Vac.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7"/>
                </msh:row>
                <msh:row>
                    <td></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeView" checked value="now"> По состоящим
                    </td>
                    <td colspan="2" onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeView" value="disch"> По выписанным
                    </td>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" vocName="vocLpuHospOtdAll" label="Отделение"
                                      fieldColSpan="6" horizontalFill="true"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Дата с"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>
                <msh:row>
                    <td>
                        <input type="submit" value="Найти"/>
                    </td>
                </msh:row>

            </msh:panel>
        </msh:form>
        <%
            String date = request.getParameter("dateBegin");
            if (date != null && !date.equals("") || typeView.equals("now")) {
                String dateEnd = request.getParameter("dateEnd");

                if (dateEnd == null || dateEnd.equals("")) {
                    dateEnd = date;
                }
                request.setAttribute("addSql", "sls.dateStart between to_date('" + date + "','dd.mm.yyyy') and to_date('" + dateEnd + "','dd.mm.yyyy')");
                String addSql;
                switch (typeView) {
                    case "now":
                        addSql = " and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date" +
                                " and m.dischargetime>CURRENT_TIME) and sls.datefinish is null";
                        break;
                    case "disch":
                        addSql = " and sls.dateFinish between to_date(' " + date + "','dd.mm.yyyy') and to_date('" + dateEnd + "','dd.mm.yyyy')";
                        break;
                    default:
                        addSql = " ";
                }
                addSql += ActionUtil.getValueInfoById("select id, name from mislpu where id=:id"
                        , "отделение", "department", "dep.id", request);
                request.setAttribute("addSql", addSql);
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска вакцинированных</msh:sectionTitle>
            <msh:sectionContent>
                <ecom:webQuery name="list_covidVac" nativeSql="
select distinct sls.id as f1_id
,pat.patientinfo as f2_patinfo
,st.code as f3_hist
,dep.name as f4_dep
,to_char(sls.datestart,'dd.MM.yyyy') as f5_start
,to_char(sls.datefinish,'dd.MM.yyyy') as f6_fin
,vhr.name as f7_vhr
,case when c.FirstVaccineСomponent_id=1 then 'Да' else 'Нет' end as f8_first
,to_char(c.dateFirstVaccine,'dd.MM.yyyy') as f9_dFirst
,case when c.secondVaccineСomponent_id=1 then 'Да' else 'Нет' end as f10_sec
,to_char(c.dateSecondVaccine,'dd.MM.yyyy') as f11_dSec
,case when c.reVaccineСomponent_id=1 then 'Да' else 'Нет' end as f12_re
,to_char(c.dateReVaccine,'dd.MM.yyyy') as f13_dRe
from medcase m
left join MedCase sls on sls.id = m.parent_id
left join Patient pat on pat.id=sls.patient_id
left join covid19 c on c.medcase_id=sls.id
left join VocHospitalizationResult vhr on vhr.id=sls.result_id
left join mislpu dep on dep.id=m.department_id
left join statisticstub st on st.medcase_id=sls.id
where c.vaccinated_id=1 and m.dtype='DepartmentMedCase'
and m.id = (select max(id) from medcase m2 where dtype='DepartmentMedCase' and parent_id=sls.id)
and c.id = (select max(id) from covid19 where medcase_id=sls.id)
    ${addSql}"/>
                <msh:table name="list_covidVac" action="entityParentView-stac_ssl.do" openNewWindow="true"
                           printToExcelButton="Сохранить в excel" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Пациент" property="2" width="20"/>
                    <msh:tableColumn columnName="Номер истории" property="3" width="5"/>
                    <msh:tableColumn columnName="Отделение поступления" property="4" width="20"/>
                    <msh:tableColumn columnName="Дата поступления" property="5" width="5"/>
                    <msh:tableColumn columnName="Дата выписки" property="6" width="5"/>
                    <msh:tableColumn columnName="Результат госп." property="7" width="5"/>
                    <msh:tableColumn columnName="I этап" property="8" width="7"/>
                    <msh:tableColumn columnName="Дата I" property="9" width="7"/>
                    <msh:tableColumn columnName="II этап" property="10" width="7"/>
                    <msh:tableColumn columnName="Дата II" property="11" width="7"/>
                    <msh:tableColumn columnName="Ревак." property="12" width="7"/>
                    <msh:tableColumn columnName="Дата ревак." property="13" width="5"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>

        <script type='text/javascript'>
            checkFieldUpdate('typeView', '${typeView}', 'now');

            function checkFieldUpdate(aField, aValue, aDefaultValue) {
                if (jQuery(":radio[name=" + aField + "][value='" + aValue + "']").val() != undefined) {
                    jQuery(":radio[name=" + aField + "][value='" + aValue + "']").prop('checked', true);
                } else {
                    jQuery(":radio[name=" + aField + "][value='" + aDefaultValue + "']").prop('checked', true);
                }
            }

        </script>
        <%} else {%>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <% } %>
    </tiles:put>
</tiles:insert>