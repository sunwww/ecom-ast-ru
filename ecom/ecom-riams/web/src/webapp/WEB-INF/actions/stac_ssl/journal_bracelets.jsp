<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
    <%
        String typeDate = ActionUtil.updateParameter("BrList","typeDate","1", request) ;
        String typeView = ActionUtil.updateParameter("BrList","typeView","1", request) ;
    %>
    <tiles:put name="title" type="string">
        <msh:title guid="helloItle-123" mainMenu="StacJournal" title="Отчет по браслетам пациентов"></msh:title>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/journal_bracelets.do" defaultField="department" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:row>
                        <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                    </msh:row>
                    <msh:row>
                        <msh:autoComplete property="filterAdd1" fieldColSpan="16" horizontalFill="true" label="Браслет" vocName="vocColorIdentityPatientWithPat"/>
                    </msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />

                    <msh:row>
                        <td class="label" title="Поиск по дате  (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
                        <td onclick="this.childNodes[1].checked='checked';">
                            <input type="radio" name="typeDate" value="1">  поступления
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                            <input type="radio" name="typeDate" value="2">  выписки
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                            <input type="radio" name="typeDate" value="3">  регистрации браслета
                        </td>
                    </msh:row>


                    <msh:row>
                        <td></td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                            <input type="radio" name="typeView" value="1"  >  свод по отделениям
                        </td>
                        <td onclick="this.childNodes[1].checked='checked';" colspan="5">
                            <input type="radio" name="typeView" value="2"  >  реестр пациентов
                        </td>
                    </msh:row>
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <script type='text/javascript'>

            checkFieldUpdate('typeDate','${typeDate}',1) ;
            checkFieldUpdate('typeView','${typeView}',1) ;
            function checkFieldUpdate(aField,aValue,aDefault) {

                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
        </script>
        <%
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and m.department_id="+department);

            String date = (String)request.getParameter("dateBegin") ;
            String dateEnd = (String)request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;


            if (typeDate.equals("1")) {typeDate="sls.dateStart";}
            else if (typeDate.equals("2")) {typeDate="sls.dateFinish";}
            else if (typeDate.equals("3")) {typeDate="cid.startdate";}

            String sqlDate = date!=null && !date.equals("")?
                    " and " + typeDate + " between to_date('"+date+"','dd.mm.yyyy') and to_date('"+dateEnd+"','dd.mm.yyyy')" :
                    " and (m.dateFinish is null or m.dateFinish=current_date and m.dischargetime>CURRENT_TIME)";
            request.setAttribute("sqlDate", sqlDate) ;

            String bracelet = (String)request.getParameter("filterAdd1");
            String brSql = bracelet!=null && !bracelet.equals("")?
                " and vcid.id=" + bracelet : "";
            request.setAttribute("brSql",brSql);
            String title = "Журнал браслетов пациентов";
            String brName = (String)request.getParameter("filterAdd1Name");
            title += " в отделении " + request.getParameter("departmentName");
            if (brName!=null && !brName.equals("")) title += " с браслетом " + brName;
            title += date!=null && !date.equals("")?
                    " за период " + date + " - " + dateEnd : " на текущий момент";
            request.setAttribute("title",title);
            if (typeView.equals("2") || (department!=null && !department.equals(""))) {
        %>
        <msh:section>
            <ecom:webQuery name="brList" nameFldSql="brList_sql" nativeSql="
       select m.id
    ,sc.code as sccode
    ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as patfio
    ,to_char(pat.birthday,'dd.mm.yyyy') as birthday
    ,to_char(m.dateStart,'dd.mm.yyyy')
    ||case when m.datestart!=sls.dateStart then '(госп. с '||to_char(sls.dateStart,'dd.mm.yyyy')||')' else '' end
    ||case when m.dateFinish is not null then ' выписка '||to_char(m.dateFinish,'dd.mm.yyyy')||' '||cast(m.dischargeTime as varchar(5)) else '' end as datestar
    	,wp.lastname||' '||wp.firstname||' '||wp.middlename as worker
   ,max(dep.name) as depn
    ,list(vdrt.name||' '||vpd.name||' '||mkb.code) as diag
,case when cast(max(cast(vcid.isfornewborn as int)) as boolean) and cast(max(cast(dep.isnewborn as int)) as boolean) then 'background:'||max(vcr.code) else '' end as styleRow
     ,cast('-' as varchar(1)) as tempId
       ,cast ((select to_json(array_agg(t)) from	(select cip.id,vc.name||' ('||vcip.name||')' as colName
    ,vc.code as colorCode,vcip.name as vsipnameJust,vc.picture as picture, substring(cip.info from 0 for 30) as info
,case when vcip.isforpatology then '1' else '0' end as isforpat
				from vocColorIdentityPatient vcip
				left join coloridentitypatient cip on cip.voccoloridentity_id=vcip.id
				left join voccolor vc on vcip.color_id=vc.id
				 left join medcase_coloridentitypatient
				 ss on ss.colorsidentity_id=cip.id where
				medcase_id=sls.id  and cip.startdate<=current_date and (cip.finishdate is null or cip.finishdate>=current_date)) as t) as varchar) as jsonAr
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
     left join WorkFunction wf on wf.id=m.ownerFunction_id
    left join Worker w on w.id=wf.worker_id
    left join Patient wp on wp.id=w.person_id
    left join Patient pat on m.patient_id = pat.id
   left join medcase_coloridentitypatient mcid on mcid.medcase_id=sls.id
left join ColorIdentityPatient cid on cid.id=mcid.colorsidentity_id
left join VocColorIdentityPatient vcid on vcid.id=cid.voccoloridentity_id
left join voccolor vcr on vcr.id=vcid.color_id
    where m.DTYPE='DepartmentMedCase' ${department}
    and m.transferDate is null ${sqlDate}  ${brSql}
    and mcid.colorsidentity_id is not null
    and (cid.finishdate is null or cid.finishdate>=current_date)
    group by  m.id,m.dateStart,pat.lastname,pat.firstname
    ,pat.middlename,pat.birthday,sc.code,wp.lastname,wp.firstname,wp.middlename,sls.dateStart
    ,bf.addCaseDuration,m.dateFinish,m.dischargeTime,sls.id
    order by pat.lastname,pat.firstname,pat.middlename
    "/>
            <msh:sectionTitle>
                ${title}
            </msh:sectionTitle>
            <msh:sectionContent>

                <msh:table name="brList" viewUrl="entityShortView-stac_slo.do" action="entityParentView-stac_slo.do" idField="1" styleRow="9">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn columnName="Стат.карта" property="2" />
                    <msh:tableColumn columnName="Фамилия имя отчество пациента" property="3" />
                    <msh:tableColumn columnName="Год рождения" property="4" />
                    <msh:tableColumn columnName="Дата поступления" property="5" />
                    <msh:tableColumn columnName="Леч.врач" property="6"/>
                    <msh:tableColumn columnName="Отделение" property="7"/>
                    <msh:tableColumn columnName="Диагноз" property="8"/>
                    <msh:tableColumn columnName="Браслеты пациента" property="10"/>
                    <msh:tableColumn columnName="Браслеты пациента hidden" property="11" hidden="true"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
        } else {
%>
        <msh:section>
            <msh:sectionTitle>Результаты поиска. Если не введён период, показываются пациенты с браслетами в текущий момент</msh:sectionTitle>
        </msh:section>
        <msh:section>
        <msh:sectionContent>
            <ecom:webQuery name="braceletsListAll" nameFldSql="braceletsListAll_sql" nativeSql="
    select m.department_id,ml.name, count(distinct sls.id) as cntSls
    ,'&department='||coalesce(m.department_id,0)||'&departmentName='||coalesce(ml.name,'')
    from medCase m
    left join MedCase as sls on sls.id = m.parent_id
    left join MisLpu ml on ml.id=m.department_id
    left join patient pat on pat.id=sls.patient_id
    left join medcase_coloridentitypatient mci on mci.medcase_id = sls.id or mci.medcase_id = m.id
    left join ColorIdentityPatient cid on cid.id=mci.colorsidentity_id
    left join VocColorIdentityPatient vcid on vcid.id=cid.voccoloridentity_id
    where m.DTYPE='DepartmentMedCase'
    and m.transferDate is null ${sqlDate} ${brSql} ${department}
    and mci.colorsidentity_id is not null
    group by m.department_id,ml.name
    order by ml.name
    " />
            <msh:table name="braceletsListAll"
                       action="journal_bracelets.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&filterAdd1=${param.filterAdd1}&filterAdd1Name=${param.filterAdd1Name}"
                       idField="4">
                <msh:tableColumn property="sn" columnName="#"/>
                <msh:tableColumn columnName="Отделение" property="2"/>
                <msh:tableColumn columnName="Кол-во пациентов с браслетами" property="3" isCalcAmount="true"/>
            </msh:table>
        </msh:sectionContent>
    </msh:section>
        <%}%>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type="text/javascript">
            function setValOrNull(field,ifcase) {
                if (ifcase || typeof $(field).value === 'undefined' || typeof $(field+'Name').value === 'undefined'
                || $(field).value==null || $(field).value=='null' || $(field+'Name').value=='null') {
                    $(field).value='';
                    $(field+'Name').value='';
                }
            }
            setValOrNull('department',true);
            setValOrNull('filterAdd1',true);
            <%
            if (request.getParameter("departmentName")!=null && request.getParameter("department")!=null)
            %>
            $('department').value='<%= request.getParameter("department") %>';
            $('departmentName').value='<%= request.getParameter("departmentName")%>';

            <%
           if (request.getParameter("filterAdd1Name")!=null && request.getParameter("filterAdd1")!=null)
           %>
            $('filterAdd1').value='<%= request.getParameter("filterAdd1") %>';
            $('filterAdd1Name').value='<%= request.getParameter("filterAdd1Name")%>';

            setValOrNull('department',false);
            setValOrNull('filterAdd1',false);

            var tableBr = getTableToSetBracelets('brList');
            if (tableBr!=null)
                setBr(tableBr,9,10);
        </script>
    </tiles:put>
</tiles:insert>