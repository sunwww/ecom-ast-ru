<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск госпитализаций, в которых не создана карта COVID</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeDate = ActionUtil.updateParameter("typeDate","typeDate","2", request) ;
            String typeType = ActionUtil.updateParameter("typeType","typeType","2", request) ;
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
        <msh:form action="/journal_searchEmptyCovid.do" defaultField="department" method="GET">
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
                    <td class="label" title="Поиск по  (typeType)" colspan="1"><label for="typeTypeName" id="typeTypeLabel">Группировать:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeType" value="1">  реестр пациентов
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeType" value="2">  свод по отделениям
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
            }
            if (request.getParameter("dateBegin")!=null &&  !request.getParameter("dateBegin").equals("")) {
                if ("2".equals(typeType) && request.getParameter("short")==null) {

        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_emptyCovid" nativeSql="
                select dep.name, count (distinct sls.id) as total
                ,(select count(distinct sls.id)  from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join bedfund as bf on bf.id=m.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join Patient pat on m.patient_id = pat.id
                left join Covid19 c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu depinner on case when sloa.department_id=501 then depinner.id=prevmc.department_id else depinner.id=sloa.department_id end
                where m.DTYPE='DepartmentMedCase'
                and ${dateTo} between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and vbt.code='14'
                and depinner.id=dep.id
                and c.id is not null
           		and (sloa.datefinish is not null or sloa.transferdate is null) and sloa.DTYPE='DepartmentMedCase'
                ) as cntCard
                ,(select count(distinct sls.id)  from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join bedfund as bf on bf.id=m.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join Patient pat on m.patient_id = pat.id
                left join Covid19 c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu depinner on case when sloa.department_id=501 then depinner.id=prevmc.department_id else depinner.id=sloa.department_id end
                where m.DTYPE='DepartmentMedCase'
                and ${dateTo} between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and vbt.code='14'
                and depinner.id=dep.id
                and c.id is null
           		and (sloa.datefinish is not null or sloa.transferdate is null) and sloa.DTYPE='DepartmentMedCase'
                ) as cntNotCard
                ,'&depId='||coalesce(dep.id,0)||'&depname='||coalesce(dep.name,'')
                from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join bedfund as bf on bf.id=m.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join Patient pat on m.patient_id = pat.id
                left join Covid19 c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu dep on case when sloa.department_id=501 then dep.id=prevmc.department_id else dep.id=sloa.department_id end
                where m.DTYPE='DepartmentMedCase'
                and ${dateTo} between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and vbt.code='14'
           		${department}
           		and (sloa.datefinish is not null or sloa.transferdate is null) and sloa.DTYPE='DepartmentMedCase'
                group by dep.id,dep.name
                order by dep.name
                " />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_emptyCovid"  noDataMessage="Нет данных"
                           action="journal_searchEmptyCovid.do?&short=Short&typeType=1&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}"
                           idField="5" cellFunction="true">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="1" columnName="Отделение" addParam="&type=total"/>
                    <msh:tableColumn property="2" isCalcAmount="true" columnName="Всего пациентов" addParam="&type=total"/>
                    <msh:tableColumn property="3" isCalcAmount="true" columnName="Карт создано" addParam="&type=create"/>
                    <msh:tableColumn property="4" isCalcAmount="true" columnName="Карт не создано" addParam="&type=notCreate"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    }
            else if (request.getParameter("short")==null || "1".equals(typeType)) {
                String type = request.getParameter("type");
                String sqlAdd="";
                if (type!=null || "1".equals(typeType)) {
                    if ("create".equals(type))
                        sqlAdd = " and c.id is not null";
                    else if ("notCreate".equals(type))
                        sqlAdd = " and c.id is null";
                    request.setAttribute("sqlAdd",sqlAdd);
                    String depId = request.getParameter("depId");
                    String depSql = depId!=null?
                            "and dep.id = " + depId : "";
                    request.setAttribute("depSql",depSql);
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_emptyCovidPat" nativeSql="
                select distinct sls.id,dep.name, pat.patientinfo,st.code
                ,case when c.id is null then '-' else '+' end as exC
                from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join bedfund as bf on bf.id=m.bedfund_id
                left join vocbedtype vbt on vbt.id=bf.bedType_id
                left join Patient pat on m.patient_id = pat.id
                left join Covid19 c on sls.id=c.medcase_id
                left join MedCase sloa on sloa.parent_id=sls.id
                left join Medcase prevmc on prevmc.id=sloa.prevmedcase_id
                left join mislpu dep on case when sloa.department_id=501 then dep.id=prevmc.department_id else dep.id=sloa.department_id end
                left join statisticstub st on st.medcase_id=sls.id
                where m.DTYPE='DepartmentMedCase'
                and ${dateTo} between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and vbt.code='14'
                ${sqlAdd}
                ${depSql}
           		${department}
           		and (sloa.datefinish is not null or sloa.transferdate is null) and sloa.DTYPE='DepartmentMedCase'
                order by case when c.id is null then '-' else '+' end,dep.name, pat.patientinfo" />
                <msh:table printToExcelButton="Сохранить в Excel" name="journal_emptyCovidPat"  noDataMessage="Нет данных"
                           action="entityParentView-stac_ssl.do" idField="1" openNewWindow="true">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                    <msh:tableColumn property="4" columnName="Номер истории"/>
                    <msh:tableColumn property="5" columnName="Есть карта?"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%} } }%>
        <script type='text/javascript'>
            checkFieldUpdate('typeDate','${typeDate}',1) ;
            checkFieldUpdate('typeType','${typeType}',2) ;
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