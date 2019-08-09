<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

    <tiles:put name="title" type="string">
        <ecom:titleTrail mainMenu="Patient" title="Протоколы ЕДКЦ" beginForm="edkcObsSheetForm" guid="b6v61-1e0b-4ebd-9f58-bdb45bd6" />
    </tiles:put>
    <tiles:put name="side" type="string">
        <msh:sideMenu guid="0d13c843-c26a-4ae2-ae97-d61b44618bae" title="Добавить">
            <msh:sideLink key="ALT+2" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_1" name="Протокол консультации" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
            <msh:sideLink key="ALT+3" action="/entityParentPrepareCreate-edkcProtocol.do?id=${param.id}&type=edkc_ev" name="Протокол ежесуточного наблюдения" guid="dc51a550-1158-41b8-89a4-bf3a90ffeedb" roles="/Policy/Mis/Patient/MobileAnestResNeo/ObservationSheet/Create" />
        </msh:sideMenu>
    </tiles:put>
    <tiles:put name="body" type="string">
        <msh:section title="Список протоколов ЕДКЦ">
            <ecom:webQuery name="protocols"  nativeSql="
      select d.id, to_char(d.dateRegistration,'dd.mm.yyyy'), d.timeRegistration, d.record
     , vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doc
     ,vtp.name as type
      from Diary as d
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
      left join VocTypeProtocol vtp on vtp.id=d.type_id
            	where d.DTYPE='Protocol' and d.obssheet_id='${param.id}'
            	order by d.dateRegistration,d.timeRegistration"/>

            <msh:table hideTitle="false" idField="1" name="protocols" action="entityParentView-edkcProtocol.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
                <msh:tableColumn columnName="#" property="sn"/>
                <msh:tableColumn columnName="Дата" property="2" width="5%"/>
                <msh:tableColumn columnName="Время" property="3" width="5%"/>
                <msh:tableColumn columnName="Протокол" property="4" cssClass="preCell" width="70%"/>
                <msh:tableColumn columnName="Специалист" property="5" width="10%"/>
                <msh:tableColumn columnName="Тип" property="6" width="10%"/>
            </msh:table>
        </msh:section>
    </tiles:put>
</tiles:insert>