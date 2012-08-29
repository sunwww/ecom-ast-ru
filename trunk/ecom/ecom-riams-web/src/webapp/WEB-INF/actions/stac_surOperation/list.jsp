<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="StacJournal" title="Список всех хир.операций в ССЛ" guid="d65a8cc3-3360-43fb-bac7-0d7dde1057ae" />
  </tiles:put>
  <tiles:put name="side" type="string" />
  <tiles:put name="body" type="string">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View">
          <ecom:webQuery name="allSurgOper" nativeSql="select so.id
          ,to_char(so.operationDate,'dd.mm.yyyy')||' '||coalesce(cast(so.operationTime as varchar(5)),'') as soperationTime
          ,ms.code||' '||ms.name as voname
          , d.name as whoIs  
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
          ,substring(so.operationText,1,100)||' ...' as operationText
          from SurgicalOperation as so 
          left join MedService ms on ms.id=so.medService_id
          left join medcase parent on parent.id=so.medcase_id 
          left join MisLpu d on d.id=so.department_id 
          left join WorkFunction wf on wf.id=so.surgeon_id
          left join Worker w on w.id=wf.worker_id
          left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
          left join Patient wp on wp.id=w.person_id
          where  
           so.medCase_id=${param.id}
          order by so.operationDate
          "/>
    <msh:tableNotEmpty name="allSurgOper">
	    <msh:section title="Хирургические операции " createUrl="entityParentPrepareCreate-stac_surOperation.do?id=${param.id}"
	    createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
	    	<msh:table viewUrl="entityShortView-stac_surOperation.do"
	    	editUrl="entityParentEdit-stac_surOperation.do"  
	    	name="allSurgOper" action="entityParentView-stac_surOperation.do" idField="1">
	    		<msh:tableColumn columnName="#" property="sn"/>
	    		<msh:tableColumn columnName="Дата и время" property="2"/>
	    		<msh:tableColumn columnName="Операция" property="3"/>
	    		<msh:tableColumn columnName="Хирург" property="5"/>
	    		<msh:tableColumn cssClass="preCell" property="6" columnName="Протокол операции"/>
	    		<msh:tableColumn columnName="Отделение" property="4"/>
	    	</msh:table>
	    </msh:section>
    </msh:tableNotEmpty>    
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить" guid="73fe6c01-daa2-49fb-af12-20402ea5695b">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_surOperation" name="Хир. операцию" title="Добавить хирургическую операцию в случай стационарного лечения" guid="1eb84508-a862-4de8-b2a9-c447c2cf7cd1" />
    </msh:sideMenu>
    
  </tiles:put>
</tiles:insert>

