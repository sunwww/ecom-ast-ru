<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >
    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Отчёт по актам РВК</msh:title>
    </tiles:put>
    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/actRVKReport.do" defaultField="dateBegin" disableFormDataConfirm="true" method="POST">
            <msh:panel>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с"/>
                    <msh:textField property="dateEnd" label="по"/>
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="department" fieldColSpan="16" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll"/>
                </msh:row>
                <msh:row>
                    <td class="label" title="Показать  (typeGroup)" colspan="1"><label for="typeGroupName" id="typeGroupLabel">Показать:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> амбулаторные
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="3"> стационарные
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Показать  (typeGroup2)" colspan="1"><label for="typeGroup2Name" id="typeGroup2Label"></label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup2" value="1" checked> все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup2" value="2"> закрытые
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup2" value="3"> открытые
                    </td>
                </msh:row>
                <msh:row>
                    <td colspan="3">
                        <input type="button" onclick="this.disabled=true;find();" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <%
            StringBuilder typeSql=new StringBuilder();
            String type1 = (String)request.getParameter("typeGroup");
            if (type1==null || type1.equals("")) type1="3";
            if (type1!=null && !type1.equals("")) {
                if (type1.equals("2")) typeSql.append(" and a.department_id is null");
                if (type1.equals("3")) typeSql.append(" and a.department_id is not null");
            }
            String type2 = (String)request.getParameter("typeGroup2");
            if (type2==null || type2.equals("")) type2="1";
            if (type2!=null && !type2.equals("")) {
                if (type2.equals("2")) typeSql.append(" and (a.datefinish is not null or a.datefinish<=current_date)");
                if (type2.equals("3")) typeSql.append(" and (a.datefinish is null or a.datefinish>current_date)");
            }
            String dateBegin = request.getParameter("dateBegin") ;
            if (dateBegin!=null && !dateBegin.equals("")) {
                String dateEnd = request.getParameter("dateEnd") ;
                if (dateEnd==null || dateEnd.equals("")) dateEnd=dateBegin;
                if (dateEnd!=null && !dateEnd.equals("")) {
                    typeSql.append(" and a.createdate between to_date('"+dateBegin+"','dd.mm.yyyy') and to_date('" + dateEnd+"','dd.mm.yyyy') ");
                }
            }
            String department = request.getParameter("department") ;
            if (department!=null && !department.equals("")) request.setAttribute("department"," and dep.id="+department);
            request.setAttribute("typeSql", typeSql.toString());
            request.setAttribute("typeGroup",type1);
            request.setAttribute("typeGroup2",type2);
            if (dateBegin!=null && !dateBegin.equals("")) {
        %>
        <msh:section>
            <msh:sectionTitle>Сведения о призывниках, направленных в ГБУЗ АО АМОКБ
                <ecom:webQuery isReportBase="false" name="totalName" nameFldSql="totalName_sql" nativeSql="
select a.id||'!'||mc.dtype,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename as fio
,to_char(pat.birthday,'dd.mm.yyyy') as bd
,adr.fullname as address
,to_char(a.datestart,'dd.mm.yyyy') as st
,to_char(a.datefinish,'dd.mm.yyyy') as df
,mkb.code||' '||a.diagnosis as ds
,dep.name as depname
,vwf.name||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as w
,a.comment as cmnt
from actrvk a
left join patient pat on pat.id=a.patient_id
left join address2 adr on adr.addressid=pat.address_addressid
left join vocidc10 mkb on mkb.id=a.idc10_id
left join mislpu dep on dep.id=a.department_id
left join workfunction wf on wf.id=a.workfunctionstart_id
left join vocworkfunction vwf on vwf.id=wf.workfunction_id
left join worker w on w.id=wf.worker_id
left join patient wpat on wpat.id=w.person_id
left join medcase mc on mc.id=a.medcase_id
where 1=1 ${typeSql} ${department}"/>
                <form action="javascript:void(0)" method="post" target="_blank"></form>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table printToExcelButton="Сохранить в excel" name="totalName" action="js-rvk_aktVisit-actView.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="ФИО" property="2"/>
                    <msh:tableColumn columnName="Число, месяц, год рождения" property="3"/>
                    <msh:tableColumn columnName="Место жительства" property="4"/>
                    <msh:tableColumn columnName="Находится на обследовании (лечении)" property="5"/>
                    <msh:tableColumn columnName="Завершил обследование (лечение)" property="6"/>
                    <msh:tableColumn columnName="Окончательный диагноз после проведённого обследования (лечения)" property="7"/>
                    <msh:tableColumn columnName="Отделение" property="8"/>
                    <msh:tableColumn columnName="Открыл акт" property="9"/>
                    <msh:tableColumn columnName="Примечание" property="10"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%} else{ %>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <%}%>
        <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
        <script type="text/javascript">
            checkFieldUpdate('typeGroup','${typeGroup}',1) ;
            checkFieldUpdate('typeGroup2','${typeGroup2}',1) ;
            function checkFieldUpdate(aField,aValue,aDefault) {
                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)==0 || (+aValue)>(+max)) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
            function find() {
                var frm = document.forms[0];
                frm.submit();
            }
        </script>
    </tiles:put>
</tiles:insert>