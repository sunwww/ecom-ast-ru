<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="StacJournal" title="Список всех перевязок в ССЛ" guid="d65a8cc3-3360-43fb-bac7-0d7dde1057ae" />
    </tiles:put>
    <tiles:put name="side" type="string" />
    <tiles:put name="body" type="string">
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/View">
            <ecom:webQuery name="allBandage" nativeSql="select so.id,
            to_char(so.thestartdate,'dd.mm.yyyy')||' '||coalesce(cast(so.thestarttime as varchar(5)),'') as datetime,
            a.duration,vam.name as vamname,va.name as vaname,substring(so.theText,1,100)||' ...' as text from bandage so
            left join MedService ms on ms.id=so.medService_id
            left join medcase parent on parent.id=so.medcase_id
            left join MisLpu d on d.id=so.thedepartment_id
            left join WorkFunction wf on wf.id=so.thesurgeon_id
            left join Worker w on w.id=wf.worker_id
            left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            left join Patient wp on wp.id=w.person_id
            left join anesthesia a on a.thebandage_id=so.id
            left join vocanesthesiamethod vam on vam.id=a.method_id
            left join vocanesthesia va on va.id=a.type_id
          where
           so.medCase_id=${param.id}
          order by so.thestartdate
          "/>
            <msh:section title="Перевязки " createUrl="entityParentPrepareCreate-stac_bandage.do?id=${param.id}"
                         createRoles="/Policy/Mis/MedCase/Stac/Ssl/Bandage/Create">
                <msh:table viewUrl="entityShortView-stac_bandage.do"
                           editUrl="entityParentEdit-stac_bandage.do"
                           name="allBandage" action="entityParentView-stac_bandage.do" idField="1">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата и время" property="2"/>
                    <msh:tableColumn columnName="Длительность" property="3"/>
                    <msh:tableColumn columnName="Метод" property="4"/>
                    <msh:tableColumn columnName="Тип" property="5"/>
                    <msh:tableColumn columnName="Протокол перевязки" property="6"/>
                </msh:table>
            </msh:section>
        </msh:ifInRole>
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu title="Добавить" guid="73fe6c01-daa2-49fb-af12-20402ea5695b">
            <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_bandage" name="Перевязку" title="Добавить перевязку в случай стационарного лечения" guid="1eb84508-a862-4de8-b2a9-c447c2cf7cd1" />
        </msh:sideMenu>
    </tiles:put>
    

</tiles:insert>