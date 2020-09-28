<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="MainJournal" title="Анализ работы родового отделения"/>
    </tiles:put>
    <tiles:put name="side" type="string">

    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeGroup = ActionUtil.updateParameter("stac_report_Term_and_Paritet","typeGroup","1",request);

            if (request.getParameter("short")==null||request.getParameter("short").equals("")) {
        %>
        <msh:form action="/stac_report_BirthTotal.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <input type="hidden" name="id" id="id" value=""/>
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="7" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <td class="label" title="Тип группировки(typeGroup)" colspan="1"><label for="typeGroup" id="typeGroupLabel">Свод по:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="1"> срокам
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="2"> паритету
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="3"> возрастам
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="4">
                        <input type="radio" name="typeGroup" value="4"> плодам
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="5">
                        <input type="radio" name="typeGroup" value="5"> районам
                    </td>
                </msh:row>
                <msh:row>
                    <td></td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="1">
                        <input type="radio" name="typeGroup" value="6"> ЭКО
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="2">
                        <input type="radio" name="typeGroup" value="7"> учёт в ЖК
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="3">
                        <input type="radio" name="typeGroup" value="8"> месту родов
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="4">
                        <input type="radio" name="typeGroup" value="9"> показаниям
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';" colspan="5">
                        <input type="radio" name="typeGroup" value="10"> выкидышам
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
                request.setAttribute("isReportBase", ActionUtil.isReportBase(date,dateEnd,request));
                String sqlAdd = "";
                String type = request.getParameter("type");

                if (request.getParameter("typeGroup") != null) {
                    if (type != null && type.equals("reestr")) {  //если уже реестр
                        String temp = request.getParameter("temp");
                        if (temp != null && !temp.equals("")) {
                            String[] temps = temp.split(":");
                            sqlAdd = " and cb.durationpregnancy between " + Long.valueOf(temps[0]) + " and " + Long.valueOf(temps[1]);
                        }
                        String paritet = request.getParameter("paritet");
                        if (paritet != null && !paritet.equals(""))
                            sqlAdd = " and cb.paritet_id=(select id from vocparitet where code='" + paritet + "') ";
                        String paritetpregn = request.getParameter("paritetpregn");
                        if (paritetpregn != null && !paritetpregn.equals(""))
                            sqlAdd = " and cb.paritetpregn_id=(select id from vocparitet where code='" + paritetpregn + "') ";
                        String age =  request.getParameter("age");
                        if (age!=null&&!age.equals("")) {
                            String[] ages=age.split(":");
                            sqlAdd=" and (cb.pangsstartdate-pat.birthday)/365 between "+Long.valueOf(ages[0])+" and "+Long.valueOf(ages[1]);
                        }
                        String num =  request.getParameter("num");
                        if (num!=null&&!num.equals("")) {
                            String[] nums=num.split(":");
                            sqlAdd=" and (select count(id) from newBorn where childBirth_id=cb.id) between "+Long.valueOf(nums[0])+" and "+Long.valueOf(nums[1]);
                        }
                        String eco =  request.getParameter("eco");
                        if (eco!=null&&!eco.equals("")) {
                            sqlAdd=(eco.equals("1"))? " and cb.iseco=true":" and (cb.iseco=false or cb.iseco is null)";
                        }
                        String gk =  request.getParameter("gk");
                        if (gk!=null&&!gk.equals("")) {
                            sqlAdd=(gk.equals("1"))? " and cb.isregisteredwithwomenconsultation=true":" and (cb.isregisteredwithwomenconsultation=false or cb.isregisteredwithwomenconsultation is null)";
                        }
                        String place =  request.getParameter("pl");
                        if (place!=null&&!place.equals("")) {
                            sqlAdd=" and place.code='"+place+"'";
                        }
                        String in =  request.getParameter("in");
                        if (in!=null&&!in.equals("")) {
                            sqlAdd=" and vocem.code='"+in+"'";
                        }
                        request.setAttribute("sqlAdd", sqlAdd);
                        String sqlDistrict = request.getParameter("district") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Астрахань"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30000001%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Ленинский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and ray.code='Л' and adr.kladr like '30000001%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Трусовский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and ray.code='Т' and adr.kladr like '30000001%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Кировский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and ray.code='К' and adr.kladr like '30000001%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Советский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and ray.code='С' and adr.kladr like '30000001%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Знаменск"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30000002%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("РАЙОН"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30%' and adr.kladr not like '30000%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Ахтубинский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30002%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Володарский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30003%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Енотаеский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30004%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Икрянинский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30005%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Камызякский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30006%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Красонярский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30007%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Лиманский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30008%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Наримановский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30009%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Приволжский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30010%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Харабалинский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30011%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Черноярский"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code='643' and adr.kladr like '30012%' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Иногородние"))
                            request.setAttribute("sqlDistrict", " and adr.kladr not like '30%' and ok.voc_code='643' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("Иностранцы"))
                            request.setAttribute("sqlDistrict", " and ok.voc_code is not null and ok.voc_code!='643' ") ;
                        if (sqlDistrict!=null && sqlDistrict.equals("бомж")) {
                            request.setAttribute("district", "без адр. или гражд.") ;
                            request.setAttribute("sqlDistrict", " and ((ok.voc_code is null and adr.addressid is null) or (ok.voc_code='643' and adr.addressid is null) or (ok.voc_code is null and adr.addressid is not null)) ");
                        }
        %>
        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportTempPregnancyReestr" nameFldSql="ReportTempPregnancyReestrSql" nativeSql="
select pat.id as patId
, pat.patientinfo
,cast('&type=reestr' as char) as fldId
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
left join vocwherebirthoccurred place on place.id=cb.wherebirthoccurred_id
left join vocchildemergency vocem on vocem.id=cb.emergency_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join Address2 adr on adr.addressid=pat.address_addressid
left join vocrayon ray on ray.id=pat.rayon_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
${sqlAdd} ${sqlDistrict}
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
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportTempPregnancy" nameFldSql="ReportTempPregnancySql" nativeSql="
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

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=1" idField="1"
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
            <msh:sectionTitle>Результаты поиска по паритету по РОДАМ за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportParitetPregnancy" nameFldSql="ReportParitetPregnancySql" nativeSql="
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
,count (case when par.code='XX' then cb.id end) as f12
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
left join vocparitet par on cb.paritet_id=par.id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportParitetPregnancy"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=2" idField="1"
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
                    <msh:tableColumn columnName="Более 12" property="14" addParam="&paritet=XX"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <msh:section>
            <msh:sectionTitle>Результаты поиска по паритету по БЕРЕМЕННОСТЯМ за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportParitetPregnancy" nameFldSql="ReportParitetPregnancySql" nativeSql="
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
,count (case when par.code='XX' then cb.id end) as f12
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
left join vocparitet par on cb.paritetpregn_id=par.id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportParitetPregnancy"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=2" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="Паритет I" property="2" addParam="&paritetpregn=I"/>
                    <msh:tableColumn columnName="Паритет II" property="3" addParam="&paritetpregn=II"/>
                    <msh:tableColumn columnName="Паритет III" property="4" addParam="&paritetpregn=III"/>
                    <msh:tableColumn columnName="Паритет IV" property="5" addParam="&paritetpregn=IV"/>
                    <msh:tableColumn columnName="Паритет V" property="6" addParam="&paritetpregn=V"/>
                    <msh:tableColumn columnName="Паритет VI" property="7" addParam="&paritetpregn=VI"/>
                    <msh:tableColumn columnName="Паритет VII" property="8" addParam="&paritetpregn=VII"/>
                    <msh:tableColumn columnName="Паритет VIII" property="9" addParam="&paritetpregn=VIII"/>
                    <msh:tableColumn columnName="Паритет IX" property="10" addParam="&paritetpregn=IX"/>
                    <msh:tableColumn columnName="Паритет X" property="11" addParam="&paritetpregn=X"/>
                    <msh:tableColumn columnName="Паритет XI" property="12" addParam="&paritetpregn=XI"/>
                    <msh:tableColumn columnName="Паритет XII" property="13" addParam="&paritetpregn=XII"/>
                    <msh:tableColumn columnName="Более 12" property="14" addParam="&paritetpregn=XX"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по возрастам
                    else if (request.getParameter("typeGroup").equals("3")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportMotherAge" nameFldSql="ReportMotherAgeSql" nativeSql="
select
count(cb.id)
,count (case when (cb.pangsstartdate-pat.birthday)/365 <=14 then cb.id end) as f1_less15
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 15 and 19 then cb.id end) as f1_15_19
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 20 and 24 then cb.id end) as f2_20_24
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 25 and 29 then cb.id end) as f3_25_29
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 30 and 34 then cb.id end) as f4_30_34
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 35 and 39 then cb.id end) as f5_35_39
,count (case when (cb.pangsstartdate-pat.birthday)/365 between 40 and 99 then cb.id end) as f6_more40
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportMotherAge"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=3" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="Моложе 15 лет " property="2" addParam="&age=0:14"/>
                    <msh:tableColumn columnName="15-19 лет " property="3" addParam="&age=15:19"/>
                    <msh:tableColumn columnName="20-24 года" property="4" addParam="&age=20:24"/>
                    <msh:tableColumn columnName="25-29 лет" property="5" addParam="&age=25:29"/>
                    <msh:tableColumn columnName="30-34 года" property="6" addParam="&age=30:34"/>
                    <msh:tableColumn columnName="35-39 лет" property="7" addParam="&age=35:39"/>
                    <msh:tableColumn columnName="40 и более" property="8" addParam="&age=40:99"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <%
            }
            //группировка по плодам
            else if (request.getParameter("typeGroup").equals("4")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportFetus" nameFldSql="ReportFetusSql" nativeSql="
select
count(cb.id)
,count (case when(select count(id) from newBorn where childBirth_id=cb.id)=1 then cb.id end) as f1
,count (case when(select count(id)from newBorn where childBirth_id=cb.id)=2 then cb.id end) as f2
,count (case when(select count(id) from newBorn where childBirth_id=cb.id)=3 then cb.id end) as f3
,count (case when(select count(id) from newBorn where childBirth_id=cb.id)>3 then cb.id end) as f3_more
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportFetus"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=4" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="1 плод " property="2" addParam="&num=1:1"/>
                    <msh:tableColumn columnName="2 плода " property="3" addParam="&num=2:2"/>
                    <msh:tableColumn columnName="3 плода" property="4" addParam="&num=3:3"/>
                    <msh:tableColumn columnName="Более 3х" property="5" addParam="&num=4:99"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по ЭКО
                    else if (request.getParameter("typeGroup").equals("6")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportEco" nameFldSql="ReportEcoSql" nativeSql="
select
count(cb.id)
,count (case when cb.iseco=true then cb.id end) as f1
,count (case when cb.iseco=false or cb.iseco is null then cb.id end) as f2
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportEco"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=4" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="C ЭКО " property="2" addParam="&eco=1"/>
                    <msh:tableColumn columnName="Без ЭКО" property="3" addParam="&eco=0"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по учёту в ЖК
                    else if (request.getParameter("typeGroup").equals("7")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportGk" nameFldSql="ReportGkSql" nativeSql="
select
count(cb.id)
,count (case when cb.isregisteredwithwomenconsultation=true then cb.id end) as f1
,count (case when cb.isregisteredwithwomenconsultation=false or cb.isregisteredwithwomenconsultation is null then cb.id end) as f2
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportGk"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=7" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="Состояли на учёте в женской консультации " property="2" addParam="&gk=1"/>
                    <msh:tableColumn columnName="НЕ состояли на учёте в женской консультации " property="3" addParam="&gk=0"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по месту родов
                    else if (request.getParameter("typeGroup").equals("8")) {
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportPlace" nameFldSql="ReportPlaceSql" nativeSql="
select
count(cb.id)
,count (case when place.code='1' then cb.id end) as f1
,count (case when place.code='2' then cb.id end) as f2
,count (case when place.code='3' then cb.id end) as f3
,count (case when place.code='4' then cb.id end) as f4
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
left join vocwherebirthoccurred place on place.id=cb.wherebirthoccurred_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportPlace"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=8" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="В стационаре " property="2" addParam="&pl=1"/>
                    <msh:tableColumn columnName="На дому " property="3" addParam="&pl=2"/>
                    <msh:tableColumn columnName="При транспортировке " property="4" addParam="&pl=3"/>
                    <msh:tableColumn columnName="Другое " property="5" addParam="&pl=4"/>
                </msh:table>

            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по районам
                    else if (request.getParameter("typeGroup").equals("5")) {
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportRayon" nameFldSql="ReportRayonSql" nativeSql="
select
count(cb.id) as f1
,count(case when ok.voc_code='643' and adr.kladr like '30000001%' then cb.id else null end) as cntAstrakhan
,count(case when ok.voc_code='643' and ray.code='Л' and adr.kladr like '30000001%' then cb.id else null end) as LeninAstrakhan
,count(case when ok.voc_code='643' and ray.code='Т' and adr.kladr like '30000001%' then cb.id else null end) as TrusovAstrakhan
,count(case when ok.voc_code='643' and ray.code='К' and adr.kladr like '30000001%' then cb.id else null end) as KirovAstrakhan
,count(case when ok.voc_code='643' and ray.code='С' and adr.kladr like '30000001%' then cb.id else null end) as SovetAstrakhan
,count(case when ok.voc_code='643' and adr.kladr like '30000002%' then cb.id else null end) as cntZnamenkc
,count(case when ok.voc_code='643' and adr.kladr like '30%' and adr.kladr not like '30000%' then cb.id else null end) as cntRayon
,count(case when ok.voc_code='643' and adr.kladr like '30002%' then cb.id else null end) as cntAhtub
,count(case when ok.voc_code='643' and adr.kladr like '30003%' then cb.id else null end) as cntVolodar
,count(case when ok.voc_code='643' and adr.kladr like '30004%' then cb.id else null end) as cntEnotaev
,count(case when ok.voc_code='643' and adr.kladr like '30005%' then cb.id else null end) as cntIkrian
,count(case when ok.voc_code='643' and adr.kladr like '30006%' then cb.id else null end) as cntKamiz
,count(case when ok.voc_code='643' and adr.kladr like '30007%' then cb.id else null end) as cntKrasnoiar
,count(case when ok.voc_code='643' and adr.kladr like '30008%' then cb.id else null end) as cntLiman
,count(case when ok.voc_code='643' and adr.kladr like '30009%' then cb.id else null end) as cntNarim
,count(case when ok.voc_code='643' and adr.kladr like '30010%' then cb.id else null end) as cntPrivol
,count(case when ok.voc_code='643' and adr.kladr like '30011%' then cb.id else null end) as cntHarab
,count(case when ok.voc_code='643' and adr.kladr like '30012%' then cb.id else null end) as cntChernoiar
,count(case when adr.kladr not like '30%' and ok.voc_code='643' then cb.id else null end) as cntInog
,count(case when ok.voc_code is not null and ok.voc_code!='643' then cb.id else null end) as cntInost
,count(distinct case
when (ok.voc_code is null and adr.addressid is null) then cb.id
when (ok.voc_code='643' and adr.addressid is null) then cb.id
when (ok.voc_code is null and adr.addressid is not null) then cb.id else null end) as cntnoadr
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
left join Omc_Oksm ok on pat.nationality_id=ok.id
left join Address2 adr on adr.addressid=pat.address_addressid
left join vocrayon ray on ray.id=pat.rayon_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportRayon"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=5" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  width="4"/>
                    <msh:tableColumn columnName="г. Астрахань всего" isCalcAmount="true" property="2" addParam="&district=Астрахань" width="4"/>
                    <msh:tableColumn columnName="Ленин- ский" isCalcAmount="true" property="3" addParam="&district=Ленинский"/>
                    <msh:tableColumn columnName="Трусов- ский" isCalcAmount="true" property="4" addParam="&district=Трусовский"/>
                    <msh:tableColumn columnName="Киров- ский" isCalcAmount="true" property="5" addParam="&district=Кировский"/>
                    <msh:tableColumn columnName="Совет- ский" isCalcAmount="true" property="6" addParam="&district=Советский"/>
                    <msh:tableColumn columnName="г. Знаменск" isCalcAmount="true" property="7" addParam="&district=Знаменск"/>
                    <msh:tableColumn columnName="Всего по районам" isCalcAmount="true" property="8" addParam="&district=РАЙОН"/>
                    <msh:tableColumn columnName="Ахту- бин- ский" isCalcAmount="true" property="9" addParam="&district=Ахтубинский"/>
                    <msh:tableColumn columnName="Воло- дар- ский " isCalcAmount="true" property="10" addParam="&district=Володарский"/>
                    <msh:tableColumn columnName="Ено- таевский" isCalcAmount="true" property="11" addParam="&district=Енотаеский"/>
                    <msh:tableColumn columnName="Икря- нинский" isCalcAmount="true" property="12" addParam="&district=Икрянинский"/>
                    <msh:tableColumn columnName="Камы- зякский" isCalcAmount="true" property="13" addParam="&district=Камызякский"/>
                    <msh:tableColumn columnName="Красно- ярский" isCalcAmount="true" property="14" addParam="&district=Красонярский"/>
                    <msh:tableColumn columnName="Лиман- ский" isCalcAmount="true" property="15" addParam="&district=Лиманский"/>
                    <msh:tableColumn columnName="Нари- манов- ский" isCalcAmount="true" property="16" addParam="&district=Наримановский"/>
                    <msh:tableColumn columnName="Привол- жский" isCalcAmount="true" property="17" addParam="&district=Приволжский"/>
                    <msh:tableColumn columnName="Хара- балин- ский" isCalcAmount="true" property="18" addParam="&district=Харабалинский"/>
                    <msh:tableColumn columnName="Черно- ярский" isCalcAmount="true" property="19" addParam="&district=Черноярский"/>
                    <msh:tableColumn columnName="Ино- город- ние" isCalcAmount="true" property="20" addParam="&district=Иногородние"/>
                    <msh:tableColumn columnName="Иност- ранцы" isCalcAmount="true" property="21" addParam="&district=Иностранцы"/>
                    <msh:tableColumn columnName="Без адр. или гражд." isCalcAmount="true" property="22" addParam="&district=бомж"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по показаниям
                    else if (request.getParameter("typeGroup").equals("9")) {
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportIndic" nameFldSql="ReportIndicSql" nativeSql="
select
count(cb.id)
,count (case when vocem.code='1' then cb.id end) as f1
,count (case when vocem.code='2' then cb.id end) as f2
,count (case when vocem.code='3' then cb.id end) as f3
,count (case when vocem.code='4' then cb.id end) as f4
,count (case when vocem.code='5' then cb.id end) as f5
from childbirth cb
left join medcase slo on slo.id=cb.medcase_id
left join patient pat on pat.id=slo.patient_id
left join vocchildemergency vocem on vocem.id=cb.emergency_id
where cb.birthFinishDate between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')

" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportIndic"

                           action="stac_report_BirthTotal.do?short=Short&type=reestr&dateBegin=${dateBegin}&dateEnd=${dateEnd}&typeGroup=9" idField="1"
                           cellFunction="true"
                >
                    <msh:tableColumn columnName="Всего рожениц" property="1" addParam=""  />
                    <msh:tableColumn columnName="Плановые " property="2" addParam="&in=1"/>
                    <msh:tableColumn columnName="Экстренные " property="3" addParam="&in=2"/>
                    <msh:tableColumn columnName="По срочным показаниям " property="4" addParam="&in=3"/>
                    <msh:tableColumn columnName="Индуцированные " property="5" addParam="&in=4"/>
                    <msh:tableColumn columnName="Спонтанные " property="6" addParam="&in=5"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%
                    }
                    //группировка по выкидышам
                    else if (request.getParameter("typeGroup").equals("10")) {
        %>
        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>

        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" name="ReportMisbirth" nameFldSql="ReportMisbirthSql" nativeSql="
select  sls.id as patId, pat.patientinfo
from medcase sls
left join patient pat on sls.patient_id=pat.id
left join medcase sloobserv on sloobserv.parent_id=sls.id
left join mislpu depobserv on depobserv.id=sloobserv.department_id
where sls.dtype='HospitalMedCase' and sloobserv.dtype='DepartmentMedCase' and depobserv.isobservable=true
and (select count(id) from childbirth where medcase_id=ANY(select id from medcase where parent_id=sls.id))=0
and sls.datestart between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')
" />
            <msh:sectionContent>
                <msh:table printToExcelButton="excel" name="ReportMisbirth" action="entityView-stac_ssl.do" idField="1" cellFunction="true">
                    <msh:tableColumn columnName="##" property="sn"/>
                    <msh:tableColumn columnName="ФИО" property="2" addParam=""  />
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