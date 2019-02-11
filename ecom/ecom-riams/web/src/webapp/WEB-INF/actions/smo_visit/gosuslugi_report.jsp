<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Отчет "Запись на прием к врачу" </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:visit_finds currentAction="blood_report"/>
    </tiles:put>

    <tiles:put name="body" type="string">
        <%
            //String typeDate =request.getParameter("typeDate") ;
            String typeDate=ActionUtil.updateParameter("BloodReport","typeDate","1", request);
            String typeGroup =ActionUtil.updateParameter("BloodReport","typeGroup","2", request) ;
            String typeRemote =ActionUtil.updateParameter("BloodReport","typeRemote","1", request) ;
            String typeSvod =ActionUtil.updateParameter("BloodReport","typeSvod","1", request) ;
        %>
        <msh:form action="/gosuslugi_report.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
            <input type="hidden" name="id" id="id"/>
            <input type="hidden" name="ticketIs" id="ticketIs" value="0"/>
            <input type="hidden" name="typeReestr" id="typeReestr" value="2"/>
            <input type="hidden" name="person" id="person" value="${param.person}"/>
            <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
            <msh:panel>
                <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
                    <msh:separator label="Параметры поиска" colSpan="9" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                    <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceStream" label="Тип резерва" vocName="vocServiceReserveType" horizontalFill="true" size="20"/>
                </msh:row>
                <msh:row>
                    <td class="label" title="Отображать" colspan="1">
                        <label for="typeSvodName" id="typeSvodLabel">Отобразить:</label></td>
                    <td onclick="this.childNodes[1].checked='checked';checkSvod();" id="tdsvod1">
                        <input type="radio" name="typeSvod" value="1" id="svod1"> общий свод
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';checkSvod();" id="tdsvod2">
                        <input type="radio" name="typeSvod" value="2" id="svod2">  свод по поликлиникам
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Отображать" colspan="1">
                        <label for="typeDateName" id="typeDateLabel"></label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="1"> Дата создания пред. направления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="2" >  Дата направления
                    </td>
                </msh:row>
                    <msh:row>
                        <td class="label" title="Поиск по пациентам (typeGroup)" colspan="1">
                            <label for="typeGroupName" id="typeGroupLabel">Группировать по пользователям:</label></td>
                        <td onclick="if (!document.getElementById('grouprb').disabled) this.childNodes[1].checked='checked';">
                            <input type="radio" name="typeGroup" value="1" id="grouprb"> Группировать
                        </td>
                        <td onclick="if (!document.getElementById('donotgrouprb').disabled) this.childNodes[1].checked='checked';">
                            <input type="radio" name="typeGroup" value="2" id="donotgrouprb"> Не группировать
                        </td>
                    </msh:row>
                    <msh:row>
                        <td class="label" title="Поиск по сайту (typeRemote)" colspan="1">
                            <label for="typeRemoteName" id="typeRemoteLabel">Найти:</label></td>
                        <td onclick="if (!document.getElementById('totalrb').disabled) this.childNodes[1].checked='checked';">
                            <input type="radio" name="typeRemote" value="1" id="totalrb"> Всё
                        </td>
                        <td onclick="if (!document.getElementById('robotrb').disabled) this.childNodes[1].checked='checked';">
                            <input type="radio" name="typeRemote" value="2" id="robotrb"> Только робот
                        </td>
                        <td onclick="if (!document.getElementById('siterb').disabled) this.childNodes[1].checked='checked';">
                            <input type="radio" name="typeRemote" value="3" id="siterb"> Только сайт
                        </td>
                </msh:row>
                    <msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
                </msh:panel>
            <%} %>
        </msh:form>

        <%
            String date = request.getParameter("dateBegin");
            String dateEnd = request.getParameter("dateEnd");
            if (date!=null
                    && !date.equals("")
                    && dateEnd!=null
                    && !dateEnd.equals("")) {
                request.setAttribute("dateBegin", date);
                request.setAttribute("dateEnd", dateEnd);
                request.setAttribute("isReportBase", ActionUtil.isReportBase(request.getParameter("dateBegin"),request.getParameter("dateEnd"),request));

                StringBuilder sqlAdd = new StringBuilder();
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
                if (typeDate.equals("1") ) {
                    request.setAttribute("dateSql", "wct.createdateprerecord") ;
                }
                else if (typeDate.equals("2")){
                    request.setAttribute("dateSql","wcd.calendardate" ) ;
                }

                StringBuilder sqlAddNew=new StringBuilder();
                if (typeRemote!=null) {
                    if (typeRemote.equals("1") && !typeGroup.equals("1")) sqlAddNew.append(" and su.id is not null");
                    if (typeRemote.equals("2")) sqlAddNew.append(" and wct.createprerecord='MedVox'"); //su id is null
                    if (typeRemote.equals("3")) {
                        sqlAddNew.append(" and (wct.createprerecord like 'ApiClient%' or wct.createprerecord ='FromSite')");
                        //if (!typeGroup.equals("1"))  sqlAddNew.append("and su.id is not null");
                    }
                }
                else if (!typeGroup.equals("1")) sqlAddNew.append(" and su.id is not null");
                String sstream = request.getParameter("serviceStream");
                if (sstream!=null && !sstream.equals("")) sqlAddNew.append(" and reservetype_id="+sstream);
                request.setAttribute ("appendSQL", sqlAdd.toString());
                request.setAttribute ("sqlAddNew", sqlAddNew.toString());
        %>
<% if (typeSvod.equals("1")) {%>
        <%if(typeGroup.equals("1")){ %>
        <msh:section>
            <ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select
count (wct.id) as cntAll
, count (case when wct.medcase_id is not null then 1 else null end) as cntRemote, wct.createprerecord as who
from workcalendartime wct
left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
where ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
${sqlAddNew}
group by wct.createprerecord
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />

            <msh:sectionTitle>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table
                        name="journal_ticket" action="/javascript:void()" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="Кем записано" property="3"/>
                    <msh:tableColumn columnName="Записано всего (записей)" property="1"/>
                    <msh:tableColumn columnName="Оформлено направление" property="2"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%}%>
        <%if(typeGroup.equals("2")){ %>
        <msh:section>
            ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select
count (wct.id) as cntAll
, count (case when su.isremoteuser='1' then 1 else null end) as cntRemote
from workcalendartime wct
left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
left join secuser su on su.login=wct.createprerecord
where ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy') ${sqlAddNew}
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
            <msh:sectionTitle>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table
                        name="journal_ticket" action="/javascript:void()" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="Записано всего" property="1"/>
                    <msh:tableColumn columnName="Записано удаленными пользователями" property="2"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%}
        else {%>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <% }
        }
        else if (typeSvod.equals("2")) {%>
        <msh:section>
            ${isReportBase}<ecom:webQuery isReportBase="${isReportBase}" maxResult="1500" name="journal_ticket" nameFldSql="journal_ticket_sql" nativeSql="
select  1, case when t.lpuname is not null then t.lpuname else '-' end as lpuname,
sum(t.total) as total,
sum(t.cnt1)||' ('||case when sum(t.total)<>0 then round(sum(t.cnt1)/sum(t.total)*100.0,2)||'%' else '0%' end||')' as promed,
sum(t.cnt2)||' ('||case when sum(t.total)<>0 then round(sum(t.cnt2)/sum(t.total)*100.0,2)||'%' else '0%' end||')' as robot,
sum(t.cnt3)||' ('||case when sum(t.total)<>0 then round(sum(t.cnt3)/sum(t.total)*100.0,2)||'%' else '0%' end||')' as site
 from (select lpu.name as lpuname
,count(wct.id) as total
,case when (wct.createprerecord ='IntegrationBot') then count(wct.id) else '0' end as cnt1
,case when (wct.createprerecord ='MedVox') then count(wct.id) else '0' end as cnt2
,case when (wct.createprerecord like 'ApiClient%' or wct.createprerecord='FromSite') then count(wct.id) else '0' end as cnt3
from workcalendartime wct
left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
left join medcase mc on mc.id=wct.medcase_id
left join mislpu lpu on lpu.id=mc.orderlpu_id
where ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
group by lpu.name,wct.createprerecord) as t
group by t.lpuname
" guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />

            <msh:sectionTitle>
            </msh:sectionTitle>
            <msh:sectionContent>
                <msh:table
                        name="journal_ticket" action="/javascript:void()" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="Поликлиника" property="2"/>
                    <msh:tableColumn columnName="Записано всего" property="3"/>
                    <msh:tableColumn columnName="Через промед" property="4"/>
                    <msh:tableColumn columnName="Через робота" property="5"/>
                    <msh:tableColumn columnName="Через сайт" property="6"/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <% }}  else {%>
        <i>Выберите параметры поиска и нажмите "Найти" </i>
        <% } %>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            function checkSvod() {
                var svod1 = document.getElementById("svod1");
                var svod2 = document.getElementById("svod2");
                if (svod2.checked) {
                    document.getElementById("donotgrouprb").setAttribute("disabled", true);
                    document.getElementById("totalrb").setAttribute("disabled", true);
                    document.getElementById("grouprb").setAttribute("disabled", true);
                    document.getElementById("robotrb").setAttribute("disabled", true);
                    document.getElementById("siterb").setAttribute("disabled", true);
                    document.getElementById("donotgrouprb").checked = document.getElementById("totalrb").checked = 'checked';
                }
                else if (svod1.checked) {
                    document.getElementById("donotgrouprb").removeAttribute("disabled");
                    document.getElementById("totalrb").removeAttribute("disabled");
                    document.getElementById("grouprb").removeAttribute("disabled");
                    document.getElementById("robotrb").removeAttribute("disabled");
                    document.getElementById("siterb").removeAttribute("disabled");
                }
            }
            checkFieldUpdate('typeDate','${typeDate}',1) ;
            checkFieldUpdate('typeGroup','${typeGroup}',2) ;
            checkFieldUpdate('typeRemote','${typeRemote}',1) ;
            checkFieldUpdate('typeSvod','${typeSvod}',1) ;


            function checkFieldUpdate(aField,aValue,aDefault) {

                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
            checkSvod();
        </script>
    </tiles:put>

</tiles:insert>