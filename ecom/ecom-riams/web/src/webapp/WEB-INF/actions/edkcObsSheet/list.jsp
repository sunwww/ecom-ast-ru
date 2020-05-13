<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Expert2" title="Листы наблюдения" beginForm="mis_patientForm" />
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:section title="Список листов наблюдения">
            <ecom:webQuery name="list" nativeSql="
            select o.id as oId, to_char(o.startdate,'dd.mm.yyyy') as std
            ,vwf.name||' '||wpat.lastname||' '||wpat.firstname||' '||wpat.middlename as  wp
            ,to_char(o.finishdate,'dd.mm.yyyy') as fd
            ,vwf2.name||' '||wpat2.lastname||' '||wpat2.firstname||' '||wpat2.middlename as  wp2
            , case when
            count(case when o.finishdate is null then '1' else null end)>0
            then null
            else
            case when max(o.finishdate)=min(o.startdate) then '1' else max(o.finishdate)-min(o.startdate)+1 end end as dlit
            ,vr.name as vrn
            from  observationsheet o
            left join workfunction wf on wf.id=o.specialiststart_id
            left join workfunction wf2 on wf2.id=o.specialistfin_id
            left join worker w on w.id=wf.worker_id
            left join worker w2 on w2.id=wf2.worker_id
            left join vocworkfunction vwf on vwf.id=wf.workfunction_id
            left join vocworkfunction vwf2 on vwf2.id=wf2.workfunction_id
            left join patient wpat on wpat.id=w.person_id
            left join patient wpat2 on wpat2.id=w2.person_id
            left join vocobservationresult vr on vr.id=o.observresult_id
            where o.patient_id= '${param.id}'
            group by o.id,vwf.name,vwf2.name,wpat.id,wpat2.id,vr.name
            order by o.id
  	"/>
            <msh:table name="list" action="entityView-edkcObsSheet.do" idField="1" >
                <msh:tableColumn columnName="#" property="sn" />
                <msh:tableColumn columnName="Дата открытия" property="2" />
                <msh:tableColumn columnName="Открыл" property="3" />
                <msh:tableColumn columnName="Дата закрытия" property="4" />
                <msh:tableColumn columnName="Закрыл" property="5" />
                <msh:tableColumn columnName="Длительность наблюдения" property="6" />
                <msh:tableColumn columnName="Исход наблюдения" property="7" />
            </msh:table>
        </msh:section>
    </tiles:put>
</tiles:insert>