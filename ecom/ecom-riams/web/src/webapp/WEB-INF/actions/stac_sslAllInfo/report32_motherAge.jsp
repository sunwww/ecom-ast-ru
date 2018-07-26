<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title guid="helloItle-123" mainMenu="MainJournal" title="Отчет по возрастам рожениц"/>
    </tiles:put>
    <tiles:put name="side" type="string">

    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            if (request.getParameter("short")==null||request.getParameter("short").equals("")) {
        %>
        <msh:form action="/stac_report_32motherAge.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
            <input type="hidden" name="id" id="id" value=""/>
            <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
                <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
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

        <% }%>
        <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
        <script type='text/javascript'>

           function checkFieldUpdate(aField,aValue,aDefaultValue) {
                eval('var chk =  document.forms[0].'+aField) ;
                var aMax=chk.length ;
                //alert(aField+" "+aValue+" "+aMax+" "+chk) ;
                if ((+aValue)==0 || (+aValue)>(+aMax)) {
                    chk[+aDefaultValue-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }



            if ($('dateBegin').value=="") {
                $('dateBegin').value=getCurrentDate() ;
            }


        </script>
        <%

            String date = request.getParameter("dateBegin") ;
            if (date!=null) {
                String dateEnd = request.getParameter("dateEnd") ;
                if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;

                request.setAttribute("dateBegin", date) ;
                request.setAttribute("dateEnd", dateEnd) ;

                String sqlAdd = "";
                String type=request.getParameter("type");

                if (type!=null&&type.equals("reestr")) {
                    String age =  request.getParameter("age");
                    if (age!=null&&!age.equals("")) {
                        String[] ages=age.split(":");
                        sqlAdd=" and (cb.pangsstartdate-pat.birthday)/365 between "+Long.valueOf(ages[0])+" and "+Long.valueOf(ages[1]);
                    }

                    request.setAttribute("sqlAdd", sqlAdd);
        %>
        <msh:section>
            <ecom:webQuery isReportBase="true" name="ReportMotherAgeReestr" nameFldSql="ReportMotherAgeReestrSql" nativeSql="
select pat.id as patId
, pat.patientinfo
,cast('&type=reestr' as char) as fldId
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.pangsstartdate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
${sqlAdd}
order by pat.patientinfo
" />
            <msh:sectionTitle>
            </msh:sectionTitle>
            <msh:sectionContent>
                <input type="button" value="Печать списка" onclick="print()" >
                <msh:table name="ReportMotherAgeReestr" action="entityView-mis_patient.do" idField="1">
                    <msh:tableColumn property="sn"/>
                    <msh:tableColumn columnName="ФИО пациента" property="2" addParam=""  />
                </msh:table>

            </msh:sectionContent>
        </msh:section>

        <%
        } else {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="true" name="ReportMotherAge" nameFldSql="ReportMotherAgeSql" nativeSql="
select
count(pat.id)
,count (case when (cb.pangsstartdate-pat.birthday)/365 <19 then cb.id end) as f1_less19
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 19 and 24 then cb.id end) as f2_19_24
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 25 and 29 then cb.id end) as f3_25_29
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 30 and 34 then cb.id end) as f4_30_34
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 35 and 39 then cb.id end) as f5_35_39
,count (case when (cb.pangsstartdate-pat.birthday)/365 >=40 then cb.id end) as f6_more40
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.pangsstartdate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table name="ReportMotherAge"

                           action="stac_report_32motherAge.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="Моложе 19 лет " property="2" addParam="&age=0:18"/>
                    <msh:tableColumn columnName="19-24 года" property="3" addParam="&age=19:24"/>
                    <msh:tableColumn columnName="25-29 лет" property="4" addParam="&age=25:29"/>
                    <msh:tableColumn columnName="30-34 года" property="5" addParam="&age=30:34"/>
                    <msh:tableColumn columnName="35-39 лет" property="6" addParam="&age=35:39"/>
                    <msh:tableColumn columnName="40 и более" property="7" addParam="&age=40:99"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <% }
        }
        %>

    </tiles:put>

</tiles:insert>