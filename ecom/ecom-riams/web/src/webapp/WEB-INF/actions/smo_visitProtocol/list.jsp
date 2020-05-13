<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список всех дневников" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitProtocol" name="Дневник специалиста" title="Добавить дневник специалиста" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+N" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
        <msh:section title="Дневники специалистов. 
        <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'> Добавить новый дневник специалиста</a>
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}'> Печать дневников специалистов </a>
        ">
          <%--
<ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" />
          <msh:table hideTitle="true" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do">
            <msh:tableColumn columnName="Дата" property="dateRegistration" cssClass="preCell" />
            <msh:tableColumn columnName="Время" property="timeRegistration" cssClass="preCell" />
            <msh:tableColumn columnName="Запись" property="record" cssClass="preCell" />
            <msh:tableColumn columnName="Специалист" property="specialistInfo" cssClass="preCell" />
          </msh:table>--%>
      <ecom:webQuery name="protocols"  nativeSql="
      select d.id, d.dateRegistration, d.timeRegistration, d.record 
     , vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename
      from Diary as d
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            	where d.DTYPE='Protocol' and d.medCase_id='${param.id}' 
            	order by d.dateRegistration,d.timeRegistration"/>

          <msh:table hideTitle="false" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Протокол" property="4" cssClass="preCell"/>
                    <msh:tableColumn columnName="Специалист" property="5"/>
                </msh:table>
          
        </msh:section>
  </tiles:put>
</tiles:insert>

