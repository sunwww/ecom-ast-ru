<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <ecom:titleTrail beginForm="mis_medCaseForm" mainMenu="Patient" title="Список всех услуг" guid="40efbd1b-4177-47a8-9aad-1971732f3f98" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:sideMenu guid="helloSideMenu-123" title="Добавить">
      <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_medService" name="Добавить услугу" title="Добавить услугу" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/MedService/Create" key="ALT+N" />
    </msh:sideMenu>
  </tiles:put>
  <tiles:put name="body" type="string">
    <ecom:webQuery name="allInfo" nativeSql="select sm.id,sm.dateStart,sm.timeExecute, sm.medService_id,vms.name as vmsname, sm.medServiceAmount, sm.workFunctionExecute_id,vwf.name||' ' ||wp.lastname||' '||wp.firstname||' '||wp.middlename, p.record, case when parent.DTYPE='HospitalMedCase' then 'Приемное отделение' when parent.DTYPE='DepartmentMedCase' then d.name else '' end from medcase as sm  left join diary p on p.medCase_id=sm.id left join medservice vms on vms.id=sm.medService_id left join workfunction wf on wf.id=sm.workFunctionExecute_id left join vocworkfunction vwf on vwf.id=wf.workFunction_id left join worker w on w.id=wf.worker_id left join patient wp on wp.id=w.person_id left join Medcase parent on parent.id=sm.parent_id left join MisLpu d on d.id=parent.department_id where ('DepartmentMedCase'=(select DTYPE from medcase where id=${param.id }) and  (sm.parent_id in (select id from medcase  where  parent_id=(select parent_id from medcase where id=${param.id } )) or sm.parent_id=(select parent_id from medcase where id=${param.id } )) and sm.DTYPE='ServiceMedCase' ) or ('HospitalMedCase'=(select DTYPE from medcase where id=${param.id }) and  (sm.parent_id in (select id from medcase  where  parent_id=${param.id}) or sm.parent_id=${param.id }) and sm.DTYPE='ServiceMedCase' ) "/>
  <msh:tableEmpty name="allInfo">
    <msh:table name="list" action="entityParentView-smo_medService.do" idField="id" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn columnName="Дата" property="dateStart" guid="0694f6a7-ed40-4ebf-a274-1efd6901cfe4" />
      <msh:tableColumn columnName="Время" property="timeExecute" guid="6682eeef-105f-43a0-be61-30a865f27972" />
      <msh:tableColumn columnName="Мед.услуга" property="medServiceInfo" guid="f2-4978-b31f-5e54ff2e45bd" />
      <msh:tableColumn columnName="Кол-во" property="medServiceAmount" guid="ff-5e54ff2e45bd" />
      <msh:tableColumn columnName="Специалист" property="workFunctionExecuteInfo" guid="f31b12-3392-4978-b31f-5e54ff2e45bd" />
    </msh:table>
    </msh:tableEmpty>
    <msh:tableNotEmpty name="allInfo">
	    <msh:section title="Просмотр всех услуг по СЛС, включая все СЛО">
	    	<msh:ifInRole roles="/Policy/Mis/MedCase/MedService/Print">
		    	<msh:table name="allInfo" action="entityParentView-smo_medService.do" idField="1">
		    		<msh:tableColumn columnName="#" property="sn"/>
		    		<msh:tableColumn columnName="Отделение" property="10"/>
		    		<msh:tableColumn columnName="Дата" property="2"/>
		    		<msh:tableColumn columnName="Время" property="3"/>
		    		<msh:tableColumn columnName="Мед.услуга" property="5"/>
		    		<msh:tableColumn columnName="Кол-во" property="6"/>
		    		<msh:tableColumn columnName="Специалист" property="8"/>
		    		<msh:tableColumn columnName="Заключение" property="9" cssClass="preCell"/>
		    	</msh:table>
	    	</msh:ifInRole>
	    	<msh:ifNotInRole roles="/Policy/Mis/MedCase/MedService/Print">
		    	<msh:table name="allInfo" action="entityParentView-smo_medService.do" idField="1">
		    		<msh:tableColumn columnName="#" property="sn"/>
		    		<msh:tableColumn columnName="Отделение" property="10"/>
		    		<msh:tableColumn columnName="Дата" property="2"/>
		    		<msh:tableColumn columnName="Время" property="3"/>
		    		<msh:tableColumn columnName="Мед.услуга" property="5"/>
		    		<msh:tableColumn columnName="Кол-во" property="6"/>
		    		<msh:tableColumn columnName="Специалист" property="8"/>
		    		<msh:tableColumn columnName="Заключение" property="9" cssClass="preCell"/>
		    	</msh:table>
	    	</msh:ifNotInRole>
	    </msh:section>
    </msh:tableNotEmpty>
    
    <ecom:webQuery name="allSurgOper" nativeSql="select so.id,so.operationDate,so.operationTime,vo.name as voname,case when parent.DTYPE='HospitalMedCase' then 'Приемное отделение' when parent.DTYPE='DepartmentMedCase' then d.name else '' end from SurgicalOperation as so left join MedService vo on vo.id=so.medService_id left join medcase parent on parent.id=so.medcase_id left join MisLpu d on d.id=parent.department_id where ('DepartmentMedCase'=(select DTYPE from medcase where id=${param.id}) and  (so.medCase_id in (select id from medcase  where  parent_id=(select parent_id from medcase where id=${param.id} )) or so.medCase_id=(select parent_id from medcase where id=${param.id} )) ) or ('HospitalMedCase'=(select DTYPE from medcase where id=${param.id}) and  (so.medCase_id in (select id from medcase  where  parent_id=${param.id}) or so.medCase_id=${param.id}) )"/>
    <msh:tableNotEmpty name="allSurgOper">
	    <msh:section title="Просмотр всех услуг типа хир.операция по СЛС, включая все СЛО">
	    	<msh:table name="allSurgOper" action="entityParentView-stac_surOperation.do" idField="1">
	    		<msh:tableColumn columnName="#" property="sn"/>
	    		<msh:tableColumn columnName="Отделение" property="5"/>
	    		<msh:tableColumn columnName="Дата" property="2"/>
	    		<msh:tableColumn columnName="Время" property="3"/>
	    		<msh:tableColumn columnName="Мед.услуга" property="4"/>
	    	</msh:table>
	    </msh:section>
    </msh:tableNotEmpty>
  </tiles:put>
</tiles:insert>

