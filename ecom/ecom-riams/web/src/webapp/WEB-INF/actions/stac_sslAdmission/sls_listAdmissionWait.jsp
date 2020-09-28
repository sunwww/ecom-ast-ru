<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="StacJournal">Журнал "время ожидания в приемном отделении"</msh:title>
    </tiles:put>
    <tiles:put name="body" type="string">
        <% if (request.getParameter("short")==null) {%>
        <msh:form action="/sls_listAdmissionWait.do" defaultField="department" disableFormDataConfirm="true" method="GET">
            <msh:panel>
                <msh:row>
                    <msh:separator label="Основные параметры" colSpan="7"/>
                </msh:row>

                <msh:row>
                    <msh:autoComplete property="department" horizontalFill="true" label="Отделение" vocName="vocLpuHospOtdAll" fieldColSpan="7" size="100"/>
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row><td>
                    <input type="submit" value="Найти">
                </td></msh:row>
            </msh:panel>
        </msh:form>


        <%
            }

            String date = request.getParameter("dateBegin") ;
            if (date!=null && !date.equals(""))  {
                String dateEnd = request.getParameter("dateEnd") ;
                String department = request.getParameter("department") ;

                if (department!=null && !department.equals("")) {
                    request.setAttribute("departmentSql", " and sls.department_id="+department) ;
                }
                if (dateEnd==null||dateEnd.equals("")) {
                    dateEnd = date;
                }
                request.setAttribute("startDate", date) ;
                request.setAttribute("finishDate", dateEnd) ;
                String reestr = request.getParameter("reestr");
                if (!"1".equals(reestr)) {
        %>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_hosp" nativeSql="
select '&dateBegin=${startDate}&dateEnd=${finishDate}&reestr=1&department='||ml.id
,ml.name
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 120 and 180 then sls.id end) as f3_23h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 181 and 240 then sls.id end) as f4_34h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 241 and 300 then sls.id end) as f5_45h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 301 and 360 then sls.id end) as f6_56h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 361 and 420 then sls.id end) as f7_67h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 421 and 480 then sls.id end) as f8_78h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 481 and 540 then sls.id end) as f9_89h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) between 541 and 600 then sls.id end) as f10_910h
,count( case when cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) > 600 then sls.id end) as f11_100h
,count(sls.id) as f12_all
from medcase sls
left join mislpu ml on ml.id=sls.department_id
left join vocdeniedhospitalizating vdg on vdg.id=sls.deniedhospitalizating_id
where sls.datestart between to_date('${startDate}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
and sls.deniedhospitalizating_id is not null ${departmentSql}
group by ml.id, ml.name
order by ml.name
        "  />
                <msh:table name="journal_hosp" printToExcelButton="Сохранить в excel" cellFunction="true"
                           action="sls_listAdmissionWait.do?short=Short" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Отделение" property="2"/>
                    <msh:tableColumn columnName="2-3 часа" property="3" addParam="&wait=23"/>
                    <msh:tableColumn columnName="3-4 часа" property="4" addParam="&wait=34"/>
                    <msh:tableColumn columnName="4-5 часов" property="5" addParam="&wait=45"/>
                    <msh:tableColumn columnName="5-6 часов" property="6" addParam="&wait=56"/>
                    <msh:tableColumn columnName="6-7 часов" property="7" addParam="&wait=67"/>
                    <msh:tableColumn columnName="7-8 часов" property="8" addParam="&wait=78"/>
                    <msh:tableColumn columnName="8-9 часов" property="9" addParam="&wait=89"/>
                    <msh:tableColumn columnName="9-10 часов" property="10" addParam="&wait=910"/>
                    <msh:tableColumn columnName="Свыше 10 часов" property="11" addParam="&wait=more_10"/>
                    <msh:tableColumn columnName="Всего" property="12"/>
                </msh:table>
            </msh:sectionContent>

        </msh:section>

        <% } else { //Реестр пациентов
                    String wait = request.getParameter("wait")!=null ? request.getParameter("wait") : "";
                    String waitSql;
                    switch (wait) {
                        case "23":
                            waitSql=" between 120 and 180";
                            break;
                        case "34":
                            waitSql=" between 181 and 240";
                            break;
                        case "45":
                            waitSql=" between 241 and 300";
                            break;
                        case "56":
                            waitSql=" between 301 and 360";
                            break;
                        case "67":
                            waitSql=" between 361 and 420";
                            break;
                        case "78":
                            waitSql=" between 421 and 480";
                            break;
                        case "89":
                            waitSql=" between 481 and 540";
                            break;
                        case "910":
                            waitSql=" between 541 and 600";
                            break;
                        case "more_10":
                            waitSql=" > 600";
                            break;
                        default:
                            waitSql=" >-1";
                    }
                    request.setAttribute("waitSql",waitSql);

        %>

        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery name="journal_hosp" nativeSql="
select sls.id as fldId
,pat.patientInfo
from medcase sls
left join patient pat on pat.id=sls.patient_id
left join mislpu ml on ml.id=sls.department_id
where sls.datestart between to_date('${startDate}','dd.MM.yyyy') and to_date('${finishDate}','dd.MM.yyyy')
and sls.deniedhospitalizating_id is not null ${departmentSql}
and cast(extract(epoch from age(coalesce(cast((sls.transferdate||' '|| sls.transfertime) as timestamp),current_timestamp),cast(sls.dateStart||' '||sls.entranceTime as timestamp)))/60 as int) ${waitSql}
order by ml.name
        "  />
                <msh:table name="journal_hosp" printToExcelButton="Сохранить в excel" openNewWindow="true"
                           action="entityParentView-stac_sslAdmission.do" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Пациент" property="2"/>
                </msh:table>
            </msh:sectionContent>

        </msh:section>

        <%
        }
        } else {%>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <% }   %>

        <script type='text/javascript'>
            //checkFieldUpdate('typeDate','${typeDate}',2) ;


            function checkFieldUpdate(aField,aValue,aDefault) {
                eval('var chk =  document.forms[0].' + aField);
                eval('var aMax =  chk.length');
                if (aMax > aDefault) {
                    aDefault = aMax
                }
                if ((+aValue) > aMax) {
                    chk[+aDefault - 1].checked = 'checked';
                } else {
                    chk[+aValue - 1].checked = 'checked';
                }
            }
        </script>
    </tiles:put>
</tiles:insert>

