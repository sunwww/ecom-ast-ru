<%@page import="ru.ecom.mis.web.action.medcase.journal.AdmissionJournalForm"%>
<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title guid="helloItle-123" mainMenu="MainJournal" title="Отчет по срокам и паритету"/>
    </tiles:put>
    <tiles:put name="side" type="string">

    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeGroup = ActionUtil.updateParameter("stac_report_Term_and_Paritet","typeGroup","1",request);
            if (request.getParameter("short")==null||request.getParameter("short").equals("")) {
        %>
        <msh:form action="/stac_report_Term_and_Paritet.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
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
                    <td class="label" title="Тип группировки(typeGroup)" colspan="1"><label for="typeGroup" id="typeGroupLabel">Группировать по:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1"> срокам
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> паритету
                    </td>
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
            if ((+aValue)==0 || (+aValue)>(+aMax)) {
                chk[+aDefaultValue-1].checked='checked' ;
            } else {
                chk[+aValue-1].checked='checked' ;
                }
            }
            if ($('dateBegin').value=="") {
                $('dateBegin').value=getCurrentDate() ;
            }
        checkFieldUpdate('typeGroup','${typeGroup}',1) ;
            </script>
        <%

            String date = request.getParameter("dateBegin") ;
            if (date!=null) {
                String dateEnd = request.getParameter("dateEnd");
                if (dateEnd == null || dateEnd.equals("")) dateEnd = date;

                request.setAttribute("dateBegin", date);
                request.setAttribute("dateEnd", dateEnd);
                String sqlAdd = "";
                String type=request.getParameter("type");

                if (request.getParameter("typeGroup")!=null) {
                    if (type!=null&&type.equals("reestr")) {  //если уже реестр
                        String temp = request.getParameter("temp");
                        if (temp != null && !temp.equals("")) {
                            String[] temps = temp.split(":");
                            sqlAdd = " and cb.durationpregnancy between " + Long.valueOf(temps[0]) + " and " + Long.valueOf(temps[1]);
                        }
                        String paritet = request.getParameter("paritet");
                        if (paritet != null && !paritet.equals(""))
                            sqlAdd = " and cb.paritet_id=(select id from vocparitet where code='" + paritet + "') ";
                        request.setAttribute("sqlAdd", sqlAdd);
        %>
        <msh:section>
            <ecom:webQuery isReportBase="true" name="ReportTempPregnancyReestr" nameFldSql="ReportTempPregnancyReestrSql" nativeSql="
select pat.id as patId
, pat.patientinfo
,cast('&type=reestr' as char) as fldId
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
${sqlAdd}
order by pat.patientinfo
" />
            <msh:sectionTitle>
            </msh:sectionTitle>
            <msh:sectionContent>
                <input type="button" value="Печать списка" onclick="print()" >
                <msh:table printToExcelButton="excel" name="ReportTempPregnancyReestr" action="entityView-mis_patient.do" idField="1">
                    <msh:tableColumn columnName="##" property="sn"/>
                    <msh:tableColumn columnName="ФИО пациента" property="2" addParam=""  />
                </msh:table>

            </msh:sectionContent>
        </msh:section>
            <% }
            //группировка по срокам
            else  if (request.getParameter("typeGroup").equals("1")) {%>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="true" name="ReportTempPregnancy" nameFldSql="ReportTempPregnancySql" nativeSql="
select
count(cb.id)
,count (case when cb.durationpregnancy between 22 and 27 then cb.id end) as f0_22_27
,count (case when cb.durationpregnancy=28 then cb.id end) as f1_28
,count (case when cb.durationpregnancy=29 then cb.id end) as f2_29
,count (case when cb.durationpregnancy=30 then cb.id end) as f3_30
,count (case when cb.durationpregnancy=31 then cb.id end) as f4_31
,count (case when cb.durationpregnancy=32 then cb.id end) as f5_32
,count (case when cb.durationpregnancy=33 then cb.id end) as f6_33
,count (case when cb.durationpregnancy=34 then cb.id end) as f6_34
,count (case when cb.durationpregnancy=35 then cb.id end) as f7_35
,count (case when cb.durationpregnancy=36 then cb.id end) as f8_36
,count (case when cb.durationpregnancy=37 then cb.id end) as f9_37
,count (case when cb.durationpregnancy=38 then cb.id end) as f10_38
,count (case when cb.durationpregnancy=39 then cb.id end) as f11_39
,count (case when cb.durationpregnancy=40 then cb.id end) as f12_40
,count (case when cb.durationpregnancy=41 then cb.id end) as f13_41
,count (case when cb.durationpregnancy>=42 then cb.id end) as f14_42_and_more
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportTempPregnancy"

                           action="stac_report_Term_and_Paritet.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=1" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="22-27 н. " property="2" addParam="&temp=22:27"/>
                    <msh:tableColumn columnName="28 н. " property="3" addParam="&temp=28:28"/>
                    <msh:tableColumn columnName="29 н. " property="4" addParam="&temp=29:29"/>
                    <msh:tableColumn columnName="30 н. " property="5" addParam="&temp=30:30"/>
                    <msh:tableColumn columnName="31 н. " property="6" addParam="&temp=31:31"/>
                    <msh:tableColumn columnName="32 н. " property="7" addParam="&temp=32:32"/>
                    <msh:tableColumn columnName="33 н. " property="8" addParam="&temp=33:33"/>
                    <msh:tableColumn columnName="34 н. " property="9" addParam="&temp=34:34"/>
                    <msh:tableColumn columnName="35 н. " property="10" addParam="&temp=35:35"/>
                    <msh:tableColumn columnName="36 н. " property="11" addParam="&temp=36:36"/>
                    <msh:tableColumn columnName="37 н. " property="12" addParam="&temp=37:37"/>
                    <msh:tableColumn columnName="38 н. " property="13" addParam="&temp=38:38"/>
                    <msh:tableColumn columnName="39 н. " property="14" addParam="&temp=39:39"/>
                    <msh:tableColumn columnName="40 н. " property="15" addParam="&temp=40:40"/>
                    <msh:tableColumn columnName="41 н. " property="16" addParam="&temp=41:41"/>
                    <msh:tableColumn columnName="42 и более н. " property="17" addParam="&temp=42:99"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по паритету
                        else if (request.getParameter("typeGroup").equals("2")) {
                        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="true" name="ReportParitetPregnancy" nameFldSql="ReportParitetPregnancySql" nativeSql="
select
count(cb.id)
,count (case when par.code='I' then cb.id end) as f0
,count (case when par.code='II' then cb.id end) as f1
,count (case when par.code='III' then cb.id end) as f2
,count (case when par.code='IV' then cb.id end) as f3
,count (case when par.code='V' then cb.id end) as f4
,count (case when par.code='VI' then cb.id end) as f5
,count (case when par.code='VII' then cb.id end) as f6
,count (case when par.code='VIII' then cb.id end) as f7
,count (case when par.code='IX' then cb.id end) as f8
,count (case when par.code='X' then cb.id end) as f9
,count (case when par.code='XI' then cb.id end) as f10
,count (case when par.code='XII' then cb.id end) as f11
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
left join vocparitet par on cb.paritet_id=par.id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportParitetPregnancy"

                           action="stac_report_Term_and_Paritet.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=2" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="Паритет I" property="2" addParam="&paritet=I"/>
                    <msh:tableColumn columnName="Паритет II" property="3" addParam="&paritet=II"/>
                    <msh:tableColumn columnName="Паритет III" property="4" addParam="&paritet=III"/>
                    <msh:tableColumn columnName="Паритет IV" property="5" addParam="&paritet=IV"/>
                    <msh:tableColumn columnName="Паритет V" property="6" addParam="&paritet=V"/>
                    <msh:tableColumn columnName="Паритет VI" property="7" addParam="&paritet=VI"/>
                    <msh:tableColumn columnName="Паритет VII" property="8" addParam="&paritet=VII"/>
                    <msh:tableColumn columnName="Паритет VIII" property="9" addParam="&paritet=VIII"/>
                    <msh:tableColumn columnName="Паритет IX" property="10" addParam="&paritet=IX"/>
                    <msh:tableColumn columnName="Паритет X" property="11" addParam="&paritet=X"/>
                    <msh:tableColumn columnName="Паритет XI" property="12" addParam="&paritet=XI"/>
                    <msh:tableColumn columnName="Паритет XII" property="13" addParam="&paritet=XII"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <%
                    }
            }
            }
        %>
    </tiles:put>
</tiles:insert>