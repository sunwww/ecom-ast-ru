<%@ page import="ru.ecom.web.util.ActionUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Поиск госпитализаций, в которых нет <u><i>формы оценки тяжести COVID</i></u></msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/journal_covidForm.do" defaultField="department" method="GET">
            <msh:panel>
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
            String typeDate = ActionUtil.updateParameter("typeDate","typeDate","2", request) ;
            String date="",dateEnd="", dateTo="";
            if (typeDate!=null) {
                if (typeDate.equals("2")) dateTo = "sls.dateFinish";
                else if (typeDate.equals("1")) dateTo = "sls.datestart";
                date = request.getParameter("dateBegin");
            }
            if (date!=null && !date.equals(""))  {
                dateEnd = request.getParameter("dateEnd") ;

                if (dateEnd==null||dateEnd.equals("")) {
                    dateEnd = date;
                }
                request.setAttribute("dateTo",dateTo) ;
                request.setAttribute("dateBegin",date) ;
                request.setAttribute("dateEnd", dateEnd) ;
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="journal_covidForm" nativeSql="
                select distinct sls.id,dep.name, pat.patientinfo
                from medCase m
                left join MedCase as sls on sls.id = m.parent_id
                left join Patient pat on m.patient_id = pat.id
                left join mislpu dep on dep.id=sls.department_id
                left join diagnosis ds on ds.medcase_id = sls.id or ds.medcase_id = m.id
                left join vocidc10 idc on idc.id=ds.idc10_id
                left join covidmark cv on cv.medcase_id = sls.id
                where m.DTYPE='DepartmentMedCase'
                and ${dateTo} between to_date('${dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')
                and sls.dtype='HospitalMedCase' and idc.code like 'U%' and cv.id is null
                order by dep.name,pat.patientinfo" />
                <msh:table name="journal_covidForm"  noDataMessage="Нет данных"
                           action="entityParentView-stac_ssl.do" idField="1">
                    <msh:tableColumn property="sn" columnName="#" />
                    <msh:tableColumn property="2" columnName="Отделение"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    } %>

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