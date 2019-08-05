<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title guid="helloItle-123" mainMenu="Journals" title="Отчет по листам наблюдений"></msh:title>
    </tiles:put>
    <tiles:put name="side" type="string">
        <tags:observSheet name="observSheet"/>
        <tags:vocObservRes name="vocObservRes"/>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:form action="/riams_edkc_patientList.do" defaultField="dateBegin" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
            <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
                <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
                    <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
                </msh:row>
                <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
                    <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
                    <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
                    <td>
                        <input type="submit" value="Найти" />
                    </td>
                </msh:row>
            </msh:panel>
        </msh:form>
        <script type='text/javascript'>
            function showPatient(id) {
                if (id)
                    window.location.href='entityView-mis_patient.do?id='+id;
            }

            function showObsSheet(id) {
                if (id)
                    window.location.href='entityView-edkcObsSheet.do?id='+id;
            }

        </script>
        <%
            String date = (String)request.getParameter("dateBegin") ;
            String dateEnd = (String)request.getParameter("dateEnd") ;

            if (dateEnd==null || dateEnd.equals("")) dateEnd=date ;
            request.setAttribute("dateBegin", date) ;
            request.setAttribute("dateEnd", dateEnd) ;
            if (date!=null && !date.equals("")) {
        %>

        <msh:section>
            <msh:sectionTitle>Результаты поиска за период с ${dateBegin} по ${dateEnd}.</msh:sectionTitle>
        </msh:section>
        <msh:section>
            <msh:sectionContent>
                <ecom:webQuery isReportBase="true" name="journal_patientList" nativeSql="
                select o.patient_id as patId,o.id as oId
                ,pat.lastname||' '||pat.firstname||' '||pat.middlename||' г.р. '||to_char(pat.birthday,'dd.mm.yyyy') as fio
                , to_char(o.startdate,'dd.mm.yyyy') as std
                ,vwf.name||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as  wp
                ,to_char(o.finishdate,'dd.mm.yyyy') as fd
                ,vwf2.name||' '||wpat2.lastname||' '||wpat2.firstname||' '||wpat2.middlename as  wp2
                from  observationsheet o
                left join workfunction wf on wf.id=o.specialiststart_id
                left join workfunction wf2 on wf2.id=o.specialistfin_id
                left join worker w on w.id=wf.worker_id
                left join worker w2 on w2.id=wf2.worker_id
                left join vocworkfunction vwf on vwf.id=wf.workfunction_id
                left join vocworkfunction vwf2 on vwf2.id=wf2.workfunction_id
                left join patient wpat on wpat.id=w.person_id
                left join patient wpat2 on wpat2.id=w2.person_id
                left join patient pat on pat.id=o.patient_id
                where o.patient_id is not null
                and o.startDate between to_date('${dateBegin}','dd.mm.yyyy') and to_date('${dateEnd}','dd.mm.yyyy')" />
                <msh:table printToExcelButton="сохранить в excel" name="journal_patientList" action="/javascript:void()" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
                    <msh:tableColumn property="sn" columnName="#"/>
                    <msh:tableColumn property="3" columnName="Пациент"/>
                    <msh:tableColumn property="4" columnName="Дата установки"/>
                    <msh:tableColumn property="5" columnName="Открыл лист наблюдения"/>
                    <msh:tableColumn property="6" columnName="Дата снятия"/>
                    <msh:tableColumn property="7" columnName="Закрыл лист наблюдения"/>
                    <msh:tableButton property="1" addParam="this" buttonFunction="showPatient" buttonName="Показать пациента" buttonShortName="Пациент"/>
                    <msh:tableButton property="2" addParam="this" buttonFunction="showObsSheet" buttonName="Показать ЛН" buttonShortName="Лист набл."/>
                </msh:table>
            </msh:sectionContent>
        </msh:section>
        <%    } else {%>
        <i>Нет данных </i>
        <% }   %>

    </tiles:put>
</tiles:insert>