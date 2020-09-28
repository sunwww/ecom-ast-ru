<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <msh:title mainMenu="StacJournal" title="" />
    </tiles:put>
    <tiles:put name="side" type="string">

    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:section title="Предварительные госпитализации по пациенту">
            <msh:sectionContent>
                <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
                    select pre.id
                    ,pre.dateFrom
                    ,ml.name as deo
                    ,vbt.name as bedType
                    ,mkb.code||' '||mkb.name as diag
                    , vwf.name ||' '||wpat.lastname as а6_doc
                    from workcalendarhospitalbed pre
                    left join mislpu ml on ml.id=pre.department_id
                    left join vocbedtype vbt on vbt.id=pre.bedtype_id
                    left join vocidc10 mkb on mkb.id=pre.idc10_id
                    left join workfunction wf on wf.id=pre.workfunction_id
                    left join worker w on w.id=wf.worker_id
                    left join vocworkfunction vwf on vwf.id=wf.workfunction_id
                    left join patient wpat on wpat.id=w.person_id
                    where pre.patient_id=${param.patient}
                    and pre.dateFrom is not null
                    order by pre.dateFrom
      "/>
                <msh:table name="list" action="entityView-smo_planHospitalByVisit.do" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="Дата пред. госпитализации" property="2" />
                    <msh:tableColumn columnName="Отделение" property="3" />
                    <msh:tableColumn columnName="Профиль коек" property="4" />
                    <msh:tableColumn columnName="Диагноз" property="5" />
                    <msh:tableColumn columnName="Кто создал" property="6" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
    </tiles:put>
</tiles:insert>
