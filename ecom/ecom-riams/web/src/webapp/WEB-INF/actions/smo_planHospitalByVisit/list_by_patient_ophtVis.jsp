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
        <msh:section title="Планирование введения ингибиторов ангиогенеза пациента в визите">
            <msh:sectionContent>
                <ecom:webQuery name="list" nameFldSql="list_sql" nativeSql="
                   select wct.id as id
                    ,to_char(wct.dateokt,'dd.mm.yyyy') as dateokt
                    ,e.name as eye
                    ,wct.comment as cmnt
                    ,vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as creator
                    ,to_char(wct.createdate,'dd.mm.yyyy')||' '||to_char(wct.createTime,'HH24:MI') as dt
                    from WorkCalendarHospitalBed wct
                    left join patient pat on wct.patient_id=pat.id
                    left join voceye e on e.id=wct.eye_id
                    left join workfunction wf on wf.id=wct.workfunction_id
                    left join MedCase mc on mc.id=wct.visit_id
                    left join vocworkFunction vwf on vwf.id=wf.workFunction_id
                    left join worker w on w.id = wf.worker_id
                    left join patient wp on wp.id=w.person_id
                    where mc.id=${param.vis}
                    and wct.dtype='PlanOphtHospital'
                    order by wct.createdate
      "/>
                <msh:table name="list" action="entityView-stac_planOphtHospitalByVisit.do" idField="1" noDataMessage="Не найдено">
                    <msh:tableColumn columnName="#" property="sn" />
                    <msh:tableColumn columnName="Дата ОКТ" property="2" />
                    <msh:tableColumn columnName="Глаз" property="3" />
                    <msh:tableColumn columnName="Замечания" property="4" />
                    <msh:tableColumn columnName="Создал" property="5" />
                    <msh:tableColumn columnName="Дата и время создания" property="6" />
                </msh:table>
            </msh:sectionContent>
        </msh:section>
    </tiles:put>
</tiles:insert>