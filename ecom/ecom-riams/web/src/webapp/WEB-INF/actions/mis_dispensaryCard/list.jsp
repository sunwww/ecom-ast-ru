<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib prefix="ecom" uri="http://www.ecom-ast.ru/tags/ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

    <tiles:put name='title' type='string'>
            <ecom:titleTrail beginForm="mis_patientForm" mainMenu="Patient" title="Карты диспансерного наблюдения" />
    </tiles:put>

    <tiles:put name='side' type='string'>
        <msh:sideMenu title="Добавить">
             <msh:sideLink params="id" action="/entityParentPrepareCreate-mis_dispensaryCard" name="Карту Д учета"  title="Добавить карту Д учета" roles="/Policy/Mis/Patient/Dispensary/Create" />
        </msh:sideMenu>
        <msh:sideMenu>
           
        </msh:sideMenu>
    </tiles:put>

    <tiles:put name='body' type='string' >
        <ecom:webQuery name="dlist"
                       nativeSql="select d.id, d.startdate, d.finishdate, mkb.code, vwf.name||' '||wpat.lastname
                    , vde.name
                    ,case when d.finishdate is not null then 'color: red' else 'color: green' end as styleRow
                    from dispensarycard d
                    left join vocidc10 mkb on mkb.id=d. diagnosis_id
                    left join workfunction wf on wf.id=d.workfunction_id
                    left join worker w on w.id=wf.worker_id
                    left join patient wpat on wpat.id=w.person_id
                    left join vocworkfunction vwf on vwf.id=wf.workfunction_id
                    left join vocdispensaryend vde on vde.id=d.endreason_id
                    where d.patient_id=${param.id}
                    order by d.finishdate desc, d.startdate "/>
        <msh:table name="dlist" action="entityView-mis_dispensaryCard.do" idField="1" styleRow="7">
            <msh:tableColumn columnName="Дата постановки на учет" property="2" />
            <msh:tableColumn columnName="Диагноз" property="4" />
            <msh:tableColumn columnName="Врач установивший наблюдение" property="5" />
            <msh:tableColumn columnName="Дата снятия с Д учета" property="3" />
            <msh:tableColumn columnName="Причина снятия с Д учета" property="6" />
        </msh:table>
    </tiles:put>
    
    <tiles:put name='javascript' type='string'>
    <script type='text/javascript'>

    </script>
</tiles:put>
</tiles:insert>