<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.s}Layout.jsp" flush="true">

    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal">Направления на исследования</msh:title>
    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:stac_journal currentAction="stac_directionsByUserDepartment"/>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            Long department = (Long) request.getAttribute("department");
            if (department != null && department.intValue() > 0) {
        %>
        <msh:section>
            <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="

    select pat.id
    ,sc.code as sccode
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy')
    ||case when m.datestart!=sls.dateStart then '(госп. с '||to_char(sls.dateStart,'dd.mm.yyyy')||')' else '' end
    ||case when m.dateFinish is not null then ' выписывается '||to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) else '' end as datestart
    	,wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
    ,cast('' as varchar) as emp
    from medCase m
    left join Diagnosis diag on diag.medcase_id=m.id
    left join vocidc10 mkb on mkb.id=diag.idc10_id
	left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationType_id
	left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id

    left join MedCase as sls on sls.id = m.parent_id
    left join medcase sloAll on sloAll.parent_id=sls.id and sloAll.dtype='DepartmentMedCase'
left join Mislpu dep on dep.id=sloAll.department_id
    left join bedfund as bf on bf.id=m.bedfund_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id =sloAll.id
    left join SurgicalOperation so1 on so1.medCase_id =sls.id
    left join medservice ms on ms.id=so.medService_id
    left join medservice ms1 on ms1.id=so1.medService_id
    left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join Patient pat on m.patient_id = pat.id
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}'
    and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    group by pat.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart
    ,bf.addCaseDuration,m.dateFinish,m.dischargeTime
    order by pat.lastname,pat.firstname,pat.middlename
    "
            />
            <msh:sectionTitle>
                Журнал состоящих пациентов в отделении  ${departmentInfo} на текущий момент
                <br>ВНИМАНИЕ! Необходимо ввести <u>дату забора крови</u>, чтобы пациент попал в направление!
                <input type="button" value="Печать на HbsAg" onclick="print('HbsAg')">
                <input type="button" value="Печать на HCV" onclick="print('HCV')">
                <input type="button" value="Печать на сифилис" onclick="print('dirSif')">
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table name="datelist" viewUrl="entityShortView-mis_patient.do" action="/javascript:void()"
                           idField="1">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn columnName="Дата забора крови" property="7"/>
                    <msh:tableColumn columnName="Регистрац. номер" property="7"/>
                    <msh:tableColumn columnName="Стат.карта" property="2"/>
                    <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3"/>
                    <msh:tableColumn columnName="Год рождения" property="4"/>
                    <msh:tableColumn columnName="Дата поступления" property="5"/>
                    <msh:tableColumn columnName="Леч.врач" property="6"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <% } else {%>
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
            <msh:section>
                <msh:sectionTitle>Свод состоящих пациентов в отделении  ${departmentInfo} на текущий момент
                </msh:sectionTitle>
                <msh:sectionContent>
                    <ecom:webQuery name="datelist" nameFldSql="datelist_sql" nativeSql="
    select m.department_id,ml.name, count(distinct sls.id) as cntSls
    ,count(distinct case when m.emergency='1' then sls.id else null end) as cntEmergency
    ,count(distinct case when so.id is not null or so1.id is not null then sls.id else null end) as cntOper
    from medCase m
    left join MedCase as sls on sls.id = m.parent_id
    left join StatisticStub as sc on sc.medCase_id=sls.id
    left join SurgicalOperation so on so.medCase_id=m.id
    left join SurgicalOperation so1 on so1.medCase_id =sls.id
    left join MisLpu ml on ml.id=m.department_id
    where m.DTYPE='DepartmentMedCase'
    and m.transferDate is null and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)
    group by m.department_id,ml.name
    order by ml.name
    "
                    />
                    <msh:table name="datelist" viewUrl="stac_directionsByUserDepartment.do?s=Short&"
                               action="stac_directionsByUserDepartment.do" idField="1">
                        <msh:tableColumn property="sn" columnName="#"/>
                        <msh:tableColumn columnName="Отделение" property="2"/>
                        <msh:tableColumn columnName="Кол-во состоящих" property="3"/>
                        <msh:tableColumn columnName="кол-во экстренных" property="4"/>
                        <msh:tableColumn columnName="кол-во опер. пациентов" property="5"/>
                    </msh:table>
                </msh:sectionContent>
            </msh:section>
        </msh:ifInRole>
        <% } %>
    </tiles:put>
    <%
        Long department = (Long) request.getAttribute("department");
        if (department != null && department.intValue() > 0) {
    %>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            var table = document.getElementsByTagName('table')[0];

            //создать текстовое поле
            function createTextField(td, ii) {
                td.innerHTML = "<label id=\"regNum" + ii + "Label\" for=\"regNum" + ii + "\"></label>" +
                    "<input title=\"Рег.&nbsp;номерNoneField\" id=\"regNum" + ii + "\" name=\"regNum" + ii + "\" size=\"10\" value=\"\" type=\"text\" autocomplete=\"off\">";
            }

            //создать дату
            function createDate(td, ii) {
                td.innerHTML = "<label id=\"dateIntake" + ii + "Label\" for=\"dateIntake" + ii + "\"></label>" +
                    "<input title=\"Дата&nbsp;забораNoneField\" id=\"dateIntake" + ii + "\" name=\"dateIntake" + ii + "\" size=\"10\" value=\"\" type=\"text\" autocomplete=\"off\">";
                new dateutil.DateField($('dateIntake' + ii));
            }

            function print(service) {
                var params = '';
                if (typeof table !== 'undefined') {
                    for (var ii = 1; ii < table.rows.length; ii++) {
                        var row = table.rows[ii];
                        var id = +row.className.replace('datelist', '')
                            .replace('selected', '').replace(' ', '');
                        if (!isNaN(id) && $('dateIntake' + ii).value != '') {
                            params = params + id + "--"
                                + $('dateIntake' + ii).value + "-"
                                + $('regNum' + ii).value + "!";
                        }
                    }
                }
                if (params.length > 0)
                    window.open('print-dirHbsAg.do?m=printDirectionHIV&s=HospitalPrintService&info=' + params + "&name="+service);
                else
                    showToastMessage('Укажите дату забора хотя бы одного пациента, чтобы распечатать направление!', null, true);
            }

            function setInputs() {
                table.className = '';
                if (typeof table !== 'undefined') {
                    for (var ii = 1; ii < table.rows.length; ii++) {
                        createDate(table.rows[ii].cells[2], ii);
                        createTextField(table.rows[ii].cells[3], ii);
                    }
                }
            }

            setInputs();
        </script>
    </tiles:put>
    <%}%>
</tiles:insert>