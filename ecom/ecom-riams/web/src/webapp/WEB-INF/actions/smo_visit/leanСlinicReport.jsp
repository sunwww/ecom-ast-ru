<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Poly">Отчёт "Бережливая поликлиника" </msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
    </tiles:put>
    <tiles:put name="body" type="string">
        <%
            String typeDate=ActionUtil.updateParameter("leanClinicReport","typeDate","1", request);
            String typeRemote =ActionUtil.updateParameter("leanClinicReport","typeRemote","1", request) ;
        %>
        <msh:form action="/leanClinicReport.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
            <%if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {%>
            <msh:panel>
                <msh:row>
                    <msh:separator label="Параметры поиска" colSpan="9" />
                </msh:row>
                <msh:row>
                    <msh:textField property="dateBegin" label="Период с" />
                    <msh:textField property="dateEnd" label="по" />
                </msh:row>
                <msh:row>
                    <msh:autoComplete property="serviceStream" label="Способ записи" vocName="vocWayOfRecord" horizontalFill="true" size="20"/>
                </msh:row>
                <msh:row>
                    <td class="label" title="Дата" colspan="1">
                        <label for="typeDateName" id="typeDateLabel">Дата</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="1"> Дата создания направления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="2" >  Дата направления
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeDate" value="3" >  Дата визита
                    </td>
                </msh:row>
                <msh:row>
                    <td class="label" title="Пользователи" colspan="1">
                        <label for="typeDateName" id="typeRemoteLabel">Пользователи</label></td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeRemote" value="1"> Все
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeRemote" value="2" >  Свои
                    </td>
                    <td onclick="this.childNodes[1].checked='checked';">
                        <input type="radio" name="typeRemote" value="3" >  Удалённые ЛПУ
                    </td>
                </msh:row>
                <msh:submitCancelButtonsRow labelSave="Сформировать" doNotDisableButtons="cancel" labelSaving="Формирование..." colSpan="4"/>
            </msh:panel>
            <%} %>
        </msh:form>
        <%
            String dateBegin = request.getParameter("dateBegin");
            String dateEnd = request.getParameter("dateEnd");
            if (dateEnd==null || dateEnd.equals("")) request.setAttribute("dateEnd", dateBegin);
            if (dateBegin!=null && !dateBegin.equals("") && dateEnd!=null && !dateEnd.equals("")) {
                request.setAttribute("dateBegin", dateBegin);
                request.setAttribute("dateEnd", dateEnd);
                request.setAttribute("isReportBase", ActionUtil.isReportBase(dateBegin,dateEnd,request));
                if (typeDate.equals("1") ) {
                    request.setAttribute("dateSql", "wct.createdateprerecord") ;
                } else if (typeDate.equals("2")){
                    request.setAttribute("dateSql","wcd.calendardate" ) ;
                } else if (typeDate.equals("3")){
                    request.setAttribute("dateSql","vis.datestart" ) ;
                }
                StringBuilder sqlAddNew=new StringBuilder();
                if (typeRemote!=null) {
                    if (typeRemote.equals("2"))
                        sqlAddNew.append(" and (su.isremoteuser is null or su.isremoteuser=false) ");
                    else if (typeRemote.equals("3"))
                        sqlAddNew.append(" and su.isremoteuser=true ");
                }
                String vocway = request.getParameter("serviceStream");
                if (vocway!=null && !vocway.equals("")) sqlAddNew.append(" and wr.id=").append(vocway);
                request.setAttribute ("sqlAddNew", sqlAddNew.toString());
                if (request.getParameter("short")==null ||request.getParameter("short").equals(""))  {
        %>
        <msh:section>
        <msh:sectionTitle>
            <ecom:webQuery isReportBase="true" name="leanClinicReport" nameFldSql="leanClinicReport_sql" nativeSql="
         select case when lpu.name is not null and lpu.name!='' then replace(replace(replace(lpu.name,'\"',' '),'''',' '),'  ',' ') else cast('Без прикрепления' as varchar(16)) end
        , count (distinct wct.id) as cntAll
        , count (case when wct.medcase_id is not null then 1 else null end) as cntVisit
        , count (case when wr.code='PROMED' then 1 else null end) as cntPROMED
        , count (case when wr.code='PROMED' and wct.medcase_id is not null then 1 else null end) as cntPROMEDVisit
        , count (case when wr.code='SITE' then 1 else null end) as cntSITE
        , count (case when wr.code='SITE' and wct.medcase_id is not null then 1 else null end) as cntSITEVisit
        , count (case when wr.code='PHONE' then 1 else null end) as cntPHONE
        , count (case when wr.code='PHONE' and wct.medcase_id is not null then 1 else null end) as cntPHONEVisit
        , count (case when wr.code='PERSONAL' then 1 else null end) as cntPERSONAL
        , count (case when wr.code='PERSONAL' and wct.medcase_id is not null then 1 else null end) as cntPERSONALVisit
        , count (case when wr.code='MEDVOX' then 1 else null end) as cntMEDVOX
        , count (case when wr.code='MEDVOX' and wct.medcase_id is not null then 1 else null end) as cntMEDVOXVisit
        , '&lpuId='||coalesce(lpu.id,0)||'&lpuName='||coalesce(replace(replace(replace(lpu.name,'\"',' '),'''',' '),'  ',' '),cast('Без прикрепления' as varchar(16)))
        from workcalendartime wct
        left join secuser su on su.login=wct.createprerecord
        left join patient pat on pat.id=wct.prepatient_id
        left join patientfond pf on pat.patientfond_id=pf.id
        left join mislpu lpu on pf.LpuAttached=lpu.codef
        left join vocwayofrecord wr on wr.id=wct.wayofrecord_id
        left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
        left join medcase vis on vis.id=wct.medcase_id
        where ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
        ${sqlAddNew}
        and (pat.id is not null or wct.prepatient_id is not null or wct.prepatientinfo is not null)
        and (wct.isdeleted is null or wct.isdeleted=false)
        group by lpu.id,lpu.name"
                           />
            <form action="leanClinicReport.do" method="post" target="_blank">
                Результат за период с ${param.dateBegin} по ${dateEnd}.
            </form>
        </msh:sectionTitle>
        <msh:sectionContent>
            <msh:table printToExcelButton="Сохранить в excel" name="leanClinicReport" cellFunction="true"
                       action="leanClinicReport.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&serviceStream=${param.serviceStream}&typeDate=${param.typeDate}&typeRemote=${param.typeRemote}"
                       idField="14">
                <msh:tableNotEmpty>
                    <tr>
                        <th colspan=1></th>
                        <th colspan=1></th>
                        <th colspan=2>ВСЕ</th>
                        <th colspan=2>ПРОМЕД</th>
                        <th colspan=2>САЙТ</th>
                        <th colspan=2>ТЕЛЕФОН</th>
                        <th colspan=2>ЛИЧНО</th>
                        <th colspan=2>РОБОТ</th>
                    </tr>
                </msh:tableNotEmpty>
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="ЛПУ прикрепления" property="1" />
                <msh:tableColumn isCalcAmount="true" columnName="Запись" property="2" addParam="&tView=All&tViewName=по ВСЕМ записям" />
                <msh:tableColumn isCalcAmount="true" columnName="Визит" property="3" addParam="&tView=Visit&tViewName=по ВСЕМ визитам" />
                <msh:tableColumn isCalcAmount="true" columnName="Запись" property="4" addParam="&tView=PROMED&tViewName=по записям через ПРОМЕД" />
                <msh:tableColumn isCalcAmount="true" columnName="Визит" property="5" addParam="&tView=PROMEDVisit&tViewName=по визитам через ПРОМЕД" />
                <msh:tableColumn isCalcAmount="true" columnName="Запись" property="6" addParam="&tView=SITE&tViewName=по записям через САЙТ" />
                <msh:tableColumn isCalcAmount="true" columnName="Визит" property="7" addParam="&tView=SITEVisit&tViewName=по визитам через САЙТ" />
                <msh:tableColumn isCalcAmount="true" columnName="Запись" property="8" addParam="&tView=PHONE&tViewName=по записям через ТЕЛЕФОН" />
                <msh:tableColumn isCalcAmount="true" columnName="Визит" property="9" addParam="&tView=PHONEVisit&tViewName=по визитам через ТЕЛЕФОН" />
                <msh:tableColumn isCalcAmount="true" columnName="Запись" property="10" addParam="&tView=PERSONAL&tViewName=по записям ЛИЧНО" />
                <msh:tableColumn isCalcAmount="true" columnName="Визит" property="11" addParam="&tView=PERSONALVisit&tViewName=по визитам ЛИЧНО" />
                <msh:tableColumn isCalcAmount="true" columnName="Запись" property="12" addParam="&tView=MEDVOX&tViewName=по записям через РОБОТА" />
                <msh:tableColumn isCalcAmount="true" columnName="Визит" property="13" addParam="&tView=MEDVOXVisit&tViewName=по визитам через РОБОТА" />
            </msh:table>
        </msh:sectionContent>
    </msh:section>
        <%
                }
                else if (request.getParameter("tView")!=null && !request.getParameter("tView").equals("")) {
                    String tView=request.getParameter("tView");
                    if (tView.contains("Visit"))
                            sqlAddNew.append(" and wct.medcase_id is not null ");
                    if (!tView.equals("All")) {
                        tView=tView.replace("Visit","");
                        if (!tView.equals("")) sqlAddNew.append(" and wr.code='"+tView+"'");
                    }
                    request.setAttribute ("sqlAddNew", sqlAddNew.toString());
                    %>
        <msh:section>
        <msh:sectionTitle>
            <ecom:webQuery isReportBase="true" name="leanClinicReportt_pats" nameFldSql="leanClinicReport_pats_sql" nativeSql="
        select distinct wct.id as wctId,wct.prepatientinfo as info,coalesce(wct.prepatient_id,patvis.id) as patId
        ,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename||' гр '||to_char(pat.birthday,'dd.mm.yyyy') as fio
        ,wct.medcase_id as vis
        ,case when wp.id is not null then vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename
        else  vwf.name end as doctor
        ,ml.name as lpuname
        from workcalendartime wct
        left join secuser su on su.login=wct.createprerecord
        left join patient pat on pat.id=wct.prepatient_id
        left join patientfond pf on pat.patientfond_id=pf.id
        left join mislpu lpu on pf.LpuAttached=lpu.codef
        left join vocwayofrecord wr on wr.id=wct.wayofrecord_id
        left join workcalendarday wcd on wcd.id=wct.workcalendarday_id
        left join medcase vis on vis.id=wct.medcase_id
        left join patient patvis on patvis.id=vis.patient_id
        left join workcalendar wc on wc.id=wcd.workcalendar_id
        left join workfunction wf on wf.id=wc.workfunction_id
        left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
        left join Worker w on w.id=wf.worker_id
        left join Patient wp on wp.id=w.person_id
        left join mislpu ml on ml.id=coalesce(wf.lpu_id,w.lpu_id)
        where ${dateSql} between to_date('${dateBegin}','dd.MM.yyyy') and to_date('${dateEnd}','dd.MM.yyyy')
        ${sqlAddNew} and case when ${param.lpuId}!=0 then lpu.id=${param.lpuId} else lpu.id is null end
        and (pat.id is not null or wct.prepatient_id is not null or wct.prepatientinfo is not null)
        and (wct.isdeleted is null or wct.isdeleted=false)"
                           />
            <form action="void" method="post" target="_blank">
                Результат ${tViewName} за период с ${param.dateBegin} по ${dateEnd} (${param.lpuName}).
            </form>
        </msh:sectionTitle>
        <msh:sectionContent>
            <msh:table printToExcelButton="Сохранить в excel" name="leanClinicReportt_pats" cellFunction="true"
                       action="javascript:void(0)"
                       idField="1">
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="Предварительная информация о пациенте" property="2" />
                <msh:tableColumn columnName="Информация о пациенте" property="4" />
                <msh:tableColumn columnName="Врач" property="6" />
                <msh:tableColumn columnName="Отделение" property="7" />
                <msh:tableButton buttonFunction="viewPatient" property="3" buttonShortName="Пациент"/>
                <msh:tableButton buttonFunction="viewVisit" property="5" buttonShortName="Визит"/>
            </msh:table>
        </msh:sectionContent>
        </msh:section>
        <%
                }
            }
        %>
    </tiles:put>
    <tiles:put name="javascript" type="string">
        <script type="text/javascript">
            checkFieldUpdate('typeDate','${typeDate}',1) ;
            checkFieldUpdate('typeRemote','${typeRemote}',1) ;

            function checkFieldUpdate(aField,aValue,aDefault) {

                eval('var chk =  document.forms[0].'+aField) ;
                var max = chk.length ;
                if ((+aValue)>max) {
                    chk[+aDefault-1].checked='checked' ;
                } else {
                    chk[+aValue-1].checked='checked' ;
                }
            }
            function viewPatient(id) {
                if (id!='') window.open('entityView-mis_patient.do?id='+id);
                else showToastMessage("Есть только предварительная информация по пациенту!",null,true);
            }
            function viewVisit(id) {
                if (id!='') window.open('entityView-smo_visit.do?id='+id);
                else showToastMessage("Визит пока не был оформлен, есть только предварительная запись!",null,true);
            }
        </script>
    </tiles:put>
</tiles:insert>