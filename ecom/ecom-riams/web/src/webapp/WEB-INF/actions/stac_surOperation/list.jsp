<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="StacJournal" title="Список всех хир.операций в ССЛ" />
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
          ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fioan
          from SurgicalOperation as so
          left join MedService ms on ms.id=so.medService_id
          left join medcase parent on parent.id=so.medcase_id
           left join anesthesia a on a.surgicaloperation_id=so.id
          left join MisLpu d on d.id=so.department_id
          left join WorkFunction wf on wf.id=so.surgeon_id
          left join Worker w on w.id=wf.worker_id
          left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
          left join Patient wp on wp.id=w.person_id
          left join workfunction wfan on wfan.id=a.anesthesist_id
left join worker wan on wan.id=wfan.worker_id
left join Patient pat on pat.id=wan.person_id
          where  
           so.medCase_id=${param.id}
          order by so.operationDate
          "/>
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
                <msh:tableColumn columnName="Анестезиолог" property="7"/>
	    	</msh:table>
	    </msh:section>
    </msh:ifInRole>
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-stac_surOperation" name="Хир. операцию" title="Добавить хирургическую операцию в случай стационарного лечения" />
    </msh:sideMenu>
    
  </tiles:put>
</tiles:insert>

